<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/visitor/visitor.css" rel="stylesheet">
</head>

<t:template title="Servicebook - Cadastro - Passo 6">
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
                        <div class="row">
                            <h3 class="center secondary-color-text">Nos conte mais sobre você!</h3>
                        </div>
                        <!-- verifica na sessão se o cadastro atual é de indivíduo e ajusta a URL -->
                        <form method="post" action="${sessionScope.get('KEY_IS_REGISTER_COMPANY') == true ? 'cadastrar-se/empresa/passo-6' : 'cadastrar-se/individuo/passo-6'}">
                            <div class="row center spacing-buttons">
                                <h4 class="center secondary-color-text">
                                    Qual o seu nome completo?
                                </h4>
                                <h5 class="center secondary-color-text">
                                    O usuários precisam saber com quem estão negociando.
                                </h5>
                                <div class="input-field col s12 l6 offset-l3 spacing-buttons">
                                    <input id="name" name="name" type="text" value="${dto.name}" class="validate">
                                    <label for="name">Nome completo</label>
                                </div>
                            </div>
                            <!-- verifica na sessão se o cadastro atual é de indivíduo -->
                            <c:if test="${sessionScope.get('KEY_IS_REGISTER_COMPANY') == false}">
                                <div class="row center spacing-buttons">
                                    <h4 class="center secondary-color-text">
                                        Qual o seu CPF?
                                    </h4>
                                    <h5 class="center secondary-color-text">
                                        Poderá ser usado para validar a veracidade dos dados pessoais.
                                    </h5>

                                    <div class="input-field col s12 l6 offset-l3 spacing-buttons">
                                        <input id="cpf" name="cpf" type="text" value="${dto.cpf}" class="">
                                        <label for="cpf">CPF</label>
                                    </div>
                                </div>
                            </c:if>
                            <!-- verifica na sessão se o cadastro atual é de empresa -->
                            <c:if test="${sessionScope.get('KEY_IS_REGISTER_COMPANY') == true}">
                                <div class="row center spacing-buttons">
                                    <h4 class="center secondary-color-text">
                                        Qual o seu CNPJ?
                                    </h4>
                                    <h5 class="center secondary-color-text">
                                        Poderá ser usado para validar a veracidade dos dados pessoais.
                                    </h5>

                                    <div class="input-field col s12 l6 offset-l3 spacing-buttons">
                                        <input id="cnpj" name="cnpj" type="text" value="${dto.cnpj}" class="">
                                        <label for="cnpj">CNPJ</label>
                                    </div>
                                </div>
                            </c:if>
                            <div class="col s6 m3 offset-m3 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn btn-gray"
                                       href="cadastrar-se?passo=5">
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

<script src="assets/libraries/jquery.mask.js"></script>

<script>
    $(document).ready(function () {
        $('#cpf').mask('000.000.000-00', {reverse: true});
        $('#cnpj').mask('00.000.000/0000-00', {reverse: true});
    });
</script>