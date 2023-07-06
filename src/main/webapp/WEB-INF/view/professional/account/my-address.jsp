<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

                <div id="containerError" class="container" style="display: none">
                    <div class="col s6">
                        <div class="card-panel red">
                            <span id="errorCity" class="white-text"></span><br>
                        </div>
                    </div>
                </div>

                <div id="containerSucess" class="container" style="display: none">
                    <div class="col s6">
                        <div class="card-panel blue">
                            <span id="sucessCity" class="white-text"></span><br>
                        </div>
                    </div>
                </div>

                <div class="section">
                    <div class="row">
                        <h3 class="row center secondary-color-text">
                            Qual o seu endereço?
                        </h3>
                        <h5 class="row secondary-color-text">
                            Será útil para filtrar serviços por região e de acordo com a distância
                            para o local de realização do serviço.
                        </h5>
                        <form id="addres-form" method="post" action="${pageContext.request.contextPath}/minha-conta/meu-endereco/${professional.id}">
                            <input type="hidden" name="_method" value="PATCH"/>
                            <input type="hidden" name="id" value="${professional.id}"/>
                            <div class="row spacing-buttons">
                                <div class="row">
                                    <div class="input-field col s8 offset-s2">
                                        <input id="postal-code" value="${professional.address.postalCode}" name="postalCode" type="text" placeholder="CEP"
                                               class="validate" disabled>
                                        <label for="postal-code">CEP</label>
                                        <span id="errorPostalCode" class="hide helper-text red-text darken-3"></span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="center">
                                        <button id="btn-search-cep" type="button" class="waves-effect waves-light btn-flat" disabled>Buscar CEP</button>
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
                                            <select id="city" name="city" disabled>
                                                <option disabled selected>Selecione uma cidade</option>
                                                <c:forEach var="city" items="${cities}">
                                                    <c:if test="${city.id == professional.address.city.id}">
                                                        <option value="${city.id}" selected>${city.name}-${city.state.uf}</option>
                                                    </c:if>

                                                    <c:if test="${city.id != professional.address.city.id}">
                                                        <option value="${city.id}">${city.name}-${city.state.uf}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                            <label for="city">Cidade</label>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row">
                                    <c:if test="${not empty states}">
                                        <div class="input-field col s8 offset-s2 white-text">
                                            <select id="state" name="state" disabled>
                                                <option disabled selected>Selecione um estado</option>
                                                <c:forEach var="state" items="${states}">
                                                    <c:if test="${state.id == professional.address.city.state.id}">
                                                        <option value="${state.id}" selected>${state.name}</option>
                                                    </c:if>

                                                    <c:if test="${state.id != professional.address.city.state.id}">
                                                        <option value="${state.id}">${state.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                            <label for="state">Estado</label>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="right">
                                <button type="button" id="edit-button" class="waves-effect waves-light btn">Editar</button>
                                <button type="submit" id="save-button" class="waves-effect waves-light btn"  style="display: none">Salvar</button>
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
            $('#addres-form select').prop('disabled', false);
            $('#addres-form .btn-flat').prop('disabled', false);
            $(this).hide();
            $('#save-button').show();
            $('select').formSelect();
        })
    })
</script>