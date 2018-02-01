package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class SendHttp {

	
	/*����get����
	 * 
	 */
	public static String sendGet(String url,Map<String ,String> params)
	{
		String result="";//���صĽ��
		BufferedReader in=null; //��ȡ��Ӧ��
		StringBuffer sb=new StringBuffer(); //�洢����
		String pa ="";// ����֮��Ĳ���
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
			
			//����URL����
			java.net.URL connURL=new java.net.URL(full_url);
			//��URL����
			java.net.HttpURLConnection httpConn=(java.net.HttpURLConnection)connURL.openConnection();
			//����ͨ������
			httpConn.setRequestProperty("Accept", "/*");
			httpConn.setRequestProperty("Connection","Keep-Alive");
			//httpConn.setRequestProperty("","");
			//����ʵ�ʵ�����
			httpConn.connect();
			//��ȡ��Ӧͷ
			Map<String,List<String>> headers=httpConn.getHeaderFields();
			//������Ӧͷ�ֶ�
			for(String key:headers.keySet())
			{
				System.out.println("key:"+key+",content:"+headers.get(key));
			}
			//����BufferedReader ����������ȡURL����Ӧ�������ñ��뷽ʽ
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
	 * ����post����
	 */
	public static String sendPost(String url,Map<String,String> params)
	{
	    String result="";//���صĽ��
	    BufferedReader in=null; //��ȡ������
	    PrintWriter out=null;
	    StringBuffer sb=new StringBuffer(); //�����������
	    String pa=""; //����֮��Ĳ���
	    
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
	      //����URL����
	      java.net.URL connURL=new java.net.URL(url);
	      //��URL����
	      java.net.HttpURLConnection httpConn=(java.net.HttpURLConnection)connURL.openConnection();
	      //����ͨ������
	      httpConn.setRequestProperty("Accept", "/*");
	      httpConn.setRequestProperty("Connection", "Keep-Alive");
	    
	      //httpConn.setRequestProperty("", "");
	      //����post��ʽ
	      httpConn.setDoInput(true);
	      httpConn.setDoOutput(true);
	      out=new PrintWriter(httpConn.getOutputStream());
	      //�����������
	      out.write(pa);
	      //flush������Ļ���
	      out.flush();
	      //����BufferedReader ����������ȡURL����Ӧ�������ñ��뷽ʽ
	      in=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
	      String line;
	      //��ȡ��������
	      while((line=in.readLine())!=null)
	      {
	    	 result+=line; 
	    	 System.out.println("post����line:"+line);
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
