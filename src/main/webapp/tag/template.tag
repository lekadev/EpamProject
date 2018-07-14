<@tag description="Template for all pages" language="java" pageEncoding="UTF-8">
<%@ taglib prefix="my" tagdir="${pageContext.request.contextPath}/tag"%>
<%@	attribute name="title" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
    <title>${title}</title>
</head>
<body>
    <div id="header">
        <my:header/>
    </div>
    <div id="container">
        <jsp:doBody/>
    </div>
    <div id="footer">
        <my:footer/>
    </div>
</body>
</html>