<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*"
    import="jspexp.vo.*"
    import="jspexp.a13_database.*"%>
<%
 	// post방식에서 한글요청값을 받을 때, 반드시 설정되어야한다.
 	request.setCharacterEncoding("utf-8");
 %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <fmt:requestEncoding value="UTF-8"/>
 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link href="/a00_com/a01_common.css" rel="stylesheet">
		<script type="text/javascript">
		/*
		
		*/
		</script>
	</head>
	<body>
		<h2 onclick="go(12, '수12')">rrrr</h2>
	</body>
	<script type="text/javascript">
		function go(num, text) {

			alert(num+"\n"+text)
		}
	/*
		
	*/
	</script>
</html>