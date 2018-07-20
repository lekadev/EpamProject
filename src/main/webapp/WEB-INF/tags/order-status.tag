<%@ tag description="Description of order status" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="orderStatus" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
    <c:when test="${orderStatus == 'LONG_LOAN'}">
        ${labels.statusLongLoan}
    </c:when>
    <c:when test="${orderStatus == 'SHORT_LOAN'}">
        ${labels.statusShortLoan}
    </c:when>
    <c:when test="${orderStatus == 'PENDING'}">
        ${labels.statusPending}
    </c:when>
    <c:when test="${orderStatus == 'RETURNED'}">
        ${labels.statusReturned}
    </c:when>
    <c:when test="${orderStatus == 'DENIED'}">
        ${labels.statusDenied}
    </c:when>
</c:choose>