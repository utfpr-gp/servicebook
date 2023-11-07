<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/visitor/visitor.css" rel="stylesheet">
</head>

<t:template title="Servicebook - Cadastro - Passo 7">
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
                        <h3 class="row center secondary-color-text">
                            Qual o seu endereço?
                        </h3>
                        <h5 class="row center secondary-color-text">
                            Será útil para filtrar serviços por região e de acordo com a distância
                            para o local de realização do serviço.
                        </h5>

                        <form method="post" action="cadastrar-se/passo-7">
                            <div class="row spacing-buttons">
                                <div class="row">
                                    <div class="center">
                                        <a id="btn-search-cep" class="waves-effect waves-light btn">Buscar CEP</a>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="postalCode" name="postalCode" type="text" placeholder="CEP"
                                               class="validate">
                                        <label for="postalCode">CEP</label>
                                        <span id="errorPostalCode" class="hide helper-text red-text darken-3"></span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="number" name="number" type="text" placeholder="Número"
                                               class="validate">
                                        <label for="number">Número</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="street" name="street" type="text" placeholder="Rua" class="validate">
                                        <label for="street">Rua</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="neighborhood" name="neighborhood" type="text" placeholder="Bairro"
                                               class="validate">
                                        <label for="neighborhood">Bairro</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="city" name="city" type="text" placeholder="Cidade" class="validate">
                                        <label for="city">Cidade</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="state" name="state" type="text" placeholder="Estado"
                                               class="validate">
                                        <label for="state">Estado</label>
                                    </div>
                                </div>
                            </div>
                            <div class="col s6 m3 offset-m3 spacing-buttons">
                                <div class="center">
                                    <a href="cadastrar-se?passo=6" class="waves-effect waves-light btn btn-gray">
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

<script src="assets/resources/scripts/cep-user-registration.js"></script>
<script src="assets/libraries/jquery.mask.js"></script>

<script>
    $('#postalCode').mask('00000-000');
</script>