<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

<h1>비밀번호 찾기</h1>
<form action="/user/sendPassword" method="post">
	<div>아이디: <input type="text" name="id"></div>
	<div>이메일: <input type="email" name="email"></div>
	<div><button type="submit">확인</button></div>
</form>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
