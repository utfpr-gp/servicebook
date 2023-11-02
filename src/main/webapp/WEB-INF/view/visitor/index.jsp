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

        // LISTA DE ESPECIALIDADE DE ACORDO COM A CATEGORIA
        $("#category-select").change(function () {
            let categoryId = $(this).val();
            $.ajax({
                url: "especialidades/categoria/"+ categoryId,
                type: "GET",
                success: function (expertises) {
                    console.log(expertises)

                    $("#expertise-select").empty();

                    //se o array for vazio, coloca o option que não há especialidades
                    if (expertises.length === 0)
                        $("#expertise-select").append("<option disabled selected>Não há especialidades</option>");
                    else
                        $("#expertise-select").append("<option disabled selected>Selecione uma especialidade</option>");

                    $.each(expertises, function (index, expertise) {
                        $("#expertise-select").append("<option value='" + expertise.id + "'>" + expertise.name + "</option>");
                    });
                    $("#expertise-select").formSelect();
                }
            });
        });

        // LISTA DE SERVIÇOS DE ACORDO COM A ESPECIALIDADE
        $("#expertise-select").change(function () {
            let expertiseId = $(this).val();
            $.ajax({
                url: "servicos/especialidade/"+ expertiseId,
                type: "GET",
                success: function (services) {

                    $("#service-select").empty();

                    //se o array for vazio, coloca o option que não há especialidades
                    if (services.length === 0)
                        $("#service-select").append("<option disabled selected>Não há serviços</option>");
                    else
                        $("#service-select").append("<option disabled selected>Selecione um serviço</option>");

                    $.each(services, function (index, service) {
                        $("#service-select").append("<option value='" + service.id + "'>" + service.name + "</option>");

                    });
                    $("#service-select").formSelect();
                }
            });
        });
    });
</script>