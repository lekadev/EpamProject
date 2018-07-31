<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:template>
    <jsp:attribute name="title"><title>${labels.editProfile}</title></jsp:attribute>
    <jsp:body>
        <div id="row">
            <div id="left-container">
                <div id="wrap-edit-form">
                    <form id="password-change-form" method="POST" action="${pageContext.request.contextPath}/controller/change-password">
                        <label class="label-input">${labels.email}: </label>
                        <input class="input-field" type="email" name="email" disabled="disabled" value="${user.email}" />
                        <label class="label-input">${labels.password}: </label>
                        <input class="input-field" type="password" name="password" value="${password}" required />
                        <label class="label-input">${labels.repeatPassword}: </label>
                        <input class="input-field" type="password" name="passwordRepeated" value="${password}" required /><br/>
                        <div id="error">
                            ${labels[passwordUpdateMessage]}
                        </div>
                        <c:remove var="passwordUpdateMessage"/>
                        <div class="left-btn">
                            <button>${labels.changePass}</button>
                        </div>
                    </form>
                    <form id="profile-edit-form" method="POST" action="${pageContext.request.contextPath}/controller/change-profile-info"><br/>
                        <label class="label-input">${labels.name}: </label>
                        <input class="input-field" type="text" name="nameFirst" value="${user.nameFirst}" required />
                        <label class="label-input">${labels.surname}: </label>
                        <input class="input-field" type="text" name="nameLast" value="${user.nameLast}" required />
                        <br/>
                        <div id="error">
                            ${labels[profileUpdateMessage]}
                        </div><br/>
                        <c:remove var="profileUpdateMessage"/>
                        <div class="left-btn">
                            <button>${labels.confirm}</button>
                        </div>
                    </form><br/>
                    <my:language-form>
                        <jsp:attribute name="label">${labels.changeLang}</jsp:attribute>
                    </my:language-form>
                </div>
            </div><hr/>
            <div id="right-container">
                <my:buttons-pane/>
            </div>
        </div><br/>
    </jsp:body>
</my:template>