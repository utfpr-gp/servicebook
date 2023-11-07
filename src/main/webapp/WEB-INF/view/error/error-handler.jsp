<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:error title="Erro!!">
    <jsp:body>
        <h2 class="center">Ooops!!! Estamos com problemas!</h2>

        <p class="center">Pedimos desculpas! Em breve iremos corrigir este erro!</p>
        <c:if test="${message != null}">
         <div class="center">
             <p>
             <h4>Detalhes sobre o erro: </h4>
             <strong>Mensagem: </strong> ${message}<br>
             <strong>URL: </strong> ${url}
             </p>
         </div>
        </c:if>

        <div class="row">
            <div class="col s12 center">
                <img class="img-responsive" src="assets/resources/images/errors/error.png" height="500px">
            </div>
        </div>
    </jsp:body>
</t:error>
