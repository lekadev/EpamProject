<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="custom" uri="/WEB-INF/tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<my:template>
    <jsp:attribute name="title"><title>${user.nameFirst} ${user.nameLast}</title></jsp:attribute>
    <jsp:body>
        <div id="row">
            <div id = "left-container">
                <my:user-info/>
            </div><hr/>
            <div id="right-container">
                <my:buttons-pane/>
            </div>
        </div>
        <div class="table-title orders">
            <h2>${labels.ordersHistory}</h2>
        </div>
        <div class="orders-table">
            <tr>
                <custom:ordersMessage orders="${orders}">
                    ${labels.noOrders}
                </custom:ordersMessage>
            </tr>
            <table>
                <tr>
                    <th>${labels.order} ID</th>
                    <th>${labels.bookTitle}</th>
                    <th>${labels.bookAuthors}</th>
                    <th>${labels.date}</th>
                    <th>${labels.status}</th>
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
                        <td class="nolinebreak">
                            <fmt:setLocale value="${language}"/>
                            <fmt:formatDate value="${order.date}"/>
                        </td>
                        <td><my:order-status orderStatus="${order.status}"/>
                            <c:if test="${order.status == 'PENDING'}">
                                <div id="cancel-order-btn">
                                    <form id="cancel-order-form" method="POST" action="${pageContext.request.contextPath}/controller/cancel-order">
                                        <input type="hidden" name="idOrder" value="${order.id}"/>
                                        <button>${labels.cancel}</button>
                                    </form>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table><br/>
        </div><br/>
    </jsp:body>
</my:template>