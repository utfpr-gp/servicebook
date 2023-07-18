<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css"
          rel="stylesheet">
</head>

<t:template-side-nav title="ServiceBook - Minha conta">
    <jsp:body>
        <div class="row">
            <t:message-box/>
            <div class="col s12">
                <h2 class="secondary-color-text">Minhas especialidades</h2>
                <blockquote class="light-blue lighten-5 info-headers">
                    <p> Se você é especialista em algum ou alguns serviços e deseja receber solicitações
                        para realização destes tipos de serviços, por favor, nos informe as suas
                        especialidades profissionais de acordo com as suportadas pelo SERVICEBOOK que logo
                        você começará a receber pedido dos clientes.</p>
                </blockquote>
            </div>
        </div>

        <c:if test="${empty professionalExpertises}">
            <t:empty-list message="Você ainda não tem especialidades."></t:empty-list>
        </c:if>

        <div class="row">
            <c:forEach var="professionalExpertise" items="${professionalExpertises}">
                <div class="col s12 m6">
                    <div class="card">
                        <div class="center">
                            <img class="activator responsive-img" style="width: 40%; margin-top: 20px"
                                 src="${professionalExpertise.pathIcon}">
                        </div>
                        <div class="card-content">
                            <span class="card-title grey-text text-darken-4">${professionalExpertise.name}
                                <i class="material-icons right activator" style="cursor: pointer">more_vert</i>
                                <i class="material-icons right">delete</i>
                                <i class="material-icons right">edit</i>
                            </span>
                        </div>
                        <div class="card-reveal">
                            <span class="card-title grey-text text-darken-4">${professionalExpertise.name}<i
                                    class="material-icons right">close</i></span>
                            <p>
                                    ${professionalExpertise.description}
                            </p>
                        </div>
                        <div class="card-action center">
                            <a class="blue-text" href="minha-conta/profissional/servicos?id=${professionalExpertise.id}">MEUS
                                SERVIÇOS</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="center spacing-buttons">
            <a href="minha-conta/profissional/especialidades/novo" class="waves-effect waves-light btn">
                NOVA ESPECIALIDADE
            </a>
        </div>
    </jsp:body>
</t:template-side-nav>