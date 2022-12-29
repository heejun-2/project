<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*"
    import="jspexp.vo.*"
    import="jspexp.a13_database.*"
    import="dao.*"
    import="vo.*"%>
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
		<title>쌍용대학교</title>
		<link href="img/ss.png" rel="shortcut icon" type="image/x-icon">
		<link href="/a00_com/a01_common.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
		<script type="text/javascript">
		/*
		
		*/
		</script>
	</head>
	<body>
		<%
		String attendance = request.getParameter("attendance");
		int adInt = 0;
		if(attendance != null) adInt = Integer.parseInt(attendance);
		
		String midtest = request.getParameter("midtest");
		int mtInt = 0;
		if(midtest != null) mtInt = Integer.parseInt(midtest);
		
		String endtest = request.getParameter("endtest");
		int etInt = 0;
		if(endtest != null) etInt = Integer.parseInt(endtest);
		
		String total = request.getParameter("total");
		if(total == null) total = "";
		
		String id =  request.getParameter("id");
		if(id == null) id = "";
		
		boolean isPass = false;
		if(id != null){
			A01_schStudent dao = new A01_schStudent();
			Student upt = new Student(adInt, mtInt, etInt, total, id);
			// 성적 입력/수정
			dao.udtStuLec(upt);
			isPass = true;
		}
		%>
		
		
	</body>
	<script type="text/javascript">
	
		var id = '<%=id %>'
		var isPass = '<%=isPass %>'
		if( isPass ){
			Swal.fire({
				  title: '성적 입력/수정이\n 완료되었습니다.',
				  icon: 'success',
				  showCancelButton: false,
				  confirmButtonColor: '#3085d6',
				  confirmButtonText: '확인'
				}).then((result) => {
				  if (result.value) {
					  location.href="inputGrade.jsp?id="+id
				  }
				})
		}

	/*
		
	*/
	</script>
</html>