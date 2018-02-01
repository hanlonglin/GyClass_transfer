package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class SendHttp {

	
	/*发送get请求
	 * 
	 */
	public static String sendGet(String url,Map<String ,String> params)
	{
		String result="";//返回的结果
		BufferedReader in=null; //读取响应流
		StringBuffer sb=new StringBuffer(); //存储参数
		String pa ="";// 编码之后的参数
		try{
			if(params.size()==1){
				for(String name:params.keySet()){
					sb.append(name).append("=").append(
				        java.net.URLEncoder.encode(params.get(name),"UTF-8"));
				}
				pa=sb.toString();
			}else{
				for(String name:params.keySet())
				{
					sb.append(name).append("=").append(
							java.net.URLEncoder.encode(params.get(name),"UTF-8")).append("&");
				}
				String temp_pa=sb.toString();
				pa=temp_pa.substring(0,temp_pa.length()-1);
			}
			
			String full_url=url+"?"+pa;
			System.out.println("url:"+full_url);
			
			//创建URL对象
			java.net.URL connURL=new java.net.URL(full_url);
			//打开URL连接
			java.net.HttpURLConnection httpConn=(java.net.HttpURLConnection)connURL.openConnection();
			//设置通用属性
			httpConn.setRequestProperty("Accept", "/*");
			httpConn.setRequestProperty("Connection","Keep-Alive");
			//httpConn.setRequestProperty("","");
			//建立实际的连接
			httpConn.connect();
			//获取响应头
			Map<String,List<String>> headers=httpConn.getHeaderFields();
			//遍历响应头字段
			for(String key:headers.keySet())
			{
				System.out.println("key:"+key+",content:"+headers.get(key));
			}
			//定义BufferedReader 输入流来读取URL的响应，并设置编码方式
			in=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
			String line;
			while((line=in.readLine())!=null)
			{
				result+=line;
				System.out.println("line:"+line);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			try{
				if(in!=null){
					in.close();
				}
			}catch(IOException eo)
			{
				eo.printStackTrace();
			}
		}
		
		return result;
	}
	/*
	 * 发送post请求
	 */
	public static String sendPost(String url,Map<String,String> params)
	{
	    String result="";//返回的结果
	    BufferedReader in=null; //读取输入流
	    PrintWriter out=null;
	    StringBuffer sb=new StringBuffer(); //处理请求参数
	    String pa=""; //编码之后的参数
	    
	    try{
	      if(params.size()==1)
	      {
	    	  for(String name:params.keySet()){
	    	      sb.append(name).append("=").append(
	    	    		  java.net.URLEncoder.encode(params.get(name),"UTF-8"));	  
	    	  }
	    	  pa=sb.toString();
	      }else{
	    	  for(String name:params.keySet())
	    	  {
	    		  sb.append(name).append("=").append(
	    				  java.net.URLEncoder.encode(params.get(name),"UTF-8")).append("&");
	    	  }
	    	  String temp_pa=sb.toString();
	    	  pa=temp_pa.substring(0,temp_pa.length()-1);
	      }
	      //创建URL对象
	      java.net.URL connURL=new java.net.URL(url);
	      //打开URL连接
	      java.net.HttpURLConnection httpConn=(java.net.HttpURLConnection)connURL.openConnection();
	      //设置通用属性
	      httpConn.setRequestProperty("Accept", "/*");
	      httpConn.setRequestProperty("Connection", "Keep-Alive");
	    
	      //httpConn.setRequestProperty("", "");
	      //设置post方式
	      httpConn.setDoInput(true);
	      httpConn.setDoOutput(true);
	      out=new PrintWriter(httpConn.getOutputStream());
	      //发送请求参数
	      out.write(pa);
	      //flush输出流的缓冲
	      out.flush();
	      //定义BufferedReader 输入流来读取URL的响应，来设置编码方式
	      in=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
	      String line;
	      //读取返回内容
	      while((line=in.readLine())!=null)
	      {
	    	 result+=line; 
	    	 System.out.println("post接收line:"+line);
	      }
	     
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally{
	    	try{
	    		if(out!=null)
	    		{
	    			out.close();
	    		}
	    		if(in!=null){
	    			in.close();
	    		}
	    	}catch(IOException eo){
	    		eo.printStackTrace();
	    	}
	    }
	    
	    return result;
	}
}
