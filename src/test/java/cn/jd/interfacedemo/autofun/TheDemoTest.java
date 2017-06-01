package cn.jd.interfacedemo.autofun;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Logger;
import org.testng.annotations.Test;

import cn.jd.interfacedemo.config.StaticProvider;
import cn.jd.interfacedemo.config.Write2Excel;


public class TheDemoTest {
	private static Logger logger=Logger.getLogger("TheDemoTest");
	
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public void toTest(Map<String, String> data) throws Exception {
//			Method method=getClass().getMethod("toTest",Map.class);
		try {
			
//			data = Write2Excel.ExcelRecord(data, method);
			logger.info("\n============Start Run：" + data.get("案例编号") + "===================");	
			String name = data.get("用户名");
			System.out.println(name);
//			Login.toLogin(target, data.get("账户"));
		}catch (AssertionError ae) {
//			logger.error("［验证点］不通过，案例执行失败！");
//			data.put("测试结果", "FAIL");
//			data.put("实际输出", "［验证点］不通过，案例执行失败！"+ae.toString());
			throw ae;
		} catch (Exception ex) {
//			logger.error("［测试脚本]出现异常，案例执行失败！");
//			data.put("测试结果", "FAIL");
//			data.put("实际输出", "［测试脚本]出现异常，案例执行失败！"+ex.toString());
			throw ex;
		}finally{
//			Write2Excel.toExcelOneReport(method, data);
//			Logout.toLogout(target);
		}
	}
}
