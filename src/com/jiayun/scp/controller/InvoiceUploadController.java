package com.jiayun.scp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.Invoice;
import com.jiayun.scp.model.InvoiceType;

@Controller
@RequestMapping("/finance/invoice_upload")
public class InvoiceUploadController {

//	private static final Logger log = Logger.getLogger(SalesOrderUploadController.class);
	private final String HOME = "redirect:/finance/invoice/list";

	// 列出 Excel 头部所有合法的字符项.
	private static Map<String, String> validHeaderItems = new HashMap<>();

	// 列出 Excel 头部必须的合法字符项.
	private static String[] requiredFeilds = {"date", "sn", "amount"};

	// 用于解析链路配置文件头部的标识位.
	private Map<String, Integer> posHeaderItems =  new HashMap<>();
	
	// Invalid row list
	private ArrayList<String> invalidRows;
	
	@Autowired
	private DaoService<Invoice> invs;

	static {
		validHeaderItems.put("日期",	 	"date");
		validHeaderItems.put("开票日期", 	"date");
		validHeaderItems.put("发票号", 	"sn");
		validHeaderItems.put("开票单位",  "issuer");
		validHeaderItems.put("供货商",   "issuer");
		validHeaderItems.put("金额",		"amount");
		validHeaderItems.put("内容", 	"description");
		validHeaderItems.put("描述", 	"description");
		validHeaderItems.put("是否为原始发票",	"isOriginal");
		validHeaderItems.put("原始发票",	"isOriginal");
		validHeaderItems.put("财务支出类型", "type");
		validHeaderItems.put("财务类型", "type");
		validHeaderItems.put("支出类型", "type");
		validHeaderItems.put("发票类型", "type");
		validHeaderItems.put("类型", "type");
	}

	@RequestMapping("/select")
	public String upload(Model model) {
		model.addAttribute("pageTitle","批量上传发票信息");
		model.addAttribute("pageContent", "finance/InvoiceUpload");
		return "mainpage";
	}
		

	@RequestMapping("/do")
	public String doUpload(@RequestParam MultipartFile upload, RedirectAttributes ra) {
       	try {
			XSSFWorkbook workbook = new XSSFWorkbook(upload.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
        	
            Iterator<Row> rowIterator = sheet.iterator();
            boolean headerFound = false;
            invalidRows = new ArrayList<String>();
            int count = 0;
            
            // 循环每一行
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // 扫描一行是不是一个合格的标题行，如果不是，就继续找头
                if(!headerFound) {
                	// 如是找到头，返回 True，同时设定了每个字段的位置.
                	headerFound = parseHeader(row);
                }
                else {
                	// 找到头了, 根据头中每个字段的位置，解析每一行.
                	Invoice invoice = parseRow(row);
                	if(invoice!=null) {
                		invs.save(invoice);
                		++ count;
                	}
                }
            }
            workbook.close();
            ra.addFlashAttribute("err",  "共导入 "+count+" 条发票信息");
		} catch (IOException e) {
			ra.addFlashAttribute("err", "打开上传文件 "+upload.getOriginalFilename()+" 时出错");
		}
		return HOME;
	} 
	// 解析一行是否为头部
	private boolean parseHeader(Row row) {
		
		// 要判断是否有一个合理的头部，
		/* 对 Excel 表的要求:
			 * 0. 日期 - 必须
			 * 1. 发票号 - 必须
			 * 2. 开票单位 - 可选
			 * 3. 内容 - 可选
			 * 4. 金额    - 必须
			 * 5. 是否为原始发票 - 可选
			 * 6. 财务支出类型 - 可选，只有三类 费用、支出、原材料
		*/
        
		// 对标题字段位置进行初始化
		// 对应的项在 Excel 中列中的位置, -1 代表没有该列.
		posHeaderItems.put("date", 	-1);
		posHeaderItems.put("sn", 	-1);
		posHeaderItems.put("issuer", 	-1);
		posHeaderItems.put("description", 		-1);
		posHeaderItems.put("amount", 	-1);
		posHeaderItems.put("isOriginal", 	-1);
		posHeaderItems.put("type", 	-1);

        //For each row, iterate through each columns
        Iterator<Cell> cellIterator = row.cellIterator();
        int pos = 0;
        while(cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            switch(cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                	// 检查  Cell 的值是不是在标准字串里面
                	String cellString = cell.getStringCellValue().trim();
                	String standardString  = validHeaderItems.get(cellString);
                	if(standardString !=null) {
                		posHeaderItems.put(standardString, pos);
                	}
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                case Cell.CELL_TYPE_NUMERIC:
                    break;
            }
            pos ++;
        }
        
        // 检查这一行是不是一个合法的行
		boolean result = true;
		for(String feild: requiredFeilds) {
			if(posHeaderItems.get(feild) < 0) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	private boolean addInvalidRow(String date, String product, String price) {
		StringBuilder invalidRow = new StringBuilder();
		invalidRow.append(date).append(":").append(product).append(":").append(price);
		if(invalidRow.length()>2) {
			invalidRows.add(invalidRow.toString());
			return true;
		}
		else {
			return false;
		}
	}

	private Invoice parseRow(Row row) {
		
		// 订单中必须有的字段
		Date date = getCellDateValue(row, posHeaderItems.get("date"));
		String sn = getCellStringValue(row, posHeaderItems.get("sn"),  "");
		String amountStr = getCellStringValue(row, posHeaderItems.get("amount"),  "");
		
		double amount;
	
		Invoice invoice = new Invoice();
		
		// 三个必要字段均不能为空
		if(date == null || sn.isEmpty() || amountStr.isEmpty()) {
			addInvalidRow(date.toString(), sn, amountStr);
			return null;
		}
		else {
			// check amount format 
			try {
				amount = Double.parseDouble(amountStr);
			}
			catch(NumberFormatException e) {
				addInvalidRow(date.toString(), sn, amountStr);
				return null;
			}

			invoice.setAmount(amount);
			invoice.setSn(sn);
			invoice.setDate(date);
			
			String description = getCellStringValue(row, posHeaderItems.get("description"), "批量导入的发票");
			invoice.setDescription(description);

			String issuer = getCellStringValue(row, posHeaderItems.get("issuer"), "未知");
			invoice.setIssuer(issuer);
			
			String typeStr = getCellStringValue(row, posHeaderItems.get("type"), "费用");
			if(typeStr.startsWith("原材料")) {
				invoice.setType(InvoiceType.MATERIAL);
			}
			else if(typeStr.startsWith("人工")) {
				invoice.setType(InvoiceType.LABOR);
			}
			else {
				invoice.setType(InvoiceType.EXPENSE);
			}
			
			String isOriginalStr = getCellStringValue(row, posHeaderItems.get("isOriginal"), "False");
			if(isOriginalStr == null || isOriginalStr.isEmpty() || isOriginalStr.startsWith("否")) {
				invoice.setOriginal(false);
			}
			else {
				invoice.setOriginal(true);
			}

			return invoice;
		}
		
	} // parse()

	private String getCellStringValue(Row row, int pos, String defaultVal) {
		String result = defaultVal;
		if(pos < 0) return result;

		Cell cell = row.getCell(pos);
		if(cell != null) {
			switch(cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue().trim();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				result = Boolean.toString(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
//				result = Double.toString(cell.getNumericCellValue());
				result = String.format("%.0f",cell.getNumericCellValue());
				break;
			}
		} else {
//			log.warn("parse input link row error.");
		}
		return result;
	}
	
	private Date getCellDateValue(Row row, int pos) {
		if(pos<0) return null;
		Cell cell = row.getCell(pos);
		return cell.getDateCellValue();
	}

}
