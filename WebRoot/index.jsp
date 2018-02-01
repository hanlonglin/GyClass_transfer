<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
 
		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
<!-- 	  
		<form action="getTokenServlet" method="post">
			<input type="text" name="uname" value="" />
			<input type="password" name="passwd" value="" />
			<input type="submit" value="提交" />
		</form>

		<form action="getDataServlet" method="get">
			 
			</br>
			<input type="text" name="event" value="1" />
			</br>
			<input type="text" name="chip" value="0" />
			</br>
			<input type="text" name="active" value="1" />
		
			</br>
			<input type="text" name="mac" value="CC:1B:E0:E0:0F:FC" />
			</br>
			<input type="text" name="token"
				value="6165d2051d1ae8d08ded15ecc38f6e07a8dc11639fd3db2b88b935e70e5cfa80f651b450edd8bb3a38230a0f05ce4712a5ae917e9c3b8f75c7089c2c44bf1e43" />
			</br>
			<input type="submit" value="提交" />
		</form>

		<form action="sendServlet" method="get">
			<input type="text" name="uname" value="111" />
			<input type="submit" value="提交" />
		</form>
 -->   
	</body>
</html>
