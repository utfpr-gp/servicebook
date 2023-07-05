<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib
        uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib
        prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Minhas Solicitações" userInfo="${user}">
    <jsp:body>
        <main class="container">
            <div class="row">

                <t:side-panel userInfo="${user}"></t:side-panel>

                <div class="col m10 offset-m1 l9">

                    <!-- Painel com as solicitações de serviços -->
                    <div class="">
                        <div class="row">
                            <div class="col s12">
                                <h2 class="secondary-color-text">Minhas Solicitações</h2>
                            </div>

                            <ul class="tabs tabs-fixed-width center">
                                <li class="tab" id="1">
                                    <a id="tab-default" data-url="minha-conta/cliente/meus-pedidos/disponiveis"
                                       href="#disponiveis">
                                        DISPONÍVEIS
                                    </a>
                                </li>
                                <li class="tab" id="2">
                                    <a data-url="minha-conta/cliente/meus-pedidos/para-orcamento" href="#paraOrcamento">
                                        PARA ORÇAMENTO
                                    </a>
                                </li>
                                <li class="tab" id="3">
                                    <a data-url="minha-conta/cliente/meus-pedidos/para-fazer" href="#paraFazer">
                                        PARA FAZER
                                    </a>
                                </li>
                                <li class="tab" id="3">
                                    <a data-url="minha-conta/cliente/meus-pedidos/fazendo" href="#fazendo">
                                        FAZENDO
                                    </a>
                                </li>
                                <li class="tab" id="4">
                                    <a data-url="minha-conta/cliente/meus-pedidos/executados" href="#executados">
                                        CONCLUÍDOS
                                    </a>
                                </li>
                            </ul>
                            <div id="disponiveis" class="col s12 no-padding">

                            </div>
                            <div id="paraOrcamento" class="col s12 no-padding">

                            </div>
                            <div id="paraFazer" class="col s12 no-padding">

                            </div>
                            <div id="fazendo" class="col s12 no-padding"></div>
                            <div id="executados" class="col s12 no-padding">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="modal-delete" class="modal">
                <div class="modal-content">
                    <form action="" method="post">
                        <input type="hidden" name="_method" value="DELETE"/>

                        <div class="modal-content">
                            <h4>
                                Você tem certeza que deseja excluir
                                <strong id="strong-name"></strong>?
                            </h4>
                        </div>
                        <div class="modal-footer">
                            <button
                                    type="button"
                                    class="modal-close btn-flat waves-effect waves-light btn btn-gray"
                            >
                                Cancelar
                            </button>
                            <button
                                    type="submit"
                                    class="modal-close btn waves-effect waves-light gray"
                            >
                                Sim, excluir!
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <div id="modal-msg" class="modal">
                <div class="modal-content">
                    <div class="modal-content">
                        <h4>${msg}</h4>
                    </div>
                    <div class="modal-footer">
                        <button
                                type="submit"
                                class="modal-close btn waves-effect waves-light gray"
                        >
                            OK
                        </button>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template>
<script src="assets/resources/scripts/requests.js"></script>

<c:if test="${not empty msg}">
    <script>
        $(document).ready(function () {
            $("#modal-msg").modal("open");
        });
    </script>
</c:if>
