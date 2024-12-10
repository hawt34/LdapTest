<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script
  src="https://code.jquery.com/jquery-3.7.1.slim.js"
  integrity="sha256-UgvvN8vBkgO0luPSUl2s8TIlOSYRoGFAX4jlCIm9Adc="
  crossorigin="anonymous"></script>
<head>
<meta charset="UTF-8">
<title>TestLoginComplete</title>
<style type="text/css">
	#container {
		width : 50%;
		padding : auto;
		margin : auto;
		margin-top : 400px;
	}
	
	#buttonArea{
		margin-top : 100px;
	
	}
	
	#Area{
		font-size : 30px;
	}
</style>
</head>
<body>
<div id="container">
		<div id="Area">현재 세션 아이디 : ${sId}</div>
			<div id="buttonArea">
				<button type="button" class="btn btn-primary" 
				onclick="window.location.href='http://localhost:8080/ad/Test?sId=${sId}';">
				비밀번호 변경
				</button>
			</div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</html>