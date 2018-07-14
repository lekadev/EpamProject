<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
    <title>Books catalogue</title>
</head>

    <body>

<%@ include file="/jsp/common/header.jspf"%>

<div id = "content">
    <div id="row">
        <div id = "left-container">
            <c:set var="role" value="${user.role}"/>
            <c:if test = "${role eq 'LIBRARIAN'}">
                <form id="book-add-form" method="POST" action="${pageContext.request.contextPath}/controller/add-book">
                    <h3>${labels.addNewBook}:</h3>
                    <div id="wrap-book-add-form">
                        <div id="book-block">
                            <label class="label-input">${labels.title}: </label>
            				<input class="input-field" type="text" name="title" />
            				<label class="label-input">${labels.publisher}: </label>
            				<input class="input-field" type="text" name="publisher"/>
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
            		    ${bookInputMessage}
            		</div>
            		<c:remove var="bookInputMessage"/>
            		<div class="left-btn">
            		    <button>${labels.addBook}</button>
            		</div>
            	</form>
            </c:if>
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
	</div></br>
	<div class="table-title catalogue">
	    <h2>${labels.tableTitle}</h2>
	</div>
	<div class="books-table">
	    <table>
	        <tr>
	            <th>ID</th>
	            <th>${labels.title}</th>
	            <th>${labels.bookAuthors}</th>
	            <th>${labels.publisher}</th>
	        </tr>
	        <c:forEach var="book" items="${catalogue}">
	            <tr>
	                <td>${book.id}</td>
	                <td><a href="${pageContext.request.contextPath}/controller/show-book?idBook=${book.id}">
	                    ${book.title}</a>
	                </td>
	                <td>
                        <c:forEach var="author" items="${book.authors}" varStatus="authorStatus">
                            <c:out value="${author.nameFirst} ${author.nameLast}"/>${!authorStatus.last ? ',' : ''}
                        </c:forEach>
                    </td>
                    <td>${book.publisher}</td>
                </tr>
            </c:forEach>
        </table>
    </div><br/>
</div>

<%@ include file="/jsp/common/footer.jspf"%>

    </body>
</html>