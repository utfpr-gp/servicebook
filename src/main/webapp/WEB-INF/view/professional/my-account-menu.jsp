<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css" rel="stylesheet" type="text/css"/>
</head>
<t:template-side-nav title="Minha Conta">
    <jsp:body>
        <main class="">
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
</t:template-side-nav>