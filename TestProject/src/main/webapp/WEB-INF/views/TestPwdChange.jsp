<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<head>
<meta charset="UTF-8">
<title>TestPwdChange</title>
<style type="text/css">
	#form {
		width : 50%;
		padding : auto;
		margin : auto;
		margin-top : 200px;
	}
	.error {
		color: red;
		font-size: 14px;
	}
	.success {
		color: green;
		font-size: 14px;
	}
</style>

<script>
	function validateForm() {
		const pwd = document.getElementById('pwd').value;
		const pwd2 = document.getElementById('pwd2').value;
		const submitButton = document.getElementById('submitBtn');

		// 비밀번호 유효성 검사
		const hasNumber = /[0-9]/.test(pwd);
		const hasUppercase = /[A-Z]/.test(pwd);
		const hasLowercase = /[a-z]/.test(pwd);
		const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(pwd);

		const checks = [hasNumber, hasUppercase, hasLowercase, hasSpecialChar].filter(Boolean).length;

		const pwdError = document.getElementById('pwdError');
		const pwd2Error = document.getElementById('pwd2Error');

		if (pwd.length < 7 || checks < 3) {
			pwdError.textContent = "비밀번호는 7글자 이상이어야 하며, 숫자, 대문자, 소문자, 특수문자 중 3가지 이상 포함해야 합니다.";
		} else {
			pwdError.textContent = "";
		}

		// 비밀번호 확인 검사
		if (pwd !== pwd2 || pwd2 === '') {
			pwd2Error.textContent = "비밀번호가 일치하지 않습니다.";
			pwd2Error.classList.remove('success');
			pwd2Error.classList.add('error');
		} else {
			pwd2Error.textContent = "비밀번호가 일치합니다.";
			pwd2Error.classList.remove('error');
			pwd2Error.classList.add('success');
		}

		// 제출 버튼 활성화 여부
		if (pwd.length >= 7 && checks >= 3 && pwd === pwd2 && pwd2 !== '') {
			submitButton.disabled = false;
		} else {
			submitButton.disabled = true;
		}
	}
</script>

</head>
<body>
	<form id="form" name="fr" action="requestTest" method="post">
		<div class="mb-3">
			<label for="exampleInputEmail1" class="form-label">아이디</label>
			<input type="text" class="form-control" id="id" readonly="readonly" value="${sId}" name="id">
		</div>
		<div class="mb-3">
			<label for="exampleInputPassword1" class="form-label">비밀번호</label>
			<input type="password" class="form-control" id="pwd" name="pwd" oninput="validateForm()">
			<div id="pwdError" class="error"></div>
		</div>
		<div class="mb-3">
			<label for="exampleInputPassword1" class="form-label">비밀번호 확인</label>
			<input type="password" class="form-control" id="pwd2" name="pwd2" oninput="validateForm()">
			<div id="pwd2Error" class="error"></div>
		</div>
		<button type="submit" id="submitBtn" class="btn btn-primary" disabled>확인</button>
	</form>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</html>
