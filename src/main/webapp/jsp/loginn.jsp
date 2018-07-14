<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
    <title>Login page</title>
</head>

    <body>

<%@ include file="/jsp/common/header.jspf"%>

<div id="content">
    <div id="login-container">
        <div id = "choose-lang" >
            <form id="change-lang-form" method="POST", action="${pageContext.request.contextPath}/controller/change-lang">${labels.language}:
                <select name="language" onchange="this.form.submit()" data-width = "fit">
                    <option value='en' ${language == 'en' ? 'selected' : ''}>EN</option>
                    <option value='ru' ${language == 'ru' ? 'selected' : ''}>RU</option>
                </select>
            </form>
        </div>
        <div id="wrap-login-form">
            <form id="login-form" method="POST" action="${pageContext.request.contextPath}/controller/login">
                <div id="wrap-input">
                    <div class="label-input"><span>${labels.email}:</span></div>
                    <div><input class="input-field" type="text" name="email" placeholder="${labels.emailHolder}"></div>
                </div>
                <div id="wrap-input">
                    <div class="label-input"><span>${labels.password}:</span></div>
                    <div><input class="input-field" type="password" name="password" placeholder="${labels.passwordHolder}"></div>
                </div>
                <div id="error">
                    ${loginMessage}
                    ${registrationMessage}
                    ${passwordUpdateMessage}
                </div>
                <c:remove var="loginMessage"/>
                <c:remove var="registrationMessage"/>
                <c:remove var="passwordUpdateMessage"/>
                <div id="sign-in-btn">
                    <button>${labels.signIn}</button>
                </div>
            </form>
        </div>
        <div id="new-account"><a href="${pageContext.request.contextPath}/controller/new-reader" >
            ${labels.newAccount}
        </div>
    </div>
</div>

<%@ include file="/jsp/common/footer.jspf"%>

    </body>
</html>