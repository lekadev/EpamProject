<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:template>
    <jsp:attribute name="title"><title>${labels.catalogue}</title></jsp:attribute>
    <jsp:body>
        <div id="row">
            <div id = "left-container">
                <c:if test = "${user.role eq 'LIBRARIAN'}">
                    <my:new-book-form/>
                </c:if>
            </div><hr/>
            <div id="right-container">
                <my:buttons-pane/>
            </div>
        </div><br/>
        <div class="table-title catalogue">
            <h2>${labels.tableTitle}</h2>
        </div>
        <div class="books-table">
            <table>
                <tr>
                    <th>ID</th>
                    <th>${labels.title}</th>
                    <th>${labels.publisher}</th>
                    <th>${labels.bookAuthors}</th>
                </tr>
                <c:forEach var="book" items="${catalogue}">
                    <tr>
                        <td>${book.id}</td>
                        <td><a href="${pageContext.request.contextPath}/controller/show-book?idBook=${book.id}">
                            ${book.title}</a>
                        </td>
                        <td>${book.publisher}</td>
                        <td>
                            <c:forEach var="author" items="${book.authors}" varStatus="authorStatus">
                                <c:out value="${author.nameFirst} ${author.nameLast}"/>${!authorStatus.last ? ',' : ''}
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div><br/>
    </jsp:body>
</my:template>