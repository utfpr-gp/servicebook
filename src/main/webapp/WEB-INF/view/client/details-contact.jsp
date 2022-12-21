<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Detalhes de Contato">
    <jsp:body>

    <main>
            <div class="section no-padding">
                <div class="row">
                    <div class="row no-margin col s12  blue lighten-4">
                        <div class="col s12 icons-area-request">
                            <div class="row">
                                <div class="col s4 star-icons dark-color-text">
                                   <span class="tooltipped"  data-position="right" data-tooltip="avaliação - ${individual.rating} estrela ">
                                        <c:forEach var="individual.rating" begin="1" end="${individual.rating}">
                                            <i class="material-icons yellow-text small">star</i>
                                        </c:forEach>
                                   </span>
                                </div>
                                <div class="col s4 center">
                                    <div class="center">
                                        <c:if test="${individual.emailVerified == true}">
                                            <i class="spacing-buttons material-icons small green-text tooltipped" data-position="bottom" data-tooltip="Email verificado">email</i>
                                        </c:if>
                                        <c:if test="${individual.emailVerified == false}">
                                            <i class="spacing-buttons material-icons small grey-text tooltipped" data-position="bottom" data-tooltip="Email não verificado">email</i>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col s4 right">
                                    <div class="right">
                                        <c:if test="${individual.phoneVerified == true}">
                                            <i class="spacing-buttons material-icons small green-text tooltipped"  data-position="left" data-tooltip="Telefone verificado">phone</i>
                                        </c:if>
                                        <c:if test="${individual.phoneVerified == false}">
                                            <i class="spacing-buttons material-icons small grey-text tooltipped" data-position="left" data-tooltip="Telefone não verificado">phone</i>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 center">
                            <c:if test="${individual.profilePicture == ''}">
                                <svg style="width:200px;height:200px" viewBox="0 0 24 24">
                                    <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                                </svg>
                            </c:if>
                            <c:if test="${individual.profilePicture != ''}">
                                <div width="200px" height="200px">
                                    <img src="${individual.profilePicture}" width="200px" alt="">
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="col s12 no-padding">
                        <div class="center title-card-resquest">
                            <P class="no-margin">${individual.name}</P>
                        </div>
                    </div>
                    <div class="col s12 m6  text-info-request">
                        <p class="contact-item center"><i class="material-icons dark-color-text">email</i>
                            <c:if test="${individual.email == ''}">
                                Sem email
                            </c:if>
                            <c:if test="${individual.email != ''}">
                                ${individual.email}
                            </c:if>
                        </p>
                    </div>
                    <div class="col s12 m6  text-info-request">
                        <p class="contact-item center"><i class="material-icons dark-color-text">phone</i>
                            <c:if test="${individual.phoneNumber == ''}">
                                Sem telefone
                            </c:if>
                            <c:if test="${individual.phoneNumber != ''}">
                                ${individual.phoneNumber}
                            </c:if>

                        </p>
                    </div>
                    <div class="col s12 text-info-request">
                        <h4 class="center primary-color-text">Anúncio</h4>
                        <c:if test="${individual.description == ''}">
                            <p class="contact-item center grey-text">Sem anúncio</p>
                        </c:if>
                        <c:if test="${individual.description != ''}">
                            <p class="contact-item center dark-color-text">${individual.description}</p>
                        </c:if>
                    </div>
                    <div class="col s12">
                        <div class="container secondary-color-text  text-info-request">
                            <p>Após entrar em contato com o profissional e contratar o serviço, nos informe que foi este o profissional contratado para que você possa avaliá-lo  após a realização do serviço</p>
                        </div>
                    </div>
                    <div class="col s12">
                        <div class="container">
                            <c:if test="${not empty jobContracted}">
                                <h4>${jobContracted.size()} avaliações</h4>
                                <c:forEach var="jobContracted" items="${jobContracted}">
                                    <c:if test="${jobContracted.rating <= 2}">
                                        <blockquote class="red lighten-5"><p>${jobContracted.comments}</p></blockquote>
                                    </c:if>
                                    <c:if test="${jobContracted.rating > 2}">
                                        <blockquote class="cyan lighten-5"><p>${jobContracted.comments}</p></blockquote>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty jobContracted}">
                                <h4>0 avaliações</h4>
                                <p>Não possui comentários</p>
                            </c:if>
                        </div>
                    </div>
                    <div class="col s12 l4 offset-l4  spacing-standard">
                        <div class="container">
                            <div class="row">
                                <div class="col s6 spacing-buttons">
                                    <div class="center">
                                        <a class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                    </div>
                                </div>
                                <div class="col s6 spacing-buttons">
                                    <div class="center">
                                        <a class="waves-effect waves-light btn" href="#!">Contratar</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>
