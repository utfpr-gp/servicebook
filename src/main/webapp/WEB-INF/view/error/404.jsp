<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:error title="Erro!!">
    <jsp:body>
        <h2 class="center">Ooops!!!</h2>

        <p class="center">A página requisitada não foi encontrada!</p>

        <div class="row">
            <div class="col s12 center">
                <img class="img-responsive" src="assets/resources/images/404.png" height="500px">
            </div>
        </div>
    </jsp:body>
</t:error>
