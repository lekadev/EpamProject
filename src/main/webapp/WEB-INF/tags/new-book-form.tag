<%@ tag description="Form for adding a new book" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="book-add-form" method="POST" action="${pageContext.request.contextPath}/controller/add-book">
    <h3>${labels.addBook}:</h3>
    <div id="wrap-book-add-form">
        <div id="book-block">
            <label class="label-input">${labels.title}: </label>
            <input class="input-field" type="text" name="title" />
            <label class="label-input">${labels.publisher}: </label>
            <input class="input-field" type="text" name="publisher" />
            <label class="label-input">${labels.quantity}: </label>
            <input class="input-field" type="text" name="numberCopies" value="1"/>
        </div>
        <div id="author-block">
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
                            <input type="checkbox" name="selectedAuthors" value="${author.id}"/>
                            ${author.nameFirst} ${author.nameLast}
                        </label>
                    </c:forEach>
                </div>
            </div></br/>
            <span>${labels.ifNoAuthor}
                <a href="${pageContext.request.contextPath}/controller/new-author">${labels.addNewAuthor}</a>
            </span>
        </div>
    </div><br/>
    <div id="error">
        ${labels[bookAddMessage]}
    </div><br/>
    <c:remove var="bookAddMessage"/>
    <div class="left-btn">
        <button>${labels.add}</button>
    </div>
</form>