<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/visitor/visitor.css" rel="stylesheet">
</head>
<t:template title="Servicebook - Bem vindo">
    <jsp:body>
        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Bem-vindo ao</h3>
                        <h3 class="header center logo-text">ServiceBook</h3>

                        <div class="row center">
                            <h3 class="center secondary-color-text">
                                Solicite um serviço ou receba solicitações de serviços todos os dias!
                            </h3>
                            <div class="center">
                                <img src="assets/resources/images/hand-icon.png" width="300px">
                            </div>
                        </div>
                        <div class="col s6 m3 offset-m3 spacing-buttons">
                            <div class="center">
                                <a class="waves-effect waves-light btn" href="login">Entrar</a>
                            </div>
                        </div>
                        <div class="col s6 m3 spacing-buttons">
                            <div class="center">
                                <a class="waves-effect waves-light btn" href="cadastrar-se">Cadastrar-se</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>
