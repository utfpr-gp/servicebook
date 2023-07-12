<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/visitor/visitor.css" rel="stylesheet">
</head>

<t:template title="Cadastro de Profissional">
    <jsp:body>
        <link href="assets/resources/styles/visitor/visitor.css" rel="stylesheet">
        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center primary-color-text"><strong>Cadastre-se</strong></h3>
                        <div class="row center">
                            <div class="col s12 l6 offset-l3  input-field">
                                <form action="#">
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input placeholder="João da Silva" name="name" id="name" type="text" class="validate">
                                            <label for="name">Nome</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input placeholder="Andrade" name="last-name" id="last-name" type="text" class="validate">
                                            <label for="last-name">Sobrenome</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input placeholder="joao@email.com" id="email" name="email" type="text" class="validate">
                                            <label for="email">Email</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s6">
                                            <input placeholder="********" id="password" name="password" type="password" class="validate">
                                            <label for="password">Senha</label>
                                        </div>
                                        <div class="input-field col s6">
                                            <input placeholder="********" id="password-repeat" name="password-repeat" type="password" class="validate">
                                            <label for="password-repeat">Repita Senha</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input placeholder="00000-000" id="cep" name="cep" type="text" class="validate">
                                            <label for="cep">CEP</label>
                                            <span id="error-cep" class="hide helper-text red-text darken-3"></span>
                                        </div>
                                        <div id="endereco-area" class="endereco-area col s12 hide">
                                            <p><span id="rua">Rua 15 de novembro</span> - <span id="bairro">Centro</span> - <span id="cidade">Guarapuava</span> - <span id="uf">PR</span></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input id="phone" name="phone" placeholder="(00) 00000-0000)" type="text" class="validate">
                                            <label for="phone">DDD + Celular</label>
                                            <span class="helper-text"> Vamos confirmar seu celular através de uma mensagem de texto</span>
                                        </div>
                                    </div>
                                    <div class="col s10 offset-s1 m6 spacing-buttons">
                                        <div class="center">
                                            <a class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                        </div>
                                    </div>
                                    <div class="col s10 offset-s1 m6 spacing-buttons">
                                        <div class="center">
                                            <button class="waves-effect waves-light btn" href="#!">Cadastrar</button>
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
</t:template>

<script src="assets/resources/scripts/cep.js"></script>
