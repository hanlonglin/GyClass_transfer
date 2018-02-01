package tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import servlet.getTokenServlet;

public class HttpTools {
	/*
	 * 发送get请求 获取手环数据， 长连接，不停获取 url固定，参数event=1,chip=0固定,mac和token需要上传
	 * 当返回fail时，出现异常
	 * 当返回end时，结束返回
	 */
	// private static String
	// DATA_url="http://api2.cassianetworks.com/gap/nodes/";
	
	
	private static String DATA_url="http://hengbang.cassia.pro:8080/api/gap/nodes/";
	public void getData( Map<String, String> params,
			HttpServletResponse response) {
		String result = "";// 返回的结果
		BufferedReader inData = null; // 读取响应流
		StringBuffer sb = new StringBuffer(); // 存储参数
		String pa = "";// 编码之后的参数
		try {
			if (params.size() == 1) {
				for (String name : params.keySet()) {
					if (!name.equalsIgnoreCase("mac")) {
						sb.append(name).append("=").append(
								java.net.URLEncoder.encode(params.get(name),
										"UTF-8"));
					} else {
						sb.append(name).append("=").append(params.get(name));
					}
				}
				pa = sb.toString();
			} else {
				for (String name : params.keySet()) {
					System.out.println("params:" + params.get(name));
					System.out.println("after encode:"
							+ java.net.URLEncoder.encode(params.get(name),
									"UTF-8"));
					if (!name.equalsIgnoreCase("mac")) {
						sb.append(name).append("=").append(
								java.net.URLEncoder.encode(params.get(name),
										"UTF-8")).append("&");
					} else {
						sb.append(name).append("=").append(params.get(name))
								.append("&");
					}
				}
				String temp_pa = sb.toString();
				pa = temp_pa.substring(0, temp_pa.length() - 1);
			}

			String full_url = DATA_url + "?" + pa;
			System.out.println("url:" + full_url);

			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConnData = (java.net.HttpURLConnection) connURL
					.openConnection();
			// 设置通用属性
			httpConnData.setRequestProperty("Accept", "/*");
			httpConnData.setRequestProperty("Connection", "Keep-Alive");
			httpConnData.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConnData.setConnectTimeout(30000);
			// httpConn.setRequestProperty("","");
			// 建立实际的连接
			httpConnData.connect();
			// 获取响应头
			Map<String, List<String>> headers = httpConnData.getHeaderFields();
			// 遍历响应头字段
			for (String key : headers.keySet()) {
				System.out.println("key:" + key + ",content:"
						+ headers.get(key));
			}
			int code = httpConnData.getResponseCode();
			if (code == 200) {
				// 请求成功
				// 定义BufferedReader 输入流来读取URL的响应，并设置编码方式
				inData = new BufferedReader(new InputStreamReader(httpConnData
						.getInputStream(), "UTF-8"));
				String line;
				while ((line = inData.readLine()) != null) {
					//System.out.println("数据：" + line);
					response.getWriter().println(line);
					response.flushBuffer();
				}
			} else {
				// 请求失败
				response.getWriter().println("fail");
			}
		} catch (Exception e) {
			System.out.println("异常：" + e.toString());
			e.printStackTrace();
		} finally {
		    try {
				response.getWriter().println("end");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("结束：finally");
			try {
				if (inData != null) {
					inData.close();
				}
			} catch (IOException eo) {
				eo.printStackTrace();
			}
		}

		// return result;
	}
	/*
	//关闭数据长连接
	public void closeData()
	{
	    try{
	    	if(inData!=null)
	    		inData.close();
	    	if(httpConnData!=null)
	    		httpConnData.disconnect();
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
*/
	/*
	 * 发送post请求 参数为json 请求获取token url,json,header 固定
	 * 返回结果：
	 *      成功返回 带有access_token的json字符串
	 *      失败返回fail
	 */
	//private static String REQUEST_url = "http://api2.cassianetworks.com/oauth2/token";
	private static String TOKEN_url = "http://hengbang.cassia.pro:8080/api/oauth2/token";
	private static String TOKEN_json = "{  \"grant_type\": \"client_credentials\"}"; // 请求的json
	private static String TOKEN_HEADER_Authorization = "Basic aGVuZ2Jhbmc6NzUxNzM2ZjlmY2UyMjdmMw=="; //用户名密码， 通过Base64加密字符串 ‘hengbang:751736f9fce227f3’得来 

	public String getToken() {
		String result = "";// 返回的结果
		BufferedReader in = null; // 读取输入流
		DataOutputStream out = null;
		StringBuffer sb = new StringBuffer(); // 处理请求参数

		try {
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(TOKEN_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "application/json");
			// httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			httpConn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConn.setRequestProperty("Authorization",
					TOKEN_HEADER_Authorization);

			// httpConn.setRequestProperty("", "");
			// 设置post方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.connect();

			out = new DataOutputStream(httpConn.getOutputStream());
			// 发送请求参数
			System.out.println("请求的json：" + TOKEN_json);
			out.write(TOKEN_json.getBytes());
			// flush输出流的缓冲
			out.flush();
			out.close();
			// 定义BufferedReader 输入流来读取URL的响应，来设置编码方式
			int code = httpConn.getResponseCode();
			if (code == 200) {
				in = new BufferedReader(new InputStreamReader(httpConn
						.getInputStream(), "UTF-8"));
			} else {
				//in = new BufferedReader(new InputStreamReader(httpConn
				//		.getErrorStream(), "UTF-8"));
				return "fail"; //请求失败返回fail
			}
			// System.out.println("request 请求头信息::"+httpConn.getRequestProperties().toString());
			System.out.println("response 信息::" + httpConn.getResponseMessage());
			String line;
			// 读取返回内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException eo) {
				eo.printStackTrace();
			}
		}

		return result;
	}
	
	
	//***************获取客户端的ip和mac******************************************
	public String getClientIP(HttpServletRequest request)
	{
		String ip=request.getHeader("x-forwarded-for");
		if(ip==null||ip.length()==0||ip.equalsIgnoreCase("unknown"))
		{
			ip=request.getHeader("Proxy-Client-IP");
		}
		if(ip==null||ip.length()==0||ip.equalsIgnoreCase("unknown"))
		{
			ip=request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip==null||ip.length()==0||ip.equalsIgnoreCase("unknown"))
		{
			ip=request.getRemoteAddr();
		}
		return ip;		
	}
	/*
	public String getClientMac(String ip)
	{
		String str="";
		String macAddress="";
		try{
			
		}catch(Exception e)
		{
		   e.printStackTrace();	
		}
		
		return str;
	}
	*/
}
