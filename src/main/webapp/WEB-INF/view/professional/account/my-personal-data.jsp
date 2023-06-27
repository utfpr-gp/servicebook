<%@ page import="java.text.ParseException" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:template title="Minhas informações pessoais">
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

            <c:if test="${not empty errors}">
                <div class="card-panel red">
                    <c:forEach var="e" items="${errors}">
                        <span class="white-text">${e.getDefaultMessage()}</span><br>
                    </c:forEach>
                </div>
            </c:if>

            <div class="row">
                <div class="container">
                    <div class="row">
                        <h3>Nos conte sobre suas informações pessoais</h3>
                        <h6>É importante, que suas informações pessoais como nome, CPF ou CNPJ (caso esteja cadastrado como uma empresa)
                            e data de nascimento estejam corretas!
                        </h6>
                    </div>

                    <div class="row">
                        <div class="texto">
                            <a href="#" class="waves-effect waves-light btn exibir">Editar</a>
                            <div class="exibir">
                                <h7>Data de nascimento:</h7>
                                <p>${user.description}</p>
                            </div>
                        </div>
                        <div class="formulario">

                            <form class="col s12"
                                  action="${pageContext.request.contextPath}/minha-conta/cadastra-informacoes-pessoais/${userDTO.id}"
                                  method="post"
                            >

                                <input type="hidden" name="_method" value="PATCH"/>

                                <div class="row">
                                    <div class="input-field col s12">
                                        <input id="name" name="name" value="${userDTO.name}">
<%--                                        <label for="name" class="form-label">Atualize seu Nome:</label>--%>
                                    </div>
                                </div>

                                <sec:authorize access="hasRole('USER')">
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input id="cpf" name="cpf" value="${userDTO.cpf}">
<%--                                            <label for="cpf" class="form-label">Atualize seu CPF:</label>--%>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input type="date" id="datepicker" name="birthdate">
                                            <label for="datepicker" class="form-label">Atualize sua data de nascimento:</label>
                                        </div>
                                    </div>
                                </sec:authorize>

                                <sec:authorize access="hasRole('COMPANY')">
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input id="cnpj" name="cnpj" value="${userDTO.cnpj}">
<%--                                            <label for="cnpj" class="form-label">Atualize seu CNPJ:</label>--%>
                                        </div>
                                    </div>
                                </sec:authorize>
                                <div class="row">
                                    <button class="waves-effect waves-light btn">salvar</button>
                                    <a href="#" id="ocultar">x</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template>
<script src="assets/libraries/jquery.mask.js"></script>
<script>
    $(document).ready(function () {
        $(".exibir").click(function (e) {
            $(".texto").hide();
            $(".formulario").show();
            e.preventDefault();
        });
        $("#ocultar").click(function (e) {
            $(".formulario").hide();
            $(".texto").show();
            e.preventDefault();
        });
        $('#cpf').mask('000.000.000-00', {reverse: true});
        $('#cnpj').mask('00.000.000/0000-00', {reverse: true});
    });
</script>
