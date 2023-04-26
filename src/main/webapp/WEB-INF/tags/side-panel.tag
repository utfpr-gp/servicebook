<%@tag description="Template para painel lateral" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="individualInfo" type="br.edu.utfpr.servicebook.util.sidePanel.UserTemplateInfo" %>
<%@attribute name="statisticProfessionalInfo" type="br.edu.utfpr.servicebook.util.sidePanel.UserTemplateStatisticDTO" %>
<%@attribute name="companyInfo" type="br.edu.utfpr.servicebook.util.sidePanel.SidePanelCompanyDTO" %>
<%@attribute name="followdto" type="br.edu.utfpr.servicebook.follower.FollowsDTO" %>

    <div class="col m2 l3 hide-on-med-and-down" style="padding-left: 0" id="area-perfil">
        <div class="row primary-background-color area-perfil no-margin">
            <div class="col s12 icons-area-request">
                <div class="row center">
                    <div class="col s12 dark-color-text">
                        <div class="row tooltipped" data-position="bottom"
                             data-tooltip="${individualInfo.rating} estrela (s).">

                            <c:forEach var="star" begin="1" end="5">
                                <c:if test="${star <= individualInfo.rating}">
                                    <i class="material-icons yellow-text small">star</i>
                                </c:if>
                                <c:if test="${star > individualInfo.rating}">
                                    <i class="material-icons yellow-text small">star_border</i>
                                </c:if>
                            </c:forEach>

                        </div>
                    </div>
                </div>
            </div>
            <div class="col s12 center">

                <c:if test="${individualInfo.profilePicture == null}">
                    <svg class="icon-person" style="width:250px;height:250px" viewBox="0 0 24 24">
                        <path class="dark-color-icon"
                              d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"/>
                    </svg>
                </c:if>

                <c:if test="${individualInfo.profilePicture != null}">
                    <div class="row">
                        <img src="${individualInfo.profilePicture}" alt="Profissional - Imagem de perfil."
                             style="width:250px;height:250px">
                    </div>
                </c:if>

                <div class="row">
                    <p>${individualInfo.description != null ? individualInfo.description : 'Perfil sem descrição.'}</p>
                </div>
                <h5 class="edit-link tertiary-color-text">
                    <a class="tertiary-color-text" href="minha-conta/perfil">Editar perfil</a>
                </h5>
            </div>
        </div>
        <div class="row secondary-background-color no-margin">
            <div class="col s12">
                <h5 class="name-header no-margin center white-text">
                    <strong>${individualInfo.name}</strong>
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

                <c:if test="${individualInfo.profileVerified}">
                    <i class="medium material-icons green-text tooltipped" data-position="top"
                       data-tooltip="Perfil verificado.">person</i>
                </c:if>
                <c:if test="${!individualInfo.profileVerified}">
                    <i class="medium material-icons gray-text tooltipped" data-position="top"
                       data-tooltip="Perfil não verificado.">person</i>
                </c:if>

            </div>
            <div class="col s4 center no-padding">

                <c:if test="${individualInfo.emailVerified}">
                    <i class="medium material-icons green-text tooltipped" data-position="top"
                       data-tooltip="Email verificado.">email</i>
                </c:if>
                <c:if test="${!individualInfo.emailVerified}">
                    <i class="medium material-icons gray-text tooltipped" data-position="top"
                       data-tooltip="Email não verificado.">email</i>
                </c:if>

            </div>
            <div class="col s4 center no-padding">

                <c:if test="${individualInfo.phoneVerified}">
                    <i class="medium material-icons green-text tooltipped" data-position="top"
                       data-tooltip="Telefone verificado.">phone</i>
                </c:if>
                <c:if test="${!individualInfo.phoneVerified}">
                    <i class="medium material-icons gray-text tooltipped" data-position="top"
                       data-tooltip="Telefone não verificado.">phone</i>
                </c:if>

            </div>
        </div>
        <c:if test="${statisticProfessionalInfo == null && company == false}">
            <div class="row primary-background-color no-margin">
                <div class="col s12">
                        <p class="header-verification tertiary-color-text center">PROFISSIONAIS FAVORITOS</p>
                </div>
            </div>
            <div class="row secondary-background-color no-margin">
                <div class="col s12">
                    <h5 class="name-header no-margin center white-text">
                        <strong><a href="profissionais-favoritos">${individualInfo.followingAmount}</a></strong>
                    </h5>
                </div>
            </div>
        </c:if>

        <c:if test="${company == true}">
            <div class="row primary-background-color no-margin">
                <div class="col s12">
                    <p class="header-verification tertiary-color-text center">SEGUIDORES</p>
                </div>
            </div>
            <div class="row secondary-background-color no-margin">
                <div class="col s12">
                    <h5 class="name-header no-margin center white-text">
                        <strong><a href="profissionais-favoritos">${individualInfo.followingAmount}</a></strong>
                    </h5>
                </div>
            </div>

            <div class="row primary-background-color no-margin">
                <div class="col s12">
                    <p class="header-verification tertiary-color-text center">FUNCIONÁRIOS</p>
                </div>
            </div>
            <div class="row secondary-background-color no-margin">
                <div class="col s12">
                    <h5 class="name-header no-margin center white-text">
                        <strong><a href="profissionais-favoritos">${individualInfo.followingAmount}</a></strong>
                    </h5>
                </div>
            </div>
        </c:if>

        <c:if test="${company == true}">
            <div class="row no-margin center">
                <div class="col s12 no-margin no-padding input-field area-profission-select">
                    <div class="spacing-buttons">
                        <a class="waves-effect waves-light btn" href="minha-conta/empresa/adicionar-profissional">
                            Adicionar Funcionário </a>
                    </div>
                </div>
            </div>
        </c:if>


        <c:if test="${statisticProfessionalInfo != null}">
            <div class="row primary-background-color no-margin">
                <div class="col s12">
                    <p class="header-verification tertiary-color-text center">SEGUIDORES</p>
                </div>
            </div>
            <div class="row secondary-background-color no-margin">
                <div class="col s12 left-align">
                    <h5 class="name-header no-margin center white-text">
                        <strong>${individualInfo.followingAmount}</strong>
                    </h5>
                </div>
            </div>
        </c:if>
        <!-- apenas apresenta o acesso quando for cliente e não for empresa -->
        <c:if test="${statisticProfessionalInfo == null and empty company }">
            <div class="row no-margin center">
                <div class="col s12 no-margin no-padding input-field area-profission-select">
                    <div class="spacing-buttons">
                        <a class="waves-effect waves-light btn" href="minha-conta/profissional">
                            Acessar como profissional </a>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${company == true}">
            <div class="row no-margin center">
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">SERVIÇOS REALIZADOS</p>
                    <div class="row secondary-background-color no-margin">
                        <h5 class="info-headers no-margin center white-text center">
                            <strong id="expertise-jobs1">${statisticInfo.jobs}</strong>
                        </h5>
                    </div>
                </div>
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">AVALIAÇÕES</p>
                    <div class="row secondary-background-color no-margin">
                        <h5 class="info-headers no-margin center white-text center">
                            <strong id="expertise-ratings1">${statisticInfo.ratings}</strong>
                        </h5>
                    </div>
                </div>
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">COMENTÁRIOS</p>
                    <div class="row secondary-background-color no-margin">
                        <h5 class="info-headers no-margin center white-text center">
                            <strong id="expertise-comments1">${statisticInfo.comments}</strong>
                        </h5>
                    </div>
                </div>

                <div class="col s12 no-margin no-padding input-field area-profission-select">
                    <p class="header-verification tertiary-color-text">
                        ESPECIALIDADES
                    </p>

                    <form method="get" action="minha-conta/profissional" id="form-expertise1">
                        <div class="input-field col s12 no-padding white-text">
                            <select name="id" id="select-expertise1">
                                <option value="0">Todas as Especialidades</option>

                                <c:forEach var="expertise" items="${expertises}">
                                    <option value="${expertise.id}" ${expertise.id == id ? 'selected' : ''}><p>${expertise.name}</p></option>
                                </c:forEach>

                            </select>
                        </div>
                        <button type="submit" hidden></button>
                    </form>

                </div>
            </div>
        </c:if>

        <c:if test="${statisticProfessionalInfo != null}">
            <div class="row no-margin center">
                <div class="col s12 no-margin no-padding input-field area-profission-select">

                    <div class="row no-margin center">
                        <div class="col s12 no-margin no-padding input-field area-profission-select">
                            <div class="spacing-buttons">
                                <a class="waves-effect waves-light btn" href="minha-conta/cliente">
                                    Acessar como cliente </a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <div class="row no-margin center">
                <div class="col s12 no-margin no-padding input-field area-profission-select">
                    <p class="header-verification tertiary-color-text">
                        ESPECIALIDADES
                    </p>

                    <form method="get" action="minha-conta/profissional" id="form-expertise">
                        <div class="input-field col s12 no-padding white-text">
                            <select name="id" id="select-expertise">
                                <option value="0">Todas as Especialidades</option>

                                <c:forEach var="expertise" items="${expertises}">
                                    <option value="${expertise.id}" ${expertise.id == id ? 'selected' : ''}><p>${expertise.name}</p></option>
                                </c:forEach>

                            </select>
                        </div>
                        <button type="submit" hidden></button>
                    </form>

                </div>
            </div>

            <div class="row no-margin center">
                <div class="col s12 no-margin no-padding input-field area-profission-select">
                    <div class="row no-margin center">
                        <div class="col s12 no-margin no-padding input-field area-profission-select">
                            <div class="spacing-buttons">
                                <a class="waves-effect waves-light btn" href="minha-conta/profissional/especialidades">
                                    Adicionar Especialidades </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row no-margin">
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">ESTRELAS</p>
                    <div class="row secondary-background-color no-margin">
                        <div class="col s12 white-text center">
                            <div class="row tooltipped" data-position="bottom"
                                 data-tooltip="${statisticInfo.ratingScore != 0 ? statisticInfo.ratingScore : individual.rating} estrela (s).">

                                <c:if test="${statisticInfo.ratingScore == 0}">
                                    <c:forEach var="star" begin="1" end="5">
                                        <c:if test="${star <= individualInfo.rating}">
                                            <i class="expertise-rating-star material-icons yellow-text small">star</i>
                                        </c:if>
                                        <c:if test="${star > individualInfo.rating}">
                                            <i class="expertise-rating-star material-icons yellow-text small">star_border</i>
                                        </c:if>
                                    </c:forEach>
                                </c:if>

                                <c:if test="${statisticInfo.ratingScore != 0}">
                                    <c:forEach var="star" begin="1" end="5">
                                        <c:if test="${star <= statisticInfo.ratingScore}">
                                            <i class="expertise-rating-star material-icons yellow-text small">star</i>
                                        </c:if>
                                        <c:if test="${star > statisticInfo.ratingScore}">
                                            <i class="expertise-rating-star material-icons yellow-text small">star_border</i>
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
                            <strong id="expertise-jobs">${statisticInfo.jobs}</strong>
                        </h5>
                    </div>
                </div>
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">AVALIAÇÕES</p>
                    <div class="row secondary-background-color no-margin">
                        <h5 class="info-headers no-margin center white-text center">
                            <strong id="expertise-ratings">${statisticInfo.ratings}</strong>
                        </h5>
                    </div>
                </div>
                <div class="col s12 no-margin no-padding">
                    <p class="header-verification tertiary-color-text center">COMENTÁRIOS</p>
                    <div class="row secondary-background-color no-margin">
                        <h5 class="info-headers no-margin center white-text center">
                            <strong id="expertise-comments">${statisticInfo.comments}</strong>
                        </h5>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
