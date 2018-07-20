<%@ tag description="Description of order status" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="authorsList" required="true" type="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="author" items="${authorsList}" varStatus="authorStatus">
    <c:out value="${author.nameFirst} ${author.nameLast}"/>${!authorStatus.last ? ',' : ''}
</c:forEach>