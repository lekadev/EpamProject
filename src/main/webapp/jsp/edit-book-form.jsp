<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:template>
    <jsp:attribute name="title"><title>${labels.editBook}</title></jsp:attribute>
    <jsp:body>
        <div id="row">
            <div id="left-container">
                <div id="wrap-edit-form">
                    <form id="book-edit-form" method="POST" action="${pageContext.request.contextPath}/controller/change-book-info">
                        <input type="hidden" name="idBook" value="${book.id}"/>
                        <h3>${labels.editBook}:</h3>
                        <label class="label-input">${labels.bookAuthors}: </label>
                        <div class="multiselect">
                            <div class="selectBox" onclick="showCheckboxes()">
                                <select>
                                   <option>${labels.selectAuthors}</option>
                                </select>
                                <div class="overSelect"></div>
                            </div>
                            <div id="checkboxes">
                                <c:forEach var="author" items="${allAuthors}">
                                    <label>
                                        <input type="checkbox" name="selectedAuthors" ${book.authors.contains(author) ? 'checked' : ''} value="${author.id}"/>
                                        ${author.nameFirst} ${author.nameLast}
                                    </label>
                                </c:forEach>
                            </div>
                        </div><br/>
                        <label class="label-input">${labels.title}: </label>
                            <input class="input-field edit-field" type="text" name="title" value="${book.title}"/>
                        <label class="label-input">${labels.publisher}: </label>
                            <input class="input-field" type="text" name="publisher" value="${book.publisher}"/>
                        <label class="label-input">${labels.quantity}: </label>
                            <input class="input-field" type="text" name="numberCopies" value="${book.numberCopies}"/>
                        <br/>
                        <div id="error">
                            ${labels[bookUpdateMessage]}
                        </div><br/>
                        <c:remove var="bookUpdateMessage"/>
                        <div class="left-btn">
                            <button>${labels.edit}</button>
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