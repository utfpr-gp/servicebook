<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:error title="Erro!!">
    <jsp:body>
        <h2 class="center">Ooops!!</h2>
        <p class="center">O recurso solicitado não foi encontrado!</p>

        <div class="row">
            <div class="col s12 center">
                <img class="img-responsive" src="assets/resources/images/errors/404.png" height="500px">
            </div>
        </div>
    </jsp:body>
</t:error>
