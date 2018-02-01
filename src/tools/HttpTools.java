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
	 * ����get���� ��ȡ�ֻ����ݣ� �����ӣ���ͣ��ȡ url�̶�������event=1,chip=0�̶�,mac��token��Ҫ�ϴ�
	 * ������failʱ�������쳣
	 * ������endʱ����������
	 */
	// private static String
	// DATA_url="http://api2.cassianetworks.com/gap/nodes/";
	
	
	private static String DATA_url="http://hengbang.cassia.pro:8080/api/gap/nodes/";
	public void getData( Map<String, String> params,
			HttpServletResponse response) {
		String result = "";// ���صĽ��
		BufferedReader inData = null; // ��ȡ��Ӧ��
		StringBuffer sb = new StringBuffer(); // �洢����
		String pa = "";// ����֮��Ĳ���
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

			// ����URL����
			java.net.URL connURL = new java.net.URL(full_url);
			// ��URL����
			java.net.HttpURLConnection httpConnData = (java.net.HttpURLConnection) connURL
					.openConnection();
			// ����ͨ������
			httpConnData.setRequestProperty("Accept", "/*");
			httpConnData.setRequestProperty("Connection", "Keep-Alive");
			httpConnData.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConnData.setConnectTimeout(30000);
			// httpConn.setRequestProperty("","");
			// ����ʵ�ʵ�����
			httpConnData.connect();
			// ��ȡ��Ӧͷ
			Map<String, List<String>> headers = httpConnData.getHeaderFields();
			// ������Ӧͷ�ֶ�
			for (String key : headers.keySet()) {
				System.out.println("key:" + key + ",content:"
						+ headers.get(key));
			}
			int code = httpConnData.getResponseCode();
			if (code == 200) {
				// ����ɹ�
				// ����BufferedReader ����������ȡURL����Ӧ�������ñ��뷽ʽ
				inData = new BufferedReader(new InputStreamReader(httpConnData
						.getInputStream(), "UTF-8"));
				String line;
				while ((line = inData.readLine()) != null) {
					//System.out.println("���ݣ�" + line);
					response.getWriter().println(line);
					response.flushBuffer();
				}
			} else {
				// ����ʧ��
				response.getWriter().println("fail");
			}
		} catch (Exception e) {
			System.out.println("�쳣��" + e.toString());
			e.printStackTrace();
		} finally {
		    try {
				response.getWriter().println("end");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("������finally");
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
	//�ر����ݳ�����
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
	 * ����post���� ����Ϊjson �����ȡtoken url,json,header �̶�
	 * ���ؽ����
	 *      �ɹ����� ����access_token��json�ַ���
	 *      ʧ�ܷ���fail
	 */
	//private static String REQUEST_url = "http://api2.cassianetworks.com/oauth2/token";
	private static String TOKEN_url = "http://hengbang.cassia.pro:8080/api/oauth2/token";
	private static String TOKEN_json = "{  \"grant_type\": \"client_credentials\"}"; // �����json
	private static String TOKEN_HEADER_Authorization = "Basic aGVuZ2Jhbmc6NzUxNzM2ZjlmY2UyMjdmMw=="; //�û������룬 ͨ��Base64�����ַ��� ��hengbang:751736f9fce227f3������ 

	public String getToken() {
		String result = "";// ���صĽ��
		BufferedReader in = null; // ��ȡ������
		DataOutputStream out = null;
		StringBuffer sb = new StringBuffer(); // �����������

		try {
			// ����URL����
			java.net.URL connURL = new java.net.URL(TOKEN_url);
			// ��URL����
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();
			// ����ͨ������
			httpConn.setRequestProperty("Accept", "application/json");
			// httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			httpConn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConn.setRequestProperty("Authorization",
					TOKEN_HEADER_Authorization);

			// httpConn.setRequestProperty("", "");
			// ����post��ʽ
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.connect();

			out = new DataOutputStream(httpConn.getOutputStream());
			// �����������
			System.out.println("�����json��" + TOKEN_json);
			out.write(TOKEN_json.getBytes());
			// flush������Ļ���
			out.flush();
			out.close();
			// ����BufferedReader ����������ȡURL����Ӧ�������ñ��뷽ʽ
			int code = httpConn.getResponseCode();
			if (code == 200) {
				in = new BufferedReader(new InputStreamReader(httpConn
						.getInputStream(), "UTF-8"));
			} else {
				//in = new BufferedReader(new InputStreamReader(httpConn
				//		.getErrorStream(), "UTF-8"));
				return "fail"; //����ʧ�ܷ���fail
			}
			// System.out.println("request ����ͷ��Ϣ::"+httpConn.getRequestProperties().toString());
			System.out.println("response ��Ϣ::" + httpConn.getResponseMessage());
			String line;
			// ��ȡ��������
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
	
	
	//***************��ȡ�ͻ��˵�ip��mac******************************************
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
