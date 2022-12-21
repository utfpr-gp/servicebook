<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template title="Etapa 01">
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
                        <h3 class="center secondary-color-text">De qual profissional você precisa?</h3>
                        <form method="post" action="requisicoes/passo-1">
                            <div class="row center">
                                <div class="col s12 m6 offset-m3 l4 offset-l4  input-field">
                                    <select id="expertiseId" name="expertiseId" class="white-text">
                                        <option value="" disabled selected>Selecione a Especialidade</option>

                                        <c:forEach var="e" items="${expertiseDTOs}">
                                            <option value="${e.id}">${e.name}</option>
                                        </c:forEach>

                                    </select>
                                </div>
                            </div>
                            <div class="col s6 m6 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
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
