package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.HttpTools;

public class getDataServlet extends HttpServlet {

	HttpTools httpTool=new HttpTools();
	/**
	 * Constructor of the object.
	 */
	public getDataServlet() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		/*
		String event=request.getParameter("event");
		String chip=request.getParameter("chip");
		String active=request.getParameter("active");
		*/
		String mac=request.getParameter("mac");
		String token=request.getParameter("token");
		
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("event","1");
		map.put("chip", "0");
		map.put("active","1");
		map.put("mac", mac);
		map.put("access_token", token);
		
		//打印数据
		/*
		System.out.println("event:"+event);
		System.out.println("chip:"+chip);
		System.out.println("active:"+active);
		*/
		System.out.println("mac:"+mac);
		System.out.println("token:"+token);
		
		//String url="http://api2.cassianetworks.com/gap/nodes/";
		//String url="http://hengbang.cassia.pro:8080/api/gap/nodes/";
		httpTool.getData(map, response);
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
				
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		System.out.println("获取数据servlet开始");
	}

}
