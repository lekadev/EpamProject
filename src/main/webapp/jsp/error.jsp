<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:template>
    <jsp:attribute name="title"><title>${labels.error}</title></jsp:attribute>
    <jsp:body>
        <br/>
        <div id="pageNotFound"><strong>${labels.pageNotFound} &nbsp;</strong>
        <a href="${pageContext.request.contextPath}/controller/profile">${labels.homePage}</a></div>
    </jsp:body>
</my:template>