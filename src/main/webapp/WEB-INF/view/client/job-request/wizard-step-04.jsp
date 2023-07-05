<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template title="Etapa 04">
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
                            <div class="card-panel orange lighten-1 msg-view center-align">
                                <span class="white-text">${msg}</span>
                            </div>
                        </div>
                    </div>
                </c:if>

                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Descreva o tipo de serviço</h3>
                        <form method="post" action="requisicoes/passo-4">
                            <div class="row center">
                                <div class="col s12 m8 offset-m2 l8 offset-l2  input-field">
                                    <div class="input-field col s12">
                                        <textarea placeholder="Digite a descrição" id="description" name="description" class="area-type-service"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="col s6 m6 spacing-buttons">
                                <div class="center">
                                    <a href="requisicoes?passo=3" class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
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
        </main>

    </jsp:body>
</t:template>
