package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.bcel.internal.generic.Type;

import tools.Base64Util;
import tools.HttpTools;
import tools.SendHttp;

public class getTokenServlet extends HttpServlet {
	
	private static String UNAME="snstest";
	private static String PASSWD="hbedu";
	
	HttpTools httpTool=new HttpTools();
	
	Logger logger=Logger.getLogger(getTokenServlet.class);
	/**
	 * Constructor of the object.
	 */
	public getTokenServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		/****���Լ��ܽ���**json����****/
		/*
		String test="aGVuZ2Jhbmc6NzUxNzM2ZjlmY2UyMjdmMw==";
		String s=new Base64Util().decrypt(test);
		System.out.println("���ܵ�result:"+s);
		
		String js="{\"uname\":\"hanlonglin\",\"passwd\":\"123456\"}";
		
		JsonObject jsonObject=new JsonParser().parse(js).getAsJsonObject();
		System.out.println("json���� uname:"+jsonObject.get("uname").getAsString());
		System.out.println("json���� passwd:"+jsonObject.get("passwd").getAsString());
	    */
		/*********************/
		
		String uname=request.getParameter("uname").toString();
		String pwd=request.getParameter("passwd").toString();	
		System.out.println("uname:"+uname+",passwd:"+pwd);
		
		String result="0";
		if(uname.equals(UNAME)&&pwd.equals(PASSWD)){
		    result=httpTool.getToken();
		    System.out.println("��ȡ��token��"+result);
		    logger.debug(uname+","+pwd+" ��½�ɹ����ͻ���IP:"+httpTool.getClientIP(request));
		}else{
			result="0";
			logger.warn(uname+","+pwd+" ��½ʧ�ܣ��ͻ���IP:"+httpTool.getClientIP(request));
		}
		
		if(result.equals("fail"))
		{
			logger.error("�û���:"+uname+",���룺"+pwd+",��������ȡtokenʧ�ܣ��ͻ��˵�ַ:"+request.getRemoteAddr());
		}
		
		//����result
		out.println(result);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		//�����Ŀ��Ŀ¼
		String root=getServletContext().getRealPath("/");
		//System.setProperty("GyClassRoot", root);
	}

}
