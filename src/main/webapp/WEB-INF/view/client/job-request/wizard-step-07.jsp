<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:client title="Etapa 07">
    <jsp:body>

        <main>
            <div class="container">
                <c:if test="${not empty message}">
                    <div class="card-panel red">
                        <c:forEach var="e" items="${message}">
                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="section">
                    <div class="row">
                        <h3 class="center grey-text"><strong>Enviamos um código para (42) 99999-1234</strong></h3>
                        <h4 class="center grey-text">Digite este código para validar seu pedido</h4>
                        <div class="row center spacing-standard">
                            <div class="col s10  offset-s1 m6 offset-m3  input-field">
                                <form method="post" action="requisicoes/passo-7">
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <div class="col s12 m8">
                                                <input placeholder="Digite o código de validação" type="text">
                                            </div>
                                            <div class="col s12 m4">
                                                <button class="waves-effect waves-light btn">Validar</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="col s6 offset-s3">
                            <div class="center">
                                <a class="waves-effect waves-light btn" href="javascript: alert('SMS Reenviado')">Reenviar SMS</a>
                            </div>
                        </div>

                        <div class="col s12 center spacing-standard">
                            <h4 class="grey-text">Digitou um número de telefone errado?</h4>
                            <a class="text-center text-form-dados modal-trigger" href="#modal1">Troque o número</a>
                        </div>

                        <div id="modal1" class="modal">
                            <div class="modal-content">
                                <div class="row">
                                    <div class="col s12 m6 offset-m3">
                                        <h5>Digite o novo número de celular:</h5>
                                        <input id="telefone" placeholder="(00) 00000-0000)" type="text" class="validate">
                                        <div class="center spacing-standard">
                                            <a class="waves-effect waves-light btn" href="javascript: alert('numero atualizado')">Atualizar</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:client>
