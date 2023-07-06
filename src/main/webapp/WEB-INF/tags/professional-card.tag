<%@tag description="Servicebook - Banner template" pageEncoding="UTF-8" %>
<%@attribute name="cities" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="professional" type="br.edu.utfpr.servicebook.model.entity.User" %>

<div class="col s12 l6 xl4">
    <div class="card-panel card-candidate">
        <div class="row no-margin">
            <div class="col s12 icons-area-request center padding">
                <div class="row">
                    <div class="col s6 center">
                        <div class="left star-icons candidate dark-color-text">
                            <c:forEach var="star" begin="1" end="5">
                                <c:if test="${star <= professional.rating}">
                                    <i class="material-icons dark-text small">star</i>
                                </c:if>
                                <c:if test="${star > professional.rating}">
                                    <i class="material-icons dark-text small">star_border</i>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="col s6">
                        <div class="col s4 left">
                            <div class="right check-circle-candidate">
                                <i class="material-icons green-text darken-3-text">check_circle</i>
                            </div>
                        </div>
                        <div class="col s8 right">
                            <div class="left black-text">${jobCandidate.individual.followsAmount != 0 ? jobCandidate.individual.followsAmount : " "}</div>
                            <div class="right"><i alt="seguir"
                                                  class="material-icons black-text small">thumb_up</i>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="col s12 center">
                <c:if test="${professional.profilePicture == null}">
                    <svg style="width:220px;height:220px" viewBox="0 0 24 24">
                        <path class="dark-color-icon"
                              d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"/>
                    </svg>
                </c:if>
                <c:if test="${professional.profilePicture != null}">
                    <div class="row">
                        <img src="${professional.profilePicture}"
                             alt="Profissional - Imagem de perfil."
                             style="width:200px;height:200px">
                    </div>
                </c:if>
            </div>
            <div class="col s12">
                <div class="center title-card-resquest">
                    <p class="truncate">${professional.name}</p>
                </div>
                <p class="contact-item center-block dark-color-text">
                    <c:if test="${professional.emailVerified}">
                        <i class="medium material-icons green-text tooltipped middle truncate"
                           data-position="top"
                           data-tooltip="Email verificado.">email </i>${professional.email}
                    </c:if>
                    <c:if test="${!professional.emailVerified}">
                        <i class="medium material-icons gray-text tooltipped middle"
                           data-position="top"
                           data-tooltip="Email não verificado.">email</i> ${professional.email}
                    </c:if>
                </p>
                <p class="contact-item center-block dark-color-text">
                    <c:if test="${professional.phoneVerified}">
                        <i class="medium material-icons green-text tooltipped middle"
                           data-position="top"
                           data-tooltip="Telefone verificado.">phone </i>${professional.phoneNumber}
                    </c:if>
                    <c:if test="${!professional.phoneVerified}">
                        <i class="medium material-icons gray-text tooltipped middle"
                           data-position="top"
                           data-tooltip="Telefone não verificado.">phone</i> ${professional.phoneNumber}
                    </c:if>
                </p>
                <div class="center">
                    <p><a class="tertiary-color-text "
                          href="profissionais/detalhes/${professional.id}">Detalhes</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>