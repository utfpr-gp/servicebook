<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:template title="ServiceBook - Minha conta" userInfo="${userInfo}">
    <jsp:body>
        <main class="container">
            <div class="row">
                <!-- Painel lateral -->
                <div class="col m4 l3 hide-on-med-and-down">
                    <t:side-panel userInfo="${userInfo}" followdto="${followdto}" statisticInfo="${statisticInfo}"></t:side-panel>
                </div>

                <!-- Painel com as solicitações de serviços -->
                <div class="col s12 l9">
                    <div class="row">
                        <div class="col s12">
                            <h2 class="secondary-color-text">Anúncios de serviços</h2>
                        </div>

                        <div class="col s12">
                            <label>
                                <input name="group1" type="radio" id="open-radio" checked />
                                <span style="color: rgb(63 81 181)">ABERTOS</span>
                            </label>
                            <label>
                                <input name="group1" type="radio" id="todo-radio" class="blue"/>
                                <span style="color: rgb(63 81 181)">PARA FAZER</span>
                            </label>
                            <label>
                                <input name="group1" type="radio" id="closed-radio" class="blue"/>
                                <span style="color: rgb(63 81 181)">REALIZADOS</span>
                            </label>
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
                        </div>

                        <!-- TABS -->
                        <div class="row" style="margin-top: 20px">
                            <ul class="tabs tabs-fixed-width center">
                                <li class="tab open-jobs" id="1">
                                    <a id="tab-default" data-url="minha-conta/profissional/em-disputa" href="#emDisputa">EM DISPUTA</a>
                                </li>
                                <li class="tab open-jobs" id="2">
                                    <a data-url="minha-conta/profissional/em-orcamento" href="#paraOrcamento">PARA ORÇAMENTO</a>
                                </li>
                                <li class="tab open-jobs" id="3">
                                    <a data-url="minha-conta/profissional/para-contratar" href="#paraContratar">PARA CONFIRMAR</a>
                                </li>
                                <li class="tab todo-jobs" id="4" style="display: none">
                                    <a id="tab-default-todo" data-url="minha-conta/profissional/para-fazer" href="#paraFazer">PARA FAZER</a>
                                </li>
                                <li class="tab todo-jobs" id="5" style="display: none">
                                    <a data-url="minha-conta/profissional/fazendo" href="#fazendo">FAZENDO</a>
                                </li>
                                <li class="tab closed-jobs" id="6" style="display: none">
                                    <a id="tab-default-closed" data-url="minha-conta/profissional/executados" href="#executados">EXECUTADOS</a>
                                </li>
                                <li class="tab closed-jobs" id="7" style="display: none">
                                    <a data-url="minha-conta/profissional/cancelados" href="#cancelados">CANCELADOS</a>
                                </li>
                            </ul>
                        </div>
                        <!-- Fim TABS -->
                        <div>
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
        </main>

    </jsp:body>
</t:template>

<script>
    console.log('oi')

    $(document).ready(function () {
        $('.tabs').tabs();

        $('#emDisputa').load($('.tab .active').attr("data-url"), function (result) {
            window.location.hash = "#emDisputa";
            $('#tab-default').click();
        });

        $('#open-radio').click(function (){
            $('.todo-jobs').hide();
            $('.closed-jobs').hide();
            $('.open-jobs').show();
            setTimeout(function () {
                $('#tab-default').click();
            }, 100);
            //$('#tab-default').click();
        });

        $('#todo-radio').click(function (){
            $('.open-jobs').hide();
            $('.closed-jobs').hide();
            $('.todo-jobs').show();
            setTimeout(function () {
                $('#tab-default-todo').click();
            }, 100);
            //$('#tab-default-todo').click();
        });

        $('#closed-radio').click(function (){
            $('.todo-jobs').hide();
            $('.closed-jobs').show();
            $('.open-jobs').hide();
            setTimeout(function () {
                $('#tab-default-closed').click();
            }, 100);
            //$('#tab-default-closed').click();
        });
    });

    $('.tab a').click(function (e) {
        e.preventDefault();
        let url = $(this).attr("data-url");
        let href = this.hash;
        window.location.hash = href;

        let urlParams = new URLSearchParams(window.location.search);
        let id = (urlParams.has('id')) ? urlParams.get('id') : 0;
        url += '?id=' + id;
        console.log(url);
        // console.log(href)
        $(href).load(url, function (result) {
            console.log(result)
            //window.scrollTo(0,0);
        });
    });
</script>