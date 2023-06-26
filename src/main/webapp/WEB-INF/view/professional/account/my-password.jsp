<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Minha senha">
    <jsp:body>

        <main>
            <c:if test="${not empty msg}">
                <div class="container">
                    <div class="col s6">
                        <div class="card-panel green lighten-1 msg-view center-align">
                            <span class="white-text">${msg}</span>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty msgError}">
                <div class="container">
                    <div class="col s6">
                        <div class="card-panel red lighten-1 msg-view center-align">
                            <span class="white-text">${msgError}</span>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="row">
                <div class="container">
                    <div class="row">
                        <h3 class="center secondary-color-text">
                            Senha
                        </h3>
                        <h5 class="center secondary-color-text">
                            Alterar senha.
                            <br>
                            A sua senha será utilizada para você acessar o sistema.
                        </h5>
                    </div>

                    <form method="post" action="minha-conta/salvar-senha/${professional.id}">
                        <div class="row">
                            <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                <input id="new-password" name="new-password" type="password"
                                       class="validate" required>
                                <label for="new-password">Digite sua nova senha</label>
                            </div>
                            <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                <input id="repeat-password" name="repeat-password" type="password"
                                       class="validate" required>
                                <label for="repeat-password">Digite sua nova senha novamente</label>
                            </div>
                        </div>

                        <div class="row col s6 offset-s6">
                            <div class="center">
                                <button type="submit" class="waves-effect waves-light btn">Salvar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template>
