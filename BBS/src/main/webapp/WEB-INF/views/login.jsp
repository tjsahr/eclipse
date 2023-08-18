<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<table align=center>
<tr><td>로그인아이디</td><td><input type=text name=userid id=userid><br></td></tr>
<tr><td>비밀번호</td><td><input type=password name=passcode id=passcode><br></td></tr>
<tr><td align=center colspan=2>
	<input type=submit id=btnSubmit name=btnSubmit value="로그인">&nbsp;
	<input type=reset id=btnReset name=btnReset value="비우기">&nbsp;
</td></tr>
</table>
</body>
<script src="https://code.jquery.com/jquery-latest.js"></script>
<script>
$(document)
.on('click','#btnSubmit',function(){
	$.ajax({
		url:'/doLogin',data:{'userid':$('#userid').val(),'passcode':$('#passcode').val()},
		method:'post',dataType:'text',
		beforeSend:function(){
			if($('#userid').val()=='' || $('#passcode').val()==''){
				alert('유저정보가 누락됐습니다.'); return false;
			}
		},
		success:function(data){		
			document.location=data;
		}
	});
})
</script>
</html>