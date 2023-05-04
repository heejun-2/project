<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*"
    import="jspexp.a13_database.*"
    import="jspexp.a13_database.vo.*" 
         import="vo.*"
    import="dao.*"%>
<%
	// post방식에서 한글요청값을 받을 때, 반드시 설정되어야한다.
	request.setCharacterEncoding("utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쌍용 대학교</title>
<link href="PJ_css/main.css" type="text/css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
</head>
<body>
<h2 id="id">${param.id}</h2>
<h2 id="lecNum">${param.lecNum}</h2>
<h2 id="lecEval">${param.lecEval}</h2>
</body>
<script type="text/javascript">
<%
String id = request.getParameter("id");
String lecNum = request.getParameter("lecNum"); 
String lecEval =request.getParameter("lecEval"); 
boolean isInsert = false;
if(lecNum!=null&& !lecNum.equals("")){
	UniversityDao dao = new UniversityDao();
	dao.uptlecEval(id, Integer.parseInt(lecNum), lecEval);
	isInsert = true;
}
%>
Swal.fire({
	  title: '강의평가가 정상적으로 제출되었습니다..',
	  icon: 'success',
	  showCancelButton: false,
	  confirmButtonColor: '#3085d6',
	  confirmButtonText: '확인'
	}).then((result) => {
	  if (result.value) {
		  window.close()
	  }
	})

</script>
</html>