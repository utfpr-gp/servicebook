<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:if test="${empty jobs}">
    <div class="container">
        <div class="row">
            <div class="col s12 spacing-buttons">
                <div class="none-profission">
                    <p class="center text-form-dados">
                        Nenhum pedido para fazer encontrado!
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
                                <h5 class="center white-text"> ${job.expertise.name} </h5>
                            </div>
                            <div class="col s2">
                                <h5 class="right white-text badge-service"> ${job.totalCandidates}/${job.quantityCandidatorsMax}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s4">
                            <p class="center text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">person</i>
                            </p>
                            <p class="center text-form-dados primary-color-text"> ${job.client.name} </p>
                        </div>
                        <div class="col s4">
                            <p class="center center-align text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">location_on</i>
                            </p>
                            <p class="center text-form-dados primary-color-text">
                                    ${job.client.address.neighborhood}, ${job.client.address.city.name}/${job.client.address.city.state.uf}
                            </p>
                        </div>
                        <div class="col s4">
                            <p class="center text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">access_time</i>
                            </p>
                            <p class="center text-form-dados primary-color-text">
                                    ${job.textualDate}
                            </p>
                        </div>
                    </div>
                    <blockquote class="light-blue lighten-5 info-headers">
                        <p>${job.description}</p>
                    </blockquote>
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
