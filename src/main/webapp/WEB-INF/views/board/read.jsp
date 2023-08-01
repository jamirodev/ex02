<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

<script>
$(function() {
	var likeResult = "${likeMap.likeResult}";
	if (likeResult == "true") {
		$("#likeHeart").css("color", "red");
	} else {
		$("#likeHeart").css("color", "black");
	}
	// ----------------------------------------
	//	            좋아요 하트
	// ----------------------------------------
	$("#likeHeart").click(function() {
		var that = $(this);
		var url = "";
		if (likeResult == "true") {
			url = "/like/deleteLike/${boardVo.bno}";
		} else {
			url = "/like/addLike/${boardVo.bno}";
		}
		
		$.post(url, function(rData) {
			console.log("rData:", rData);
			if (rData == "true") {
				var likeCount = parseInt($("#likeCount").text());
				var color = "";
				if (likeResult == "true") {
					color = "black";
					likeCount--;
					likeResult = "false";
				} else {
					color = "red";
					likeResult = "true";
					likeCount++;
				}
				$("#likeHeart").css("color", color);
				$("#likeCount").text(likeCount);
			}
		});
	});
	
	$("#uploadDiv").on("dragenter dragover", function(e) {
		e.preventDefault();
	});
	
	$("#uploadDiv").on("drop", function(e) {
		e.preventDefault();
		console.log(e);
		var file = e.originalEvent.dataTransfer.files[0];
		console.log(file);
		// <form enctype="multipart/form-data" action="/upload/uploadFile">
		//    <input type="file" name="file">
		// <form>
		var formData = new FormData(); // HTML5
		formData.append("file", file);
		url = "/upload/uploadFile";
		$.ajax({
			"type" : "post",
			"processData" : false, // ?a=b&c=d(x)
			"contentType" : false, // application/x-www-form-urlencoded (x)
			"url" : url,
			"data" : formData,
			"success" : function(rData) {
				console.log(rData);
				var div = $("#uploadedItem").clone();
				div.removeAttr("id");
				div.addClass("uploadedItem");
				div.addClass("newUpload");
				var filename = rData.substring(rData.indexOf("_") + 1);
				div.find("span").text(filename);
				
				if (isImage(filename)) {
					var thumbnail = getThumbnailName(rData);
					div.find("img")
						.attr("src", "/upload/displayImage?filePath=" + thumbnail);
				}
				div.find("a").attr("data-filename", rData).show();

				$("#uploadedDiv").append(div);
				div.fadeIn(1000);
				
			}
		});
	});
	
	
// 	var attachList;
	$.getJSON("/board/getAttachList/${boardVo.bno}", function(rData) {
		console.log(rData);
// 		attachList = rData;
// 		for (var v = 0; v < rData.length; v++) {}
		// 제이쿼리 객체집합
// 		$(".a").each(function() {});
		$.each(rData, function(i) { // rData - 배열 [파일명, 파일명, ...]
			console.log(rData[i]);
			var div = $("#uploadedItem").clone();
			div.removeAttr("id");
			div.addClass("uploadedItem");
			var filename = rData[i].substring(rData[i].indexOf("_") + 1);
			div.find("span").text(filename);
			
			if (isImage(filename)) {
				var thumbnail = getThumbnailName(rData[i]);
				div.find("img")
					.attr("src", "/upload/displayImage?filePath=" + thumbnail);
			}
			div.find("a").attr("data-filename", rData[i]);

			$("#uploadedDiv").append(div);
			div.fadeIn(1000);
		});
	});
	
	// 글 수정 버튼
	$("#btnModify").click(function() {
		$(".readonly").prop("readonly", false);
		$("#btnModifyFinish").fadeIn(1000);
		$(this).fadeOut(1000);
		
		//첨부파일 삭제 링크(x)
		// .uploadedItem 에 <a href="#">&times;</a>
// 		console.log("attachList", attachList);
// 		$(".uploadedItem").each(function(i) {
// 			var a_tag = "<a href='#' data-filename='"   + attachList[i] + "'>&times;</a>";
// 			$(this).append(a_tag);
// 		});

		$(".uploadedItem a").show();
		
		$("#uploadDiv").slideDown(1000);
		
	});
	
	// 첨부파일 삭제 링크 [x]
	$("#uploadedDiv").on("click", "a", function(e) {
		e.preventDefault();
		var that = $(this);
		var filename = that.attr("data-filename");
		var url = "/upload/deleteAttach";
		$.ajax({
			"type" : "delete",
			"url" : url,
			"data" : filename,
			"dataType" : "text",
			"success" : function(rData) {
				console.log(rData);
				if (rData == "SUCCESS") {
					that.parent().remove();
				}
			}
		});
	});
	
	$("#btnList").click(function() {
		$("#frmPaging").submit();
	});
	
	// 삭제 버튼
	$("#btnDelete").click(function() {
		$("#frmPaging").attr("action", "/board/delete");
		$("#frmPaging").submit();
	});
	
	function getReplyList() {
		$.get("/reply/list/${boardVo.bno}", function(rData) {
			console.log(rData);
			/*
			for (var v = 0; v < rData.length; v++) {
				var tr = $("#replyList").find("tr").eq(0).clone();
				var tds = tr.find("td");
				tds.eq(0).text(rData[v].rno);
				tds.eq(1).text(rData[v].replytext);
				tds.eq(2).text(rData[v].replyer);
				tds.eq(3).text(rData[v].regdate);
				tds.eq(4).text(rData[v].updatedate);
				$("#replyList").append(tr);
			}
			*/
			$.each(rData, function() {
				var tr = $("#replyList").find("tr").eq(0).clone();
				var tds = tr.find("td");
				tds.eq(0).text(this.rno);
				tds.eq(1).find("span").text(this.replytext);
				tds.eq(2).find("span").text(this.replyer);
				tds.eq(3).text(toDateString(this.regdate));
				tds.eq(4).text(toDateString(this.updatedate));
				
				if ("${loginInfo.u_id}" != this.replyer) {
					// 수정
					tds.eq(5).empty();
					// 삭제
					tds.eq(6).empty();
				}
				$("#replyList").append(tr);
				tr.fadeIn(3000);
			});
		});
	}
	
	getReplyList();
	
	// 댓글 입력 버튼
	$("#btnReplyWrite").click(function() {
		var url = "/reply/insert";
		var data = {
				"bno" : "${boardVo.bno}",
				"replytext" : $("#replytext").val()
		};
		console.log(data);
// 		$.post(url, data, function(rData) {
// 			console.log(rData);
// 		});
		$.ajax({
			"type" : "post",
			"url"  : url,
			"headers" : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "post"
			},
			"dataType" : "text",
			"data" : JSON.stringify(data),
			"success" : function(rData) {
				console.log(rData);
				if (rData == "success") {
					$("#replyList").find("tr:gt(0)").remove();
					getReplyList();
				}
			}
		});
	});
	
	// 댓글 수정 버튼
	$("#replyList").on("click", ".btn-reply-update", function() {
		$(".btn-reply-update").show();
		$(".btn-reply-updateFinish").hide();
		$(".hideSpan").show();
		
		var replytextTemp = $("#replytextTemp");
		var tdReplytext = $(this).parent().parent().find("td").eq(1).find("span");
		console.log(tdReplytext);
		var replytext = tdReplytext.text().trim();
		replytextTemp.val(replytext);
// 		tdReplytext.empty();
		tdReplytext.hide();
		tdReplytext.parent().append(replytextTemp);
		replytextTemp.show();
		
		var replyerTemp = $("#replyerTemp");
		var tdReplyer = $(this).parent().parent().find("td").eq(2).find("span");
		var replyer = tdReplyer.text().trim();
		replyerTemp.val(replyer);
// 		tdReplyer.empty();
		tdReplyer.hide();
		tdReplyer.parent().append(replyerTemp);
		replyerTemp.show();
		$(this).hide();
		$(this).next().show();
		
		
		
	});
	
	// 댓글 수정 완료 버튼
	$("#replyList").on("click", ".btn-reply-updateFinish", function() {
		var that = $(this);
		var rno = that.parent().parent().find("td").eq(0).text().trim();
		var replytext = $("#replytextTemp").val().trim();
		var replyer = $("#replyerTemp").val().trim();
		var sData = {
				"rno" : parseInt(rno),
				"replytext" : replytext,
				"replyer" : replyer
		};
		var url = "/reply/update";
		$.ajax({
			"type" : "patch",
			"url"  : url,
			"headers" : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "patch"
			},
			"dataType" : "text",
			"data" : JSON.stringify(sData),
			"success" : function(rData) {
// 				console.log(rData);
// 				console.log(parseInt(rData));
// 				console.log(parseFloat(rData));
				console.log(toDateString(parseInt(rData)));
				if (rData) {
					var updatedate = toDateString(parseInt(rData));
					that.parent().prev().text(updatedate);
					var replytextTemp = $("#replytextTemp");
					var tdReplytext = that.parent().parent().find("td").eq(1);
					tdReplytext.text(replytextTemp.val());
					var replyerTemp = $("#replyerTemp");
					var tdReplyer = that.parent().parent().find("td").eq(2);
					tdReplyer.text(replyerTemp.val());
					replytextTemp.val("").hide();
					$("#replyTempDiv").append(replytextTemp);
					replyerTemp.val("").hide();
					$("#replyTempDiv").append(replyerTemp);
					that.hide();
					that.prev().show();
				}
			}
		});
	});
	
	// 댓글 삭제 버튼
	$("#replyList").on("click", ".btn-reply-delete", function() {
		var that = $(this);
		var rno = $(this).parent().parent().find("td").eq(0).text();
		console.log(rno);
		var url = "/reply/delete/" + rno + "/${boardVo.bno}";
		$.ajax({
			"type" : "delete",
			"url"  : url,
			"headers" : {
				"X-HTTP-Method-Override" : "delete"
			},
			"success" : function(rData) {
				console.log(rData);
// 				console.log(parseInt(rData));
// 				console.log(parseFloat(rData));
				that.parent().parent().remove();
			}
		});
		
	});
	
	// 폼전송
	$("#myform").submit(function() {
		// $.each(배열, function() {});
		$(".newUpload").each(function(i) {
			console.log($(this));
			var filename = $(this).find("a").attr("data-filename");
			console.log(filename);
			var inputTag  = "<input type='hidden' ";
			inputTag += "name='files[" + i + "]'";
			inputTag += "value='" + filename + "'>";
			$("#myform").prepend(inputTag);
		});
		
// 		return false; // 폼전송을 하지 않음
	});
	
	
});
</script>

