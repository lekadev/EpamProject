<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
    <title>Librarian profile</title>
</head>

<body>

<%@ include file="/jsp/common/header.jspf"%>

<div id="content">
    <div id="row">
        <div id = "left-container">
            <img class="image" src = "${pageContext.request.contextPath}/image/user.png" width = "110" height = "120">
            <div id="info">
			    <h3>${user.nameFirst} ${user.nameLast}</h3><hr/>
			    ID#: ${user.id}<br/>
			    ${labels.phone}: ${user.numberPhone}<br/>
			    ${labels.email}: ${user.email}<br/>
			</div>
		</div><hr/>
		<div id="right-container">
			<div class = "right-btn">
				<form class = "logout-form" method = "POST" action = "${pageContext.request.contextPath}/controller/logout">
				    <button>${labels.logout}</button>
				</form>
			</div>
			<div class = "right-btn">
				<a href="${pageContext.request.contextPath}/controller/edit-profile">
			        <button>${labels.settings}</button>
			    </a>
			</div>
			<div class = "right-btn">
			    <a href="${pageContext.request.contextPath}/controller/show-catalogue">
			        <button>${labels.catalogue}</button>
			    </a>
			</div>
		</div>
	</div><br/>
	<div class="table-title orders">
	    <h2>${labels.allOrders}</h2>
	</div>
	<div class="orders-table">
		<table>
			<tr>
			    <th>ID</th>
			    <th>${labels.user}</th>
			    <th>${labels.bookTitle}</th>
				<th>${labels.approve}</th>
				<th>${labels.date}</th>
			</tr>
			<c:forEach var="order" items="${orders}">
			    <tr>
                   	<td>${order.id}</td>
                   	<td class="nolinebreak">${order.user.nameFirst} ${order.user.nameLast}</td>
                   	<td><a href="${pageContext.request.contextPath}/controller/show-book?idBook=${order.book.id}">
                   	    ${order.book.title}</a>
                   	</td>
                   	<td class="nolinebreak">${labels.status}: ${order.status.description}
                   	    <c:if test = "${order.status ne 'RETURNED' and order.status ne 'DENIED'}">
                   	        <form id="change-status-form" method="POST" action="change-status">
                   	            <input type="hidden" name="idOrder" value="${order.id}"/>
                                <select name="status" id="choose-status" data-width = "fit">
                                    <option selected disabled>- ${labels.changeStatus}</option>
                                    <c:forEach var="status" items="${allStatuses}">
                                        <option>${status}</option>
                                    </c:forEach>
                                </select>
                                <div id="confirm-btn">
                                    <button>${labels.confirm}</button>
                                </div>
                            </form>
                        </c:if>
					</td>
					<td class="nolinebreak">${order.date}</td>
				</tr>
            </c:forEach>
		</table><br/>
	</div>

</div>

<%@ include file="/jsp/common/footer.jspf"%>

    </body>
</html>