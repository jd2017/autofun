package cn.jd.interfacedemo.config;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.jd.interfacedemo.instrumentsdriver.InstrumentDriverTestCase;



/**
 * @author jd.bai
 * @date 2017年5月18日
 * @time 下午5:29:31 
 * @cookies: 
 */
public class HttpClientRequest{
	static Logger logger = Logger.getLogger(HttpClientRequest.class.getName());
	private static String host = InstrumentDriverTestCase.host;
	/**
	 * json对象 post获取cookies
	 * @param url
	 * @param jsonParam
	 * @param noNeedResponse
	 * @param strs 指定 cookie cookieValue
	 * @return
	 */
	public static JSONObject postJson4Cookies(String url, JSONObject jsonObject,boolean noNeedResponse,String ...strs) {
		JSONObject jsonResult = null;
		//create client
		CloseableHttpClient client = HttpClients.createDefault();
		//create context
		HttpClientContext context = HttpClientContext.create();
		HttpPost post = new HttpPost(url);
		if(jsonObject!=null){
			StringEntity entity = new StringEntity(jsonObject.toString(),"UTF-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			post.setEntity(entity);
		}
		//存储result数据；
		Map<String, String> map = new HashMap<String, String>();
		//response for result；
		String result = null;
		try {
			if(strs.length>1){
				BasicCookieStore basicCS = setCookies(strs[0],strs[1]);
				context.setAttribute(HttpClientContext.COOKIE_STORE,
						basicCS);
			}
			CloseableHttpResponse httpResponse = client.execute(post, context);
			CookieStore cookieStore = context.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			Iterator<Cookie> cookiesItr = cookies.iterator();
			while (cookiesItr.hasNext()) {
				Cookie cookieImp = cookiesItr.next();
				logger.info("cookies:"+cookieImp.toString());
			}
			//response for result；
			result  = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			map.put("status", "200");
			/** 请求发送成功，并得到响应 **/
			if (statusCode == HttpStatus.SC_OK) {
				map.put("status", "200");
				if (noNeedResponse) {
					// response 返回非json数据时；
					jsonResult = JSONObject.fromObject(map);
					return jsonResult;
				}
				System.out.println("Resutl:"+result);
				//把字符串转换成json对象
				jsonResult = JSONObject.fromObject(result);
			} else {
				logger.info(result);
				map.put("status",Integer.toString(statusCode));
				map.put("message",result);
				jsonResult = JSONObject.fromObject(map);
			}
			httpResponse.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	/**
	 * Get请求，携带cookie
	 * @param url
	 * @param cookie
	 * @param cookieValue
	 * @return json 对象
	 */
	public static JSONObject getJson4Cookies(String url,boolean noNeedResponse,String ...strs){
		
		CloseableHttpClient client = HttpClients.createDefault();
		//create context
		HttpClientContext context = HttpClientContext.create();
		//Illegal character in query at index 70: 解决空格问题；
		url = url.replaceAll(" ", "%20");
		HttpGet get =new HttpGet(url);
		
		// 设置cookie内容；
		if(strs.length>1){
			BasicCookieStore basicCS = setCookies(strs[0],strs[1]);
			context.setAttribute(HttpClientContext.COOKIE_STORE,
					basicCS);
//			get.addHeader("Content-Type","application/json; charset=" + "UTF-8");	//request set for json
		}
		JSONObject jsonResult = null;
		try {
			CloseableHttpResponse response = client.execute(get,context);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			Map<String, String> map = new HashMap<String, String>();
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK){
				map.put("status", "200");
				if (noNeedResponse) {
					// response 返回非json数据时；
					jsonResult = JSONObject.fromObject(map);
					return jsonResult;
				}
				jsonResult=JSONObject.fromObject(result);
				System.out.println(jsonResult);
			}else{
				map.put("message",result);
				map.put("status",Integer.toString(status));
				jsonResult = JSONObject.fromObject(map);
			}
			logger.info(jsonResult.toString());
		} catch (Exception e) {
			logger.info("发生异常：" + e);
		} finally {
			get.releaseConnection();
		}
		return jsonResult;
	}
	/**
	 * get请求，String 返回值
	 * @param url
	 * @param charset
	 * @return 
	 * @throws Exception
	 */
	public static String doGetRequest(String url, String charset) {

		CloseableHttpClient client =HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		if (null == url || !url.startsWith("http")) {
			logger.info("请求地址格式不对");
		}
		// 设置请求的编码方式
		if (null != charset) {
			method.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + charset);
		} else {
			method.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + "utf-8");
		}
		CloseableHttpResponse response;
		String resonseBody = null;
		try {
			response = client.execute(method);
			int status = response.getStatusLine().getStatusCode();
			if (status != HttpStatus.SC_OK) {// 打印服务器返回的状态
				logger.info("Method failed: " + status);
			}
			resonseBody = EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.info("response:" + response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			// 释放连接
			method.releaseConnection();
		}
		return resonseBody;
	}
	/**
	 * 设置 Domain 、time
	 * @param co
	 * @param coValue
	 * @return
	 */
	private static BasicCookieStore setCookies(String co,String coValue){
		
		BasicClientCookie2 cookie = new BasicClientCookie2(co, coValue);
		cookie.setDomain(host);
		cookie.setPath("/");
	
		 Date date = setDate();
		 //设置有效时间
		 cookie.setExpiryDate(date);
		 BasicCookieStore basicCS = new BasicCookieStore();
		 basicCS.addCookie(cookie);
		 return basicCS;
	}
	/**
	 * 指定有效期增加一天；
	 * @return
	 */
	public static Date setDate(){
		Date date=new Date();//取时间 
	     Calendar calendar = new GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
	     date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
	   return date;
	}
	/** 
	 * 绕过验证 
	 *   
	 * @return 
	 * @throws NoSuchAlgorithmException  
	 * @throws KeyManagementException  
	 */  
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
	    SSLContext sc = SSLContext.getInstance("SSLv3");  
	  
	    // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法  
	    X509TrustManager trustManager = new X509TrustManager() {  
	        @Override  
	        public void checkClientTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) {  
	        }  
	        @Override  
	        public void checkServerTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) {  
	        }  
	        @Override  
	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	            return null;  
	        }  
	    };  
	    sc.init(null, new TrustManager[] { trustManager }, null);  
	    return sc;  
	}  
	/** 
	 * 模拟请求 
	 *  
	 * @param url       资源地址 
	 * @param map   参数列表 
	 * @param encoding  编码 
	 * @return 
	 * @throws NoSuchAlgorithmException  
	 * @throws KeyManagementException  
	 * @throws IOException  
	 * @throws ClientProtocolException  
	 */  
	public static String send(String url, Map<String,String> map,String encoding) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {  
	    String body = "";  
	    //采用绕过验证的方式处理https请求  
	    SSLContext sslcontext = createIgnoreVerifySSL();  
	      
	       // 设置协议http和https对应的处理socket链接工厂的对象  
	       Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
	           .register("http", PlainConnectionSocketFactory.INSTANCE)  
	           .register("https", new SSLConnectionSocketFactory(sslcontext))  
	           .build();  
	       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
	       HttpClients.custom().setConnectionManager(connManager);  
	  
	       //创建自定义的httpclient对象  
	    CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();  
//	       CloseableHttpClient client = HttpClients.createDefault();  
	    //创建post方式请求对象  
	    HttpGet httpPost = new HttpGet(url);  
	    //装填参数  
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
	    if(map!=null){  
	        for (Entry<String, String> entry : map.entrySet()) {  
	            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
	        }  
	    }  
	    //设置参数到请求对象中  
//	    httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  
	  
	    System.out.println("请求地址："+url);  
	    System.out.println("请求参数："+nvps.toString());  
	      
	    //设置header信息  
	    //指定报文头【Content-type】、【User-Agent】  
	    httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
	    httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	      
	    //执行请求操作，并拿到结果（同步阻塞）  
	    CloseableHttpResponse response = client.execute(httpPost);  
	    //获取结果实体  
	    HttpEntity entity = response.getEntity();  
	    if (entity != null) {  
	        //按指定编码转换结果实体为String类型  
	        body = EntityUtils.toString(entity, encoding);  
	    }  
	    EntityUtils.consume(entity);  
	    //释放链接  
	    response.close();  
	       return body;  
	}  

}
