<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Servicebook - Entrar">
    <jsp:body>

        <main>
            <div class="container">
                <c:if test="${not empty msg}">
                    <div class="row">
                        <div class="col s12">
                            <div class="card-panel green lighten-1 msg-view center-align">
                                <span class="white-text">${msg}</span>
                            </div>
                        </div>
                    </div>
                </c:if>

                <div class="section">
                    <div class="row">
                        <div class="spacing-standard">
                            <h3 class="center primary-color-text">Seja bem-vindo ao</h3>
                            <h3 class="header center logo-text">ServiceBook</h3>
                        </div>

                        <c:if test="${not empty errors}">
                            <div class="card-panel red col s12">
                                <c:forEach var="e" items="${errors}">
                                    <span class="white-text">${e.getDefaultMessage()}</span><br>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="col s12 m6 login-token">
                            <h6 class="header center login-logo-text">LOGIN POR RECEBIMENTO DE <br> CÓDIGO POR EMAIL</h6>
                            <form class="login-form input-login" action="login/email" method="post">
                                <div class="row">
                                    <div class="input-field col offset-s1 s10">
                                        <input id="email" name="email" type="text" value="${dto.email}" class="validate white-text" placeholder="joao@gmail.com">
                                        <label class="white-text" for="email">Qual o seu email?</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s11">
                                        <button type="submit" id="logar" class="waves-effect waves-light btn right">ENTRAR</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col s12 m6 login-email">
                            <form class="login-form" method="POST" action="login">
                                <h6 class="header center login-logo-text">LOGIN POR EMAIL E SENHA</h6>
                                <div class="row">
                                    <div class="input-field col offset-s1 s10">
                                        <input id="username" name="username" type="text" class="validate white-text" placeholder="joao@gmail.com">
                                        <label class="white-text" for="email">Qual o seu email?</label>
                                    </div>

                                    <div class="input-field col offset-s1 s10">
                                        <input id="password" name="password" type="password" class="validate white-text" placeholder="senha">
                                        <label class="white-text" for="password">Qual a sua senha?</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s11">
                                        <button id="logar1" type="submit" class="waves-effect waves-light btn right">ENTRAR</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="spacing-standard center">
                        <hr class="hr-login">
                        <h3 class="center text-darken-4 text-register">Você ainda não tem um conta?</h3>
                        <a class="center waves-effect waves-light btn" id="register" href="cadastrar-se">Cadastrar</a>
<%--                        <button id="register" class="center waves-effect waves-light btn">Cadastrar</button>--%>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template>
