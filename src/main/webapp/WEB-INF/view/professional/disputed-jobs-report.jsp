<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<div class="container col s12 center-align">
    <t:pagination-tab-ajax pagination="${pagination}"></t:pagination-tab-ajax>
</div>

<c:if test="${empty jobs}">
    <div class="container">
        <div class="row">
            <div class="col s12 spacing-buttons">
                <div class="none-profission">
                    <p class="center text-form-dados">
                        Nenhum pedido em disputa encontrado!
                    </p>
                    <p class="center">
                        Um novo pedido pode chegar aqui a qualquer momento.
                    </p>
                </div>
            </div>
        </div>
    </div>
</c:if>

<c:forEach var="job" items="${jobs}">
    <div class="container">
        <div class="row">
            <div class="col s12 spacing-buttons">
                <div style="border: solid 1px black">
                    <div class="secondary-background-color">
                        <div class="row">
                            <div class="col s8 offset-s2">
                                <h5 class="center white-text"> ${job.jobRequest.expertise.name} </h5>
                            </div>
                            <div class="col s2">
                                <h5 class="right white-text badge-service"> ${job.jobRequest.totalCandidates}/${job.jobRequest.quantityCandidatorsMax}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s4">
                            <p class="center text-form-dados primary-color-text">

                                <c:if test="${job.jobRequest.client.profilePicture == null}">
                                    <i class="material-icons small dark-color-text">person</i>
                                </c:if>
                                <c:if test="${job.jobRequest.client.profilePicture != null}">
                                    <img src="${job.jobRequest.client.profilePicture}" alt="Cliente - Imagem de perfil."
                                         style="width:100px;height:100px" class="circle">
                                </c:if>

                            </p>
                            <p class="center text-form-dados primary-color-text"> ${job.jobRequest.client.name} </p>
                        </div>
                        <div class="col s4">
                            <p class="center center-align text-form-dados primary-color-text">

                                <c:if test="${job.jobRequest.client.address.city.image == null}">
                                    <i class="material-icons small dark-color-text">location_on</i>
                                </c:if>
                                <c:if test="${job.jobRequest.client.address.city.image != null}">
                                    <img src="${job.jobRequest.client.address.city.image}" alt="Imagem da Cidade."
                                         style="width:100px;height:100px" class="circle">
                                </c:if>

                            </p>
                            <p class="center text-form-dados primary-color-text">
                                    ${job.jobRequest.client.address.neighborhood}, ${job.jobRequest.client.address.city.name}/${job.jobRequest.client.address.city.state.uf}
                            </p>
                        </div>
                        <div class="col s4">
                            <p class="center text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">access_time</i>
                            </p>
                            <p class="center text-form-dados primary-color-text">

                                <c:choose>
                                    <c:when test="${job.jobRequest.intervalOfDays == 0}">
                                        Para hoje
                                    </c:when>
                                    <c:when test="${job.jobRequest.intervalOfDays == 1}">
                                        Para amanhã
                                    </c:when>
                                    <c:otherwise>
                                        Para os próximos dias
                                    </c:otherwise>
                                </c:choose>

                            </p>
                        </div>
                    </div>
                    <blockquote class="light-blue lighten-5 info-headers">
                        <p>${job.jobRequest.description}</p>
                    </blockquote>
                    <div class="center">
                        <p>Solicitado em: ${job.jobRequest.dateCreated}</p>
                        <p>Disponível até: ${job.jobRequest.dateExpired}</p>

                        <c:if test="${job.jobRequest.intervalOfDays == 0}">
                            <p>O cliente solicitou para: hoje</p>
                        </c:if>
                        <c:if test="${job.jobRequest.intervalOfDays == 1}">
                            <p>O cliente solicitou para: amanhã</p>
                        </c:if>
                        <c:if test="${job.jobRequest.intervalOfDays > 1}">
                            <p>O cliente solicitou o serviço para daqui a: ${job.jobRequest.intervalOfDays} dias.</p>
                        </c:if>

                        <p>Você se candidatou para este serviço em: ${job.date}</p>

                    </div>
                    <div>
                        <div class="center">
                            <a href="" class="waves-effect waves-light btn spacing-buttons">
                                Detalhes
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<div class="container col s12 center-align">
    <t:pagination-tab-ajax pagination="${pagination}"></t:pagination-tab-ajax>
</div>
