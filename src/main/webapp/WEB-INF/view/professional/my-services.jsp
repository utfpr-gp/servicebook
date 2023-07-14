<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css"
          rel="stylesheet">
</head>

<t:template-side-nav title="ServiceBook - Minha conta">
    <jsp:body>
        <div class="row">
            <div class="col s12">
                <h2 class="secondary-color-text">Meus serviços de ${fn:toLowerCase(expertise.name)}</h2>
                <blockquote class="light-blue lighten-5 info-headers">
                    <p>Adicione os serviços que você oferece para que os clientes saibam exatamente qual é a sua
                        especialidade e possam encontrá-lo com maior facilidade.</p>
                    <p>Além de divulgar seus serviços aos clientes, você pode definir um preço. Isso permite que os
                        clientes adquiram um cupom que dá direito ao serviço diretamente pelo ServiceBook.</p>
                    <p>Se você deseja que os clientes que compraram seus serviços possam agendar automaticamente,
                        forneça informações sobre o tempo médio de execução do serviço. Isso ajudará a otimizar a
                        adequação à sua agenda.</p>
                </blockquote>
            </div>
        </div>

        <!-- Lista vazia -->
        <c:if test="${empty professionalServiceOfferings}">
            <t:empty-list message=""></t:empty-list>
        </c:if>

        <!-- Lista de serviços -->
        <div class="row">
            <c:forEach var="serviceOffering" items="${professionalServiceOfferings}">
                <c:set var="serviceName" value="${not empty serviceOffering.name ? serviceOffering.name : serviceOffering.service.name}" />
                <c:set var="serviceDescription" value="${not empty serviceOffering.description ? serviceOffering.description : serviceOffering.service.description}" />
                <div class="col s12 m6">
                    <div class="card">
                        <div class="">
                            <div class="row">
                                <div class="col s2 offset-s10 valign-wrapper">
                                    <a href="#modal-delete" id="delete-service-offering"
                                       class="myclass waves-effect waves-teal btn-flat modal-trigger"
                                       data-url="${pageContext.request.contextPath}/minha-conta/profissional/servicos/${serviceOffering.id}"
                                       data-name="${serviceName}">
                                        <i class="myclass material-icons">delete</i>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="card-content">
                            <span class="card-title activator grey-text text-darken-4">${serviceName}<i class="material-icons right">more_vert</i></span>
                        </div>
                        <div class="card-reveal">
                            <span class="card-title grey-text text-darken-4">${serviceName}<i class="material-icons right">close</i></span>
                            <p>
                                    ${serviceDescription}
                            </p>
                        </div>
                        <div class="card-action center">
                            <a class="blue-text" href="minha-conta/profissional/meus-anuncios/${serviceOffering.id}">MEUS ANÚNCIOS</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>


        <!-- Fim Lista de serviços -->

        <div class="center spacing-buttons">
            <button class="waves-effect waves-light btn">
                <a href="#modal-services" class="modal-trigger">
                    NOVO SERVIÇO
                </a>
            </button>
        </div>

        <!-- Modal para cadastro de um serviço -->
        <div id="modal-services" class="modal">
            <div class="modal-content ui-front">
                <div class="row">
                    <div class="col s9 offset-m1">
                        <h4 class="flow-text">Escolha uma ou mais serviços!</h4>
                    </div>
                    <div class="col s3 m2">
                        <button class="modal-close modal-expertise-close right">
                            <i class="material-icons">close</i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Fim Modal para cadastro de um serviço -->

        <!-- Modal para remoção de uma especialidade -->
        <div id="modal-delete" class="modal">
            <div class="modal-content">
                <form action="" method="post">

                    <input type="hidden" name="_method" value="DELETE"/>

                    <div class="modal-content">
                        <h4>Você tem certeza que deseja remover <strong id="strong-name"></strong> das suas
                            especialidades?</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button"
                                class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar
                        </button>
                        <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <!-- Fim Modal para remoção de uma especialidade -->

    </jsp:body>
</t:template-side-nav>

<script>
</script>