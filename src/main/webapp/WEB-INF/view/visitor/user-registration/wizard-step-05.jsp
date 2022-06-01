<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Cadastro - Passo 5">
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
                        <c:choose>
                            <c:when test="${dto.phoneNumber == null}">

                                <h3 class="center secondary-color-text">
                                    Vamos validar o seu número de celular?
                                </h3>
                                <h5 class="center secondary-color-text">
                                    Enviamos um código para o seu telefone.
                                </h5>
                                <h5 class="center secondary-color-text">
                                    Por favor, digite o código para validar o seu celular!
                                </h5>

                                <form method="post" action="cadastrar-se/passo-5">
                                    <div class="row center spacing-buttons">
                                        <div class="input-field col s12 l6 offset-l3 spacing-buttons">
                                            <input id="code" name="code" type="text" class="validate">
                                            <label for="code">Código</label>
                                        </div>
                                        <div class="col s6 offset-s3">
                                            <div class="center">
                                                <a href="cadastrar-se?passo=6" class="waves-effect waves-light btn">
                                                    Fazer Depois
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col s6 m3 offset-m3 spacing-buttons">
                                        <div class="center">
                                            <a class="waves-effect waves-light btn btn-gray"
                                               href="cadastrar-se?passo=4">
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

                            </c:when>
                            <c:otherwise>

                                <h3 class="center secondary-color-text">
                                    Vamos validar o seu número de celular?
                                </h3>
                                <h5 class="center secondary-color-text">
                                    Enviamos um código para ${dto.phoneNumber}.
                                </h5>
                                <h5 class="center secondary-color-text">
                                    Por favor, digite o código para validar o seu celular!
                                </h5>

                                <form method="post" action="cadastrar-se/passo-5">
                                    <div class="row center spacing-buttons">
                                        <div class="input-field col s12 l6 offset-l3 spacing-buttons">
                                            <input id="code1" name="code" type="text" class="validate">
                                            <label for="code1">Código</label>
                                        </div>
                                        <div class="col s6 offset-s3">
                                            <div class="center">
                                                <a href="cadastrar-se?passo=8" class="waves-effect waves-light btn">
                                                    Fazer Depois
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col s6 m3 offset-m3 spacing-buttons">
                                        <div class="center">
                                            <a class="waves-effect waves-light btn btn-gray"
                                               href="cadastrar-se?passo=4">
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

                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:visitor>
