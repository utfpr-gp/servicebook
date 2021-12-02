<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:individual title="ServiceBook - Minha conta">
    <jsp:body>

        <main>
            <div class="row">
                <div class="col s12 l3 hide-on-med-and-down no-padding" id="area-perfil">
                    <div class="row primary-background-color area-perfil no-margin">
                        <div class="col s12 icons-area-request">
                            <div class="row center">
                                <div class="col s12 dark-color-text">
                                    <div class="row tooltipped" data-position="bottom"
                                         data-tooltip="${individual.rating} estrela (s).">

                                        <c:forEach var="star" begin="1" end="5">
                                            <c:if test="${star <= individual.rating}">
                                                <i class="material-icons yellow-text small">star</i>
                                            </c:if>
                                            <c:if test="${star > individual.rating}">
                                                <i class="material-icons yellow-text small">star_border</i>
                                            </c:if>
                                        </c:forEach>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 center">

                            <c:if test="${individual.profilePicture == null}">
                                <svg class="icon-person" style="width:250px;height:250px" viewBox="0 0 24 24">
                                    <path class="dark-color-icon"
                                          d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"/>
                                </svg>
                            </c:if>

                            <c:if test="${individual.profilePicture != null}">
                                <div class="row">
                                    <img src="${individual.profilePicture}" alt="Profissional - Imagem de perfil."
                                         style="width:250px;height:250px">
                                </div>
                            </c:if>

                            <div class="row">
                                <p>${individual.description != null ? individual.description : 'Perfil sem descrição.'}</p>
                            </div>
                            <h5 class="edit-link tertiary-color-text">
                                <a class="tertiary-color-text" href="">Editar perfil</a>
                            </h5>
                        </div>
                    </div>
                    <div class="row secondary-background-color no-margin">
                        <div class="col s12">
                            <h5 class="name-header no-margin center white-text">
                                <strong>${individual.name}</strong>
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

                            <c:if test="${individual.profileVerified}">
                                <i class="medium material-icons green-text tooltipped" data-position="top"
                                   data-tooltip="Perfil verificado.">person</i>
                            </c:if>
                            <c:if test="${!individual.profileVerified}">
                                <i class="medium material-icons gray-text tooltipped" data-position="top"
                                   data-tooltip="Perfil não verificado.">person</i>
                            </c:if>

                        </div>
                        <div class="col s4 center no-padding">

                            <c:if test="${individual.emailVerified}">
                                <i class="medium material-icons green-text tooltipped" data-position="top"
                                   data-tooltip="Email verificado.">email</i>
                            </c:if>
                            <c:if test="${!individual.emailVerified}">
                                <i class="medium material-icons gray-text tooltipped" data-position="top"
                                   data-tooltip="Email não verificado.">email</i>
                            </c:if>

                        </div>
                        <div class="col s4 center no-padding">

                            <c:if test="${individual.phoneVerified}">
                                <i class="medium material-icons green-text tooltipped" data-position="top"
                                   data-tooltip="Telefone verificado.">phone</i>
                            </c:if>
                            <c:if test="${!individual.phoneVerified}">
                                <i class="medium material-icons gray-text tooltipped" data-position="top"
                                   data-tooltip="Telefone não verificado.">phone</i>
                            </c:if>

                        </div>
                    </div>
                    <div class="row no-margin center">

                        <div class="col s12 no-margin no-padding input-field area-profission-select">

                            <div class="row no-margin center">
                                <div class="col s12 no-margin no-padding input-field area-profission-select">
                                    <div class="spacing-buttons">
                                        <a class="waves-effect waves-light btn" href="minha-conta/meus-pedidos">
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
                    <div class="row no-margin">
                        <div class="col s12 no-margin no-padding">
                            <p class="header-verification tertiary-color-text center">ESTRELAS</p>
                            <div class="row secondary-background-color no-margin">
                                <div class="col s12 white-text center">
                                    <div class="row tooltipped" data-position="bottom"
                                         data-tooltip="${professionalExpertiseRating != null ? professionalExpertiseRating : individual.rating} estrela (s).">

                                        <c:if test="${professionalExpertiseRating == null}">
                                            <c:forEach var="star" begin="1" end="5">
                                                <c:if test="${star <= individual.rating}">
                                                    <i class="material-icons yellow-text small">star</i>
                                                </c:if>
                                                <c:if test="${star > individual.rating}">
                                                    <i class="material-icons yellow-text small">star_border</i>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>

                                        <c:if test="${professionalExpertiseRating != null}">
                                            <c:forEach var="star" begin="1" end="5">
                                                <c:if test="${star <= professionalExpertiseRating}">
                                                    <i class="material-icons yellow-text small">star</i>
                                                </c:if>
                                                <c:if test="${star > professionalExpertiseRating}">
                                                    <i class="material-icons yellow-text small">star_border</i>
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
                                    <strong>${jobs}</strong>
                                </h5>
                            </div>
                        </div>
                        <div class="col s12 no-margin no-padding">
                            <p class="header-verification tertiary-color-text center">AVALIAÇÕES</p>
                            <div class="row secondary-background-color no-margin">
                                <h5 class="info-headers no-margin center white-text center">
                                    <strong>${ratings}</strong>
                                </h5>
                            </div>
                        </div>
                        <div class="col s12 no-margin no-padding">
                            <p class="header-verification tertiary-color-text center">COMENTÁRIOS</p>
                            <div class="row secondary-background-color no-margin">
                                <h5 class="info-headers no-margin center white-text center">
                                    <strong>${comments}</strong>
                                </h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col m10 offset-m1 l9">
                    <a id="show-area-perfil"
                       class="hide-on-large-only show-area-perfil waves-effect waves-light btn btn-floating grey darken-3 z-depth-A">
                        <i class="material-icons">compare_arrows</i>
                    </a>
                    <div class="row">
                        <div class="col s12">
                            <h2 class="secondary-color-text">Anúncios de serviços</h2>
                            <blockquote class="light-blue lighten-5 info-headers">
                                <p>
                                    Abaixo você encontra os pedidos disponíveis no momento. Clique nos quadros para mais
                                    detalhes.
                                </p>
                            </blockquote>
                        </div>
                        <div class="col s12">
                            <div class="row">
                                <div class="container center">
                                    <a href="minha-conta/profissional" class="waves-effect waves-light btn"><i class="material-icons right">sync</i>ATUALIZAR</a>
                                </div>

                                <ul class="tabs tabs-fixed-width center">
                                    <li class="tab">
                                        <a id="tab-default" data-url="minha-conta/profissional/disponiveis"
                                           href="#disponiveis">
                                            DISPONÍVEIS
                                        </a>
                                    </li>
                                    <li class="tab">
                                        <a data-url="minha-conta/profissional/em-disputa" href="#emDisputa">
                                            EM DISPUTA
                                        </a>
                                    </li>
                                    <li class="tab">
                                        <a data-url="minha-conta/profissional/para-fazer" href="#paraFazer">
                                            PARA FAZER
                                        </a>
                                    </li>
                                    <li class="tab">
                                        <a data-url="minha-conta/profissional/executados" href="#executados">
                                            EXECUTADOS
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div id="disponiveis" class="col s12">

                            </div>
                            <div id="emDisputa" class="col s12">

                            </div>
                            <div id="paraFazer" class="col s12">

                            </div>
                            <div id="executados" class="col s12">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:individual>

<script>
    $(document).ready(function () {
        $('#disponiveis').load($('.tab .active').attr("data-url"), function (result) {
            window.location.hash = "#disponiveis";
            $('#tab-default').click();
        });
    });

    $('#select-expertise').formSelect();

    $('#select-expertise').change(function () {
        $('#form-expertise').submit();
    });

    $('.tab a').click(function (e) {
        e.preventDefault();

        let url = $(this).attr("data-url");
        let href = this.hash;
        window.location.hash = href;

        let urlParams = new URLSearchParams(window.location.search);
        let id = (urlParams.has('id')) ? urlParams.get('id') : 0;
        url += '?id=' + id;

        $(href).load(url, function (result) {
        });
    });
</script>
