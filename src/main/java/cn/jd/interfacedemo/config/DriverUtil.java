package cn.jd.interfacedemo.config;
import org.apache.log4j.Logger;
/**
 * 
 * @author jd.bai
 * @date 2017年5月24日
 * @time 下午3:32:46
 */
public class DriverUtil {
	private static Logger logger=Logger.getLogger(DriverUtil.class.getName());
		
		public static String getHost(){
			String host = Config.get("host");
			if (host == null) {
				throw new Error("autofun.properties中没有设置host属性");
			}
			if(host.startsWith("~")){
				host = host.replaceAll("~", System.getProperty("user.home"));
			}
			logger.debug("host:"+host);
			return host;
		}
		public static String getK_V(String key){
			
			return null;
		}
		public  static String getCookieName(){
			String cookieName = Config.get("cookieName");
			logger.debug("cookieName:"+cookieName);
			return cookieName;
		}
		public  static String getCookieValue(){
			String cookieValue = Config.get("cookieValue");
			logger.debug("cookieValue:"+cookieValue);
			return cookieValue;
		}
		public static String getUrl(String key){
			String url = Config.get(key);
			return url;
		}
}
