<%@ tag description="Template for all pages" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ attribute name="title" fragment="true" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
        <jsp:invoke fragment="title"/>
    </head>
    <body>
        <div id="header">
            <my:header/>
        </div>
        <div id="content">
            <jsp:doBody/>
        </div>
        <div id="footer">
            <my:footer/>
        </div>
    </body>
</html>