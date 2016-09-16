package com.jiayun.scp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.jiayun.scp.model.ExpRecord;
import com.jiayun.scp.model.ExpT2;
import com.jiayun.scp.model.Invoice;
import com.jiayun.scp.model.Staff;
import com.jiayun.scp.util.ExpRecordUtil;

@Controller
@RequestMapping("/finance/expense_upload")


public class ExpUploadController {
	
	private static final String REDIR_EXP_LIST="redirect:/finance/expense/list";

	// 列出 Excel 头部所有合法的字符项.
	private static Map<String, String> validHeaderItems = new HashMap<>();

	// 列出 Excel 头部必须的合法字符项.
	private static String[] requiredFeilds = {"date", "t2", "expName", "amount"};

	// 用于解析链路配置文件头部的标识位.
	private Map<String, Integer> posHeaderItems =  new HashMap<>();
	
	// Invalid row list
	private ArrayList<String> invalidRows;
	
	@Autowired
	private DaoService<ExpRecord> ers;
	
	@Autowired
	private DaoService<ExpT2> et2s;
	
	@Autowired
	private DaoService<Staff> ss;
	
	@Autowired
	private DaoService<Invoice> invs;
	
	@Autowired
	private ExpRecordUtil erUtil;
	
	private String login;
	
	static {
		validHeaderItems.put("日期",	 	"date");
		validHeaderItems.put("供应商",	"vendor");
		validHeaderItems.put("供货商", 	"vendor");
		validHeaderItems.put("供货单位", 	"vendor");
		validHeaderItems.put("种类", 	"t2");
		validHeaderItems.put("商品名",	"expName");
		validHeaderItems.put("商品",		"expName");
		validHeaderItems.put("货品",		"expName");
		validHeaderItems.put("价格", 	"amount");
		validHeaderItems.put("金额", 	"amount");
		validHeaderItems.put("备注", 	"description");
		validHeaderItems.put("说明", 	"description");
		validHeaderItems.put("描述", 	"description");
		validHeaderItems.put("付款人", 	"payer");
		validHeaderItems.put("支付人", 	"payer");
		validHeaderItems.put("发票号", 	"invNum");
	}
	
	@RequestMapping("/select")
	public String upload(Model model) {
		model.addAttribute("pageTitle","批量上传支出记录");
		model.addAttribute("pageContent", "finance/ExpUpload");
		return "mainpage";
	}

	@RequestMapping("/do")
	public String doUpload(@RequestParam MultipartFile upload, RedirectAttributes ra, HttpServletRequest request) {
		login = request.getUserPrincipal().getName();
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
                	ExpRecord er = parseRow(row);
                	if(er !=null) {
                		ers.save(er);
                		++ count;
                	}
                }
            }
            workbook.close();
//            printInvalidRows();
            ra.addFlashAttribute("err",  "共导入 "+count+" 条记录");
		} catch (IOException e) {
			ra.addFlashAttribute("err", "打开上传文件 "+upload.getOriginalFilename()+" 时出错");
		}
		return REDIR_EXP_LIST;
	}
	
//	private void printInvalidRows() {
//		for(String s: invalidRows) {
//			System.out.println(s);
//		}
//	}
	
	// 解析一行是否为头部
	private boolean parseHeader(Row row) {
		
		// 要判断是否有一个合理的头部，
		/* 对 Excel 表的要求:
			 * 0. 日期 - 必须
			 * 1. 供应商 - 可选
			 * 2. 种类 - 必须
			 * 3. 商品名 - 必须
			 * 4. 价格 - 必须
			 * 5. 付款人 - 可选 
		*/
        
		// 对标题字段位置进行初始化
		// 对应的项在 Excel 中列中的位置, -1 代表没有该列.
		posHeaderItems.put("date", 		-1);
		posHeaderItems.put("vendor", 	-1);
		posHeaderItems.put("t2", 		-1);
		posHeaderItems.put("expName", 	-1);
		posHeaderItems.put("amount", 	-1);
		posHeaderItems.put("payer", 	-1);
		posHeaderItems.put("invNum", 	-1);

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
	
	private boolean addInvalidRow(Date date, String et2, String expName, String price) {
		StringBuilder invalidRow = new StringBuilder();
		String dateStr;
		if (date == null) {
			dateStr = "无日期";
		}
		else {
			dateStr = date.toString();
		}
		invalidRow.append(dateStr).append(":").append(et2).append(":").append(expName).append(":").append(price);
		if(invalidRow.length()>3) {
			invalidRows.add(invalidRow.toString());
			return true;
		}
		else {
			return false;
		}
	}

	private ExpRecord parseRow(Row row) {
		
		// 订单中必须有的字段
		Date date        = getCellDateValue(row, posHeaderItems.get("date"));
		String t2Str 	 = getCellStringValue(row, posHeaderItems.get("t2"), "");
		String expName   = getCellStringValue(row, posHeaderItems.get("expName"), "");
		String priceStr	 = getCellStringValue(row, posHeaderItems.get("amount"), "");
		
		double price;
		ExpT2 t2;

		// 四个必要字段均不能为空
		if(date == null || t2Str.isEmpty() || expName.isEmpty() || priceStr.isEmpty()) {
			addInvalidRow(date, t2Str, expName, priceStr);
			return null;
		}
		else {
			// check for et2
			t2 = et2s.getByName(t2Str);
			if(t2 == null) {
				addInvalidRow(date, t2Str, expName, priceStr);
				return null;
			}
			// check for price
			try {
				price = Double.parseDouble(priceStr);
			}
			catch(NumberFormatException e) {
				addInvalidRow(date, t2Str, expName, priceStr);
				return null;
			}

			ExpRecord er = new ExpRecord();
			er.setDate(date);
			er.setAmount(price);
			er.setExpName(expName);
			er.setT2(t2);
			
			//
			String description = getCellStringValue(row, posHeaderItems.get("description"), "");
			er.setSummary(description);
			
			String supplierName= getCellStringValue(row, posHeaderItems.get("vendor"), "");
			er.setSupplierName(supplierName);
			
			Staff s = ss.getByName(login);
			er.setStaff(s);
			er.setOwner(s);
			
			String invNum = getCellStringValue(row, posHeaderItems.get("invNum"), "");
			
			if( ! invNum.isEmpty()) {
				Invoice invoice = invs.getByUniqueString("sn", invNum);
				if(invoice == null) {
					invoice = new Invoice();
					invoice.copyFrom(er);
				}
				er.getInvoiceSet().add(invoice);
			}
			
			er.setSn(erUtil.genSN());
			return er;
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
