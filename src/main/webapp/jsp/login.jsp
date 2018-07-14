<my:template title="Login page">
    <jsp:body>
        <div id="login-container">
                <div id = "choose-lang" >
                    <form id="change-lang-form" method="POST", action="${pageContext.request.contextPath}/controller/change-lang">${labels.language}:
                        <select name="language" onchange="this.form.submit()" data-width = "fit">
                            <option value='en' ${language == 'en' ? 'selected' : ''}>EN</option>
                            <option value='ru' ${language == 'ru' ? 'selected' : ''}>RU</option>
                        </select>
                    </form>
                </div>
                <div id="wrap-login-form">
                    <form id="login-form" method="POST" action="${pageContext.request.contextPath}/controller/login">
                        <div id="wrap-input">
                            <div class="label-input"><span>${labels.email}:</span></div>
                            <div><input class="input-field" type="text" name="email" placeholder="${labels.emailHolder}"></div>
                        </div>
                        <div id="wrap-input">
                            <div class="label-input"><span>${labels.password}:</span></div>
                            <div><input class="input-field" type="password" name="password" placeholder="${labels.passwordHolder}"></div>
                        </div>
                        <div id="error">
                            ${loginMessage}
                            ${registrationMessage}
                            ${passwordUpdateMessage}
                        </div>
                        <c:remove var="loginMessage"/>
                        <c:remove var="registrationMessage"/>
                        <c:remove var="passwordUpdateMessage"/>
                        <div id="sign-in-btn">
                            <button>BUTTON</button>
                        </div>
                    </form>
                </div>
                <div id="new-account"><a href="${pageContext.request.contextPath}/controller/new-reader" >
                    ${labels.newAccount}
                </div>
            </div>
    </jsp:body>
</my:template>