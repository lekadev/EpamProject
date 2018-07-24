<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
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
                    <th>${labels.date}</th>
                    <th>${labels.approve}</th>
                </tr>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.id}</td>
                        <td class="nolinebreak">${order.user.nameFirst} ${order.user.nameLast}</td>
                        <td><a href="${pageContext.request.contextPath}/controller/show-book?idBook=${order.book.id}">
                            ${order.book.title}</a>
                        </td>
                        <td class="nolinebreak">
                            <fmt:setLocale value="${language}"/>
                            <fmt:formatDate value="${order.date}"/>
                        </td>
                        <td class="nolinebreak">${labels.status}: <my:order-status orderStatus="${order.status}"/>
                            <c:if test = "${order.status ne 'RETURNED' and order.status ne 'DENIED'}">
                                <form id="change-status-form" method="POST" action="change-status">
                                    <input type="hidden" name="idOrder" value="${order.id}"/>
                                    <select name="status" id="choose-status" data-width = "fit">
                                        <option selected disabled>- ${labels.changeStatus}</option>
                                        <c:forEach var="status" items="${allStatuses}">
                                            <option value="${status}"><my:order-status orderStatus="${status}"/></option>
                                        </c:forEach>
                                    </select>
                                    <div class="table-btn">
                                        <button>${labels.confirm}</button>
                                    </div>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table><br/>
        </div>
    </jsp:body>
</my:template>