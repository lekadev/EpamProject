<%@ tag description="Information about user" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<div class = "right-btn">
    <a href="${pageContext.request.contextPath}/controller/profile">
        <button>${labels.profile}</button>
    </a>
</div>