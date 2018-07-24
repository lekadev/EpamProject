<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:template>
    <jsp:attribute name="title"><title>${labels.loginPage}</title></jsp:attribute>
    <jsp:body>
        <div id="login-container">
            <my:language-form>
                <jsp:attribute name="label">${labels.language}</jsp:attribute>
            </my:language-form>
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
                        ${labels[loginMessage]}
                        ${labels[registrationMessage]}
                        ${labels[passwordUpdateMessage]}
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
    </jsp:body>
</my:template>