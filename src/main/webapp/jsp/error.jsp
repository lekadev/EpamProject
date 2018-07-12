<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
    <title>Error page</title>
</head>

    <body>

<%@ include file="/jsp/common/header.jspf"%>

<div id="content"><br/>
    <div id="pageNotFound"><strong>${labels.pageNotFound} &nbsp;</strong>
    <a href="${pageContext.request.contextPath}/controller/profile">${labels.homePage}</a></div>
</div>

<%@ include file="/jsp/common/footer.jspf"%>

    </body>
</html>