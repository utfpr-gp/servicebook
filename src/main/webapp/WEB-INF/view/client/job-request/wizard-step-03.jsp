<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:client title="Etapa 03">
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
                        <form method="post" action="requisicoes/passo-3">
                            <h4 class="center secondary-color-text">Qual a quantidade máxima de contatos que deseja receber? </h4>
                            <div class="row center">
                                <h3 id="value-input-range" class="grey-text center">10</h3>
                                <div class="col s8 offset-s2 range-field  spacing-standard ">
                                    <input id="quantity_candidators_max" name="quantity_candidators_max" type="range" min="1" max="20" value="10">
                                </div>
                            </div>
                            <div class="col s6 m6 spacing-buttons">
                                <div class="center">
                                    <a href="javascript:history.back()" class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
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
</t:client>
