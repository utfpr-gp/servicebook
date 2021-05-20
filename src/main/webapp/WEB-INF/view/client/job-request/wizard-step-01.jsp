<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:client title="Etapa 01">
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
                                    <select id="expertiseId" name="expertiseId" class="white-text select-city">
                                        <option value="0" selected><p class="white-text">Pedreiro </p></option>
                                        <option value="1"><p>Encanador</p></option>
                                        <option value="2"><p>Eletricista</p></option>
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
</t:client>
