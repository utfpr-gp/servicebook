<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:client title="Etapa 06">
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
                        <h3 class="center grey-text"><strong>Último passo!</strong></h3>
                        <p class="center grey-text text-form-dados">Não perca tempo ligando para vários profissionais. Preencha os dados abaixo e nós encontraremos os melhores para você!</p>
                        <div class="row center">
                            <div class="col s12 l6 offset-l3  input-field">
                                <form method="post" action="requisicoes/passo-6">
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input placeholder="00000-000"  value="${dto.cep}" id="cep" data-mask="00000-000" name="cep" type="text" class="validate">
                                            <label for="cep">CEP</label>
                                            <span id="error-cep" class="hide helper-text red-text darken-3"></span>
                                        </div>
                                        <div id="endereco-area" class="endereco-area col s12 hide">
                                            <p><span id="rua">Rua 15 de novembro</span> - <span id="bairro">Centro</span> - <span id="cidade">Guarapuava</span> - <span id="uf">PR</span></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input placeholder="João da Silva" id="name" value="${dto.name}" name="name" type="text" class="validate">
                                            <label for="name">Nome</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input placeholder="joao@email.com"  value="${dto.email}" name="email" id="email" type="text" class="validate">
                                            <label for="email">Email</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input name="phone" id="phone" value="${dto.phone}" data-mask="(00) 00000-0000" placeholder="(00) 0000-0000" type="text" class="validate">
                                            <label for="phone">DDD + Celular</label>
                                            <span class="helper-text"> Vamos confirmar seu celular através de uma mensagem de texto</span>
                                        </div>
                                    </div>
                                    <div class="col s6 m6 spacing-buttons">
                                        <div class="center">
                                            <a href="requisicoes?passo=5" class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                        </div>
                                    </div>
                                    <div class="col s6 m6 spacing-buttons">
                                        <div class="center">
                                            <button class="waves-effect waves-light btn">Próximo</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>


    </jsp:body>
</t:client>

<script src="assets/resources/scripts/cep.js"></script>