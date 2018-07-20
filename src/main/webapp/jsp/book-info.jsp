<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:template>
    <jsp:attribute name="title"><title>${labels.bookDetails}</title></jsp:attribute>
    <jsp:body>
        <div id="row">
            <div id="left-container">
                <img class="image" src = "${pageContext.request.contextPath}/image/book.png" width = "110" height = "120">
                <div id="info">
                    <h3>&laquo;${book.title}&raquo;</h3><hr/>
                    <strong>${labels.book} ID: </strong>
                        ${book.id}<br/>
                    <strong>${labels.bookAuthors}: </strong>
                        <my:authors-list authorsList="${book.authors}"/><br/>
                    <strong>${labels.publisher}: </strong>
                        ${book.publisher}<br/>
                    <strong>${labels.availableCopies}: </strong>
                        ${book.numberCopies}<br/>
                    <c:choose>
                        <c:when test = "${user.role eq 'READER'}">
                            <c:set var="displayOrderButton" value="true"/>
                            <c:forEach var="order" items="${orders}">
                                <c:if test="${order.book eq book and order.status ne 'RETURNED' and order.status ne 'DENIED'}">
                                    <c:set var="displayOrderButton" value="false"/>
                                </c:if>
                            </c:forEach>
                            <c:if test="${displayOrderButton eq 'true'}">
                                <div class="left-btn">
                                    <form method="POST" action="${pageContext.request.contextPath}/controller/order-book">
                                        <input type="hidden" name="idBook" value="${book.id}"/>
                                        <button>${labels.orderBook}</button>
                                    </form>
                                </div>
                            </c:if>
                        </c:when>
                        <c:when test="${user.role == 'LIBRARIAN'}">
                            <div class="left-btn">
                                <a href="${pageContext.request.contextPath}/controller/edit-book?idBook=${book.id}">
                                    <button>${labels.edit}</button>
                                </a>
                            </div>
                        </c:when>
                    </c:choose>
                </div>
            </div><hr/>
            <div id="right-container">
                <my:buttons-pane/>
            </div>
        </div>
    </jsp:body>
</my:template>