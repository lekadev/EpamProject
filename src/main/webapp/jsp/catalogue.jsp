<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:template>
    <jsp:attribute name="title"><title>Books catalogue</title></jsp:attribute>
    <jsp:body>
        <div id="row">
            <div id = "left-container">
                <c:if test = "${user.role eq 'LIBRARIAN'}">
                    <my:new-book-form/>
                </c:if>
            </div><hr/>
            <div id="right-container">
                <my:buttons-pane/>
            </div>
        </div><br/>
        <div class="table-title catalogue">
            <h2>${labels.tableTitle}</h2>
        </div>
        <div class="books-table">
            <table>
                <tr>
                    <th>ID</th>
                    <th>${labels.title}</th>
                    <th>${labels.bookAuthors}</th>
                    <th>${labels.action}</th>
                </tr>
                <c:forEach var="book" items="${catalogue}">
                    <tr>
                        <td>${book.id}</td>
                        <td><a href="${pageContext.request.contextPath}/controller/show-book?idBook=${book.id}">
                            ${book.title}</a>
                        </td>
                        <td>
                            <c:forEach var="author" items="${book.authors}" varStatus="authorStatus">
                                <c:out value="${author.nameFirst} ${author.nameLast}"/>${!authorStatus.last ? ',' : ''}
                            </c:forEach>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test = "${user.role eq 'READER'}">
                                    <c:set var="displayOrderButton" value="true"/>
                                    <c:forEach var="order" items="${orders}">
                                        <c:if test="${order.book eq book and order.status ne 'RETURNED' and order.status ne 'DENIED'}">
                                            <c:set var="displayOrderButton" value="false"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${displayOrderButton eq 'true'}">
                                        <div class="table-btn">
                                            <form method="POST" action="${pageContext.request.contextPath}/controller/order-book">
                                                <input type="hidden" name="idBook" value="${book.id}"/>
                                                <button>${labels.orderBook}</button>
                                            </form>
                                        </div>
                                    </c:if>
                                </c:when>
                                <c:when test="${user.role == 'LIBRARIAN'}">
                                    <div class="table-btn">
                                        <a href="${pageContext.request.contextPath}/controller/edit-book?idBook=${book.id}">
                                            <button>${labels.edit}</button>
                                        </a>
                                    </div>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div><br/>
    </jsp:body>
</my:template>