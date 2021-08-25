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
                        Nenhum pedido encontrado!
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
    ${job}

    <div class="container">
        <div class="row">
            <div class="col s12 spacing-buttons">
                <div style="border: solid 1px black">
                    <div class="secondary-background-color">
                        <div class="row">
                            <div class="col s8 offset-s2">
<%--                                <h5 class="center white-text"> ${job.expertise.name} </h5>--%>
<%--                            </div>--%>
<%--                            <div class="col s2">--%>
<%--                                <h5 class="right white-text badge-service"> ${job.totalCandidates}/${job.quantityCandidatorsMax}</h5>--%>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s3">
                            <p class="center text-form-dados primary-color-text">

<%--                                <c:if test="${job.client.profilePicture == null}">--%>
<%--                                    <i class="material-icons small dark-color-text">person</i>--%>
<%--                                </c:if>--%>
<%--                                <c:if test="${job.client.profilePicture != null}">--%>
<%--                                    <img src="${job.client.profilePicture}" alt="Cliente - Imagem de perfil."--%>
<%--                                         style="width:100px;height:100px" class="circle">--%>
<%--                                </c:if>--%>

<%--                            </p>--%>
<%--                            <p class="center text-form-dados primary-color-text"> ${job.client.name} </p>--%>
                        </div>
                        <div class="col s6">
                            <p class="center text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">location_on</i>
                            </p>
                            <p class="center text-form-dados primary-color-text">
                                Santana, Guarapuava-PR
                            </p>
                        </div>
                        <div class="col s3">
                            <p class="center text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">access_time</i>
                            </p>
                            <p class="center text-form-dados primary-color-text">
                                Próxima semana
                            </p>
                        </div>
                    </div>
<%--                    <blockquote class="light-blue lighten-5 info-headers">--%>
<%--                        <p>${job.description}</p>--%>
<%--                    </blockquote>--%>
<%--                    <div class="center">--%>
<%--                        <p>Solicitado em: ${job.dateCreated}</p>--%>
<%--                        <p>Disponível até: ${job.dateExpired}</p>--%>
<%--                    </div>--%>
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
