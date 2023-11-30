<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/cinema/resources/css/store/adminStoreEnroll.css">
<script src="https://kit.fontawesome.com/08e9cd3338.js" crossorigin="anonymous"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

	<div class="product">
		<div></div>
		
		<div class="product-main">
			<div id="top">
				<div><h1>스토어 - 제품등록</h1></div>
				<div id="enroll"><button>수정</button></div>
				<div id="delete"><button>취소</button></div>
			</div>
			<div id="product-detail">
				<div>
					<i class="fa-solid fa-circle-plus"></i>
				</div>
				<div id="detail">
					<div><input type="text" placeholder="ex)우리 패키지"></div>
					<div id="description">
						<div>가격</div>
						<div><input type="text" placeholder="61,000원"></div>
						<div>상품구성</div>
						<div><input type="text" placeholder="일반 영화 관람권 4매+더블콤보 1개"></div>
						<div>제품설명</div>
						<div><textarea id="productDetail" cols="20" rows="5" placeholder="일반 영화를 관람할 수 있는 영화 관람권 4매와 맛있는 팝콘과 콜라를 먹을 수 있는 우리 패키지"></textarea></div>
						<div>분류</div>
						<div><input type="checkbox" ></div>
					</div>
				</div>
			</div>
		
		</div>
	
		<div></div>
	</div>
</body>
</html>