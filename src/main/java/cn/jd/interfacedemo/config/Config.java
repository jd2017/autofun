package cn.jd.interfacedemo.config;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * @author jd.bai
 * @date 2017年5月24日
 * @time 下午3:32:33
 */
public class Config {
private static Logger logger = Logger.getLogger("Config");
	
	private static Properties prop;
	public static String get(String key) {
		if (prop == null) {
			prop = new Properties();
			try {
				String file = new Config().getClass()
						.getResource("/autofun.properties").toURI().getPath();
				logger.trace("Config Path: "+file);
				System.out.println("Config Path: "+file);
				//Specified encoding
				prop.load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			} catch (Exception e) {
				throw new Error("未找到autofun.properties文件，请将config目录添加到build path的source中。");
			}
		}
		String value = prop.getProperty(key);
		if (value == null||value.trim()=="")
			return null;
		return value.trim();
	}
}
