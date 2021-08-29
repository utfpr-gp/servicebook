<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:if test="${empty jobs}">
    <div class="container">
        <div class="row">
            <div class="col s12 spacing-buttons">
                <div class="none-profission center">
                    <i class="material-icons large"> sentiment_dissatisfied </i>
                    <p class="center text-form-dados">
                        Nenhuma solicitação encontrada!
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
                                <h5 class="center white-text"> ${job.expertise.name} </h5>
                            </div>
                            <div class="col s2">
                                <h5 class="right white-text badge-service"> ${job.totalCandidates}/${job.quantityCandidatorsMax}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="row no-margin">
                        <div class="col s12 center center-align">
                            <svg style="width:100px;height:100px" viewBox="0 0 24 24">
                                <path class="dark-color-icon" d="M11,2H13V4H13.5A1.5,1.5 0 0,1 15,5.5V9L14.56,9.44L16.2,12.28C17.31,11.19 18,9.68 18,8H20C20,10.42 18.93,12.59 17.23,14.06L20.37,19.5L20.5,21.72L18.63,20.5L15.56,15.17C14.5,15.7 13.28,16 12,16C10.72,16 9.5,15.7 8.44,15.17L5.37,20.5L3.5,21.72L3.63,19.5L9.44,9.44L9,9V5.5A1.5,1.5 0 0,1 10.5,4H11V2M9.44,13.43C10.22,13.8 11.09,14 12,14C12.91,14 13.78,13.8 14.56,13.43L13.1,10.9H13.09C12.47,11.5 11.53,11.5 10.91,10.9H10.9L9.44,13.43M12,6A1,1 0 0,0 11,7A1,1 0 0,0 12,8A1,1 0 0,0 13,7A1,1 0 0,0 12,6Z" />
                            </svg>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s6">
                            <p class="center center-align text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">location_on</i>
                            </p>
                            <p class="center text-form-dados primary-color-text">
                                    ${job.client.address.neighborhood}, ${job.client.address.city.name}/${job.client.address.city.state.uf}
                            </p>
                        </div>
                        <div class="col s6">
                            <p class="center text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">access_time</i>
                            </p>
                            <p class="center text-form-dados primary-color-text">
                                    Solicitado: ${job.dateCreated}
                            </p>
                        </div>
                    </div>
                    <blockquote class="light-blue lighten-5 info-headers">
                        <p>${job.description}</p>
                    </blockquote>
                    <div>
                        <div class="row no-margin center">
                            <div class="col l2 offset-l8 spacing-buttons center">
                                <div class="center">
                                    <a href="#modal-delete" data-url="${pageContext.request.contextPath}/minha-conta/meus-pedidos/${job.id}" data-name="${job.description}" class="waves-effect waves-light btn modal-trigger">Excluir</a>                                                        </div>
                               </div>
                            <div class="col l2 spacing-buttons center">
                                <div class="center">
                                    <a class="waves-effect waves-light btn" href="minha-conta/meus-pedidos/${job.id}">Ver</a>
                                </div>
                            </div>
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
