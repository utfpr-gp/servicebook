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
                        <h6>É importante, que suas informações pessoais como nome, CPF ou CNPJ (caso esteja cadastrado
                            como uma empresa)
                            e data de nascimento estejam corretas!
                        </h6>
                    </div>

                    <div class="row">
                        <div class="texto">
                            <a href="#" class="waves-effect waves-light btn exibir">Editar</a>
                            <div class="exibir">
                                <p>Seu nome: ${userDTO.name}</p>
                                <sec:authorize access="hasRole('USER')">
                                    <p>Data de nascimento: ${userDTO.birthDate}</p>
                                    <p>Seu CPF: ${userDTO.cpf}</p>
                                </sec:authorize>
                                <sec:authorize access="hasRole('COMPANY')">
                                    <p>Seu CNPJ: ${userDTO.cnpj}</p>
                                </sec:authorize>
                            </div>
                        </div>
                        <div class="formulario">
                            <form class="col s12"
                                  action="${pageContext.request.contextPath}/minha-conta/cadastra-informacoes-pessoais/${userDTO.id}"
                                  method="post"
                            >
                                <input type="hidden" name="_method" value="PATCH"/>
                                <div class="row">
                                    <div class="form-group col s12">
                                        <label for="name" class="form-label">Atualize seu Nome:</label>
                                        <input id="name" name="name" value="${userDTO.name}">
                                    </div>
                                </div>
                                <sec:authorize access="hasRole('USER')">
                                    <div class="row">
                                        <div class="form-group col s12">
                                            <label for="cpf" class="form-label">Atualize seu CPF:</label>
                                            <input id="cpf" name="cpf" value="${userDTO.cpf}">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col s12">
                                            <label for="birthDate" class="form-label">Atualize sua data de
                                                nascimento:</label>
                                            <input type="date" id="birthDate" name="birthDate">
                                        </div>
                                    </div>
                                </sec:authorize>

                                <sec:authorize access="hasRole('COMPANY')">
                                    <div class="row">
                                        <div class="form-group col s12">
                                            <label for="cnpj" class="form-label">Atualize seu CNPJ:</label>
                                            <input id="cnpj" name="cnpj" value="${userDTO.cnpj}">
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
