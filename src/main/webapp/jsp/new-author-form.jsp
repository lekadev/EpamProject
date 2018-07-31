<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:template>
    <jsp:attribute name="title"><title>${labels.addAuthor}</title></jsp:attribute>
    <jsp:body>
        <div id="row">
            <div id="left-container">
                <div id="wrap-edit-form">
                    <form id="add-author-form" method="POST" action="${pageContext.request.contextPath}/controller/add-author">
                        <h3>${labels.addAuthor}:</h3><br/>
                        <label class="label-input">${labels.name}: </label>
                        <input class="input-field" type="text" name="nameFirst"/>
                        <label class="label-input">${labels.surname}: </label>
                        <input class="input-field" type="text" name="nameLast"/><br/>
                        <div id="error">
                            ${labels[authorAddMessage]}
                        </div><br/>
                        <c:remove var="authorAddMessage"/>
                        <div class="left-btn">
                            <button>${labels.add}</button>
                        </div>
                    </form>
                </div>
            </div><hr/>
            <div id="right-container">
                <my:buttons-pane/>
            </div>
        </div><br/>
    </jsp:body>
</my:template>