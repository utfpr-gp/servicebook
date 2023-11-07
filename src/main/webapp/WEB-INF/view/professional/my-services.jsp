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
            <t:message-box/>
            <div class="col s12">
                <c:if test="${expertise != null}">
                    <h2 class="secondary-color-text">Meus serviços de ${fn:toLowerCase(expertise.name)}</h2>
                </c:if>
                <c:if test="${empty expertise}">
                    <h2 class="secondary-color-text">Meus serviços</h2>
                </c:if>
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

<%--        <hr style="margin-bottom: 50px"/>--%>

        <form id="expertise-form" action="minha-conta/profissional/servicos" style="margin: 50px 0px" method="get">
            <div class="input-field">
                <select id="expertise-select" name="id" value="${expertise.id}">
                    <option disabled selected>Todas as especialidades</option>
                    <c:forEach var="e" items="${expertises}">
                        <option value="${e.id}" ${e.id eq expertise.id ? 'selected="selected"' : ''}>${e.name}</option>
                    </c:forEach>
                </select>
                <label for="expertise-select" style="top: -42px">Selecione uma especialidade</label>
            </div>
        </form>

<%--        <hr style="margin: 50px 0px"/>--%>

        <!-- Lista vazia -->
        <c:if test="${empty professionalServiceOfferings}">
            <t:empty-list message=""></t:empty-list>
        </c:if>

        <!-- Lista de serviços -->
        <div class="row">
            <c:forEach var="serviceOffering" items="${professionalServiceOfferings}">
                <div class="col s12 m6">
                    <t:service-card serviceOffering="${serviceOffering}" edit="true"/>
                </div>
            </c:forEach>
        </div>
        <!-- Fim Lista de serviços -->

        <div class="center spacing-buttons">
            <a class="waves-effect waves-light btn" href="minha-conta/profissional/servicos/novo?id=${expertise.id}">
                NOVO SERVIÇO
            </a>
        </div>
    </jsp:body>
</t:template-side-nav>

<script>
    document.getElementById("expertise-select").addEventListener("change", function() {
        document.getElementById("expertise-form").submit();
    });
</script>