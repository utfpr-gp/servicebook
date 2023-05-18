<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template title="Etapa 08">
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
                        <h3 class="center secondary-color-text"><strong>Olá João, recebemos a sua solicitação de serviço!</strong></h3>
                        Dados do anúncio...
                        <p class="center secondary-color-text text-form-dados">Confirma os dados?</p>
                        <form action="requisicoes/passo-7">
                            <div class="col s12 l6 offset-l3 spacing-standard">
                                <div class="center">
                                    <a type="submit" class="waves-effect waves-light btn">Confirmo!</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>
