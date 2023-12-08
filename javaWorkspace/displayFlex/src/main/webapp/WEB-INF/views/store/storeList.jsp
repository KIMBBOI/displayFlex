<%@page import="java.util.List"%>
<%@page import="displayFlex.store.vo.StoreVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/cinema/resources/css/store/storeList.css">
<script defer src="/cinema/resources/js/store/storeList.js"></script>
<%
	List<StoreVo> storeVoList2 = (List<StoreVo>)request.getAttribute("storeVoList2");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스토어</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<div class="store">
		
		<div></div>
		
		<div class="store-main">
			<div id="top">
				<div><h1>스토어</h1></div>
				<%-- <c:if test="${loginMember.adminYn eq 'Y'}"> --%> 
				<div id="enroll"><button onclick="location.href='/cinema/admin/store/enroll'">등록</button></div>
				<div id="delete"><button>삭제</button></div>
				<%-- </c:if> --%>
			</div>
			<div id="smallMenu">
				<c:forEach items="${storeVoList2}" var="vo">
				<div><button onclick="handleMenu();"><span>${vo.category}</span></button></div>
				<!-- <div><button onclick="handleMenu();"><span>기프트카드</span></button></div>
				<div><button onclick="handleMenu();"><span>콤보</span></button></div>
				<div><button onclick="handleMenu();"><span>팝콘</span></button></div>
				<div><button onclick="handleMenu();"><span>음료</span></button></div>
				<div><button onclick="handleMenu();"><span>스낵</span></button></div> -->
				</c:forEach>
			</div>
			<div class="itemPoto">
				<c:forEach items="${storeVoList}" var="vo">
					<div>
						<a href="/cinema/store/product">
						<img src="${vo.image}" alt="product" width="200" height="200">
						<br>
						<span id="first"><b>${vo.title}</b></span>
						</a>
						<br>
						<span id="second">${vo.productElement}</span>
						<br>
						<br>
						<span id="third"><b>${vo.price}</b></span>
					</div>
				</c:forEach>
				<div></div>
				<div></div>
			</div>

		</div>
		
		<div></div>
	
	</div>

</body>
</html>