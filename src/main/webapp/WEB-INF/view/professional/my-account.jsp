<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="ServiceBook - Minha conta" userInfo="${userInfo}">
    <jsp:body>
        <main class="container">
            <div class="row">
                <t:side-panel userInfo="${userInfo}" followdto="${followdto}" statisticInfo="${statisticInfo}"></t:side-panel>
                <div class="col m10 offset-m1 l9">
                    <a id="show-area-perfil"
                       class="hide-on-large-only show-area-perfil waves-effect waves-light btn btn-floating grey darken-3 z-depth-A">
                        <i class="material-icons">compare_arrows</i>
                    </a>
                    <div class="row">
                        <div class="col s12">
                            <h2 class="secondary-color-text">Anúncios de serviços</h2>
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
                                <%--                            <div class="row">--%>
                            <div class="row" style="margin-top: 20px">
                                <div class="tab col" style="width: 150px; border-bottom-width: 1px; border-bottom-color: #00b0ff; border-bottom-style: solid; margin-right: 10px">
                                    <a id="tab-default" data-url="minha-conta/profissional/disponiveis"
                                       href="#disponiveis" class="truncate">
                                        Disponíveis
                                    </a>
                                </div>

                                <div class="tab col dropdown-trigger sub-tab" data-target='pendentes'
                                     style="width: 150px; border-bottom-width: 1px; border-bottom-color: #00b0ff; border-bottom-style: solid; margin-right: 10px">
                                    <a>Pendentes<i class="material-icons right">expand_more</i></a>
                                    </a>
                                    <ul id='pendentes' class='dropdown-content' style="width: 150px">
                                        <li><a data-url="minha-conta/profissional/em-disputa" href="#emDisputa" style="width: 150px">Disputas</a></li>
                                        <li class="divider" tabindex="-1"></li>
                                        <li><a data-url="minha-conta/profissional/em-orcamento" href="#paraOrcamento">Orçamento</a></li>
                                        <li class="divider" tabindex="-1"></li>
                                        <li><a data-url="minha-conta/profissional/para-contratar" href="#paraContratar">A contratar</a></li>
                                    </ul>
                                </div>

                                <div class="tab col dropdown-trigger sub-tab" data-target='atuais'
                                     style="width: 150px; border-bottom-width: 1px; border-bottom-color: #00b0ff; border-bottom-style: solid; margin-right: 10px">
                                    <a>Atuais<i class="material-icons right">expand_more</i></a>
                                    </a>
                                    <ul id='atuais' class='dropdown-content' style="width: 150px">
                                        <li><a data-url="minha-conta/profissional/para-fazer" href="#paraFazer" style="width: 150px">Para fazer</a></li>
                                        <li class="divider" tabindex="-1"></li>
                                        <li><a data-url="minha-conta/profissional/fazendo" href="#fazendo">Fazendo</a></li>
                                    </ul>
                                </div>

                                <div class="tab col dropdown-trigger sub-tab" data-target='finalizados'
                                     style="width: 150px; border-bottom-width: 1px; border-bottom-color: #00b0ff; border-bottom-style: solid; margin-right: 10px">
                                    <a>Finalizados<i class="material-icons right">expand_more</i></a>
                                    </a>
                                    <ul id='finalizados' class='dropdown-content' style="width: 150px">
                                        <li><a data-url="minha-conta/profissional/executados" href="#executados" style="width: 150px">Executados</a></li>
                                        <li class="divider" tabindex="-1"></li>
                                        <li><a data-url="minha-conta/profissional/cancelados" href="#cancelados">Cancelados</a></li>
                                    </ul>
                                </div>

                                <div class="right-align">
                                    <a href="minha-conta/profissional" class="waves-effect waves-light btn"><i class="material-icons right">sync</i>ATUALIZAR</a>
                                </div>
                            </div>

                            <div id="conteudo">
                                <div id="disponiveis" class="col s12">
                                </div>
                                <div id="emDisputa" class="col s12">
                                </div>
                                <div id="paraOrcamento" class="col s12">
                                </div>
                                <div id="paraContratar" class="col s12">
                                </div>
                                <div id="paraFazer" class="col s12">
                                </div>
                                <div id="fazendo" class="col s12">
                                </div>
                                <div id="executados" class="col s12">
                                </div>
                                <div id="cancelados" class="col s12">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>

<script>
    $(document).ready(function () {
        // $('.tabs').tabs();

        $('#disponiveis').load($('.tab').attr("data-url"), function (result) {
            window.location.hash = "#disponiveis";
            $('#tab-default').click();
        });
    });

    function ine () {
        return $('#conteudo').html('' +
            '<div id="disponiveis" class="col s12">' +
            '</div><div id="emDisputa" class="col s12"></div>' +
            '<div id="paraOrcamento" class="col s12"></div>' +
            '<div id="paraContratar" class="col s12"></div>' +
            '<div id="paraFazer" class="col s12"></div>' +
            '<div id="fazendo" class="col s12"></div>' +
            '<div id="executados" class="col s12"></div>' +
            '<div id="cancelados" class="col s12"></div>');
    };

    $('.tab a').click(function (e) {
        e.preventDefault();
        ine()
        let url = $(this).attr("data-url");
        let href = this.hash;
        window.location.hash = href;

        let urlParams = new URLSearchParams(window.location.search);
        let id = (urlParams.has('id')) ? urlParams.get('id') : 0;
        url += '?id=' + id;
        console.log(url);
        // console.log(href)
        $(href).load(url, function (result) {
            // console.log(result)
        });
    });

    document.addEventListener('DOMContentLoaded', function() {
        // let delimitador = document.getElementById('sub-tab');
        let elems = document.querySelectorAll('.sub-tab');
        console.log((elems))
        let t = M.Dropdown.init(elems, {
            coverTrigger:false,
            constrainWidth:false,
            // container:delimitador
        });
        // console.log(t)
    });
</script>