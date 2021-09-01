<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:client title="Minhas Solicitações">
    <jsp:body>

        <main>
            <div class="row">
                <div class="col s12 l3 hide-on-med-and-down no-padding" id="area-perfil">
                    <div class="row primary-background-color area-perfil no-margin">
                        <div class="col s12 icons-area-request client">
                            <div class="row center no-margin">
                                <div class="col s12 dark-color-text">
                                    <div class="row tooltipped" data-position="bottom"
                                        data-tooltip="${client.rating} estrela (s).">
                                        <c:forEach var="star" begin="1" end="5">
                                            <c:if test="${star <= client.rating}">
                                                <i class="material-icons dark-color-icon-text small">star</i>
                                            </c:if>
                                            <c:if test="${star > client.rating}">
                                                <i class="material-icons dark-color-icon-text small">star_border</i>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 center">

                            <c:if test="${client.profilePicture == null}">
                                <svg class="icon-person" style="width:250px;height:250px" viewBox="0 0 24 24">
                                    <path class="dark-color-icon"
                                          d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"/>
                                </svg>
                            </c:if>

                            <c:if test="${client.profilePicture != null}">
                                <div class="row">
                                    <img src="${client.profilePicture}" alt="Cliente - Imagem de perfil."
                                         style="width:250px;height:250px">
                                </div>
                            </c:if>
                            <div class="row">
                                <p>Perfil sem descrição.</p>
                            </div>

                            <h5 class="edit-link tertiary-color-text">
                                <a class="tertiary-color-text" href="">Editar perfil</a>
                            </h5>
                        </div>
                    </div>
                    <div class="row secondary-background-color no-margin">
                        <div class="col s12">
                            <h5 class="name-header client no-margin center white-text">
                                <strong>${client.name}</strong>
                            </h5>
                        </div>
                    </div>

                    <div class="row primary-background-color no-margin">
                        <div class="col s12">
                            <p class="header-verification tertiary-color-text center">VERIFICAÇÃO DO PERFIL</p>
                        </div>
                    </div>
                    <div class="row secondary-background-color no-margin">
                        <div class="col s4 center no-padding">

                            <c:if test="${client.profileVerified}">
                                <i class="medium material-icons green-text tooltipped" data-position="top"
                                   data-tooltip="Perfil verificado.">person</i>
                            </c:if>
                            <c:if test="${!client.profileVerified}">
                                <i class="medium material-icons gray-text tooltipped" data-position="top"
                                   data-tooltip="Perfil não verificado.">person</i>
                            </c:if>

                        </div>
                        <div class="col s4 center no-padding">

                            <c:if test="${client.emailVerified}">
                                <i class="medium material-icons green-text tooltipped" data-position="top"
                                   data-tooltip="Email verificado.">email</i>
                            </c:if>
                            <c:if test="${!client.emailVerified}">
                                <i class="medium material-icons gray-text tooltipped" data-position="top"
                                   data-tooltip="Email não verificado.">email</i>
                            </c:if>

                        </div>
                        <div class="col s4 center no-padding">

                            <c:if test="${client.phoneVerified}">
                                <i class="medium material-icons green-text tooltipped" data-position="top"
                                   data-tooltip="Telefone verificado.">phone</i>
                            </c:if>
                            <c:if test="${!client.phoneVerified}">
                                <i class="medium material-icons gray-text tooltipped" data-position="top"
                                   data-tooltip="Telefone não verificado.">phone</i>
                            </c:if>

                        </div>
                    </div>
                    <div class="row no-margin center">
                        <div class="col s12 no-margin no-padding input-field area-profission-select">

                            <div class="center spacing-buttons">
                                <button class="waves-effect waves-light btn">
                                    Acessar como profissional
                                </button>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="col m10 offset-m1 l9">
                    <a id="show-area-perfil"
                       class="hide-on-large-only show-area-perfil waves-effect waves-light btn btn-floating grey darken-3 z-depth-A">
                        <i class="material-icons">compare_arrows</i>
                    </a>
                    <div class="container">
                    <div class="row">
                            <div class="col s12">
                                <h2 class="secondary-color-text">Minhas Solicitações</h2>
                            </div>

                        <div class="center">
                            <a href="minha-conta/meus-pedidos" class="waves-effect waves-light btn"><i class="material-icons right">sync</i>ATUALIZAR</a>
                        </div>

                        <ul class="tabs tabs-fixed-width center">
                            <li class="tab">
                                <a id="tab-default" data-url="minha-conta/meus-pedidos/disponiveis"
                                   href="#disponiveis">
                                    DISPONÍVEIS
                                </a>
                            </li>
                            <li class="tab">
                                <a data-url="minha-conta/meus-pedidos/para-orcamento" href="#paraOrcamento">
                                    PARA ORÇAMENTO
                                </a>
                            </li>
                            <li class="tab">
                                <a data-url="minha-conta/meus-pedidos/para-fazer" href="#paraFazer">
                                    PARA FAZER
                                </a>
                            </li>
                            <li class="tab">
                                <a data-url="minha-conta/meus-pedidos/executados" href="#executados">
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
                            <h4>Você tem certeza que deseja excluir <strong id="strong-name"></strong>?</h4>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                            <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>

    </jsp:body>
</t:client>
<script src="assets/resources/scripts/requests.js"></script>
