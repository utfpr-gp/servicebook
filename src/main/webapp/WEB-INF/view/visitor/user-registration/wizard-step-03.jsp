<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Servicebook - Cadastro - Passo 3">
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
                        <h3 class="center secondary-color-text">
                            Vamos definir a sua senha de acesso?
                        </h3>
                        <h5 class="center secondary-color-text">
                            Digite a sua senha e depois redigite para se certificar de que digitou corretamente!
                        </h5>

                        <form method="post" action="cadastrar-se/passo-3">
                            <div class="row center">
                                <div class="input-field col s12 l6 offset-l3">
                                    <input id="password" name="password" type="password" value="${dto.password}"
                                           class="validate">
                                    <label for="password">Digite sua senha</label>
                                </div>
                            </div>
                            <div class="row center">
                                <div class="input-field col s12 l6 offset-l3">
                                    <input id="repassword" name="repassword" type="password" value="${dto.repassword}"
                                           class="validate">
                                    <label for="repassword">Digite novamente</label>
                                </div>
                            </div>
                            <div class="col s6 m3 offset-m3 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn btn-gray"
                                       href="cadastrar-se?passo=2">
                                        Voltar
                                    </a>
                                </div>
                            </div>
                            <div class="col s6 m3 spacing-buttons">
                                <div class="center">
                                    <button type="submit" class="waves-effect waves-light btn">Próximo</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>