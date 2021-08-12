<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:professional title="ServiceBook - Minha conta">
    <jsp:body>

        <main>
            <div class="row">
                <div class="col s12 l3 hide-on-med-and-down no-padding" id="area-perfil">
                    <div class="row primary-background-color area-perfil no-margin">
                        <div class="col s12 icons-area-request">
                            <div class="row center">
                                <div class="col s12 dark-color-text">
                                    <div class="row tooltipped" data-position="bottom"
                                         data-tooltip="${professional.rating} estrelas">

                                        <c:forEach var="star" begin="1" end="5">
                                            <c:if test="${star <= professional.rating}">
                                                <i class="material-icons yellow-text small">star</i>
                                            </c:if>
                                            <c:if test="${star > professional.rating}">
                                                <i class="material-icons yellow-text small">star_border</i>
                                            </c:if>
                                        </c:forEach>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 center">

                            <c:if test="${professional.profilePicture == null}">
                                <svg class="icon-person" style="width:250px;height:250px" viewBox="0 0 24 24">
                                    <path class="dark-color-icon"
                                          d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"/>
                                </svg>
                            </c:if>

                            <c:if test="${professional.profilePicture != null}">
                                <div class="row">
                                    <img src="${professional.profilePicture}" alt="Profissional - Imagem de perfil"
                                         style="width:250px;height:250px">
                                </div>
                            </c:if>

                            <div class="row">
                                <p>${professional.description}</p>
                            </div>

                            <h5 class="edit-link tertiary-color-text">
                                <a class="tertiary-color-text" href="">Editar perfil</a>
                            </h5>
                        </div>
                    </div>
                    <div class="row secondary-background-color no-margin">
                        <div class="col s12">
                            <h5 class="name-header no-margin center white-text">
                                <strong>${professional.name}</strong>
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

                            <c:if test="${professional.emailVerified && professional.phoneVerified}">
                                <i class="medium material-icons green-text tooltipped" data-position="top"
                                   data-tooltip="Perfil verificado">person</i>
                            </c:if>
                            <c:if test="${!professional.emailVerified || !professional.phoneVerified}">
                                <i class="medium material-icons gray-text tooltipped" data-position="top"
                                   data-tooltip="Perfil não verificado">person</i>
                            </c:if>

                        </div>
                        <div class="col s4 center no-padding">

                            <c:if test="${professional.emailVerified}">
                                <i class="medium material-icons green-text tooltipped" data-position="top"
                                   data-tooltip="Email verificado">email</i>
                            </c:if>
                            <c:if test="${!professional.emailVerified}">
                                <i class="medium material-icons gray-text tooltipped" data-position="top"
                                   data-tooltip="Email não verificado">email</i>
                            </c:if>

                        </div>
                        <div class="col s4 center no-padding">

                            <c:if test="${professional.phoneVerified}">
                                <i class="medium material-icons green-text tooltipped" data-position="top"
                                   data-tooltip="Telefone verificado">phone</i>
                            </c:if>
                            <c:if test="${!professional.phoneVerified}">
                                <i class="medium material-icons gray-text tooltipped" data-position="top"
                                   data-tooltip="Telefone não verificado">phone</i>
                            </c:if>

                        </div>
                    </div>
                    <div class="row no-margin">
                        <div class="col s12 no-margin no-padding input-field area-profission-select">
                            <p class="header-verification tertiary-color-text center">
                                ESPECIALIDADES
                            </p>

                            <!-- Fix! -->
                            <form id="form-expertises" method="get" action="minha-conta/filtros">
                                <select name="expertise" id="expertise" class="white-text no-padding">
                                    <option value="0" selected><p class="white-text">Todas</p></option>

                                    <c:forEach var="expertise" items="${expertises}">
                                        <option value="${expertise.id}"><p>${expertise.name}</p></option>
                                    </c:forEach>

                                </select>

                                <button type="submit" hidden></button>
                            </form>

                            <script>
                                window.onload = function () {
                                    $('#expertise').change(function () {
                                        $('#form-expertises').submit();
                                    });
                                }
                            </script>
                            <!-- -->

                            <div class="center spacing-buttons">
                                <button class="waves-effect waves-light btn">
                                    <i class="material-icons left">add</i>
                                    Adicionar especialidade
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="row no-margin">
                        <div class="col s12 no-margin no-padding">
                            <p class="header-verification tertiary-color-text center">ESTRELAS</p>
                            <div class="row secondary-background-color no-margin">
                                <div class="col s12 white-text center">
                                    <div class="row tooltipped" data-position="bottom"
                                         data-tooltip="${professional.rating} estrelas">

                                        <c:forEach var="star" begin="1" end="5">
                                            <c:if test="${star <= professional.rating}">
                                                <i class="material-icons yellow-text small">star</i>
                                            </c:if>
                                            <c:if test="${star > professional.rating}">
                                                <i class="material-icons yellow-text small">star_border</i>
                                            </c:if>
                                        </c:forEach>

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
                                <ul class="tabs center">
                                    <li class="tab"><a class="active" href="#disponiveis">DISPONÍVEIS</a></li>
                                    <li class="tab"><a href="#emDisputa">EM DISPUTA</a></li>
                                    <li class="tab"><a href="#paraFazer">PARA FAZER</a></li>
                                    <li class="tab"><a href="#executados">EXECUTADOS</a></li>
                                </ul>
                            </div>
                            <div id="disponiveis" class="col s12">
                                <div class="container">
                                    <div class="row">
                                        <div class="col s12 spacing-buttons">
                                            <div style="border: solid 1px black">
                                                <div class="secondary-background-color">
                                                    <div class="row">
                                                        <div class="col s8 offset-s2">
                                                            <h5 class="center white-text">
                                                                PEDREIRO
                                                            </h5>
                                                        </div>
                                                        <div class="col s2">
                                                            <h5 class="right white-text badge-service">
                                                                4/10
                                                            </h5>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col s4">
                                                        <p class="center text-form-dados primary-color-text">
                                                            <i class="material-icons small dark-color-text">person</i>
                                                            Maria
                                                        </p>
                                                    </div>
                                                    <div class="col s4">
                                                        <p class="center text-form-dados primary-color-text">
                                                            <i class="material-icons small dark-color-text">location_on</i>
                                                            Santana, Guarapuava-PR
                                                        </p>
                                                    </div>
                                                    <div class="col s4">
                                                        <p class="center text-form-dados primary-color-text">
                                                            <i class="material-icons small dark-color-text">access_time</i>
                                                            Próxima semana
                                                        </p>
                                                    </div>
                                                </div>
                                                <blockquote class="light-blue lighten-5 info-headers">
                                                    <p>Descrição do serviço ...</p>
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
                            </div>
                            <div id="emDisputa" class="col s12">
                                <div class="container">
                                    <div class="row">
                                        <div class="col s12 spacing-buttons">
                                            <div class="none-profission">
                                                <p class="center text-form-dados">
                                                    Você ainda não tem pedidos, mas um novo pedido pode chegar aqui a
                                                    qualquer momento.
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="paraFazer" class="col s12">
                                <div class="container">
                                    <div class="row">
                                        <div class="col s12 spacing-buttons">
                                            <div class="none-profission">
                                                <p class="center text-form-dados">
                                                    Você ainda não tem pedidos, mas um novo pedido pode chegar aqui a
                                                    qualquer momento.
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="executados" class="col s12">
                                <div class="container">
                                    <div class="row">
                                        <div class="col s12 spacing-buttons">
                                            <div class="none-profission">
                                                <p class="center text-form-dados">
                                                    Você ainda não tem pedidos, mas um novo pedido pode chegar aqui a
                                                    qualquer momento.
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:professional>
