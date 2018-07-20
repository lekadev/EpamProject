<%@ tag description="Information about user" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<img src = "${pageContext.request.contextPath}/image/user.png" width = "110" height = "120">
<div id="info">
    <h3>${user.nameFirst} ${user.nameLast}</h3><hr/>
    ID#: ${user.id}<br/>
    <c:if test="${user.role == 'LIBRARIAN'}">
        ${labels.phone}: ${user.numberPhone}<br/>
    </c:if>
    ${labels.email}: ${user.email}<br/>
</div>