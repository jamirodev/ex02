<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

<script>
$(function() {
	var msg = "${msg}";
	if (msg == "SUCCESS") {
		alert("처리가 완료되었습니다.");
	}
	
	$(".page-link").click(function(e) {
		e.preventDefault();
		var page = $(this).attr("href");
// 		location.href = "/board/list?page=" + page + "&perPage=${pagingDto.perPage}";
		$("input[name=page]").val(page);
		var form = $("#frmPaging");
		form.submit();
	});
	
	$("#perPage").change(function() {
		var perPage = $(this).val();
		console.log("perPage:", perPage);
		location.href = "/board/list?perPage=" + perPage
	});
	
	$("#btnSearch").click(function() {
		var searchType = $("#searchType").val();
		var keyword = $("#keyword").val();
		location.href = "/board/list?searchType=" + searchType + "&keyword=" + keyword;
	});
	
	// 제목 클릭
	$(".a_title").click(function(e) {
		e.preventDefault();
		var bno = $(this).attr("href");
		$("input[name=bno]").val(bno);
		$("#frmPaging").attr("action", "/board/read");
		$("#frmPaging").submit();
	});
	
	// 댓글갯수뱃지
	$(".a_title").next().click(function() {
		var bno = $(this).prev().attr("href");
		$("input[name=bno]").val(bno);
		$("#frmPaging").attr("action", "/board/read");
		$("#frmPaging").submit();
		// /board/read?bno=&page=#replyListDiv
				
	});
	
	var targetid = "";
	var messageSendUrl = "";
	// 쪽지 보내기
	$(".sendMessage").click(function(e) {
		e.preventDefault();
		$("#modal-354826").trigger("click");
		targetid = $(this).parent().prev().text();
		messageSendUrl = $(this).attr("href");
		console.log("targetid:", targetid);
		console.log("messageSendUrl:", messageSendUrl);
	});
	
	// 쪽지보내기 버튼
	$("#btnModalSend").click(function() {
		var sData = {
				"targetid" : targetid,
				"message" : $("#message").val()
		};
		$.post(messageSendUrl, sData, function(rData) {
			console.log("rData:", rData);
			if (rData == "SUCCESS") {
				$("#btnModalClose").trigger("click");
				var point = parseInt($("#u_point").text());
				point += 5;
				$("#u_point").text(point);
			}
		});
	});
});
</script>

<form id="frmPaging" action="/board/list" method="get">
<%@ include file="/WEB-INF/views/include/frmPaging.jsp" %>
</form>
<div class="container-fluid">

<!-- 쪽지보내기 모달창 -->
	<div class="row">
		<div class="col-md-12">
			 <a style="display:none;"
			 	id="modal-354826" href="#modal-container-354826" role="button" class="btn" data-toggle="modal">
			 	Launch demo modal</a>
			
			<div class="modal fade" id="modal-container-354826" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="myModalLabel">
								쪽지 보내기
							</h5> 
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">
							<input type="text" id="message"
								class="form-control">
						</div>
						<div class="modal-footer">
							 
							<button type="button" id="btnModalSend"
								class="btn btn-primary">
								보내기
							</button> 
							<button type="button" id="btnModalClose"
								class="btn btn-secondary" data-dismiss="modal">
								닫기
							</button>
						</div>
					</div>
					
				</div>
				
			</div>
			
		</div>
	</div>
<!-- // 쪽지보내기 모달창 -->


	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron">
				<h2>
					글목록
				</h2>
				
				<p>
					<a class="btn btn-primary btn-large" href="/board/register">글쓰기</a>
				</p>
			</div>
		</div>
	</div>
	
	
	<div class="row" style="margin-bottom:30px;">
		<!-- n줄씩 보기 -->
		<div class="col-md-2">
			<select name="perPage" id="perPage">
			<c:forEach var="v" begin="5" end="30" step="5">
				<option value="${v}"
					<c:if test="${pagingDto.perPage eq v}">
						selected
					</c:if>
				>${v}줄씩 보기</option>
			</c:forEach>
			</select>
		</div>
		<!-- 검색 -->
		<div class="col-md-10">
			<select name="searchType" id="searchType">
				<option value="t"
					<c:if test="${pagingDto.searchType == 't'}">
						selected
					</c:if>
				>제목</option>
				<option value="c"
					<c:if test="${pagingDto.searchType == 'c'}">
						selected
					</c:if>
				>내용</option>
				<option value="w"
					<c:if test="${pagingDto.searchType == 'w'}">
						selected
					</c:if>
				>작성자</option>
				<option value="tc"
					<c:if test="${pagingDto.searchType == 'tc'}">
						selected
					</c:if>
				>제목+내용</option>
				<option value="tcw"
					<c:if test="${pagingDto.searchType == 'tcw'}">
						selected
					</c:if>
				>제목+내용+작성자</option>
			</select>
			<input type="text" name="keyword" id="keyword"
				value="${pagingDto.keyword}">
			<button type="button" id="btnSearch">검색</button>
		</div>
	</div>
	<!-- n줄씩 보기 -->
	
	<!-- 게시글 목록 -->
	<div class="row">
		<div class="col-md-12">
			<table class="table">
				<thead>
					<tr>
						<th>#</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="boardVo">
					<tr>
						<td>${boardVo.bno}</td>
						<td><a class="a_title" href="${boardVo.bno}">${boardVo.title}</a>
							<c:if test="${boardVo.replycnt ne 0}">
								<span class="badge badge-dark"
									style="cursor: pointer; margin-left:10px;">${boardVo.replycnt}</span>
							</c:if>
						</td>
						<td> 
						<c:choose>
						<c:when  test="${empty loginInfo or loginInfo.u_id eq boardVo.writer}">
							${boardVo.writer}
						</c:when>
						<c:otherwise>
							<div class="dropdown">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">${boardVo.writer}</button>
								<div class="dropdown-menu">
									<a class="dropdown-item sendMessage" href="/message/sendMessage">쪽지 보내기</a> 
									<a class="dropdown-item" href="#">포인트 선물하기</a> 
									<a class="dropdown-item" href="#">회원 정보보기</a>
								</div>
							</div>
						</c:otherwise>
						
						</c:choose>
						</td>
						<td>${boardVo.regdate}</td>
						<td>${boardVo.viewcnt}</td>
					</tr>
				</c:forEach>	
				</tbody>
			</table>
		</div>
	</div>
	<!-- // 게시글 목록 -->
	
	<!-- 페이징 -->
	<div class="row">
		<div class="col-md-12">
			<nav>
				<ul class="pagination justify-content-center">
				<c:if test="${pagingDto.startPage ne 1}">
					<li class="page-item">
						<a class="page-link" href="${pagingDto.startPage - 1}">&laquo;</a>
					</li>
				</c:if>
				<c:forEach var="v" begin="${pagingDto.startPage}" 
								   end="${pagingDto.endPage}">
					<li 
						<c:choose>
							<c:when test="${pagingDto.page eq v}">
								class="page-item active"
							</c:when>
							<c:otherwise>
								class="page-item"
							</c:otherwise>
						</c:choose>
					>
						<a class="page-link" href="${v}">${v}</a>
					</li>
				</c:forEach>	
				<c:if test="${pagingDto.endPage lt pagingDto.totalPage}">
					<li class="page-item">
						<a class="page-link" href="${pagingDto.endPage + 1}">&raquo;</a>
					</li>
				</c:if>
				</ul>
			</nav>
		</div>
	</div>
	<!-- // 페이징 -->
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>