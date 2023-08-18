<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
<table>
<tr>
	<td>로그인아이디</td><td><input type=text id=userid name=userid></td>
</tr>
<tr>
	<td>비밀번호</td><td><input type=password id=passcode name=passcode></td>
</tr>
<tr>
	<td>비밀번호확인</td><td><input type=password id=passcode1 name=passocode1></td>
</tr>
<tr>
	<td colspan=2 align=center>
		<input type=submit id=btnid value="회원가입">
		<input type=reset id=btnReset value="비우기">
	</td>
</tr>
</table>
<a href='/login'>로그인</a>
</body>
<script src="https://code.jquery.com/jquery-latest.js"></script>
<script>
$(document)
.on('click','#btnid',function(){
	if($('#userid').val()==''){
		alert('userid가 없습니다.'); return false;
	}
	if($('#passcode').val()==''){
		alert('비밀번호가 없습니다.'); return false;
	} else if($('#passcode').val()!=$('#passcode1').val()){
		alert('비밀번호가 일치하지 않습니다.'); return false;
	}
	$.ajax({
		url:'/doSignup',data:{'userid':$('#userid').val(),'passcode':$('#passcode').val()},
		method:'post',dataType:'text',
		success:function(data){
			document.location=data;
		},
	success:function(data){
		if(data.substr(0,1)=="/"){
			document.location=data;
		} else {
			alert(data);
		}
	}
	})
	return false;
})
</script>
</html>