<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/admin/admin.css" rel="stylesheet">
</head>

<t:template-dashboard-admin title="Dash de Serviços">
    <jsp:body>
        <!-- Mensagens -->
        <div class="row">
            <div class="col s12 l6 offset-l3 spacing-buttons">
                <c:if test="${not empty msg}">
                    <div class="card-panel green lighten-1 msg-view center-align">
                        <span class="white-text">${msg}</span>
                    </div>
                </c:if>
                <c:if test="${not empty msgError}">
                    <div class="card-panel red msg-view center-align">
                        <span class="white-text">${msgError}</span>
                    </div>
                </c:if>
                <c:if test="${not empty errors}">
                    <div class="card-panel red msg-view center-align no-padding center">
                        <c:forEach var="e" items="${errors}">
                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
        <!-- Fim Mensagens -->

        <div class="row">
            <h4 style="padding-left: .4em">Requisições de serviços
                <c:if test="${not empty jobRequestFilterParam}">
                    em ${jobRequestFilterParam}
                </c:if>
            </h4>

            <div class="col s12 m3">
                <div class="card indigo">
                    <div class="card-content white-text">
                        <span class="card-title">${totalJobAvailable != null ? totalJobAvailable : 0}</span>
                        <p>DISPONÍVEIS</p>
                    </div>
                </div>
            </div>

            <div class="col s12 m3">
                <div class="card indigo">
                    <div class="card-content white-text">
                        <span class="card-title">${totalJobDoing != null ? totalJobDoing : 0}</span>
                        <p>EM ANDAMENTO</p>
                    </div>
                </div>
            </div>

            <div class="col s12 m3">
                <div class="card indigo">
                    <div class="card-content white-text">
                        <span class="card-title">${totalJobClosed != null ? totalJobClosed : 0}</span>
                        <p>FINALIZADOS</p>
                    </div>
                </div>
            </div>

            <div class="col s12 m3">
                <div class="card indigo">
                    <div class="card-content white-text">
                        <span class="card-title">${totalJobs != null ? totalJobs : 0}</span>
                        <p>TOTAL</p>
                    </div>
                </div>
            </div>


            <div class="col s12 cards-container">
                <h4 style="padding-left: .4em">Usuários
                    <c:if test="${not empty userFilterParam}">
                        com especialidade: ${userFilterParam}
                    </c:if>
                </h4>

                <div class="col s12 m3">
                    <div class="card indigo">
                        <div class="card-content white-text">
                            <span class="card-title">${totalClients}</span>
                            <p>CLIENTES</p>
                        </div>
                    </div>
                </div>

                <div class="col s12 m3">
                    <div class="card indigo">
                        <div class="card-content white-text">
                            <span class="card-title">${totalProfessionals}</span>
                            <p>PROFISSIONAIS</p>
                        </div>
                    </div>
                </div>

                <div class="col s12 m3">
                    <div class="card indigo">
                        <div class="card-content white-text">
                            <span class="card-title">${totalCompanies}</span>
                            <p>EMPRESAS</p>
                        </div>
                    </div>
                </div>

                <div class="col s12 m3">
                    <div class="card indigo">
                        <div class="card-content white-text">
                            <span class="card-title">${totalUsers}</span>
                            <p>TOTAL</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template-dashboard-admin>
