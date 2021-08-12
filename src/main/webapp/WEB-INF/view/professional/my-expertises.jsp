<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:professional title="Minha conta">
    <jsp:body>

        <main>
            <div class="row">
                <div class="col s12 l3 hide-on-med-and-down no-padding" id="area-perfil">
                    <div class="row primary-background-color area-perfil no-margin">
                        <div class="col s12 icons-area-request">
                            <div class="row center">
                                <div class="col s12 star-icons dark-color-text">
                                    <i class="material-icons ">star</i>
                                    <i class="material-icons">star</i>
                                    <i class="material-icons">star</i>
                                    <i class="material-icons">star_border</i>
                                    <i class="material-icons">star_border</i>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 center">
                            <img id="profile-picture" src="${professional.profilePicture}" alt="foto de perfil">
                        </div>
                        <div class="col s12 center">
                            <h5 class="edit-link tertiary-color-text"><a class="tertiary-color-text" href="profissionais/editar">Editar perfil</a></h5>
                        </div>
                    </div>
                    <div class="row secondary-background-color no-margin">
                        <div class="col s12">
                            <h5 class="name-header no-margin center white-text"><strong>${professional.name}</strong></h5>
                        </div>
                    </div>
                    <div class="row primary-background-color no-margin">
                        <div class="col s12">
                            <p class="header-verification tertiary-color-text center">VERIFICAÇÃO DO PERFIL</p>
                        </div>
                    </div>
                    <div class="row secondary-background-color no-margin">
                        <div class="col s4 center no-padding"> <i class="medium material-icons ${professional.identityVerified ? 'green' : 'white'}-text">person</i></div>
                        <div class="col s4 center no-padding"> <i class="medium material-icons ${professional.emailVerified ? 'green' : 'white'}-text">email</i></div>
                        <div class="col s4 center no-padding"> <i class="medium material-icons ${professional.phoneVerified ? 'green' : 'white'}-text">phone</i></div>
                    </div>
                    <div class="row">
                    </div>
                    <div class="row no-margin">
                        <div class="col s12 no-margin no-padding input-field area-profission-select">
                            <select class="white-text select-city no-padding">
                                <option value="0" selected><p class="white-text">Todas</p></option>
                                <option value="1"><p class="white-text">Pedreiro</p></option>
                                <option value="2"><p class="white-text">Encanador</p></option>
                                <option value="3"><p class="white-text">Eletrecista</p></option>
                                <option value="4"><p class="white-text">Pintor</p></option>
                                <option value="5"><p class="white-text">Jardineiro</p></option>
                            </select>
                            <div class="center spacing-buttons">
                                <button class="waves-effect waves-light btn">
                                    <a style="color: white" href="#modal-expertises" class="modal-trigger">
                                        Adicionar nova especialidade
                                    </a>
                                </button>
                            </div>

                            <div id="modal-expertises" class="modal">
                                <div class="modal-content">
                                    <form id="form-expertises" action="profissionais/especialidades" method="post">
                                        <c:forEach var="expertise" items="${expertises}">
                                            <label class="card-expertise card col s12 m4">
                                                <input name="ids" type="checkbox" class="reset-checkbox" value="${expertise.id}">
                                                <span>${expertise.name}</span>
                                            </label>
                                        </c:forEach>
                                        <div class="input-field col s8">
                                            <button class="btn waves-effect waves-light left">Salvar</button>
                                        </div>
                                    </form>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="row no-margin">
                        <div class="col s12 no-margin no-padding">
                            <p class="header-verification tertiary-color-text center">ESTRELAS</p>
                            <div class="row secondary-background-color no-margin">
                                <div class="col s12 star-icons white-text center">
                                    <i class="material-icons ">star</i>
                                    <i class="material-icons">star</i>
                                    <i class="material-icons">star</i>
                                    <i class="material-icons">star_border</i>
                                    <i class="material-icons">star_border</i>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 no-margin no-padding">
                            <p class="header-verification tertiary-color-text center">SERVIÇOS REALIZADOS</p>
                            <div class="row secondary-background-color no-margin">
                                <h5 class="info-headers no-margin center white-text center"><strong>81</strong></h5>
                            </div>
                        </div>
                        <div class="col s12 no-margin no-padding">
                            <p class="header-verification tertiary-color-text center">AVALIAÇÕES</p>
                            <div class="row secondary-background-color no-margin">
                                <h5 class="info-headers no-margin center white-text center"><strong>81</strong></h5>
                            </div>
                        </div>
                        <div class="col s12 no-margin no-padding">
                            <p class="header-verification tertiary-color-text center">COMENTÁRIOS</p>
                            <div class="row secondary-background-color no-margin">
                                <h5 class="info-headers no-margin center white-text center"><strong>81</strong></h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col m10 offset-m1 l9">
                    <a id="show-area-perfil" class="hide-on-large-only show-area-perfil waves-effect waves-light btn btn-floating grey darken-3 z-depth-A" ><i class="material-icons">compare_arrows</i></a>
                    <div class="row expertises">
                        <c:forEach var="professionalExpertises" items="${professionalExpertises}">
                        <div class="col s5 card card-expertise-list">
                            <p class="center">${professionalExpertises.name}</p>
                        </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:professional>