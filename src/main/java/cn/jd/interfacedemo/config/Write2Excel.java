package cn.jd.interfacedemo.config;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

public class Write2Excel {
	private static String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	private static String sPath = System.getProperty("user.dir") + "/target/";
	private static String _ReportXls = sPath + "autofun" + date + ".xls";
	public static void createExcel(String excelFilePath, String sheetName) {
		HSSFWorkbook wBook;
		File file = new File(excelFilePath);
		if(!file.exists()){
			wBook = new HSSFWorkbook();
			setSheet(wBook, sheetName);
		}
	}
	// set sheet for excel
	public static void setSheet(HSSFWorkbook wBook,String sheetName){
		String[] columnnName = { "案例编号","编号备注", "预期输出", "类名", "方法名", "测试结果", "实际输出", "输入项", "测试时间"};
		HSSFSheet sheet = wBook.createSheet(sheetName);
//		前两个参数是你要用来拆分的列数和行数,冻结第一个行；
//		sheet.createFreezePane(0, 1);
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 5500);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 2000);
		sheet.setColumnWidth(6, 5500);
		sheet.setColumnWidth(7, 5500);
		sheet.setColumnWidth(8, 5500);
		int columnNum = columnnName.length;
		HSSFRow row = sheet.createRow(0);
		for(int i =0;i<columnNum;i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(setTitleStytle(wBook));
			cell.setCellValue(columnnName[i]);
		}
		// 设置首行自动筛选 前两个参数是列数和行数。后两个参数是下面窗口的可见象限，其中第三个参数是右边区域可见的左边列数，第四个参数是下面区域可见的首行。
		CellRangeAddress m = new CellRangeAddress(0, 1, 0, 10);
		sheet.setAutoFilter(m);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(_ReportXls);
			wBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void report2Excel(Map<String, String> map,Method method) {
		report2Excel1(map, _ReportXls, method);
	}
	public static void report2Excel(Map<String, String> map, String sPath,Method method) {
		if (map.size() > 0) {
			String sheetname = method.getName();
			System.out.println(sheetname);
			
			int columnNum = 0;
			try {
				createExcel(sPath, sheetname);

				// 获取文件流,并创建一个EXCEL文件对象
				FileInputStream is = new FileInputStream(sPath);
				HSSFWorkbook wbook = new HSSFWorkbook(is);
				//get Cell for row、column
				HSSFSheet sheet = wbook.getSheet(sheetname);
				HSSFRow titleRow = sheet.getRow(0);
				columnNum = titleRow.getPhysicalNumberOfCells();
				HSSFRow curRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
				
				// map.put("输入项", getAllFromMap(map));
				for (int i = 0; i < columnNum; i++) {
					String colName = titleRow.getCell(i).getStringCellValue().toString();
					HSSFCell curCol = curRow.createCell(i);
					curCol.setCellStyle(setContentStytle(wbook));
					if (map.containsKey(colName)) {
						System.out.println("#####" + map.get(colName));
						curCol.setCellValue(map.get(colName));
					}
				}// end for
				FileOutputStream fileOut = new FileOutputStream(sPath);
				wbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void report2Excel1(Map<String, String> map, String sPath,Method method) {
		if (map.size() > 0) {
			String sheetname = method.getName();
			int columnNum = 0;
			try {
				createExcel(sPath, sheetname);

				// Read target excel object
				FileInputStream is = new FileInputStream(sPath);
				HSSFWorkbook wbook = new HSSFWorkbook(is);
				int sheets = wbook.getNumberOfSheets();
				
				StringBuffer sb = new StringBuffer();
				for(int i =0;i<sheets;i++){
					String name = wbook.getSheetName(i);
					sb.append(name+",");
				}
				if(!sb.toString().contains(sheetname)){
					setSheet(wbook,sheetname);
				}
				HSSFSheet sheet = wbook.getSheet(sheetname);
				HSSFRow titleRow = sheet.getRow(0);
				//是获取不为空的列个数
				columnNum = titleRow.getPhysicalNumberOfCells();
				HSSFRow curRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
				// map.put("输入项", getAllFromMap(map));
				
				for (int i = 0; i < columnNum; i++) {
					String colName = titleRow.getCell(i).getStringCellValue().toString();
					HSSFCell curCol = curRow.createCell(i);
					curCol.setCellStyle(setContentStytle(wbook));
					if (map.containsKey(colName)) {
						System.out.println("***" + map.get(colName)+"***");
						curCol.setCellValue(map.get(colName));
					}
				}// end for
				FileOutputStream fileOut = new FileOutputStream(sPath);
				wbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static String getAllFromMap(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		Set<String> key = map.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
//			if (s != "") {
				sb.append(s).append("=").append(map.get(s));
				sb.append(",");
//			}
		}
		sb.deleteCharAt(sb.length()-1).append("]");
		return sb.toString();
	}
	public static Map<String, String> recordExcel(Map<String, String> map, Method method) {
		map.put("输入项", getAllFromMap(map));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("测试时间", format.format(new Date()));
		map.put("类名", method.getDeclaringClass().getName());
		map.put("方法名", method.getName());
		map.put("测试结果", "PASS");

		return map;
	}
	private static HSSFCellStyle setTitleStytle(HSSFWorkbook wBook) {
		HSSFPalette customPalette = wBook.getCustomPalette();
		// 设置为绿色背景色
		customPalette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 204, (byte) 255, (byte) 255);

		Font font = wBook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		HSSFCellStyle cStyle = wBook.createCellStyle();
		cStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		cStyle.setAlignment(CellStyle.ALIGN_CENTER);

		cStyle.setBorderBottom((short) 1);
		cStyle.setBorderLeft((short) 1);
		cStyle.setBorderRight((short) 1);
		cStyle.setBorderTop((short) 1);
		cStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cStyle.setFont(font);
		return cStyle;
	}
	private static HSSFCellStyle setContentStytle(HSSFWorkbook wbook) {
		HSSFPalette customPalette = wbook.getCustomPalette();
		// 设置为黄色背景
		customPalette.setColorAtIndex(HSSFColor.YELLOW.index, (byte) 255, (byte) 255, (byte) 204);

		Font font = wbook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);

		HSSFCellStyle cStyle = wbook.createCellStyle();
		cStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		cStyle.setAlignment(CellStyle.ALIGN_LEFT);
		// 添加边框
		cStyle.setBorderBottom((short) 1);
		cStyle.setBorderLeft((short) 1);
		cStyle.setBorderRight((short) 1);
		cStyle.setBorderTop((short) 1);

		cStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cStyle.setFont(font);
		// // Set the default column width;
		// HSSFSheet sheet = wbook.getSheetAt(0);
		// sheet.autoSizeColumn(2);
		// sheet.autoSizeColumn(3);
		// sheet.autoSizeColumn(4);
		// sheet.autoSizeColumn(5);
		// sheet.autoSizeColumn(9);
		return cStyle;
	}
	
}
