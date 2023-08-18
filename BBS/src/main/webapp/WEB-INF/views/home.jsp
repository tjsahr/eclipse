<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판</title>
</head>
<style>
body { text-align:center; }
table { width : 80%; margin-left:auto; margin-right:auto; }
#tblBoard{ border-collapse : collapse; }
#tblBoard th { background-color : black; color : yellow; border : 1px solid yellow; }
#tblBoard td { border : 1px solid black; cursor : pointer; }
</style>
<body align=center>
<input type=hidden name=id value="${id}">
<h1>자유 게시판</h1>
<table>
<tr><td align=right>
	${login}
</td></tr>
</table>
<table id=tblBoard>
<thead>
	<tr><th>게시물 번호</th><th>제목</th><th>작성자</th><th>조회수</th><th>작성시각</th><th>수정시각</th></tr>
</thead>
<tbody>
<c:forEach items="${blist}" var="list">
<tr><td>${list.seqno}</td><td>${list.title}</td><td>${list.writer}</td><td>${list.hit}</td><td>${list.created}</td><td>${list.updated}</td></tr>
</c:forEach>
</tbody>
</table>
<table>
<tr>
	<%-- <td style='text-align:left;'>${prev}&nbsp;${next}</td> --%>
	<td style='font-size:24px;'>${pagestr}</td>
	<!-- <td style='text-align:right;'><a href='/write'><h3>게시물 작성</h3></a></td> -->
	<td>${write }</td>
</tr>
</table>
</body>
<script src="https://code.jquery.com/jquery-latest.js"></script>
<script>
$(document)
.on('click','#tblBoard tbody tr',function(){
	document.location = '/view?seqno=' + $(this).find('td:first').text();
	return false;
})
.on('click','#btnLogout',function(){
	$.ajax({
		url:'/logout',data:{},method:'post',dataType:'text',
		beforeSend:function(){
			if(!confirm('정말로 로그아웃할까요?')){
				return false;
			}
			return true;
		},
		success:function(data){
			document.location=data;
		}
	});
})
</script>
</html>