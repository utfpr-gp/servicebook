<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css" rel="stylesheet" type="text/css"/>
</head>
<t:template title="Minha Conta">
    <jsp:body>
        <main class="container">
            <div class="row primary-background-color">
                <div class="col s12 m4 center">
                    <c:if test="${professional.profilePicture == null}">
                        <svg class="icon-person" viewBox="0 0 24 24">
                            <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                        </svg>
                    </c:if>
                    <c:if test="${professional.profilePicture != null}">
                        <div class="row">
                            <img src="${professional.profilePicture}" alt="Imagem de perfil." style="padding-top:10px;padding-bottom:10px;width:200px;height:200px"/>
                        </div>
                    </c:if>
                    <h5 class="center edit-link tertiary-color-text"><a class="tertiary-color-text" href="#" id="editLink">Editar foto</a></h5>
                    <form id="imageUploadForm" action="${pageContext.request.contextPath}/minha-conta/enviar-foto" method="POST" enctype="multipart/form-data">
                        <input type="file" name="fileUpload" id="fileUpload" style="display: none;" accept="image/png, image/svg, image/jpg">
                        <button type="submit" id="submitButton" style="display: none;"></button>
                    </form>
                </div>
                <div class="col s12 m8 center">
                    <h3 class="center secondary-color-text"><strong>${professional.name}</strong></h3>
                    <h5 class="center secondary-color-text"><i class="small material-icons">email</i> ${professional.email}</h5>
                    <h5 class="center secondary-color-text"><i class="small material-icons">local_phone</i> ${professional.phoneNumber}</h5>
                    <h5 class="center secondary-color-text"><i class="small material-icons">location_on</i> ${professional.address.neighborhood}, ${city.name} - ${city.state.uf}</h5>
                </div>
            </div>
            <div class="row">
                <div class="col s12 l6 spacing-buttons">
                    <a href="minha-conta/profissional/meus-jobs" class="dark-color-text">
                        <div class="active-profission no-padding">
                            <h3 class="center"><strong><i class="medium material-icons">chrome_reader_mode</i></strong></h3>
                            <h4 class="center"><strong>Minhas Solicitações</strong></h4>
                        </div>
                    </a>
                </div>
                <div class="col s12 l6 spacing-buttons">
                    <a href="minha-conta/profissional/especialidades" class="dark-color-text">
                        <div class="active-profission no-padding">
                            <h3 class="center"><strong><i class="medium material-icons">assignment_ind</i></strong></h3>
                            <h4 class="center"><strong>Minhas Especialidades</strong></h4>
                        </div>
                    </a>
                </div>
                <div class="col s12 l6 spacing-buttons">
                    <a href="minha-conta/profissional/servicos" class="dark-color-text">
                        <div class="active-profission no-padding">
                            <h3 class="center"><strong><i class="medium material-icons">work</i></strong></h3>
                            <h4 class="center"><strong>Meus Serviços</strong></h4>
                        </div>
                    </a>
                </div>
                <div class="col s12 l6 spacing-buttons">
                    <a href="minha-conta/profissional/meus-anuncios" class="dark-color-text">
                        <div class="active-profission no-padding">
                            <h3 class="center"><strong><i class="medium material-icons">store</i></strong></h3>
                            <h4 class="center"><strong>Minha Loja</strong></h4>
                        </div>
                    </a>
                </div>
                <div class="col s12 l6 spacing-buttons">
                    <a href="minha-conta/profissional/minha-agenda" class="dark-color-text">
                        <div class="active-profission no-padding">
                            <h3 class="center"><strong><i class="medium material-icons">event_note</i></strong></h3>
                            <h4 class="center"><strong>Minha Agenda</strong></h4>
                        </div>
                    </a>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template>