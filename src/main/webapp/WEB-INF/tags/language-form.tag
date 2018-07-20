<%@ tag description="Form for changing language" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="label" fragment="true" required="true"%>

<div id = "choose-lang" >
    <form id="change-lang-form" method="POST", action="${pageContext.request.contextPath}/controller/change-lang">
    <jsp:invoke fragment="label"/>:
        <select name="language" onchange="this.form.submit()" data-width = "fit">
            <option value='en' ${language == 'en' ? 'selected' : ''}>EN</option>
            <option value='ru' ${language == 'ru' ? 'selected' : ''}>RU</option>
        </select>
    </form>
</div>