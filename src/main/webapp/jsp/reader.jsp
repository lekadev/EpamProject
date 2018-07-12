<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
    <title>Reader profile</title>
</head>

    <body>

<%@ include file="/jsp/common/header.jspf"%>

<div id="content">
    <div id="row">
        <div id = "left-container">
            <img src = "${pageContext.request.contextPath}/image/user.png" width = "110" height = "120">
            <div id="info">
                <h3>${user.nameFirst} ${user.nameLast}</h3><hr/>
                ${labels.reader} ID#: ${user.id}<br/>
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
    </div>
    <div class="table-title orders">
        <h2>${labels.ordersHistory}</h2>
    </div>
    <div class="orders-table">
        <tr>${fn:length(orders) lt 1 ? 'You do not have orders yet' : ''}</tr>
        <table>
            <tr>
                <th>${labels.order} ID</th>
                <th>${labels.bookTitle}</th>
                <th>${labels.bookAuthors}</th>
                <th>${labels.status}</th>
                <th>${labels.date}</th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.book.title}</td>
                    <td>
                        <c:forEach var="author" items="${order.book.authors}" varStatus="authorStatus">
                            <c:out value="${author.nameFirst} ${author.nameLast}"/>${!authorStatus.last ? ',' : ''}
                        </c:forEach>
                    </td>
                    <td>${order.status.description}
                        <c:if test="${order.status == 'PENDING'}">
                            <div id="cancel-order-btn">
                                <form id="cancel-order-form" method="POST" action="${pageContext.request.contextPath}/controller/cancel-order">
                                    <input type="hidden" name="idOrder" value="${order.id}"/>
                                    <button>${labels.cancel}</button>
                                </form>
                            </div>
                        </c:if>
                    </td>
                    <td>${order.date}</td>
                </tr>
            </c:forEach>
        </table><br/>
    </div><br/>
</div>

<%@ include file="/jsp/common/footer.jspf"%>

    </body>
</html>



  </body></html>