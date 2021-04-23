<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:visitor title="Login">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <div class="spacing-standard">
                            <h3 class="center primary-color-text">Seja bem-vindo ao</h3>
                            <h3 class="header center logo-text">ServiceBook</h3>
                        </div>
                        <form class="login-form">
                            <div class="row center">
                                <div class="input-field col s10 offset-s1 m6 offset-m3  l4 offset-l4 center">
                                    <input placeholder="joao@gmail.com" id="email" name="email" type="text" class="validate">
                                    <label for="email">Email</label>
                                </div>
                            </div>
                            <div class="center">
                                <button id="logar" class="waves-effect waves-light btn">ENTRAR</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:visitor>
