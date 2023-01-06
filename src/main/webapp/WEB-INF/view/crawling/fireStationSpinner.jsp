<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>fire Station</title>
	<script type="text/javascript">
		window.onload = function() {
			const form = document.getElementById('form');
			form.submit();
		}
	</script>
</head>
<body style="margin: 40px">
	<h3>서울 소재 소방서 현황</h3>
	<hr>
	<div style="text-align: center;">
		<img src="/img/animated-icon-setting.gif">
		<p style="font-size:9px;">아이콘 제작자: Freepik - Flaticon</p>
		<form style="display: none;" action="/crawling/fireStation" method="post" id="form">
			<input type="hidden" name="dummy" value="0">
		</form>
	</div>
</body>
</html>