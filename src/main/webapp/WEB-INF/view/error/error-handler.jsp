<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Erro!!">
    <jsp:body>
        <h2>Ooops!!! Estamos com problemas!</h2>

        <p>Pedimos desculpas! Em breve iremos corrigir este erro!</p>
        <c:if test="${message != null}">
            <p>
                <h4>Detalhes sobre o erro: </h4>
                <strong>Mensagem: </strong> ${message}<br>
                <strong>URL: </strong> ${url}
            </p>
        </c:if>

        <div class="row">
            <div class="col s12 center">
                <img class="img-responsive" src="assets/res/img/error.png" height="500px">
            </div>
        </div>
    </jsp:body>
</t:template>
