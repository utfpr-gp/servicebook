<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/admin/admin.css" rel="stylesheet">
</head>

<t:admin title="Dash de Serviços">
    <jsp:body>

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
        <div class="row ">
            <div class="col s3 center">
                <h4>Filtrar por</h4>
                <form action="a/dashboard/q" method="get">
                    <h6>Data do serviço</h6>
                    <div class="input-field col s12">
                        <select name="startDate" id="select-status" class="white-text mt-1 no-padding center" style="padding: 0 !important;">
                            <c:choose>
                                <c:when  test="${not empty setMonth}">
                                    <option value="${setMonth}" disabled selected>${jobRequestFilterParam}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="" disabled selected>Mês / Ano</option>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="entry" items="${months}">
                                <option value="${entry.key}">${entry.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <h6>Especialidade do usuário</h6>
                    <div class="input-field col s12">
                        <select name="expertiseId" id="select-professional" class="white-text mt-1 no-padding center" style="padding: 0 !important;">
                            <c:choose>
                                <c:when  test="${not empty setExpertise}">
                                    <option value="${setExpertise}" disabled selected>${userFilterParam}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="" disabled selected>Qualquer especialidade</option>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="expertise" items="${expertises}">
                                <option value="${expertise.id}">${expertise.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button class="waves-effect waves-light btn col s12 mt-1 center" type="submit"><i class="material-icons left">filter_list</i> Filtrar</button>
                    <a class="waves-effect waves-light btn grey col s12 mt-1 center" href="a/dashboard"><i class="material-icons left">delete</i> Limpar</a>
                </form>
            </div>

            <div class="col s9">
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

                        <c:if test="${not empty totalClients}">
                            <div class="col s12 m3">
                                <div class="card indigo">
                                    <div class="card-content white-text">
                                        <span class="card-title">${totalClients}</span>
                                        <p>CLIENTES</p>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty totalProfessionals}">
                            <div class="col s12 m3">
                                <div class="card indigo">
                                    <div class="card-content white-text">
                                        <span class="card-title">${totalProfessionals}</span>
                                        <p>PROFISSIONAIS</p>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty totalCompanies}">

                            <div class="col s12 m3">
                                <div class="card indigo">
                                    <div class="card-content white-text">
                                        <span class="card-title">${totalCompanies}</span>
                                        <p>EMPRESAS</p>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty totalClients}">
                            <div class="col s12 m3">
                                <div class="card indigo">
                                    <div class="card-content white-text">
                                        <span class="card-title">${totalClients}</span>
                                        <p>TOTAL</p>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:admin>
