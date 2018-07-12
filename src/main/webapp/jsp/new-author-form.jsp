<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
    <title>New author form</title>
</head>

<body>

<%@ include file="/jsp/common/header.jspf"%>

<div id="content">
    <div id="row">
        <div id="left-container">
            <div id="wrap-profile-edit-form">
                <form id="add-author-form" method="POST" action="${pageContext.request.contextPath}/controller/add-author">
                    <label class="label-input">${labels.name}: </label>
                    <input class="input-field" type="text" name="nameFirst"/>
                    <label class="label-input">${labels.surname}: </label>
                    <input class="input-field" type="text" name="nameLast"/><br/>
                    <div id="error">
                        ${authorAddMessage}
                    </div>
                    <c:remove var="authorAddMessage"/>
                    <div class="left-btn">
                        <button>${labels.addAuthor}</button>
                    </div>
                </form>
            </div>
        </div><hr/>
        <div id="right-container">
            <div class = "right-btn">
                <form class = "logout-form" method = "POST" action = "${pageContext.request.contextPath}/controller/logout">
                    <button>${labels.logout}</button>
				</form>
			</div>
			<div class = "right-btn">
			    <a href="${pageContext.request.contextPath}/controller/profile">
			        <button>${labels.profile}</button>
			    </a>
			</div>
			<div class = "right-btn">
			    <a href="${pageContext.request.contextPath}/controller/show-catalogue">
			        <button>${labels.catalogue}</button>
			    </a>
			</div>
		</div>
	</div><br/>
</div>

<%@ include file="/jsp/common/footer.jspf"%>

    </body>
</html>