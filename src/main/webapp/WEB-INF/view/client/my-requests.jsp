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
                        <div class="col s12 icons-area-request">
                            <div class="row center">
                                <div class="col s12 dark-color-text">
                                    <div class="row tooltipped" data-position="bottom"
                                         data-tooltip="${client} estrela (s).">
                                        <i class="material-icons yellow-text small">star_border</i>
                                        <i class="material-icons yellow-text small">star_border</i>
                                        <i class="material-icons yellow-text small">star</i>
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
                            <h5 class="name-header no-margin center white-text">
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

                    <div class="row">
                        <div class="container">

                            <c:if test="${empty jobRequests}">
                                <div class="col s12 center">
                                    <i class="material-icons large"> sentiment_dissatisfied </i>
                                    <h2 class="secondary-color-text"> Não há nenhuma solicitação.</h2>
                                </div>
                            </c:if>
                            <div class="col s12">

                                <c:if test="${not empty jobRequests}">
                            </div>
                            <div class="col s12">
                                <div class="row">
                                    <h2 class="secondary-color-text ">Minhas solicitações</h2>

                                    <c:forEach var="jobRequest" items="${jobRequests}">
                                        <div class="col s12">

                                            <div class="row card-request spacing-standard">
                                                <div class="col s8 m10 l2 center center-align">
                                                    <svg style="width:120px;height:120px" viewBox="0 0 24 24">
                                                        <path class="dark-color-icon" d="M11,2H13V4H13.5A1.5,1.5 0 0,1 15,5.5V9L14.56,9.44L16.2,12.28C17.31,11.19 18,9.68 18,8H20C20,10.42 18.93,12.59 17.23,14.06L20.37,19.5L20.5,21.72L18.63,20.5L15.56,15.17C14.5,15.7 13.28,16 12,16C10.72,16 9.5,15.7 8.44,15.17L5.37,20.5L3.5,21.72L3.63,19.5L9.44,9.44L9,9V5.5A1.5,1.5 0 0,1 10.5,4H11V2M9.44,13.43C10.22,13.8 11.09,14 12,14C12.91,14 13.78,13.8 14.56,13.43L13.1,10.9H13.09C12.47,11.5 11.53,11.5 10.91,10.9H10.9L9.44,13.43M12,6A1,1 0 0,0 11,7A1,1 0 0,0 12,8A1,1 0 0,0 13,7A1,1 0 0,0 12,6Z" />
                                                    </svg>
                                                </div>
                                                <div class="col s4 m2 hide-on-large-only">
                                                    <div class="center">
                                                        <div class="badge-requests no-margin right"><span>${jobRequest.amountOfCandidates}</span></div>
                                                    </div>
                                                </div>
                                                <div class="col s12 l8 text-detail-request">
                                                    <p>${jobRequest.expertiseDTO.name}</p>
                                                    <p class="truncate">${jobRequest.description}</p>
                                                    <p>Solicitado: ${jobRequest.dateCreated}</p>
                                                </div>
                                                <div class="col s4 l2 hide-on-med-and-down">
                                                    <div class="center">
                                                        <div class="badge-requests no-margin right"><span>${jobRequest.amountOfCandidates}</span></div>
                                                    </div>
                                                </div>
                                                <div class="col s12">
                                                    <div class="row no-margin">
                                                        <div class="col s6 m6 l2 offset-l8 spacing-buttons center">
                                                            <div class="center">
                                                                <a href="#modal-delete" data-url="${pageContext.request.contextPath}/minha-conta/meus-pedidos/${jobRequest.id}" data-name="${jobRequest.description}" class="waves-effect waves-light btn modal-trigger">Excluir</a>                                                        </div>
                                                        </div>
                                                        <div class="col s6 m6  l2 spacing-buttons center">
                                                            <div class="center">
                                                                <a class="waves-effect waves-light btn" href="minha-conta/meus-pedidos/${jobRequest.id}">Ver</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            </c:if>
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
