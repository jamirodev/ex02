<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %>


<script>
window.addEventListener('beforeunload', function(event) {
  alert('I am the 1st one.');
});
window.addEventListener('unload', function(event) {
	alert('I am the 3rd one.');
});
</script>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

