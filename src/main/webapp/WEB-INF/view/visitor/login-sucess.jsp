<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Login">
    <jsp:body>

        <main>
            <div class="container">
                <c:if test="${not empty errors}">
                    <div class="card-panel red">
                        <c:forEach var="e" items="${errors}">
                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="section">
                    <div class="row">
                        <div class="col s12 m10 offset-m1  l6 offset-l3 spacing-standard">
                            <h3 class="center primary-color-text">Seja bem-vindo ao</h3>
                            <h3 class="header center logo-text">ServiceBook</h3>
                            <h5 class="black-text center">Enviamos um email para ${loginUser.email}</h5>
                            <h5 class="black-text center">Acesse o link enviado para ter acesso a sua conta.</h5>
                            <h5 class="black-text center">OU</h5>
                            <h5 class="black-text center">Digite o código enviado para o seu email para se autenticar</h5>
                        </div>
                        <div class="col s12">
                            <div class="center">
                                <form class="login-form" method="post" action="login/token">
                                    <div class="row">
                                        <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                            <input id="code" name="code" type="text" class="validate">
                                            <label for="code">Código</label>
                                        </div>
                                    </div>
                                    <button class="waves-effect waves-light btn" type="submit">OK</button>
                                </form>
                            </div>
                        </div>
                        <div>
                            <form class="login-form"  action="login/email" method="post">
                                <div class="row">
                                    <div class="input-field col offset-s1 m6 offset-m3  l6 offset-l3">
                                        <input id="email" name="email" type="hidden" value="${loginUser.email}" class="validate" placeholder="joao@gmail.com">
                                    </div>
                                </div>
                                <div class="center">
                                    <button class="waves-effect waves-light btn" type="submit">Reenviar Código</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>
