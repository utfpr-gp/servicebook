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
                                <a class="tertiary-color-text" href="minha-conta/perfil">Editar perfil</a>
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
                            <p class="header-verification tertiary-color-text">
                                ESPECIALIDADES
                            </p>

                            <form method="get" action="minha-conta" id="form-expertise">
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
                                         data-tooltip="${professionalExpertiseRating != null ? professionalExpertiseRating : professional.rating} estrela (s).">

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
                            <h2 class="secondary-color-text">Minhas especialidades</h2>
                            <blockquote class="light-blue lighten-5 info-headers">
                                <p> Se você é especialista em algum ou alguns serviços e deseja receber solicitações para realização destes tipos de serviços, por favor, nos informe as suas especialidades profissionais de acordo com as suportadas pelo SERVICEBOOK que logo você começará a receber pedido dos clientes.</p>
                            </blockquote>
                        </div>
                    </div>


                    <div class="col s12">
                        <div class="row expertises">
                            <c:forEach var="professionalExpertises" items="${professionalExpertises}">
                            <div class="col s12 m5 offset-m1 card-expertise-list row">
                                <div class="col s3 expertise-icon">
                                    <i class="material-icons">work</i>
                                </div>
                                <div class="col s8">
                                    <p class="center">
                                        <strong>
                                            ${professionalExpertises.name}
                                        </strong>
                                    </p>
                                </div>
                                <div class="col s9 right">
                                    <p class="center">
                                        Descrição da especialidade
                                    </p>
                                </div>
                            </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="center spacing-buttons">
                        <button  class="waves-effect waves-light btn">
                            <a href="#modal-expertises" class="modal-trigger">
                                Adicionar nova especialidade
                            </a>
                        </button>
                    </div>

                    <div id="modal-expertises" class="modal">
                        <div class="modal-content ui-front">
                            <div class="row">
                                <div class="col s9">
                                    <h4>Escolha uma ou mais especialidades!</h4>
                                </div>
                                <div class="col s3">
                                    <button class="modal-close modal-expertise-close right">
                                        <i class="material-icons">close</i>
                                    </button>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col s12">
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <i class="material-icons prefix">work</i>
                                            <input type="text" id="txtBusca" class="autocomplete">
                                            <label for="txtBusca">Selecione sua especialidade</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <form class="s12" id="form-expertises" action="minha-conta/profissional/especialidades" method="post">
                                    <ul id="search-expertises">
                                        <c:forEach var="expertise" items="${expertises}">
                                            <li>
                                                <label class='card-expertise col s12 m10 offset-m1'>
                                                    <input id='ids' name='ids' type='checkbox' class='reset-checkbox' value="${expertise.id}">
                                                    <span class='center name-expertise'>
                                                    <i class='material-icons'>work</i>
                                                    ${expertise.name}
                                                </span>
                                                </label>
                                            </li>
                                        </c:forEach>
                                    </ul>

                                        <%--                                <div id="result-expertises">--%>

                                        <%--                                </div>--%>
                                    <div class="input-field col s8 offset-s1">
                                        <button id="submit-expertise" type="submit" class="btn waves-effect waves-light left">Salvar</button>
                                    </div>
                                    <div class="input-field col s3">
                                        <a class="btn waves-effect waves-light modal-close">Fechar</a>
                                    </div>
                                </form>
                            </div>


                        </div>
                    </div>

                </div>
            </div>
        </main>

    </jsp:body>
</t:professional>

<script>

    $(function(){
        $("#txtBusca").keyup(function(){
            var texto = $(this).val();

            $("#search-expertises li").css("display", "block");
            $("#search-expertises li").each(function(){
                if($(this).text().toUpperCase().indexOf(texto.toUpperCase()) < 0)
                    $(this).css("display", "none");
            });
        });
    });
</script>