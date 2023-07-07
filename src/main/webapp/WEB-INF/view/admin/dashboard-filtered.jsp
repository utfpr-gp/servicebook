<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:admin title="Dash de Serviços">
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
                    <div class="card-panel red msg-view center-align">
                        <c:forEach var="e" items="${errors}">
                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
        <!-- Fim Mensagens -->
        <div class="row">
            <div class="col s3 center">
                <form action="a/dashboard/q" method="get">
                    <h4>Data </h4>
                    <select name="startDate" id="select-status" class="white-text select-city" >
                        <option value="--" disabled selected>Mês / Ano</option>
                        <c:forEach var="entry" items="${months}">
                            <option value="${entry.key}">${entry.value}</option>
                        </c:forEach>
                    </select>
                    <button class="waves-effect waves-light btn" type="submit"><i class="material-icons left">filter_list</i> Filtrar</button>
                </form>
                <form action="a/dashboard/q" method="get">
                    <h4>Especialidade</h4>
                    <select name="expertiseId" id="select-professional" class="white-text select-city">
                        <option value="--">Qualquer</option>
                        <c:forEach var="expertise" items="${expertises}">
                            <option value="${expertise.id}">${expertise.name}</option>
                        </c:forEach>
                    </select>
                    <button class="waves-effect waves-light btn" type="submit"><i class="material-icons left">filter_list</i> Filtrar</button>
                </form>
                <a class="waves-effect waves-light btn grey col s12 mt-1" href="a/servicos/dashboard"><i class="material-icons left">delete</i> Limpar</a>

            </div>

            <div class="col s9">
                <div class="row">
                    <h4>REQUISIÇÕES DE SERVIÇOS
                        <c:if test="${jobRequestFilterParam}">
                            (desde ${jobRequestFilterParam})
                        </c:if>
                    </h4>

                    <div class="col s12 m3">
                        <div class="card indigo">
                            <div class="card-content white-text">
                                <span class="card-title">${totalJobAvailable}</span>
                                <p>DISPONÍVEIS</p>
                            </div>
                        </div>
                    </div>

                    <div class="col s12 m3">
                        <div class="card indigo">
                            <div class="card-content white-text">
                                <span class="card-title">${totalJobDoing}</span>
                                <p>EM ANDAMENTO</p>
                            </div>
                        </div>
                    </div>

                    <div class="col s12 m3">
                        <div class="card indigo">
                            <div class="card-content white-text">
                                <span class="card-title">${totalJobClosed}</span>
                                <p>FINALIZADOS</p>
                            </div>
                        </div>
                    </div>

                    <div class="col s12 m3">
                        <div class="card indigo">
                            <div class="card-content white-text">
                                <span class="card-title">${totalJobs}</span>
                                <p>TOTAL</p>
                            </div>
                        </div>
                    </div>


                    <div class="col s12 cards-container">
                        <h4>USUÁRIOS
                        <c:if test="${serFilterParam}">
                            (apenas ${userFilterParam})
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
