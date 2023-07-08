<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:template title="Vagas de Emprego">
    <jsp:body>
        <!-- Lista de vagas de emprego -->
        <div class="row">
            <div class="col s12 ">
                <h3 class="secondary-color-text center center-align">Vagas de Emprego</h3>
            </div>
            <div class="container center-align">
        <c:choose>
            <c:when test="${not empty jobs}">
                <div class="container center-align">
                    <br>
                    <select id="select-expertise" class="white-text">
                        <option value="--">Qualquer Profissão</option>
                        <c:forEach var="expertise" items="${expertises}">
                            <option value="${expertise.name}">${expertise.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <c:forEach var="job" items="${jobs}">
                    <div class="card horizontal s9 m12 left-align">
                        <div class="card-image col s2">
                            <img src=${job.company.profilePicture}>
                        </div>
                        <div class="card-stacked">
                            <div class="card-content">
                                <h4 class="job-title">${job.title} - <span class="expertise">${job.expertise.name}</span></h4>
                                <span class="grey-text">${job.company.name} <br> <span class="companyCep">${job.company.address.postalCode}</span>, ${job.company.address.neighborhood} - ${job.company.address.city.state.uf}</span>
                                <p>${job.description}</p>
                            </div>
                            <div class="card-action">
                                <c:choose>
                                    <c:when test="${job.salary != null}">
                                        <i class="material-icons">attach_money</i>
                                        <fmt:formatNumber value="${job.salary / 1000}" type="currency" currencySymbol="R$"/> mil / mês
                                    </c:when>
                                    <c:otherwise>À combinar</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise><h5>Não há vagas de emprego no momento</h5></c:otherwise>
        </c:choose>
        <div class="center"><t:pagination pagination="${pagination}" relativePath="/c/vagas-de-emprego/listar"></t:pagination></div>
        </div>
    </jsp:body>
</t:template>
