package cn.jd.interfacedemo.config;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
/**
 * @author jd.bai
 * @date 2017年5月19日
 * @time 上午9:17:59
 */
//Excel Data resource download
public class ExcelDataProvider implements Iterator<Object[]> {
	private Workbook book = null;
	private Sheet sheet = null;	
	private int rowNum = 0;		//行
	private int curRowNum = 0;
	private int colunmNum = 0;	//列
	private String[] colunmnName;
	public ExcelDataProvider(String classname, String methodname) {

		//book path  classname名字替换所有省略符
		String wbookFile = System.getProperty("user.dir")+"/src/test/resources/"+classname.replaceAll("\\.", "/")+".xls";
		wbookFile = wbookFile.replace("\\", "/");
		System.out.println("Excel Path:" + wbookFile);
		try {
			this.book = Workbook.getWorkbook(new File(wbookFile));
			this.sheet = book.getSheet(methodname);
			this.rowNum = sheet.getRows();
			Cell[] cs = sheet.getRow(0);
			this.colunmNum = cs.length;
			colunmnName = new String[cs.length];
			for(int i=0; i<cs.length; i++){
				colunmnName[i]=cs[i].getContents().toString(); //Store column name
			}
			this.curRowNum++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Iterate over each row of data
	@Override
	public boolean hasNext() {
		if(this.rowNum == 0|| this.curRowNum >= this.rowNum){
			try {
				book.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}else{
			return true;
		}
	}
	//
	@Override
	public Object[] next() {
		Cell[] cs = sheet.getRow(this.curRowNum);
		Map<String,String> map = new HashMap<String, String>();
		for(int i = 0;i <this.colunmNum;i++){
			String temp = "";
			try {
				temp = cs[i].getContents().toString();
			} catch (ArrayIndexOutOfBoundsException e) {
				temp = "";
				e.printStackTrace();
			}
			map.put(this.colunmnName[i], temp);
		}
		Object row[] = new Object[1];
		row[0] = map;
		this.curRowNum ++;
		
		return row;
	}
	
	@Override
	public void remove() {
		new UnsupportedOperationException("remove unsupported.");
	}
}
