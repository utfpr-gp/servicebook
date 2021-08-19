<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:client title="Minhas Solicitações">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <c:if test="${empty jobRequests}">
                            <div class="col s12 center">
                                <i class="material-icons large"> sentiment_dissatisfied </i>
                                <h3 class="center-block"> Não há nenhuma solicitação.</h3>
                            </div>
                        </c:if>

                        <c:if test="${not empty jobRequests}">
                            <h3 class="secondary-color-text center spacing-standard">Minhas solicitações</h3>
                            <c:forEach var="jobRequest" items="${jobRequests}">
                            <div class="row card-request spacing-standard">
                                <div class="col s8 m10 l2 center">
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
                                    <div class="row">
                                        <div class="col s6 m6 l2 offset-l8 spacing-buttons center">
                                            <div class="center">
                                                <a class="waves-effect waves-light btn" href="#!">Excluir</a>
                                            </div>
                                        </div>
                                        <div class="col s6 m6  l2 spacing-buttons center">
                                            <div class="center">
                                                <a class="waves-effect waves-light btn" href="#!">Ver</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:client>
