package cn.jd.interfacedemo.autofun;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.testng.Reporter;
import org.testng.annotations.Test;

import cn.jd.interfacedemo.config.Config;
import cn.jd.interfacedemo.config.DriverUtil;
import cn.jd.interfacedemo.config.HttpClientRequest;
import cn.jd.interfacedemo.config.StaticProvider;
import cn.jd.interfacedemo.config.Write2Excel;
import cn.jd.interfacedemo.instrumentsdriver.InstrumentDriverTestCase;

public class HttpClientRequestTest extends InstrumentDriverTestCase{
//	private  String[] cookie_V = {"autofunCookie","423caf51-02e8-4f8b-8b30-953624d5e141"};
	/**
	 * http://hostname:port/xcgj-app-ws/ws/0.1/user/relation
	 * http://10.23.211.68/xcgj-ws/ws/0.1/debug/push?userId=1&catageoryKey=101&type=1
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public void getPush(Map<String, String> data){
			String methodName="getPush";
			Method method=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);//"http://"+host+"/xcgj-ws/ws/0.1/debug/push?userId=95&catageoryKey=101&type=1";
			JSONObject jsonResult = HttpClientRequest.getJson4Cookies(url,true);
			if(!jsonResult.get("status").equals("200"))
				setFailData(data, null, jsonResult,methodName);
//			Assert.assertEquals(json.get("status"), "2100");
		}catch (AssertionError ae) {
			setFailData(data, ae, null,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, null,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.1.1	用户登录
	 * HTTP Method：POST
	 * http://hostname:port/xcgj-app-ws/ws/0.1/oAuth/login
		Param:
			{“countryCode”:xxx,"username":xxx,"password":xxx }
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void postLogin(Map<String, String> data) {
		String methodName="postLogin";
		Method method=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("countryCode","60");
	 		params.put("username","1350000000");
	 		params.put("password","e10adc3949ba59abbe56e057f20f883e");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	        JSONObject jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,true);
	        if(!jsonResult.get("status").equals("200"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, null,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, null,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.1.2	用户退出
	 * 接口描述：用户退出当前登录用户，清空session，退出之后不再接收推送消息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/user/logout?clientId=XXX
		clientId为APP端生成的唯一标识（个推推送）。采用个推服务推送的APP版本才需添加字段clientId，其值由个推提供。
		另，乐乘APP中Android3.1版本及以上，已采用个推服务。IOS待定。
		//6aacef78f2c11d6cd966da31b32dce84
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public void getLogout(Map<String, String> data){
		String methodName="getLogout";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]+"test");
			if(!jsonResult.get("message").toString().contains("用户未登录"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.1.3	用户注册
	 * HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/user/register
		Param:
		{“countryCode”:xxx,"username":xxx,"password":xxx,"captcha":xxx }
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void postUserRegister(Map<String, String> data) {  
			String methodName="postUserRegister";
			Method method=null;
			JSONObject jsonResult=null;
			try {
				method=getClass().getMethod(methodName,Map.class);
				data = Write2Excel.recordExcel(data, method);
				String url = DriverUtil.getUrl(methodName).replace("host", host);
				Map<String, Object> params = new HashMap<String, Object>();
		 		params.put("countryCode","60");
		 		params.put("username","1350000000");
		 		params.put("password","e10adc3949ba59abbe56e057f20f883e");
		 		params.put("captcha","2134");
		 		JSONObject jsonParam = JSONObject.fromObject(params);
		 		jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,false);
		 		if(!jsonResult.get("message").toString().contains(("Captcha not found")))
					setFailData(data, null, jsonResult,methodName);
			}catch (AssertionError ae) {
				setFailData(data, ae, jsonResult,methodName);
				throw ae;
			} catch (Exception e) {
				setFailData(data, e, jsonResult,methodName);
			}finally{
				Write2Excel.report2Excel(data, method);
			}
    }
	/**
	 * 1.1.4	请求手机号验证码
	 * HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/captcha
		Params： 
			{“countryCode”:xxx,"mobile":xxx,"businessType":xxx}
		其中：
		businessType：业务类型（注册：register；找回密码：findpwd），必填；
		mobile：用户手机号，必填；countryCode为国家手机区号如中国是86
		Return Value：
		成功：Status.OK（手机会收到短信验证码。）
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void postCaptcha(Map<String, String> data) {
		String methodName="postCaptcha";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("countryCode","60");
	 		params.put("mobile","1350000000");
	 		params.put("businessType","register");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,true);
	 		if(!jsonResult.get("message").toString().contains("Phone number has been registered"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.1.5	重置密码
	 * HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/user/password
		Param:
		{“countryCode”:xxx,"mobile":xxx,"newpwd":xxx,"captcha":xxx}
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void postNewPWD(Map<String, String> data) {
		String methodName="postNewPWD";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("countryCode","60");
	 		params.put("mobile","1350000001");
	 		params.put("newpwd","e10adc3949ba59abbe56e057f20f883e");
	 		params.put("captcha","2399");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,false);
	 		if(!jsonResult.get("message").toString().contains("Captcha not found"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.1.6	修改密码
	 * 接口描述：该接口用于修改密码。
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/user/updatePwd
		Param:
			{"oldPwd":xxx,"newPwd":xxx,"newPwdRepeat":xxx}
			其中：oldPwd：老密码；newPwd：新密码；newPwdRepeat：重复密码；
		Return Value：
		成功：Status.OK

	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void postUpdatePwd(Map<String, String> data) {  
		String methodName="postUpdatePwd";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("oldPwd","e10adc3949ba59abbe56e057f20f883F");
	 		params.put("newPwd","e10adc3949ba59abbe56e057f20f883e");
	 		params.put("newPwdRepeat","e10adc3949ba59abbe56e057f20f883e");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url, jsonParam,false,cookie_V[0], cookie_V[1]);
	 		if(!jsonResult.get("message").toString().contains("Current password is wrong"))
					setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.1.7	请求人、车、设备信息
	 * 接口描述：该接口用于获取当前登录用户及绑定设备、车辆基本信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/user/relation
		Return Value：
			成功：Status.OK
		{
		"user":{"userId.....
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void getRelation(Map<String, String> data) {  
		String methodName="getRelation";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
		    jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
	    	 if(jsonResult.get("user")==null)
					setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.1.8	注册客户和client的关系
	 * 接口描述：该接口用于注册客户和client的关系鉴权，需要登录乐乘。
		HTTP Method：POST
		Request URL： http://hostname:port/xcgj-app-ws/ws/0.1/oAuth/register
		Param:
			{"clientId":xxx ,”clientType”:xxx}
			其中：clientId为APP端生成的唯一标识（个推推送）clientType为客户端的类型，android传0，IOS传1; 
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void postRegister(Map<String, String> data) {  
		String methodName="postRegister";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("clientId","6aacef78f2c11d6cd966da31b32dce84");
	 		params.put("clientType","0");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url, jsonParam,true,cookie_V[0], cookie_V[1]);
	 		 if(!jsonResult.get("status").equals("200"))
					setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.1.9	设置app端显示语言接口描述：该接口用于设置app端显示语言，需要登录乐乘。
			HTTP Method：POST
			Request URL： http://hostname:port/xcgj-app-ws/ws/0.1/oAuth/changeLanguage
			Param:
				{"clientId":xxx ,”clientType”:xxx,"language":xxx}
				其中：clientId为APP端生成的唯一标识（个推推送）clientType为客户端的类型，android传0，IOS传1; 
			language为APP端的显示语言标识，选择中文传zh-CN，选择英文传en-US，以后有其他语言，按照国家语言代码来传值。
			此接口仅采用个推服务的APP关注。
			Return Value：
			成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postChangeLanguage(Map<String, String> data) {  
		String methodName="postChangeLanguage";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("clientId","6aacef78f2c11d6cd966da31b32dce84");
	 		params.put("clientType","0");
	 		params.put("language","zh-CN");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url, jsonParam,true,cookie_V[0], cookie_V[1]);
	 		 if(!jsonResult.get("status").equals("200"))
					setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.2	车辆品牌/系列/车型
	 * 1.2.1	请求车辆品牌列表
		接口描述：根据设备ID请求适配的车辆品牌列表。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/bms/brands
		Return Value：
		成功：Status.OK
		{"count":xxx,"items":{["brandId":xxx,"brandName":xxx,"imgUrl":xxx,"spell":xxx]}}
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void getBrands(Map<String, String> data) {
		String methodName="getBrands";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
	    	jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
	    	 if(jsonResult.get("count")==null)
					setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.2.2	请求车辆系列列表
		接口描述：根据车辆品牌ID请求适配的车系列表。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/bms/models/v2?brandId=xxx
		其中：brandId：品牌ID；
		Return Value：
		成功：Status.OK
		{"count":xxx,"items":{["modelId":xxx,"modelName":xxx}
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void getBrandId(Map<String, String> data) {  
		String methodName="getBrandId";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
	    	 if(jsonResult.get("count")==null)
					setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.2.3	请求车辆车型列表
		接口描述：根据车系ID请求适配的车型列表。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/bms/styles/v2?modelId=xx
		其中： modelId为车系ID；
		Return Value：
		成功：Status.OK
		JSON：
		{"count":xxx,"items":{["styleId":xxx,"styleName":xxx,"year":xxx]}}
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void getModelId(Map<String, String> data) {
		String methodName="getModelId";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
	    	 if(jsonResult.get("count")==null)
					setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.3	安防
		1.3.1	请求车辆位置信息
		接口描述：该接口用于请求车辆位置信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/vehicle/location
		Return Value：
		成功：Status.OK
		{"lng":xxx,"lat":xxx"speed":xxx,"time":xxx,"address":xxx,"locateType":xxx,"radii":xxx,"direction":xxx, oriLat :xx,”oriLng”:xx}
		其中locateType：定位类型，分为gps和cellid；
			radii：为cellid的半径，cellid时才有值，默认1000米；
		direction：航向角；
		oriLat:原始纬度
       	oriLng:原始经度
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
    public  void getLocation(Map<String, String> data) {  
		String methodName="getLocation";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]); 
			if(jsonResult.get("lng")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.3.2	设置安防震动级别
		接口描述：设置安防震动灵敏度（范围为1-5的整数）。
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/monitor/setting/shakeLevel
		Param
		{"level":"xxx"}
		Return Value: 
		成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getShakeLevel(Map<String, String> data) {  
		String methodName="getShakeLevel";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]); 
			if(jsonResult.get("level")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
   }
	/**
	 * 1.3.3	请求安防配置信息
		接口描述：该接口用于请求安防配置信息（震动级别、定时时间等）。 
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/monitor/setting
		Return Value: 
			成功：Status.OK
			{"ignate":"xxx","displace":"xxx","shake":"xxx","level":"xxx","fuel":"xxx","lowFuel":"xxx","battery":"xxx","lowBattery":"xxx","trip":"xxx","weekReport":"xxx","maintenance":"xxx","carBeta":"xxx","remindSwitch":xxx,"remindStartTime":xxx,"remindEndTime":xxx }
			其中：
			ignate：点火报警；
			displace：移位报警；
			shake：震动报警；
			level为震动灵敏度；
			fuel：油量报警
			lowFuel：油量报警阀值
			battery：电压报警；
			lowBattery：电压报警阀值
			trip:单次行程报告；
			weekReport:  每周数据报告；
			maintenance: 保养提醒；
			carBeta：资讯开关;
			remindSwitch:勿扰模式开关;
			remindStartTime: 勿扰模式起始时间;
			remindEndTime: 勿扰模式结束时间;
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getSetting(Map<String, String> data) {  
		String methodName="getSetting";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]); 
			if(jsonResult.get("level")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
   }
	/**
	 * 1.3.4	设置防骚扰模式开关状态
		接口描述：该接口用于设置防骚扰模式的开/关状态。 
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/monitor/setting/remind/switch
		Param
		 {"remindSwitch":"xxx"}
		remindSwitch：true、false
		Return Value: 
		成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postSwitch(Map<String, String> data) {  
		String methodName="postSwitch";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);  
			Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("remindSwitch","false");
	 		JSONObject jsonObject = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url, jsonObject, true, cookie_V[0], cookie_V[1]);
			if(!jsonResult.get("status").equals("200"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.3.5	设置防骚扰模式时间段
		接口描述：该接口用于设置防骚扰时间段。 
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/monitor/setting/remind/timing
		Param
		 { "remindStartTime":"xxx","remindEndTime":xxx}
		time格式为：07:20
		Return Value: 
			成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postTiming(Map<String, String> data) {  
		String methodName="postTiming";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);  
			Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("remindStartTime","07:20");
	 		params.put("remindEndTime","07:19");
	 		JSONObject jsonObject = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url, jsonObject, true, cookie_V[0], cookie_V[1]);
			if(!jsonResult.get("status").equals("200"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
    }
	/**
	 * 1.4	诊断
		1.4.1	发起全车诊断
		接口描述：该接口用于向终端发起全车诊断指令
		HTTP Method：POST
		Request URL： http://hostname:port/xcgj-app-ws/ws/0.1/diagnosis/trigge
		Return Value: 
			成功：Status.OK
			{"timeout":xxx}，timeout所选车型诊断超时的时间，目前默认3分钟；
	 */
	@Deprecated
	//@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getTrigge(Map<String, String> data) {  
   	 String url = "http://10.23.211.68/xcgj-app-ws/ws/0.1/diagnosis/trigge";
   	 JSONObject jsonObject = HttpClientRequest.getJson4Cookies(url,true,cookie_V[0], cookie_V[1]);
   	 System.out.println(jsonObject);
//   	 System.out.println(jsonObject.get("level"));
   }
	/**
	 * 1.5	个人信息
		1.5.1	请求个人信息
		接口描述：该接口用于获取当前登录用户基本信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/user
		Return Value：
			成功：Status.OK
		{"userId":xxx,"nickname":xxx,"mobile":xxx,"email":xxx,"head":xxx,"sex":xxx,"birthday":xxx,"area":xxx,"signature":xxx,“countryCode”:xxx,}
		其中：area：地区（string）；signature：个性签名
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getUser(Map<String, String> data) {
		String methodName="getUser";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
			if(jsonResult.get("userId")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
   }
	/**
	 * 1.5.2	修改个人信息
		接口描述：该接口用于修改个人信息。
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/user
		Param:
			{"nickname":xxx,"email":xxx,"sex":xxx,"birthday":xxx,"area":xxx,"signature":xxx}
		Return Value：
			成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postUpdateUser(Map<String, String> data) {  
		String methodName="postUpdateUser";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);  
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("nickname","alert('0')");
	 		params.put("signature","@qq.com");
	// 		params.put("sex","Male");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
		    jsonResult = HttpClientRequest.postJson4Cookies(url, jsonParam,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("id")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.6	车辆
		1.6.1	请求当前车况数据
		接口描述：该接口用于请求车辆当前里程、油量等信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/condition
		Return Value：
		成功：Status.OK
		JSON：
		{"mileage":xxx,"fuel":xxx,"voltage":xxx,"time":xxx,"allowInitMileage":xxx,"canReadMileage":xxx,"canReadFuel":xxx,"fuelStatus":xxx,
		"voltageStatus":xxx }
		其中：
			time："2016-03-21 08:32:31"（电压最后更新时间）；
			allowInitMileage：是否允许设置初始里程，true/false；
			canReadMileage：是否能读取到里程；
			canReadFuel：是否能读取到油量；
		fuelStatus：油量状态(0:正常 1:低油量)；
		voltageStatus：电压状态(0:正常 1:低电压)；
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getCondition(Map<String, String> data) {  
		String methodName="getCondition";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host); 
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("mileage")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.6.2	设置车型
		接口描述：该接口用于设置车辆的车型信息。
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/vehicle/style
		Param:
			{"styleId":xxx, "from":xxx }
		其中：from为init就不阻塞。
		Return Value：
		成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postStyle(Map<String, String> data) {  
		String methodName="postStyle";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);  
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("styleId","41134");
	 		params.put("from","");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url, jsonParam,true,cookie_V[0], cookie_V[1]);
			if(!jsonResult.get("status").equals("200"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.6.3	请求车型CM3升级结果
		接口描述：该接口用于请求设置车型后设备升级CM3的结果。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/vehicle/style/status
		Param:
		{"status":xxx,"failReason":xxx,"time":xxx",
		vehicleStyle":{"brandId":xxx,"brandName":xxx,"modelId":xxx,"modelName":xxx,"styleId":xxx,"styleName":xxx,"mUrl":xxx}
		}
			其中：
			status：升级状态：0、1、2 升级中/成功/失败
			failReason：失败原因；status为2时才有值；
			time：返回结果的时间；
		Return Value：
		成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getStatus(Map<String, String> data) {  
		String methodName="getStatus";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
			if(!jsonResult.get("status").equals("1"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.6.4	初始化里程
		接口描述：该接口用于初始化车辆里程信息。只能设置一次。
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/vehicle/mileage/init
		Param:
		{"mileage":xxx}
		Return Value：
			成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postMileage(Map<String, String> data) {  
		String methodName="postMileage";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("mileage","123456");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url, jsonParam,false,cookie_V[0], cookie_V[1]);
		   	if(!jsonResult.get("message").toString().contains("修改车辆信息失败！"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.6.5	设置车辆基本信息
		接口描述：该接口用于设置车辆的基本信息。
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/vehicle
		Param:
		{"licensePlate":xxx,"ein":xxx,"vin":xxx,"boughtDate":xxx}
		车牌号等信息，有唯一性校验。
		Return Value：
		成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postVehicle(Map<String, String> data) {  
		String methodName="postVehicle";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("licensePlate","1234567");
	 		params.put("ein","12ss0");
	 		params.put("vin","12345678900000000");
	 		params.put("boughtDate","2017-12-12");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
		   	jsonResult = HttpClientRequest.postJson4Cookies(url, jsonParam,true,cookie_V[0], cookie_V[1]);
		   	if(!jsonResult.get("status").equals("200"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.6.6	请求车辆基本信息
		接口描述：该接口用于请求车辆的基本信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/vehicle
		Return Value：
		成功：Status.OK
		{
		"vehicleId":xxx,"licensePlate":xxx,"vin":xxx,"ein":xxx,"boughtDate":xxx,
		"vehicleStyle":{"brandId":xxx,"brandName":xxx,"modelId":xxx,"modelName":xxx,"styleId":xxx,"styleName":xxx,"mUrl":xxx}
		}
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getVehicle(Map<String, String> data) {  
		String methodName="getVehicle";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(!jsonResult.get("vehicleId").equals("57"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.7	okOBD设备
		1.7.1	请求设备信息
		接口描述：该接口用请求设备信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/terminal
		Return Value：
			成功：Status.OK
		{"terminalId":xxx,"imei":xxx,"code":xxx,"terminalType":xxx,"connectionStatus":xxx,"lastDataPackageTime":xxx,"status":xxx}
			其中：
		connectionStatus：设备连接状态；
		lastDataPackageTime：最后上包时间；
		status：设备状态（1：正常/在线；2：故障；）
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getTerminal(Map<String, String> data) {  
		String methodName="getTerminal";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		 	if(!jsonResult.get("imei").equals("201760135000000"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.7.2	绑定设备
		接口描述：该接口用于绑定obd设备，原先的返回值可以在绑定成功后调用接口1.1.7获取。
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/terminal/bind
		Param:
			{"imei":xxx,"code":xxx, "styleId ":xxx }
		参数说明：
		styleId：车型编号（可以为空）。
		Return Value：
			成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postBind(Map<String, String> data) {  
		String methodName="postBind";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("imei","201760135000000");
	 		params.put("code","3177");
	 		params.put("styleId","");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
		   	jsonResult = HttpClientRequest.postJson4Cookies(url, jsonParam,true,cookie_V[0], cookie_V[1]);
			if(!jsonResult.get("status").equals("200"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.7.3	请求设备状态
		接口描述：该接口用户获取设备状态。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/terminal/status
		Return Value：
			成功：Status.OK
		{"status":xxx}
			status：设备状态（1：正常/在线；2：故障（包括离线和GPS故障）；）
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getTerminalStatus(Map<String, String> data) {  
		String methodName="getTerminalStatus";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
	   	 	jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("gpsValidity")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.8	其他
		1.8.1	请求app配置信息
		接口描述：该接口用于在未登录下请求APP的一些配置信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/setting
		Return Value：
			成功：Status.OK
		{
		 “GeTuiPush”:{ “GeTuiAppID": "xxx","GeTuiAppKey": "xxx","GeTuiMasterSecret": "xxx" }
		}
		
		后台有问题
	*/
	//@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getAppSetting(Map<String, String> data) {  
		String methodName="getAppSetting";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
	   	 	jsonResult = HttpClientRequest.getJson4Cookies(url,false);
		   	if(jsonResult.get("gpsValidity")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 接口描述：该接口用于请求用户所在城市的行车提醒信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/drivingRemind?cityName=xxx
		传递数据：
		{
			"today":xxx,
			"cityName":xxx,
		"weather":{"carWashIndex":xxx}
		"fuelPrice：{"count":xxx,"items":{["type":xxx,"price":xxx],["type":xxx,"price":xxx]}},
		"limit"：xxx
		}
		参数说明：
		today：日期
		weather：天气信息，carWashIndex：洗车指数（适宜 较适宜 不宜 较不宜）；
		cityName：城市（北京）
		fuelPrice：油价，type：油类型；price：单价；
		limit：限行尾号（"3,9"），不限行为","；
		Return Value：
		成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getDrivingRemind(Map<String, String> data) {  
		String methodName="getDrivingRemind";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			System.out.println(url);
	   	 	jsonResult = HttpClientRequest.getJson4Cookies(url,false);
	   	 	if(jsonResult.get("today")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.8.3	提交反馈意见
		接口描述：该接口用于提交反馈意见。
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/feedback
		传递数据：
		{"content":xxx,"contact":xxx}
		参数说明：
		content：反馈内容；
		contact：联系方式；
		Return Value：
		成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postFeedback(Map<String, String> data) {  
		String methodName="postFeedback";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("content","test你好kkk");
	 		params.put("contact","999");
	// 		params.put("styleId","");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
		   	jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,true,cookie_V[0], cookie_V[1]);
		   	if(!jsonResult.get("status").equals("200"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.8.4	反馈意见列表(分页)
		接口描述：该接口请求提交反馈意见列表,带分页。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/feedback
		Request Param：pageSize=xx&pageIndex=xx
		Return Value：
		成功：Status.OK
		{"page":xxx , "count":xxx,"items": [{"content": "xxx","createTime": "xxx","status": "xxx","reply": [{"replyContent": "xxx","replyTime": "xxx"}]}]}
		参数说明：
		content：反馈内容；
		createTime:反馈时间
		status:回复状态  0:已提交  1:已回复
		replyContent ":回复内容
		 replyTime:回复时间
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getFeedback(Map<String, String> data) {  
		String methodName="getFeedback";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
	   	 	if(jsonResult.get("page")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.9	个人周报
		1.9.1	请求驾驶周报列表
		接口描述：请求车辆历史周报数据，默认取上周开始六周的数据（用于html5页面）。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/drivingBehavior/weeks?pageSize=xx&pageIndex=xx
		Return Value：
		成功：Status.OK
		JSON：
		{"page":xxx , "count":xxx,
		"items":{["year":xxx,"week":xxx,"startDate":xxx,"endDate":xxx,"avgScore":xxx,"mileage":xxx,"totalFuel":xxx, "drivingTime":xxx }}
			其中：
				year：当前周所在年；
				week：当前周是当年的第几周
				startDate：当前周的开始日期；
				endDate：当前周的结束日期；
				avgScore：平均分；
				mileage：行驶里程；
				totalFuel：耗油量；
			drivingTime：周驾驶时长(分钟)；
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getWeeks(Map<String, String> data) {  
		String methodName="getWeeks";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
	   	 	jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("page")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.9.2	请求驾驶周报详情
		接口描述：请求year、week周驾驶周报详情（用于html5页面）。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/drivingBehavior/weeks/info?year=xx&week=xx
		Return Value：
		成功：Status.OK
		JSON：
		{
		"year":xxx,"week":xxx,"startDate":xxx,"endDate":xxx,"
		avgScore":xxx,"scores":[xxx],
		"mileage":xxx,"mileages":["xxx"],
		"totalFuel":xxx,"fuels":[xxx,],
		"fuelPer100km":xxx," miitFuelConsumption":xxx,
		"drivingTime":xxx,"drivingTimes":[xxx],
		"voltageStatus":xxx,"voltageTrend ":[xxx], 
		}
			其中：
				Year：当前周所在年；
				Week：当前周是当年的第几周
				startDate：当前周的开始日期；
				endDate：当前周的结束日期；
				avgScore：平均分；scores：本周所有行程评分
				mileage：行驶里程；mileages：本周日行驶里程；
				totalFuel：耗油量；fuels：本周日耗油量
				fuelPer100km：百公里油耗；miitFuelConsumption：工信部油耗； 
				drivingTime：周驾驶时长(分钟)；drivingTimes：本周日驾驶时长；
				voltageTrend:本周电压趋势；voltageStatus:电压状态（0:正常 1:异常）；
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getInfoWeeks(Map<String, String> data) {  
		String methodName="getInfoWeeks";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("year")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.10	行程
		1.10.1	请求最后一次行程信息
		接口描述：该接口用于请求最后一次已结束的行程信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/trip/last
		Return Value：
			成功：Status.OK
		{"tripId":xxx,"mileage":xxx,"fuelPer100km":xxx,"driverTime":xxx,"avgSpeed":xxx,"score":xxx,"startTime":xxx,"endTime":xxx,"fuel":xxx, "trackImgPath":xxx,"rankDesc":xxx,"drive_hour":xxx,"drive_min":xxx,"title":xxx,"titleDesc":xxx, }
			行程编号，行驶距离，百公里油耗，行驶时长，平均时速，行程评分，开始时间，结束时间，耗油量，轨迹图片地址，排名描述；
			drive_hour:驾驶小时，drive_min：驾驶分钟，title：评分称号、titleDesc：称号描述
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getTriplast(Map<String, String> data) {  
		String methodName="getTriplast";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("tripId")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.10.2	请求单次行程详情
		接口描述：该接口用于根据Trip获取行程详细信息，需要登录。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/trip/info?tripId=xxx
		Return Value：
			成功：Status.OK
		{
		"tripId":xxx, "mileage":xxx,"fuelPer100km":xxx,"driverTime":xxx,"avgSpeed":xxx,"startTime":xxx,"endTime":xxx,"fuel":xxx,
		"carbonEmission":xxx,"maxSpeed":xxx,"countOverSpeed":xxx,"acceleration":xxx,"brake":xxx,"sharpTurn":xxx,"fatigueDriving":xxx
		"handBrake":xxx,"turnLight":xxx,"nightLight":xxx,"score":xxx,
		" title ":xxx,"titleDesc":xxx,"drivingAdvice":xxx,"shareDesc":xxx,"shareUrl":xxx,"trackImgPath":xxx,"speedDesc":xxx,
		"drive_hour":xxx,"drive_min":xxx, "driver_time_to_hour":xxx,"idleTime":xxx,"stillHot":xxx, vehicleId":xxx,
		"driveHour ":xxx, "speed":xxx, "timeDuration":xxx, "stable":xxx, "carefule":xxx 
		}
		其中：
			行程编号tripId，行驶距离mileage，百公里油耗fuelPer100km，行驶时长driverTime，平均时速avgSpeed，
		开始时间startTime，结束时间endTime，耗油量fuel、碳排放carbonEmission、最高速度maxSpeed、超速次数countOverSpeed、
		急加速acceleration、急减速brake、急转弯sharpTurn、疲劳驾驶fatigueDriving（1/0是/否）、未手刹松handBrake（1/0是/否）、
		转向未打灯turnLight（1/0 是/否）、夜间行驶未开大灯nightLight（1/0 是/否）、行程评分score，评分称号title、称号描述titleDesc、驾驶建议drivingAdvice（如：安全第一）、分享描述shareDesc（如：求安慰、得瑟一下），分享链接地址：shareUrl,
		"speedDesc" : 速度描述 、drive_hour:驾驶小时，drive_min：驾驶分钟 , driver_time_to_hour：不满60分钟的转换为小时(小数点后一位) ,idleTime:怠速时间 ,stillHot:原地热车时间,vehicleId:车id（盒子模式行程才有）。 
		driveHour：时长；speed：速度；timeDuration：时段；statTime：开始时间(毫秒数)；endTime：结束时间(毫秒数)；stable：平稳；carefule：专注度(盒子模式下始终为100) 
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getTriplastinfo(Map<String, String> data) {  
		String methodName="getTriplastinfo";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("tripId")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.10.3	请求行程轨迹信息
		接口描述：该接口用于查询指定车辆指定时间段内的轨迹信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/trip/track?startTime=xxx&endTime=xxx &oriPosition=xxx
		其中：
		oriPosition:是否需要返回原始坐标(true/false)。此字段适用于英文版乐乘APP（比如马来西亚项目）。
		Return Value：
			成功：Status.OK
		{
		"track":[{"lng":xxx,"lat":xx",oriLat :xx,”oriLng”:xx,speed":xxx,"time":xxx,"address":xxx,"locateType":xxx,"radii":xxx,"direction":xxx}],
		"event":[ {"lng":xxx,"lat":xxx",oriLat :xx,”oriLng”:xx,speed":xxx,"time":xxx,"address":xxx,"locateType":xxx,"radii":xxx,"direction":xxx,"type":xxx}]
		}
			其中type：急加速/急刹车/急转弯/超速 1/2/3/4
		oriLat:原始纬度
		oriLng:原始经度
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getTriptrack(Map<String, String> data) {  
		String methodName="getTriptrack";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("track")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.10.4	请求总行程信息
		接口描述：该接口用于请求总行程信息。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/trip/total
		Return Value：
			成功：Status.OK
		{"totalMileage":xxx,"totalFuel":xxx,"totalScore":xxx,"availablePoints"}
			总里程，总耗油，总评分，积分（可用积分）
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getTriptotal(Map<String, String> data) {  
		String methodName="getTriptotal";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("totalMileage")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.10.5	分页请求历史行程信息
		接口描述：该接口用于分页请求行程信息，请求当前日期前的pageSize条数据。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/trip/page
		Request Param：pageSize=1&pageIndex=1&startTime=2017-05-15&endTime=2017-05-15
		其中：startTime、endTime用于统计某天的行程列表，当前版本已删除了此功能。
		Return Value：
			成功：Status.OK
		{"page":xxx ,"count":xxx,"items":{["tripId":xxx,"mileage":xxx,"fuelPer100km":xxx,"driverTime":xxx,"avgSpeed":xxx,"score":xxx,"startTime":xxx,"endTime":xxx,"fuel":xxx,"trackImgPath":xxx," maxSpeed ":xxx]}
		其中：
		page：当前页，count：当前页记录数，tripId：行程编号，mileage：行驶距离，fuelPer100km：百公里油耗，driverTime：行驶时长，avgSpeed：平均时速，score：行程评分，startTime：开始时间，endTime：结束时间，fuel：耗油量，trackImgPath：轨迹图片地址；maxSpeed：最高速度, 
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getTrippage(Map<String, String> data) {  
		String methodName="getTrippage";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("page")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.11	消息中心
		1.11.1	获取最新消息数量
		接口描述：该接口获取当前用户各类消息的未读消息数量
		HTTP Method：GET
		Request URL： http://hostname:port/xcgj-app-ws/ws/0.1/message/new
		Return Value: 
			成功：Status.OK
		返回值说明：
		{"totalCount":xxx,"items":[{"category":xxx,"count":xxx}]}
		其中：
			totalCount：未读消息总数量
		category：消息分类 0-安防，1-车况；
		count：新消息数量
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getMessagenew(Map<String, String> data) {  
		String methodName="getMessagenew";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("totalCount")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.11.2	分页获取某类消息
		接口描述：该接口用于分页获取所有分类的消息。
		HTTP Method：GET
		Request URL： http://hostname:port/xcgj-app-ws/ws/0.1/message/category
		Params：pageIndex=0&pageSize=20&category=xxx
		Return Value: 
			成功：Status.OK
		{
		"count":1," page":0, "category":1, 
		"items":[{"id":xxx,"subType":"xxx","content":"xx","address":xxx,"status":false,"receivedTime":"xxx","url":xxx,"lng":xxx,"lat":xxx}]
		} 
		返回值说明: url: 可能为空
		另：返回值按照receiceTime倒序排序。
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getMessagecategory(Map<String, String> data) {  
		String methodName="getMessagecategory";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("category")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.11.4	删除消息
		接口描述：该接口用于删除消息。
		HTTP Method：POST
		Request URL： http://hostname:port/xcgj-app-ws/ws/0.1/message/delete
		Param: {"items":[{"id":1}, {"id":2}]}
		{
			"items": [{"id": "503"}, {"id": "*"}]
		}
		Return Value: 
			成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postMessagedelete(Map<String, String> data) {  
		String methodName="postMessagedelete";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	Map<String, Object> params = new HashMap<String, Object>();
		   	String obj = "[{'id':'48499999'}, {'id':'*'}]";
	 		params.put("items",obj);
	 		JSONObject jsonParam = JSONObject.fromObject(params);
		   	jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,true,cookie_V[0], cookie_V[1]);
			   	if(!jsonResult.get("status").equals("200"))
					setFailData(data, null, jsonResult,methodName);
			}catch (AssertionError ae) {
				setFailData(data, ae, jsonResult,methodName);
				throw ae;
			} catch (Exception e) {
				setFailData(data, e, jsonResult,methodName);
			}finally{
				Write2Excel.report2Excel(data, method);
			}
	}
	/**
	 * 1.12	设置
		1.12.1	请求消息通知状态
		接口描述：请求当前终端消息通知状态信息；
		HTTP Method：GET
		Request URL： http://hostname:port/xcgj-app-ws/ws/0.1/monitor/setting/message/notification
		Return Value: 
			成功：Status.OK
		{"ignate":true,"displace":true,"shake":true,"level":true,"fuel":true,"lowFuel":true,"battery":true,"lowBattery":true,"trip":true,
		"weekReport":true, "remindSwitch": false,"remindStartTime": xx:xx,
		"remindEndTime": xx:xx}
		其中：
		ignate：点火报警；
		displace：移位报警；
		shake：震动报警；
		level为震动灵敏度；
		fuel：油量报警
		lowFuel：油量报警阀值
		battery：电压报警；
		lowBattery：电压报警阀值
		trip:单次行程报告；
		weekReport:  每周数据报告；
		remindSwitch:勿扰模式开关;
		remindStartTime: 勿扰模式起始时间;
		remindEndTime: 勿扰模式结束时间;
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getMessagenotification(Map<String, String> data) {  
		String methodName="getMessagenotification";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("ignate")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.12.2	设置消息通知状态
		接口描述：设置消息通知状态。 
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/monitor/setting/message/notification
		Param
		 {"ignate":true,"displace":true,"shake":true,"level":true,"fuel":true,"lowFuel":true,"battery":true,"lowBattery":true,"trip":true,
		"weekReport":true }
		Return Value: 
			成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postMessagenotification(Map<String, String> data) {  
		String methodName="postMessagenotification";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("ignate",false);
	 		params.put("displace",true);
	 		System.out.println(params);
	 		JSONObject jsonParam = JSONObject.fromObject(params);
			jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,true,cookie_V[0], cookie_V[1]);
		   	if(!jsonResult.get("status").equals("200"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.14	电子围栏
		1.14.1	查询围栏列表
		接口描述：该接口用于查询当前用户的所有围栏列表, 需要登录.
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/fence/list
		Return Value：
		成功：Status.OK
		{"count":xxx, "items":[{"id": xxx, name:"xxx", radius:xxx, remind:0/1/2, lat:xxx, lng: xxx, address: xxx, created: "2015-xx-xx"}]}
		其中：
		count：围栏数量；id:围栏编号；name: 围栏名称； radius: 围栏半径, 单位为米(m),；remind:进出围栏提醒(0:进入提醒,1: 离开围栏提醒, 2: 进入+离开围栏提醒), lat:纬度,lng: 经度, address: 围栏的位置描述, created: 围栏创建时间
		排序规则: 按照创建时间,升序排列, 最先创建的围栏, 在最前面.
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getFencelist(Map<String, String> data) {  
		String methodName="getFencelist";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult = HttpClientRequest.getJson4Cookies(url,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("count")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.14.2	添加围栏
		接口描述：该接口用于给当前用户的车辆添加围栏监控.
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/fence/add
		参数：{"name:"xxx, "radius":xxx, "remind":0/1/2, "lat":xxx, "lng": xxx, "address": xxx}
		参数说明: name: 围栏名称, radius: 围栏半径, 单位为米(m), 但省略单位,  remind:进出围栏提醒(0:进入围栏提醒,1: 离开围栏提醒, 2: 进入+离开围栏提醒), lat:纬度,lng: 经,address: 围栏的位置描述
		Return Value：
		成功：Status.OK
		{"id": xxxx}
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postFenceadd(Map<String, String> data) {  
		String methodName="postFenceadd";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("name","test￥￥*？？test");
	 		params.put("remind",2);
	 		params.put("radius",500);
	 		params.put("lat",2);
	 		params.put("lng",2);
	 		params.put("address","test\\(^o^)/`$*`~");
	 		System.out.println(params);
	 		JSONObject jsonParam = JSONObject.fromObject(params);
		   	jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("id")==null)
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.14.3	删除围栏
		接口描述：该接口用于给当前用户的车辆添加围栏监控.
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/fence/delete
		参数：{"id": xxx}		id: 围栏的id标识
		Return Value：
		成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postFencedelete(Map<String, String> data) {  
		String methodName="postFencedelete";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("id","11710");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,false,cookie_V[0], cookie_V[1]);
		   	if(jsonResult.get("message").toString().contains("删除失败，请稍后重试"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.14.4	修改围栏
		接口描述：该接口用于修改围栏信息.
		HTTP Method：POST
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/fence/update
		参数：{"id": xxx, "name":xxx, "radius":xxx, "remind":0/1/2, "lat":xxx, lng": xxx, "address": xxx}
		参数说明: id:围栏编号, name: 围栏名称, radius: 围栏半径, 单位为米(m), 但省略单位,  remind:进出围栏提醒(0:进入围栏提醒,1: 离开围栏提醒, 2: 进入+离开围栏提醒), latlng: 围栏位置, address: 围栏的位置描述
		Return Value：
		成功：Status.OK
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void postFenceupdate(Map<String, String> data) {  
		String methodName="postFenceupdate";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
		   	Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("id","11697");
	 		params.put("name","update");
	 		JSONObject jsonParam = JSONObject.fromObject(params);
	 		jsonResult = HttpClientRequest.postJson4Cookies(url,jsonParam,false,cookie_V[0], cookie_V[1]);
		   	if(!jsonResult.get("message").toString().contains("没有获取到中心点坐标"))
				setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	/**
	 * 1.15	活动中心
		1.15.1	获取最新活动信息
		接口描述：该接口用于获取最新的活动信息，不用登录。
		HTTP Method：GET
		Request URL：http://hostname:port/xcgj-app-ws/ws/0.1/activity
		Return Value：
			成功：Status.OK
		{"id":xxx, "name":xxx, "imgUrl":xxx,"linked":xxx}
	 */
	@Test(dataProvider = "dp", dataProviderClass = StaticProvider.class)
	public  void getActivity(Map<String, String> data) {  
		String methodName="getActivity";
		Method method=null;
		JSONObject jsonResult=null;
		try {
			method=getClass().getMethod(methodName,Map.class);
			data = Write2Excel.recordExcel(data, method);
			String url = DriverUtil.getUrl(methodName).replace("host", host);
			jsonResult= HttpClientRequest.getJson4Cookies(url,false);
		   	if(jsonResult.get("name")==null)
					setFailData(data, null, jsonResult,methodName);
		}catch (AssertionError ae) {
			setFailData(data, ae, jsonResult,methodName);
			throw ae;
		} catch (Exception e) {
			setFailData(data, e, jsonResult,methodName);
		}finally{
			Write2Excel.report2Excel(data, method);
		}
	}
	private void setFailData(Map<String,String> data,Throwable e,JSONObject json,String methodName){
		Reporter.log("测试方法"+methodName+"失败");
		data.put("测试结果", "FAIL");
		if(json!=null)
			data.put("实际输出", "［验证点］不通过，jsonResult:"+json.get("message")+"status:"+json.get("status"));
		else
			data.put("实际输出", "［验证点］不通过，Throwable:"+e.toString());
	}
	@Test
	public void dateTest() throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException{
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date date =new Date();
//		System.out.println(sdf.format(date));
//		String st = cookie_V[0];
//		System.out.println(st);
//		String url = "http://10.23.211.68/xcgj-app-ws/ws/0.1/advertisement";
//	   	JSONObject jsonObject = GetCookies.getJson4Cookies(url,false);
//	   	System.out.println(jsonObject);
		 String url = "https://developer.apple.com";  
//		 String body = HttpClientRequest.send(url, null, "utf-8");  
		 System.out.println("交易响应结果：");  
//		 System.out.println(body);  
		 System.out.println("-----------------------------------");  
	}
}
