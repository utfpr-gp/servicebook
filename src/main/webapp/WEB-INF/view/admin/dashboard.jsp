<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:admin title="Dash de Serviços">
    <jsp:body>
        <div class="row">
            <div class="col s3 center">
                <form action="a/servicos/dashboard" method="post">
                    <h4>Data </h4>
                    <select name="dateFinal" id="select-status" class="white-text select-city" >
                        <option value="--">Mês / Ano</option>
                        <option value="23-01-01">Jan/23</option>
                        <option value="23-02-01">Fev/23</option>
                        <option value="23-03-01">Mar/23</option>
                        <option value="23-04-01">Abr/23</option>
                        <option value="23-05-01">Mai/23</option>
                        <option value="23-06-01">Jun/23</option>
                        <option value="23-07-01">Jul/23</option>
                    </select>
                    <button class="waves-effect waves-light btn" type="submit"><i class="material-icons left">filter_list</i> Filtrar</button>
                </form>
                <form action="a/servicos/dashboard" method="post">
                    <h4>Profissão</h4>
                    <select name="expertise" id="select-professional" class="white-text select-city" name="expertise">
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
                    <h4>RESUMO DE SERVIÇOS
                        <c:if test="${jobRequestFilterParam}">
                            (desde ${jobRequestFilterParam})
                        </c:if>
                    </h4>
                    <c:if test="${not empty totalJobContracted}">
                        <div class="col s12 m4">
                            <div class="card indigo">
                                <div class="card-content white-text">
                                    <span class="card-title">${totalJobContracted}</span>
                                    <p>TOTAL</p>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty totalJobDoing}">
                        <div class="col s12 m4">
                            <div class="card indigo">
                                <div class="card-content white-text">
                                    <span class="card-title">${totalJobDoing}</span>
                                    <p>EM ANDAMENTO</p>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty totalJobClosed}">
                        <div class="col s12 m4">
                            <div class="card indigo">
                                <div class="card-content white-text">
                                    <span class="card-title">${totalJobClosed}</span>
                                    <p>EXPIRADOS</p>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <div class="col s12 cards-container">
                        <h4>RESUMO DOS USUÁRIOS
                        <c:if test="${serFilterParam}">
                            (apenas ${userFilterParam})
                        </c:if>
                        </h4>

                        <c:if test="${not empty totalClients}">
                            <div class="col s12 m4">
                                <div class="card indigo">
                                    <div class="card-content white-text">
                                        <span class="card-title">${totalClients}</span>
                                        <p>CLIENTES</p>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty totalProfessionals}">
                            <div class="col s12 m4">
                                <div class="card indigo">
                                    <div class="card-content white-text">
                                        <span class="card-title">${totalProfessionals}</span>
                                        <p>PROFISSIONAIS</p>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty totalCompanies}">

                            <div class="col s12 m4">
                                <div class="card indigo">
                                    <div class="card-content white-text">
                                        <span class="card-title">${totalCompanies}</span>
                                        <p>EMPRESAS</p>
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
