package com.jiayun.scp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.jiayun.scp.model.Customer;
import com.jiayun.scp.model.OrderItem;
import com.jiayun.scp.model.OrgType;
import com.jiayun.scp.model.ProdC1;
import com.jiayun.scp.model.ProdC2;
import com.jiayun.scp.model.ProdPart;
import com.jiayun.scp.model.ProdPartItem;
import com.jiayun.scp.model.ProdSelling;
import com.jiayun.scp.model.SalesOrder;
import com.jiayun.scp.model.UserSaleType;
import com.jiayun.scp.util.SalesOrderUtil;

@Controller
@RequestMapping("/sale/order_upload")
public class SalesOrderUploadController {
	
//	private static final Logger log = Logger.getLogger(SalesOrderUploadController.class);
	private static final String REDIR_ORDER_LIST="redirect:/sale/order/list";

	// 列出 Excel 头部所有合法的字符项.
	private static Map<String, String> validHeaderItems = new HashMap<>();

	// 列出 Excel 头部必须的合法字符项.
	private static String[] requiredFeilds = {"orderDate", "product", "price"};

	// 用于解析链路配置文件头部的标识位.
	private Map<String, Integer> posHeaderItems =  new HashMap<>();
	
	// Invalid row list
	private ArrayList<String> invalidRows;
	
	@Autowired
	private DaoService<Customer> cs;
	@Autowired
	private DaoService<OrgType> ots;
	@Autowired
	private DaoService<SalesOrder> sos;
	@Autowired
	private DaoService<ProdSelling> pss;
	@Autowired
	private DaoService<ProdPartItem> ppis;
	@Autowired
	private DaoService<ProdPart> pps;
	@Autowired
	private DaoService<ProdC1> pc1s;
	@Autowired
	private DaoService<ProdC2> pc2s;
	@Autowired
	private DaoService<UserSaleType> usts;
	@Autowired
	private SalesOrderUtil soUtil;

	static {
		validHeaderItems.put("日期",	 	"orderDate");
		validHeaderItems.put("下单日期", 	"orderDate");
		validHeaderItems.put("订单日期", 	"orderDate");
		validHeaderItems.put("客户",		"customer");
		validHeaderItems.put("用户",		"customer");
		validHeaderItems.put("产品",		"product");
		validHeaderItems.put("优惠类型",	"userSaleType");
		validHeaderItems.put("客户优惠类型", "userSaleType");
		validHeaderItems.put("价格", 	"price");
		validHeaderItems.put("金额", 	"price");
		validHeaderItems.put("说明", 	"description");
		validHeaderItems.put("描述", 	"description");
		validHeaderItems.put("摘要", 	"description");
		validHeaderItems.put("销售人", "salesPerson");
		validHeaderItems.put("销售人员", "salesPerson");
	}

