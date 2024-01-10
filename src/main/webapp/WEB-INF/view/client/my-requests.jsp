<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib
        uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib
        prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/client/client.css" rel="stylesheet">
</head>

<t:template-side-nav title="Minhas Solicitações" userInfo="${userInfo}">
    <jsp:body>
        <div class="breadcrumbs" style="margin-top: 20px">
            <a href="${pageContext.request.contextPath}/">Início</a> &gt;
            Minhas Solicitações
        </div>

        <h2 class="secondary-color-text">Minhas Solicitações</h2>
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
            <li class="tab" id="4">
                <a data-url="minha-conta/cliente/meus-pedidos/fazendo" href="#fazendo">
                    FAZENDO
                </a>
            </li>
            <li class="tab" id="5">
                <a data-url="minha-conta/cliente/meus-pedidos/executados" href="#executados">
                    CONCLUÍDOS
                </a>
            </li>
        </ul>
        <div id="disponiveis" class="col s12 no-padding"></div>
        <div id="paraOrcamento" class="col s12 no-padding"></div>
        <div id="paraFazer" class="col s12 no-padding"></div>
        <div id="fazendo" class="col s12 no-padding"></div>
        <div id="executados" class="col s12 no-padding"></div>
        <!-- fim row -->

        <!-- Modal de remoção -->
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
        <!-- Fim do modal de remoção -->

        <!-- Modal de mensagem -->
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
        <!-- Fim do modal de mensagem -->
        </main>
    </jsp:body>
</t:template-side-nav>
<script src="assets/resources/scripts/requests.js"></script>

<c:if test="${not empty msg}">
    <script>
        $(document).ready(function () {
            $("#modal-msg").modal("open");
        });
    </script>
</c:if>
