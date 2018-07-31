<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:template>
    <jsp:attribute name="title"><title>${labels.register}</title></jsp:attribute>
    <jsp:body>
        <div id="wrap-new-user-form">
            <form id="new-user-form" method="POST" action="${pageContext.request.contextPath}/controller/register">
                <div class="wrap-field">
                    <label class="label-input">${labels.email}: </label>
                    <input class="input-field" type="email" name="email" required/>
                </div>
                <div class="wrap-field">
                    <label class="label-input">${labels.password}: </label>
                    <input class="input-field" type="password" name="password" required/>
                </div>
                <div class="wrap-field">
                    <label class="label-input">${labels.repeatPassword}: </label>
                    <input class="input-field" type="password" name="passwordRepeated" required/>
                </div><br/>
                <div class="wrap-field">
                    <label class="label-input">${labels.name}: </label>
                    <input class="input-field" type="text" name="nameFirst" required/>
                </div>
                <div class="wrap-field">
                    <label class="label-input">${labels.surname}: </label>
                    <input class="input-field" type="text" name="nameLast" required/>
                </div><br/>
                <div id="error">
                    ${labels[registrationMessage]}
                </div><br/>
                <c:remove var="registrationMessage"/>
                <div id="register-btn">
                    <button>${labels.register}</button>
                </div>
            </form>
        </div>
        <div id="log-in-btn">${labels.haveAccount}
            <a href="${pageContext.request.contextPath}/controller/start">${labels.signIn}</a>
        </div><br/>
    </jsp:body>
</my:template>