	@RequestMapping("/select")
	public String upload(Model model) {
		model.addAttribute("pageTitle","批量上传销售记录");
		model.addAttribute("pageContent", "sale/SalesOrderUpload");
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
                	SalesOrder order = parseRow(row);
                	if(order!=null) {
                		order.setSn(soUtil.genSN());
                		sos.save(order);
                		++ count;
                	}
                }
            }
            workbook.close();
            ra.addFlashAttribute("err",  "共导入 "+count+" 条销售记录");
		} catch (IOException e) {
			ra.addFlashAttribute("err", "打开上传文件 "+upload.getOriginalFilename()+" 时出错");
		}
		return REDIR_ORDER_LIST;
	}
	
	// 解析一行是否为头部
	private boolean parseHeader(Row row) {
		
		// 要判断是否有一个合理的头部，
		/* 对 Excel 表的要求:
			 * 0. 日期 - 必须
			 * 1. 客户 - 可选，不填会写未知
			 * 2. 产品 - 必须
			 * 3. 价格 - 必须
			 * 4. 销售人    - 可选
			 * 5. 描述 - 可选
		*/
        
		// 对标题字段位置进行初始化
		// 对应的项在 Excel 中列中的位置, -1 代表没有该列.
		posHeaderItems.put("orderDate", 	-1);
		posHeaderItems.put("customer", 	-1);
		posHeaderItems.put("product", 	-1);
		posHeaderItems.put("price", 		-1);
		posHeaderItems.put("description", 	-1);
		posHeaderItems.put("salesPerson", 	-1);
		posHeaderItems.put("userSaleType", 	-1);

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

	private SalesOrder parseRow(Row row) {
		
		// 订单中必须有的字段
		Date orderDate 		= getCellDateValue(row, posHeaderItems.get("orderDate"));
		String productName  = getCellStringValue(row, posHeaderItems.get("product"),  "");
		String priceString  = getCellStringValue(row, posHeaderItems.get("price"),  "");
		
		ProdSelling ps;
		double discountPrice;
		
		SalesOrder order = new SalesOrder();
		
		// 三个必要字段均不能为空
		if(orderDate == null || productName.isEmpty() || priceString.isEmpty()) {
			addInvalidRow(orderDate.toString(), productName, priceString);
			return null;
		}
		else {
			// check price format 
			try {
				discountPrice = Double.parseDouble(priceString);
			}
			catch(NumberFormatException e) {
				addInvalidRow(orderDate.toString(), productName, priceString);
				return null;
			}

			// check product
			ps = pss.getByName(productName);
			if(ps == null) {
				ps = genNewProduct(productName, discountPrice);
			}
			
			OrderItem oi = new OrderItem();
			oi.setProdSelling(ps);
			oi.setQuantity(1);
			oi.setListPrice(ps.getListprice());
			oi.setDiscountPrice(discountPrice);
			oi.setTotalPrice(discountPrice);
			oi.genDiscount();

			order.setOrderDate(orderDate);
			order.setDiscountPrice(order.getDiscountPrice()+discountPrice);
			order.setListPrice(order.getListPrice()+ps.getListprice());
			order.getItems().add(oi);
		
			String description = getCellStringValue(row, posHeaderItems.get("description"), "批量导入的定单");
			order.setDescription(description);

			String customerName = getCellStringValue(row, posHeaderItems.get("customer"), "未知");
			Customer customer = cs.getByName(customerName);
			if(customer == null) {
				customer = genNewCustomer(customerName);
			}
			order.setCustomer(customer);
			
			String salesPerson = getCellStringValue(row, posHeaderItems.get("salesPerson"), "未知");
			order.setChannelName(salesPerson);
			
			String ustName = getCellStringValue(row, posHeaderItems.get("userSaleType"), "普通用户");
			UserSaleType ust = usts.getByName(ustName);
			if(usts==null) {
				ust = usts.getByName("普通用户");
			}
			order.setUserSaleType(ust);
			
			order.updatePriceAndDiscount();
			order.updateProductSummary();

			return order;
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
	
	private ProdSelling genNewProduct(String name, double price) {
		ProdC2 c2 = pc2s.getByName("其它");
		if(c2==null) {
			ProdC1 c1 = pc1s.getByName("其它");
			if(c1 == null) {
				c1 = new ProdC1();
				c1.setName("其它");
				pc1s.save(c1);
			}
			c2 = new ProdC2();
			c2.setName("其它");
			c2.setC1(c1);
//			pc2s.save(c2);
			c1.getC2list().add(c2);
			pc1s.update(c1);
		}
		ProdPart pp = new ProdPart();
		pp.setC2(c2);
		pp.setListprice(price);
		pp.setName(name);
		
		pps.save(pp);
		
		ProdSelling ps = new ProdSelling();
		
		ProdPartItem ppi = new ProdPartItem();
		ppi.setPart(pp);
		ppi.setQuantity(1);
		ppi.setSelling(ps);
		
		List<ProdPartItem> l = new ArrayList<>();
		l.add(ppi);
		
		ps.setC2(c2);
		ps.setListprice(price);
		ps.setName(name);
		ps.setPartslist(l);
		
		pss.save(ps);
		
		return ps;
	}

	private Customer genNewCustomer(String name) {
		Customer c = new Customer();
		OrgType ot = ots.getByName("个人");
		c.setName(name);
		c.setDescription("未知用户，请立即修改、补充信息");
		c.setNameAndPhone(name);
		c.setOrgType(ot);
		return c;
	}
	
}