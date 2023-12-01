<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="/cinema/resources/css/mypage/userRemove.css">

</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="main">
<form class= "wrap" action="/cinema/views/mypage/userRemove">
    <div class="wrap-main">
        <div>
            <div class="wrap-main-first-font">회원 탈퇴를 신청하기 전, 다음 내용을 꼭 확인해주세요.</div>
            <div class="wrap-main-second-font">
          	  <div>· 고객 정보 및 개인형 서비스 이용 기록은 개인 정보보호 처리 방침 기준에 따라 삭제됩니다.</div>
          	  <div>· 회원 탈퇴 시 보유하고 계신 포인트는 자동 반환 처리되며, 이는 탈퇴 전 보유하신 포인트를 전부 소진하시길 바랍니다.</div>
          	  <div>· 회원 탈퇴 시 더 이상 FLEX 서비스 사용이 불가능하며, FLEX 공식 사이트에서도 탈퇴 처리됩니다.</div>
          	  <div>· 개인정보보호 처리법에 의거하여 탈퇴 후 1개월 지난 후에 재가입이 가능합니다.</div>
          	  <div>· 그동안 FLEX를 이용해주셔서 감사합니다.</div>
</div>
            <div class="wrap-main-third-font"><input type="button" id="bt1">안내사항을 모두 확인하였으며, 이에 동의합니다.</div>
        </div>
        <div class="button-class"><input type="button" value="FLEX 회원 탈퇴" id="bt2"><input type="button" value="취소" id="bt3"></div>
    </div>
</div>
</form>
</body>
</html>