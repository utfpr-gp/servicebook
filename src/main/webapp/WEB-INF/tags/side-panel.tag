<%@ tag description="Template para painel lateral" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="userInfo" type="br.edu.utfpr.servicebook.util.UserTemplateInfo" %>
<%@ attribute name="statisticInfo" type="br.edu.utfpr.servicebook.util.UserTemplateStatisticInfo" %>
<%@ attribute name="followdto" type="br.edu.utfpr.servicebook.model.dto.FollowsDTO" %>
<c:set var="currenturl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="col s12" style="padding-left: 0" id="area-perfil">
    <div class="row center primary-background-color area-perfil no-margin" >
        <div class="dark-color-text tooltipped col offset-s1 center-align" data-position="bottom"
             data-tooltip="${userInfo.rating} estrela (s)." style="cursor: pointer">

            <c:forEach var="star" begin="1" end="5">
                <c:if test="${star <= userInfo.rating}">
                    <i class="material-icons yellow-text small col s2">star</i>
                </c:if>
                <c:if test="${star > userInfo.rating}">
                    <i class="material-icons yellow-text small col s2">star_border</i>
                </c:if>
            </c:forEach>
        </div>

        <!-- Foto do perfil -->
        <div class="col s12 center">
            <c:if test="${userInfo.profilePicture == null}">
                <svg class="icon-person" style="width:250px;height:250px" viewBox="0 0 24 24">
                    <path class="dark-color-icon"
                          d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"/>
                </svg>
            </c:if>

            <c:if test="${userInfo.profilePicture != null}">
                <div class="row">
                    <img src="${userInfo.profilePicture}" alt="Profissional - Imagem de perfil."
                         class="col s10 offset-s1 responsive-img">
                </div>
            </c:if>

            <div class="row">
                <p>${userInfo.description != null ? userInfo.description : 'Perfil sem descrição.'}</p>
            </div>
            <h5 class="edit-link tertiary-color-text">
                <a class="tertiary-color-text" href="minha-conta/perfil">Editar perfil</a>
            </h5>
        </div>
        <!-- Foto do perfil -->
    </div>

    <!-- Nome da empresa -->
    <div class="row secondary-background-color no-margin">
        <div class="col s12">
            <h5 class="name-header no-margin center white-text">
                <strong>${userInfo.name}</strong>
            </h5>
        </div>
    </div>
    <!-- Fim nome da empresa -->

    <!-- Verificação de perfil -->
    <div class="row primary-background-color no-margin">
        <div class="col s12">
            <p class="header-verification tertiary-color-text center">VERIFICAÇÃO DO PERFIL</p>
        </div>
    </div>
    <div class="row secondary-background-color no-margin">
        <div class="col s4 center no-padding">

            <c:if test="${userInfo.profileVerified}">
                <i class="medium material-icons green-text tooltipped" data-position="top"
                   data-tooltip="Perfil verificado.">person</i>
            </c:if>
            <c:if test="${!userInfo.profileVerified}">
                <i class="medium material-icons gray-text tooltipped" data-position="top"
                   data-tooltip="Perfil não verificado.">person</i>
            </c:if>

        </div>
        <div class="col s4 center no-padding">

            <c:if test="${userInfo.emailVerified}">
                <i class="medium material-icons green-text tooltipped" data-position="top"
                   data-tooltip="Email verificado.">email</i>
            </c:if>
            <c:if test="${!userInfo.emailVerified}">
                <i class="medium material-icons gray-text tooltipped" data-position="top"
                   data-tooltip="Email não verificado.">email</i>
            </c:if>

        </div>
        <div class="col s4 center no-padding">

            <c:if test="${userInfo.phoneVerified}">
                <i class="medium material-icons green-text tooltipped" data-position="top"
                   data-tooltip="Telefone verificado.">phone</i>
            </c:if>
            <c:if test="${!userInfo.phoneVerified}">
                <i class="medium material-icons gray-text tooltipped" data-position="top"
                   data-tooltip="Telefone não verificado.">phone</i>
            </c:if>

        </div>
    </div>
    <!-- Fim da verificação de perfil -->

    <c:choose>
        <c:when test="${access_type eq 'CLIENT'}">
            <!-- profissionais favoritos -->
            <div class="row primary-background-color no-margin">
                <div class="col s12">
                    <p class="header-verification tertiary-color-text center">PROFISSIONAIS FAVORITOS</p>
                </div>
            </div>
            <div class="row secondary-background-color no-margin">
                <div class="col s12">
                    <h5 class="name-header no-margin center white-text">
                        <strong><a href="minha-conta/cliente/profissionais-favoritos">${userInfo.followingAmount}</a></strong>
                    </h5>
                </div>
            </div>
            <!-- Fim profissionais favoritos -->
        </c:when>
        <c:when test="${access_type eq 'PROFESSIONAL' or access_type eq 'COMPANY'}">

            <!-- Seguidores -->
            <div class="row primary-background-color no-margin">
                <div class="col s12">
                    <p class="header-verification tertiary-color-text center">SEGUIDORES</p>
                </div>
            </div>
            <div class="row secondary-background-color no-margin">
                <div class="col s12">
                    <h5 class="name-header no-margin center white-text">
                        <strong>${userInfo.followingAmount}</strong>
                    </h5>
                </div>
            </div>
            <!-- Fim Seguidores -->

            <!-- Funcionários - mostra só para empresa -->
            <c:if test="${access_type eq 'COMPANY'}">
                <!-- Quantidade de funcionários -->
                <div class="row primary-background-color no-margin">
                    <div class="col s12">
                        <p class="header-verification tertiary-color-text center">FUNCIONÁRIOS</p>
                    </div>
                </div>
                <div class="row secondary-background-color no-margin">
                    <div class="col s12">
                        <h5 class="name-header no-margin center white-text">
                            <strong><a href="profissionais-favoritos">${userInfo.followingAmount}</a></strong>
                        </h5>
                    </div>
                </div>
                <!-- Fim Quantidade de funcionários -->

                <div class="row no-margin center">
                    <div class="col s12 no-margin no-padding input-field area-profission-select">
                        <div class="spacing-buttons">
                            <a class="waves-effect waves-light btn" href="minha-conta/empresa/adicionar-profissional">
                                Adicionar Funcionário </a>
                        </div>
                    </div>
                </div>
            </c:if>
            <!-- Fim Funcionários - mostra só para empresa -->

            <!-- Especialidades -->
            <div class="row no-margin center">
                <div class="col s12 no-margin no-padding input-field area-profission-select">
                    <p class="header-verification tertiary-color-text">
                        ESPECIALIDADES
                    </p>

                    <form method="get" action="minha-conta/profissional" id="form-expertise">
                        <div class="input-field col s12 no-padding white-text">
                            <select name="id" id="select-expertise" onchange="updateStatistics(this)">
                                <option value="0">Todas as Especialidades</option>

                                <c:forEach var="e" items="${professionalExpertises}">
                                    <option value="${e.id}" ${e.id == currentExpertiseId ? 'selected' : ''}><p>${e.name}</p></option>
                                </c:forEach>

                            </select>
                        </div>
                        <button type="submit" hidden></button>
                    </form>
                </div>
            </div>
            <!-- Fim Especialidades -->

            <!-- Estatísticas -->
            <div class="row no-margin">
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">ESTRELAS</p>
                    <div class="row secondary-background-color no-margin">
                        <div class="col s12 white-text center">
                            <div class="row tooltipped" data-position="bottom"
                                 data-tooltip="${statisticInfo.ratingScore != 0 ? statisticInfo.ratingScore : userInfo.rating} estrela (s).">

                                <c:if test="${statisticInfo.ratingScore == 0}">
                                    <c:forEach var="star" begin="1" end="5">
                                        <c:if test="${star <= userInfo.rating}">
                                            <i class="material-icons yellow-text small col s2">star</i>
                                        </c:if>
                                        <c:if test="${star > userInfo.rating}">
                                            <i class="material-icons yellow-text small col s2">star_border</i>
                                        </c:if>
                                    </c:forEach>
                                </c:if>

                                <c:if test="${statisticInfo.ratingScore != 0}">
                                    <c:forEach var="star" begin="1" end="5">
                                        <c:if test="${star <= statisticInfo.ratingScore}">
                                            <i class="material-icons yellow-text small col s2">star</i>
                                        </c:if>
                                        <c:if test="${star > statisticInfo.ratingScore}">
                                            <i class="material-icons yellow-text small col s2">star_border</i>
                                        </c:if>
                                    </c:forEach>
                                </c:if>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">SERVIÇOS REALIZADOS</p>
                    <div class="row secondary-background-color no-margin">
                        <h5 class="info-headers no-margin center white-text center">
                            <strong id="expertise-jobs" class="expertise-jobs">${statisticInfo.jobs}</strong>
                        </h5>
                    </div>
                </div>
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">AVALIAÇÕES</p>
                    <div class="row secondary-background-color no-margin">
                        <h5 class="info-headers no-margin center white-text center">
                            <strong id="expertise-ratings" class="expertise-ratings">${statisticInfo.ratings}</strong>
                        </h5>
                    </div>
                </div>
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">COMENTÁRIOS</p>
                    <div class="row secondary-background-color no-margin">
                        <h5 class="info-headers no-margin center white-text center">
                            <strong id="expertise-comments" class="expertise-comments">${statisticInfo.comments}</strong>
                        </h5>
                    </div>
                </div>
            </div>
            <!-- Fim Estatísticas -->
        </c:when>
    </c:choose>
</div>

<script src="assets/resources/scripts/professional-statistics.js"></script>