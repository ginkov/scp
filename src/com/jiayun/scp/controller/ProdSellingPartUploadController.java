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
import com.jiayun.scp.model.ProdC1;
import com.jiayun.scp.model.ProdC2;
import com.jiayun.scp.model.ProdPart;
import com.jiayun.scp.model.ProdPartItem;
import com.jiayun.scp.model.ProdSelling;

@Controller
@RequestMapping("/product/part_upload")
public class ProdSellingPartUploadController {
	private static final String REDIR_PART_LIST="redirect:/product/part/list";

	// 列出 Excel 头部所有合法的字符项.
	private static Map<String, String> validHeaderItems = new HashMap<>();

	// 列出 Excel 头部必须的合法字符项.
	private static String[] requiredFeilds = {"c2", "name", "price"};

	// 用于解析链路配置文件头部的标识位.
	private Map<String, Integer> posHeaderItems =  new HashMap<>();
	
	// Invalid row list
	private ArrayList<String> invalidRows;
	
	@Autowired
	private DaoService<ProdSelling> pss;
	
	@Autowired
	private DaoService<ProdPart> pps;
	
	@Autowired
	private DaoService<ProdC1> pc1s;
	
	@Autowired
	private DaoService<ProdC2> pc2s;
	
	static {
		validHeaderItems.put("类型",	 	"c1");
		validHeaderItems.put("类别",	 	"c1");
		validHeaderItems.put("子类", 	"c2");
		validHeaderItems.put("种类", 	"c2");
		validHeaderItems.put("名称",		"name");
		validHeaderItems.put("部件",		"name");
		validHeaderItems.put("价格", 	"price");
		validHeaderItems.put("标价", 	"price");
		validHeaderItems.put("上线日期", 	"onlineDate");
		validHeaderItems.put("描述", 	"description");
		validHeaderItems.put("摘要", 	"description");
	}
	
	@RequestMapping("/select")
	public String upload(Model model) {
		model.addAttribute("pageTitle","批量上传产品部件清单");
		model.addAttribute("pageContent", "product/PartUpload");
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
                	ProdSelling part = parseRow(row);
                	if(part!=null) {
                		pss.save(part);
                		++ count;
                	}
                }
            }
            
            workbook.close();
            ra.addFlashAttribute("err",  "共导入 "+count+" 条记录");
		} catch (IOException e) {
			ra.addFlashAttribute("err", "打开上传文件 "+upload.getOriginalFilename()+" 时出错");
		}
		return REDIR_PART_LIST;
	}
	
	// 解析一行是否为头部
	private boolean parseHeader(Row row) {
		
		// 要判断是否有一个合理的头部，
		/* 对 Excel 表的要求:
			 * 0. 类型 - 可选
			 * 1. 子类 - 必须
			 * 2. 名称 - 必须
			 * 3. 价格 - 必须
			 * 4. 上线日期 - 可选
			 * 5. 描述 - 可选
		*/
        
		// 对标题字段位置进行初始化
		// 对应的项在 Excel 中列中的位置, -1 代表没有该列.
		posHeaderItems.put("c1", 	-1);
		posHeaderItems.put("c2", 	-1);
		posHeaderItems.put("name", 	-1);
		posHeaderItems.put("price", 		-1);
		posHeaderItems.put("onlineDate", 	-1);
		posHeaderItems.put("description", 	-1);

        //For each row, iterate through each columns
        Iterator<Cell> cellIterator = row.cellIterator();
        int pos = 0;
        while(cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            switch(cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                	// 检查  Cell 的值是不是在标准字串里面
                	String cellString = cell.getStringCellValue();
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

	private ProdSelling parseRow(Row row) {
		
		// 订单中必须有的字段
		String c2Str 	= getCellStringValue(row, posHeaderItems.get("c2"), "");
		String name  	= getCellStringValue(row, posHeaderItems.get("name"), "");
		String priceStr = getCellStringValue(row, posHeaderItems.get("price"), "");
		
		ProdSelling ps;
		double price;
		ProdC2 c2;

		// 三个必要字段均不能为空
		if(c2Str.isEmpty() || name.isEmpty() || priceStr.isEmpty()) {
			addInvalidRow(c2Str, name, priceStr);
			return null;
		}
		else {
			// check for name
			ps = pss.getByName(name);
			if(ps == null) {
				ps = new ProdSelling();
				// check price format 
				try {
					price = Double.parseDouble(priceStr);
				}
				catch(NumberFormatException e) {
					addInvalidRow(c2Str, name, priceStr);
					return null;
				}
				// check for c2
				c2 = pc2s.getByName(c2Str);
				if(c2 == null) {
					addInvalidRow(c2Str, name, priceStr);
					return null;
				}
				ProdPart pp = new ProdPart();
				pp.setC2(c2);
				pp.setListprice(price);
				pp.setName(name);
				
				String description = getCellStringValue(row, posHeaderItems.get("description"), "");
				pp.setDescription(description);
				pps.save(pp);
				
				Date onlineDate = getCellDateValue(row, posHeaderItems.get("onlineDate"));

				ProdPartItem ppi = new ProdPartItem();
				ppi.setPart(pp);
				ppi.setQuantity(1);
				ppi.setSelling(ps);
				
				ps.setC2(c2);
				ps.setDescription(description);
				ps.setListprice(price);
				ps.setName(name);
				ps.setOnlineDate(onlineDate);
				ps.getPartslist().add(ppi);
				
				pss.save(ps);
				return ps;
			}
			else {
				// 同名部件已经存在, 不能加入
				return null;
			}
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
       
