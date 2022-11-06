<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:professional title="ServiceBook - Minha conta">
    <jsp:body>

        <main>
            <div class="row">
                <t:side-panel individual="${user}" dataIndividual="${dataIndividual}"></t:side-panel>
                <div class="col m10 offset-m1 l9">
                    <a id="show-area-perfil"
                       class="hide-on-large-only show-area-perfil waves-effect waves-light btn btn-floating grey darken-3 z-depth-A">
                        <i class="material-icons">compare_arrows</i>
                    </a>
                    <div class="row">
                        <div class="col s12">
                            <h2 class="secondary-color-text">Anúncios de serviços</h2>
                            ${professionalExpertiseRating}
                            <blockquote class="light-blue lighten-5 info-headers">
                                <p>
                                    Abaixo você encontra os pedidos disponíveis no momento. Clique nos quadros para mais
                                    detalhes.
                                </p>
                            </blockquote>
                        </div>
                        <div class="col s12">
                            <c:if test="${not empty msg}">
                                <div class="row">
                                    <div class="col s12 l4 offset-l4">
                                        <div class="card-panel green lighten-1 msg-view center-align">
                                            <span class="white-text">${msg}</span>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="row">
                                <div class="container center">
                                    <a href="minha-conta/profissional" class="waves-effect waves-light btn"><i class="material-icons right">sync</i>ATUALIZAR</a>
                                </div>

                                <ul class="tabs tabs-fixed-width center">
                                    <li class="tab">
                                        <a id="tab-default" data-url="minha-conta/profissional/disponiveis"
                                           href="#disponiveis">
                                            DISPONÍVEIS
                                        </a>
                                    </li>
                                    <li class="tab">
                                        <a data-url="minha-conta/profissional/em-disputa" href="#emDisputa">
                                            EM DISPUTA
                                        </a>
                                    </li>
                                    <li class="tab">
                                        <a data-url="minha-conta/profissional/para-contratar" href="#paraContratar">
                                            PARA CONFIRMAR
                                        </a>
                                    </li>
                                    <li class="tab">
                                        <a data-url="minha-conta/profissional/para-fazer" href="#paraFazer">
                                            PARA FAZER
                                        </a>
                                    </li>
                                    <li class="tab">
                                        <a data-url="minha-conta/profissional/executados" href="#executados">
                                            EXECUTADOS
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div id="disponiveis" class="col s12">

                            </div>
                            <div id="emDisputa" class="col s12">

                            </div>
                            <div id="paraContratar" class="col s12">

                            </div>
                            <div id="paraFazer" class="col s12">

                            </div>
                            <div id="executados" class="col s12">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:professional>

<script>
    $(document).ready(function () {
        $('#disponiveis').load($('.tab .active').attr("data-url"), function (result) {
            window.location.hash = "#disponiveis";
            $('#tab-default').click();
        });
    });

    $('#select-expertise').formSelect();

    $('#select-expertise').change(function () {
        $('#form-expertise').submit();
    });

    $('.tab a').click(function (e) {
        e.preventDefault();

        let url = $(this).attr("data-url");
        let href = this.hash;
        window.location.hash = href;

        let urlParams = new URLSearchParams(window.location.search);
        let id = (urlParams.has('id')) ? urlParams.get('id') : 0;
        url += '?id=' + id;

        $(href).load(url, function (result) {
        });
    });
</script>
