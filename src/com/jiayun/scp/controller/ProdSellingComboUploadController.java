package com.jiayun.scp.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.jiayun.scp.model.ProdC2;
import com.jiayun.scp.model.ProdPart;
import com.jiayun.scp.model.ProdPartItem;
import com.jiayun.scp.model.ProdSelling;

@Controller
@RequestMapping("/product/combo_upload")
public class ProdSellingComboUploadController {
	private static final String REDIR_COMBO_LIST="redirect:/product/combo/list";

	// 列出 Excel 头部所有合法的字符项.
	private static Map<String, String> validHeaderItems = new HashMap<>();

	// 列出 Excel 头部必须的合法字符项.
	private static String[] requiredFeilds = {"name", "part"};

	// 用于解析链路配置文件头部的标识位.
	private Map<String, Integer> posHeaderItems =  new HashMap<>();
	
	// Invalid row list
	private ArrayList<String> invalidRows;
	
	@Autowired
	private DaoService<ProdSelling> pss;
	
	@Autowired
	private DaoService<ProdPart> pps;
	
	@Autowired
	private DaoService<ProdC2> pc2s;
	
	static {
		validHeaderItems.put("子类", 	"c2");
		validHeaderItems.put("种类", 	"c2");
		validHeaderItems.put("名称",		"name");
		validHeaderItems.put("部件",		"part");
		validHeaderItems.put("价格", 	"price");
		validHeaderItems.put("标价", 	"price");
		validHeaderItems.put("上线日期", 	"onlineDate");
		validHeaderItems.put("描述", 	"description");
		validHeaderItems.put("摘要", 	"description");
	}
	
	@RequestMapping("/select")
	public String upload(Model model) {
		model.addAttribute("pageTitle","批量上传产品套装清单");
		model.addAttribute("pageContent", "product/ComboUpload");
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
                	ProdSelling combo = parseRow(row);
                	if(combo!=null) {
                		pss.save(combo);
                		++ count;
                	}
                }
            }
            
            workbook.close();
            ra.addFlashAttribute("err",  "共导入 "+count+" 条记录");
		} catch (IOException e) {
			ra.addFlashAttribute("err", "打开上传文件 "+upload.getOriginalFilename()+" 时出错");
		}
		return REDIR_COMBO_LIST;
	}
	
	// 解析一行是否为头部
	private boolean parseHeader(Row row) {
		
		// 要判断是否有一个合理的头部，
		/* 对 Excel 表的要求:
			 * 0. 类型 - 可选
			 * 1. 名称 - 必须
			 * 2. 部件 - 必须
		*/
        
		// 对标题字段位置进行初始化
		// 对应的项在 Excel 中列中的位置, -1 代表没有该列.
		posHeaderItems.put("c2", 	-1);
		posHeaderItems.put("name", 	-1);
		posHeaderItems.put("part", 	-1);

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
	
	private boolean addInvalidRow(String name, String part) {
		StringBuilder invalidRow = new StringBuilder();
		invalidRow.append(name).append(":").append(part);
		if(invalidRow.length()>1) {
			invalidRows.add(invalidRow.toString());
			return true;
		}
		else {
			return false;
		}
	}

	private ProdSelling parseRow(Row row) {
		
		// 订单中必须有的字段
		String name  	= getCellStringValue(row, posHeaderItems.get("name"), "");
		String partName = getCellStringValue(row, posHeaderItems.get("part"), "");
		
		ProdSelling ps;

		// 两个必要字段均不能为空
		if(name.isEmpty() || partName.isEmpty()) {
			addInvalidRow(name, partName);
			return null;
		}
		else {
			ProdPart pp = pps.getByName(partName);
			if(pp == null) {
				return null;
			}
			else {
				ProdPartItem ppi = new ProdPartItem();
				ppi.setPart(pp);
				ppi.setQuantity(1);
				ps = pss.getByName(name);
				if(ps ==null) {
					ps = new ProdSelling();
					ps.setName(name);
					ps.setC2(pc2s.getByName("套装"));
				}
				ppi.setSelling(ps);
				ps.getPartslist().add(ppi);
				ps.setListprice(ps.getListprice()+pp.getListprice());
			}
			return ps;
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

}
       
