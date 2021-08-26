<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:client title="Detalhes da Solicitação">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">

                            <c:if test="${empty candidates}">
                                <div class="col s12 center">
                                    <i class="material-icons large"> sentiment_dissatisfied </i>
                                    <h2 class="secondary-color-text"> Não há nenhum candidato.</h2>
                                </div>
                            </c:if>

                        <c:if test="${not empty candidates}">

                        <div class="col s12">
                            <h2 class="secondary-color-text spacing-standard tertiary-color-text">Escolha um ${expertise.name}!</h2>

                        </div>
                        <div class="col s12 m6 tertiary-color-text description-job  text-info-request">
                            <p>${jobRequest.description}</p>
                            <p>Pedido expedido em ${jobRequest.dateExpired}</p>
                        </div>
                        <div class="col s12 m6 l3">
                            <div class="center">
                                <a href="#!" class="spacing-buttons waves-effect waves-light btn">Parar de receber propostas</a>
                            </div>
                        </div>
                        <div class="col s12 m6 l3">
                            <div class="center">
                                <a href="#!" class="spacing-buttons waves-effect waves-light btn">Excluir o pedido</a>
                            </div>
                        </div>
                        <div class="col s12 tertiary-color-text description-orcamento text-info-request">
                            <hr class="hr-request-area">
                            <p>Entre em contato com um ou mais profissionais que se interessaram em realizar o serviço para marcar um orçamento.</p>
                            <p>${candidates.size()} profissionais responderam a sua solicitação:</p>
                        </div>
                            <c:forEach var="jobCandidate" items="${candidates}">

                            <div class="col s12 l4">
                                <div class="card-panel card-candidate">
                                    <div class="row">

                                        <div class="col s12 icons-area-request center padding">
                                            <div class="row">
                                                <div class="col s6 center">

                                                    <div class="left star-icons candidate dark-color-text">


                                                                <c:forEach var="star" begin="1" end="5">
                                                                                    <c:if test="${star <= jobCandidate.professional.rating}">
                                                                                        <i class="material-icons dark-text small">star</i>
                                                                                    </c:if>
                                                                                    <c:if test="${star > jobCandidate.professional.rating}">
                                                                                        <i class="material-icons dark-text small">star_border</i>
                                                                                    </c:if>
                                                                </c:forEach>

                                                        </div>
                                                    </div>
                                                    <div class="col s6">
                                                        <div class="right check-circle-candidate">
                                                            <i class="material-icons green-text darken-3-text">check_circle</i>
                                                        </div>
                                                    </div>

                                            </div>
                                        </div>
                                        <div class="col s12 center">

                                            <c:if test="${jobCandidate.professional.profilePicture == null}">
                                                <svg style="width:220px;height:220px" viewBox="0 0 24 24">
                                                    <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                                                </svg>
                                            </c:if>

                                            <c:if test="${jobCandidate.professional.profilePicture != null}">
                                                <div class="row">
                                                    <img src="${jobCandidate.professional.profilePicture}" alt="Profissional - Imagem de perfil."
                                                         style="width:200px;height:200px">
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="col s12">
                                            <div class="center title-card-resquest">
                                                <P>${jobCandidate.professional.description}</P>
                                            </div>
                                            <p class="contact-item center-block dark-color-text">
                                                <c:if test="${jobCandidate.professional.emailVerified}">
                                                    <i class="medium material-icons green-text tooltipped middle" data-position="top"
                                                       data-tooltip="Email verificado.">email </i>${jobCandidate.professional.email}
                                                </c:if>
                                                <c:if test="${!jobCandidate.professional.emailVerified}">
                                                    <i class="medium material-icons gray-text tooltipped middle" data-position="top"
                                                       data-tooltip="Email não verificado.">email</i> ${jobCandidate.professional.email}
                                                </c:if>
                                            </p>

                                            <p class="contact-item center-block dark-color-text">
                                                <c:if test="${jobCandidate.professional.phoneVerified}">
                                                    <i class="medium material-icons green-text tooltipped middle" data-position="top"
                                                       data-tooltip="Telefone verificado.">phone </i>${jobCandidate.professional.phoneNumber}
                                                </c:if>
                                                <c:if test="${!jobCandidate.professional.phoneVerified}">
                                                    <i class="medium material-icons gray-text tooltipped middle" data-position="top"
                                                       data-tooltip="Telefone não verificado.">phone</i> ${jobCandidate.professional.phoneNumber}
                                                </c:if>
                                            </p>

                                            <div class="center">
                                                <p><a class="tertiary-color-text " href="#!">Detalhes</a></p>
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
