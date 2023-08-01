<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.jsp" %>
<script>
$(function() {
	var loginResult = "${loginResult}";
	if (loginResult == "FAIL") {
		$("#loginFailMessage").slideDown(3000);
	}
});
</script>
<div class="container-fluid">
	<div class="row" style="display:none;" id="loginFailMessage">
		<div class="col-md-12">
			<div class="alert alert-danger">
			  <strong>로그인 실패</strong> 아이디와 비밀번호를 확인해주세요.
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<div class="card card-info">
              <div class="card-header">
                <h3 class="card-title">로그인</h3>
              </div>
              <!-- /.card-header -->
              <!-- form start -->
              <form class="form-horizontal"
              	action="/user/login" method="post">
                <div class="card-body">
                  <div class="form-group row">
                    <label for="u_id" class="col-sm-2 col-form-label">아이디</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" 
                      	id="u_id" name="u_id" value="user01"
                      	placeholder="아이디를 입력해주세요">
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="u_pw" class="col-sm-2 col-form-label">비밀번호</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" 
                      id="u_pw" name="u_pw" value="user01"
                      placeholder="비밀번호를 입력해주세요">
                    </div>
                  </div>
                  <div class="form-group row">
                    <div class="offset-sm-2 col-sm-10">
                      <div class="form-check">
                        <input type="checkbox" class="form-check-input" 
                        	id="useCookie" name="useCookie">
                        <label class="form-check-label" for="useCookie">아이디 기억</label>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- /.card-body -->
                <div class="card-footer">
                  <button type="submit" class="btn btn-info">로그인</button>
                  <a href="/user/forgotPassword" class="btn btn-danger">비밀번호 찾기</a>
                  <a href="/" class="btn btn-default float-right">취소</a>
                </div>
                <!-- /.card-footer -->
              </form>
            </div>
         </div>
      </div>
  </div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
