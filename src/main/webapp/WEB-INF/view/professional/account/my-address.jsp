<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css" rel="stylesheet">
</head>

<t:template title="Editar endereço">
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
                    <div class="container">
                        <div class="col s6">
                            <div class="card-panel red">
                                <c:forEach var="e" items="${errors}">
                                    <span class="white-text">${e.getDefaultMessage()}</span><br>
                                </c:forEach>
                            </div>
                        </div>
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
                        <form id="addres-form" method="post" action="${pageContext.request.contextPath}/minha-conta/salvar-endereco/${professional.id}">
                            <input type="hidden" name="_method" value="PATCH"/>
                            <div class="row spacing-buttons">
                                <div class="row">
                                    <div class="center">
                                        <a id="btn-search-cep" class="waves-effect waves-light btn">Buscar CEP</a>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="postal-code" value="${professional.address.postalCode}" name="postalCode" type="text" placeholder="CEP"
                                               class="validate" disabled>
                                        <label for="postal-code">CEP</label>
                                        <span id="errorPostalCode" class="hide helper-text red-text darken-3"></span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="number" name="number" value="${professional.address.number}" type="text" placeholder="Número"
                                               class="validate" disabled>
                                        <label for="number">Número</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="street" value="${professional.address.street}" name="street" type="text" placeholder="Rua" class="validate" disabled>
                                        <label for="street">Rua</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="neighborhood" value="${professional.address.neighborhood}" name="neighborhood" type="text" placeholder="Bairro"
                                               class="validate" disabled required>
                                        <label for="neighborhood">Bairro</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <c:if test="${not empty cities}">
                                        <div class="input-field col s8 offset-s2">
                                            <select name="city">
                                                <c:forEach var="city" items="${cities}">
                                                    <option class="validate" id="city" value="${city.name}">${city.name} - ${city.state.uf}</option>
                                                </c:forEach>
                                            </select>
                                            <label for="city">Cidade</label>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row">
                                    <c:if test="${not empty states}">
                                        <div class="input-field col s8 offset-s2 white-text">
                                            <select name="state">
                                                <c:forEach var="state" items="${states}">
                                                    <option id="state" value="${state.name}" >${state.name}</option>
                                                </c:forEach>
                                            </select>
                                            <label for="city">Estado</label>
                                        </div>
                                    </c:if>
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
                                    <button type="button" id="edit-button" class="waves-effect waves-light btn">Editar</button>
                                    <button type="submit" id="save-button" class="waves-effect waves-light btn"  style="display: none">Salvar</button>
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
    $(document).ready(function () {
        $('#postal-code').mask('00000-000');
        $('#edit-button').click(function () {
            $('#addres-form input').prop('disabled', false);
            // deixei comentado pois não esta funcionando no select
            // $('#addres-form select').prop('disabled', false);
            console.log($('select'))
            $(this).hide();
            $('#save-button').show();
        })
    })
</script>