<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Entrar">
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
                        <form class="login-form">
                            <div class="row center">
                                <div class="input-field col s10 offset-s1 m6 offset-m3  l4 offset-l4 center">
                                    <input id="email" name="email" type="text"
                                           class="validate" value="${email}">
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
