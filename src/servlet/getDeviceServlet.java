package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tools.HttpTools;

public class getDeviceServlet extends HttpServlet {

	//要筛选返回的hub
	String []hubs={
			"CC:1B:E0:E0:0F:FC",
			"CC:1B:E0:E0:24:58",
			"CC:1B:E0:E0:1C:3C"
	};
	
	HttpTools httpTool=new HttpTools();
	/**
	 * Constructor of the object.
	 */
	public getDeviceServlet() {
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
		PrintWriter out = response.getWriter();
	
		String token=request.getParameter("token");
		String all_result=httpTool.getDevices(token); //获取的所有设备的列表
		System.out.println("接收到结果："+all_result);
		JsonObject resultObject=new JsonObject();
		JsonObject jsonObject=null;
		String result=""; //返回给客户端的结果
		try{
			jsonObject=new JsonParser().parse(all_result).getAsJsonObject();
			for(int i=0;i<hubs.length;i++)
			{
				if(jsonObject.has(hubs[i]))
				{
				    resultObject.add(hubs[i], jsonObject.get(hubs[i]).getAsJsonArray());	
				}
			}
			result=resultObject.toString();
		}catch(Exception e)
		{
		    e.printStackTrace();
		    //out.println(result);
		    result="{\"error\":\"token not found or overdue\"}";
		}
		out.println(result);
		//System.out.println("得到设备："+resultObject.toString());	
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
		    return ;
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
