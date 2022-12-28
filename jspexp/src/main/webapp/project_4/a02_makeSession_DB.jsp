<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" import="java.util.*" import="jspexp.vo.*"
   import="jspexp.a13_database.*" import="vo.*"%>
<%
    // post방식에서 한글요청값을 받을 때, 반드시 설정되어야한다.
    request.setCharacterEncoding("utf-8");
 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="UTF-8" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/a00_com/a01_common.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<script type="text/javascript">
      /*
      
      */
      </script>
</head>
<jsp:useBean id="dao" class="dao.eduloginDao" />
<jsp:useBean id="reMem" class="vo.member_s" />
<jsp:setProperty property="*" name="reMem" />
<body>
   <c:if test="${not empty dao.login(reMem)}">
   		<c:set var="mem" scope="session" value="${dao.login(reMem)}" />
   </c:if>
   
   <%
   String id = request.getParameter("id");
   if(id == null) id = "";
   session.setAttribute("id", id);
		   
   %>
   <jsp:useBean id="pro" class="vo.Professor"/>
   <jsp:setProperty property="id" name="pro"/>
	<c:set var="pro" scope="session" value="${dao.loginName(pro)}" />
</body>
<script type="text/javascript">
      var isFail = '${mem.id}'
      var id = '<%=id %>'
      if(isFail==''){
    	  Swal.fire({
			  title: '로그인 오류',
			  text: "아이디(학번), 비밀번호를 다시 입력해주세요.",
			  icon: 'error',
			  showCancelButton: false,
			  confirmButtonColor: '#3085d6',
			  confirmButtonText: '확인'
			}).then((result) => {
			  if (result.value) {
				  location.href="a01_login_DB.jsp"
			  }
			})

      }else{
    	  Swal.fire({
			  title: '로그인 성공',
			  icon: 'success',
			  showCancelButton: false,
			  confirmButtonColor: '#3085d6',
			  confirmButtonText: '확인'
			}).then((result) => {
			  if (result.value) {
				  location.href="schStudent.jsp?id="+id
	              
			  }
			})

      }
   /*
      
   */
   </script>
</html>