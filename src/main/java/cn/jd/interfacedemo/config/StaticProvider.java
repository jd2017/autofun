package cn.jd.interfacedemo.config;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.testng.annotations.DataProvider;

public class StaticProvider {
	@DataProvider(name = "dp")
	public static Iterator<Object[]> getDataByTestMethodName(Method method){
		return new ExcelDataProvider(method.getDeclaringClass().getName(),method.getName());
		
	}
}