<form id="frmPaging" action="/board/list" method="get">
<%@ include file="/WEB-INF/views/include/frmPaging.jsp" %>
</form>

<div id="replyTempDiv">
	<input type="text" class="form-control" id="replytextTemp"
		value="" style="display:none;">
	<input type="text" class="form-control" id="replyerTemp"
		value="" style="display:none;">
	
</div>

<div class="container-fluid">

	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron">
				<h2>상세 보기</h2>
				<p><button id="btnList" type="button" class="btn btn-success">글목록</button></p>
			</div>
			<div>
				<form id="myform" role="form" action="/board/mod" method="post">
					<%@ include file="/WEB-INF/views/include/frmPaging.jsp" %>
					<div class="form-group">
						<label for="title">제목</label>
						<input type="text" class="form-control readonly" 
							id="title" name="title" value="${boardVo.title}" readonly/>
					</div>
					<div class="form-group">
						<label for="content">내용</label>
						<textarea class="form-control readonly" 
							id="content" name="content" readonly>${boardVo.content}</textarea>
					</div>
					
					
					<div id="uploadedItem" style="display:none">
						<img src="/images/default.png" height="100"><br>
						<span style="background-color: #dddddd; font-weight: bold;">default</span>
						<a href="#" style="display:none;">&times;</a>
					</div>
					
					<div id="uploadDiv" style="display:none;">
				
					</div>
					
					<div id="uploadedDiv">
					</div>
					
					<div style="clear:both;"></div>
					<%-- 로그인 사용자 아이디, 작성자 아이디 --%>
					<c:if test="${loginInfo.u_id eq boardVo.writer}">
					<div>
						<button type="button" class="btn btn-warning"
							id="btnModify">수정</button>
						<button type="submit" class="btn btn-primary"
							id="btnModifyFinish" style="display:none">수정완료</button>
						<button type="button" class="btn btn-danger"
							id="btnDelete">삭제</button>
					</div>
					</c:if>
				</form>
			</div>
			
		</div>
	</div>
	
	<div class="text-center">
		<i class="fa fa-heart" aria-hidden="true" 
			style="font-size:50px; cursor:pointer"
			id="likeHeart"></i><br>
		<span id="likeCount" style="font-size:20px;"
			class="badge badge-success">${likeMap.likeCount}</span>
	</div>
	
	<!-- 댓글 입력 -->
	<div class="row" style="margin-top:30px;">
		<div class="col-md-11">
			<input type="text" class="form-control" placeholder="댓글내용"
				id="replytext">
		</div>
		
		<div class="col-md-1">
			<button type="button" class="btn btn-sm btn-primary"
				id="btnReplyWrite">입력</button>
		</div>
	</div>
	
	<!-- 댓글 목록 -->
	<div id="replyListDiv" class="row" style="margin-top:30px;">
		<div class="col-md-12">
			<table class="table">
				<thead>
					<tr>
						<th>#</th>
						<th>댓글 내용</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>수정일</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody id="replyList">
					<tr style="display:none">
						<td></td>
						<td><span class="hideSpan"></span></td>
						<td><span class="hideSpan"></span></td>
						<td></td>
						<td></td>
						<td><button type="button" class="btn btn-sm btn-warning btn-reply-update">수정</button>
							<button type="button" class="btn btn-sm btn-primary btn-reply-updateFinish"
								style="display:none">완료</button>
							</td>
						<td><button type="button" class="btn btn-sm btn-danger btn-reply-delete">삭제</button></td>
					</tr>
					
				</tbody>
			</table>
		</div>
	</div>
</div>	

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
