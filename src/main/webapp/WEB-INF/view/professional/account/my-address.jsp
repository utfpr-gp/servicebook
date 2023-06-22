<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Servicebook - Cadastro - Passo 7">
    <jsp:body>

        <main>
            <div class="container">
                <c:if test="${not empty msg}">
                    <div class="container">
                        <div class="col s6">
                            <div class="card-panel green lighten-1 msg-view center-align">
                                <span class="white-text">${msg}</span>
                            </div>
                        </div>
                    </div>
                </c:if>

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
                        <form method="post" action="${pageContext.request.contextPath}/minha-conta/salvar-endereco/${professional.id}">
                            <div class="row spacing-buttons">
                                <div class="row">
                                    <div class="center">
                                        <a id="btn-search-cep" class="waves-effect waves-light btn">Buscar CEP</a>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="postalCode" value="${professional.address.postalCode}" name="postalCode" type="text" placeholder="CEP"
                                               class="validate">
                                        <label for="postalCode">CEP</label>
                                        <span id="errorPostalCode" class="hide helper-text red-text darken-3"></span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="number" name="number" value="${professional.address.number}" type="text" placeholder="Número"
                                               class="validate">
                                        <label for="number">Número</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="street" value="${professional.address.street}" name="street" type="text" placeholder="Rua" class="validate">
                                        <label for="street">Rua</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="neighborhood" value="${professional.address.neighborhood}" name="neighborhood" type="text" placeholder="Bairro"
                                               class="validate">
                                        <label for="neighborhood">Bairro</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="city" name="city" value="${city.name}" type="text" placeholder="Cidade" class="validate">
                                        <label for="city">Cidade</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="state" name="state" value="${city.state.name}" type="text" placeholder="Estado"
                                               class="validate">
                                        <label for="state">Estado</label>
                                    </div>
                                </div>
                            </div>
                            <div class="col s6 m3 offset-m3 spacing-buttons">
                                <div class="center">
                                    <a href="minha-conta/perfil" class="waves-effect waves-light btn btn-gray">
                                        Voltar
                                    </a>
                                </div>
                            </div>
                            <div class="col s6 m3 spacing-buttons">
                                <div class="center">
                                    <button type="submit" class="waves-effect waves-light btn">Editar</button>
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