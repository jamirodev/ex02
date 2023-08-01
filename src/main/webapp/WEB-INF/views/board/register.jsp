<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

<script>
$(function() {
	var nowSubmit = false;

	// 창닫기, 새로고침시 이미 올려진 파일 삭제
	// 전송시에도 현재페이지는 unload됨 - 플래그 변수(nowSubmit) 값 변경
	
	$(window).bind("beforeunload", function(e) {
		// alert, confirm, prompt - 동작 안함
		// e.preventDefault(); - 동작 안함
		if (!nowSubmit) {
			$(".uploadedItem").find("a").each(function() {
				var filename = $(this).attr("data-filename")
				$.ajax({
					"type" : "delete",
					"url" : "/upload/deleteFile",
					"dataType" : "text",  // 받을 데이터 타입
					"data" : filename,
					"success" : function() {
						
					}
				});
			});
		}
// 		return true;
		
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
				var filename = rData.substring(rData.indexOf("_") + 1);
				div.find("span").text(filename);
				
				if (isImage(filename)) {
					var thumbnail = getThumbnailName(rData);
					div.find("img")
						.attr("src", "/upload/displayImage?filePath=" + thumbnail);
				}
				div.find("a").attr("data-filename", rData);

				$("#uploadedDiv").append(div);
				div.fadeIn(1000);
				
			}
		});
	});
	
	// 첨부파일 삭제 링크
	$("#uploadedDiv").on("click", "div > a", function(e) {
		var that = $(this);
		e.preventDefault();
		console.log("x");
		var url = "/upload/deleteFile";
		var filename = that.attr("data-filename");
		$.ajax({
			"type" : "delete",
			"url" : url,
			"dataType" : "text",  // 받을 데이터 타입
			"data" : filename,
			"headers" : {
				"Content-Type" : "text/plain; charset=utf-8",
				"X-HTTP-Method-Override" : "delete"
			},
			"success" : function(rData) {
				if (rData == "SUCCESS") {
					that.parent().fadeOut(1000);
				}
			}
		});
	});
	
	var index = 0;
	
	// 폼전송
	$("#myform").submit(function() {
		
		nowSubmit = true;
// 		var as = $("#uploadedItem a");
// 		for (var v = 0; v < as.length; v++) {
		$(".uploadedItem a").each(function() {
			var fullname = $(this).attr("data-filename");
			console.log(fullname);
			var inputTag = "<input type='hidden' ";
			inputTag += " name='files[" + (index++) + "]'";
			inputTag += " value='" + fullname + "'>";
			$("#myform").prepend(inputTag);
		});
// 		return false; // 전송을 하지 않음
	});
});
</script>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron">
				<h2>글쓰기 양식</h2>
				<p><a href="/board/list" class="btn btn-success">글목록</a></p>
			</div>
			<form id="myform" role="form" action="/board/register" method="post">
				<div class="form-group">
					<label for="title">제목</label>
					<input type="text" class="form-control" 
						id="title" name="title"/>
				</div>
				<div class="form-group">
					<label for="content">내용</label>
					<textarea class="form-control" 
						id="content" name="content"></textarea>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label for="writer">작성자</label> -->
<!-- 					<input type="text" class="form-control"  -->
<!-- 						id="writer" name="writer"/> -->
<!-- 				</div> -->
				
				<div id="uploadDiv">
				
				</div>
				
				<div id="uploadedItem" style="display:none;">
					<img src="/images/default.png" height="100"><br>
					<span >default</span>
					<a href="#">&times;</a>
				</div>
				
				<div id="uploadedDiv">
					
				</div>
				
				<div style="clear:both;">
					<button type="submit" class="btn btn-primary"
						style="margin-top: 30px;">
						작성완료
					</button>
				</div>
			</form>
		</div>
	</div>
</div>	
            

<%@ include file="/WEB-INF/views/include/footer.jsp" %>