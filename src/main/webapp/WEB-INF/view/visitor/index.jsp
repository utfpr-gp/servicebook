<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/visitor/visitor.css" rel="stylesheet">
</head>
<t:template title="Servicebook - Início">
    <jsp:body>

        <main>
            <t:banner cities="${cities}"></t:banner>

            <t:search-bar items="${categories}"></t:search-bar>

            <section class="container center-align">
                <div class="row">
                    <h4>QUAL É A QUANTIDADE DE SERVIÇOS OFERECIDOS?</h4>
                    <div class="col s12 m4">
                        <div class="card indigo">
                            <div class="card-content white-text">
                                <span class="card-title">${totalExpertises}</span>
                                <p>ESPECIALIDADES</p>
                            </div>
                        </div>
                    </div>
                    <div class="col s12 m4">
                        <div class="card indigo">
                        <div class="card-content white-text">
                            <span class="card-title">${totalProfessionals}</span>
                            <p>PROFISSIONAIS</p>
                        </div>
                        </div>
                    </div>
                    <div class="col s12 m4">
                        <div class="card indigo">
                            <div class="card-content white-text">
                                <span class="card-title">${totalCompanies}</span>
                                <p>EMPRESAS</p>
                            </div>
                        </div>
                    </div>

                    <div class="col s12 cards-container">
                        <h4>QUAL É A QUANTIDADE DE CLIENTES INTERESSADOS?</h4>
                        <div class="col s12 m4">
                            <div class="card indigo">
                                <div class="card-content white-text">
                                    <span class="card-title">${totalClients}</span>
                                    <p>CLIENTES</p>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 m4">
                            <div class="card indigo">
                                <div class="card-content white-text">
                                    <span class="card-title">${totalJobContracted}</span>
                                    <p>ANÚNCIOS</p>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 m4">
                            <div class="card indigo">
                                <div class="card-content white-text">
                                    <span class="card-title">${totalJobRequests}</span>
                                    <p>AVALIAÇÕES</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </jsp:body>
</t:template>

<script>

    $(document).ready(function () {

    });
</script>