package cn.jd.interfacedemo.instrumentsdriver;
import cn.jd.interfacedemo.config.DriverUtil;

public class InstrumentDriverTestCase extends ThreadGroup{
//	private static Logger logger = Logger.getLogger(InstrumentDriverTestCase.class.getName());
	public InstrumentDriverTestCase() {
		super("InstrumentDriverTestCase");
	}
	public static String host = DriverUtil.getHost();
	public static String[] cookie_V = {DriverUtil.getCookieName(),DriverUtil.getCookieValue()};
	
}
