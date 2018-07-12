<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
    <title>Edit profile</title>
</head>

    <body>

<%@ include file="/jsp/common/header.jspf"%>

<div id="content">
    <div id="row">
        <div id="left-container">
            <div id="wrap-profile-edit-form">
                <form id="password-change-form" method="POST" action="${pageContext.request.contextPath}/controller/change-password">
                    <label class="label-input">${labels.email}: </label>
                    <input class="input-field" type="email" name="email" disabled="disabled"value="${user.email}" />
                    <label class="label-input">${labels.password}: </label>
                    <input class="input-field" type="password" name="password" value="${password}" required />
                    <label class="label-input">${labels.repeatPassword}: </label>
                    <input class="input-field" type="password" name="passwordRepeated" value="${password}" required /><br/>
                    <div id="error">
                        ${passwordUpdateMessage}
                    </div>
                    <c:remove var="passwordUpdateMessage"/>
                    <div class="left-btn">
                        <button>${labels.changePass}</button>
                    </div>
                </form>

                <form id="profile-edit-form" method="POST" action="${pageContext.request.contextPath}/controller/change-info"><br/>
                    <label class="label-input">${labels.name}: </label>
                    <input class="input-field" type="text" name="nameFirst" value="${user.nameFirst}" required />
                    <label class="label-input">${labels.surname}: </label>
                    <input class="input-field" type="text" name="nameLast" value="${user.nameLast}" required />
                    <c:set var="role" value="${user.role}"/>
                    <c:if test = "${role eq 'LIBRARIAN'}">
                        <label class="label-input">${labels.phone}: </label>
                        <input class="input-field" type="text" name="numberPhone" value="${user.numberPhone}" required />
                    </c:if><br/>
                    <div id="error">
                        ${profileUpdateMessage}
                    </div>
                    <c:remove var="profileUpdateMessage"/>
                    <div class="left-btn">
                        <button>${labels.confirm}</button>
                    </div>
                </form></br>

                <form id="change-lang-form" method="POST", action="${pageContext.request.contextPath}/controller/change-lang">
                    <div id = "change-lang" >${labels.changeLang}:
                        <select name="language" onchange="this.form.submit()" data-width = "fit">
                            <option value='en' ${language == 'en' ? 'selected' : ''}>EN</option>
                            <option value='ru' ${language == 'ru' ? 'selected' : ''}>RU</option>
                        </select><br/>
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
		</div>
	</div><br/>
</div>

<%@ include file="/jsp/common/footer.jspf"%>

    </body>
</html>