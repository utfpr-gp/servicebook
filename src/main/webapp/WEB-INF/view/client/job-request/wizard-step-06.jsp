<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <h3 class="center grey-text"><strong>Qual o seu email?</strong></h3>
                        <p class="center grey-text text-form-dados"> O seu email será usado como seu identificador para entrar na aplicação e principalmente, para receber atualizações sobre o estado do serviço cadastrado.!</p>
                        <div class="row center">
                            <div class="col s12 l6 offset-l3  input-field">
                                <form method="post" action="requisicoes/passo-6">

                                    <div class="row">
                                        <div class="input-field col s12">
                                            <input placeholder="joao@email.com" value="${email}"
                                                   name="email" id="emailClient" type="text" >
                                            <label for="emailClient">Email</label>
                                        </div>
                                    </div>

                                    <div class="col s6 m6 spacing-buttons">
                                        <div class="center">
                                            <a href="requisicoes?passo=5" class="waves-effect waves-light btn btn-gray"
                                               href="#!">Voltar</a>
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
<script src="assets/libraries/jquery.mask.js"></script>
<script src="assets/resources/scripts/cep.js"></script>
