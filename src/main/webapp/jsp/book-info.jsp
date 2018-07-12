<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
    <title>Book info</title>
</head>

    <body>

<%@ include file="/jsp/common/header.jspf"%>

<div id="content">
    <div id="row">
        <div id="left-container">
            <img class="image" src = "${pageContext.request.contextPath}/image/book.png" width = "110" height = "120">
            <div id="info">
                <h3><q>${book.title}</q></h3><hr/>
                <strong>ID: </strong>${book.id}<br/>
                <strong>${labels.bookAuthors}: </strong>
                    <c:forEach var="author" items="${book.authors}" varStatus="authorStatus">
                        <c:out value="${author.nameFirst} ${author.nameLast}"/>${!authorStatus.last ? ',' : ''}
		            </c:forEach><br/>
		        <strong>${labels.publisher}: </strong>${book.publisher}<br/>
		        <strong>${labels.availableCopies}: </strong>${book.numberCopies}<br/>
		        <c:set var="role" value="${user.role}"/>
		        <c:if test = "${role eq 'READER'}">
		            <div class="left-btn">
		                <form method="POST" action="${pageContext.request.contextPath}/controller/order-book">
		                    <input type="hidden" name="idBook" value="${param.idBook}"/>
		                    <button>${labels.orderBook}</button>
		                </form>
		            </div>
		        </c:if>
		    </div>
		</div><hr/>
		<div id="right-container">
		    <div class = "right-btn">
		        <form class = "logout-form" method = "POST" action = "${pageContext.request.contextPath}/controller/logout">
		            <button>${labels.logout}</button>
		        </form>
			</div>
			<div class = "right-btn">
			    <a href="${pageContext.request.contextPath}/controller/profile">
			        <button>${labels.profile}</button>
			    </a>
			</div>
		</div>
	</div>
</div>

<%@ include file="/jsp/common/footer.jspf"%>

    </body>
</html>