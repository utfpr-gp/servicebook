<%@tag description="Servicebook - Banner template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="serviceOffering" type="br.edu.utfpr.servicebook.model.dto.ProfessionalServiceOfferingDTO" %>
<%@attribute name="edit" type="java.lang.Boolean" %>

<c:set var="serviceName"
       value="${not empty serviceOffering.name ? serviceOffering.name : serviceOffering.service.name}"/>
<c:set var="serviceDescription"
       value="${not empty serviceOffering.description ? serviceOffering.description : serviceOffering.service.description}"/>

<div class="card">
    <div class="center">
        <img class="activator responsive-img"
             style="width: 40%; margin-top: 20px"
             src="${serviceOffering.service.pathIcon}">
    </div>

    <div class="card-content">
        <span class="card-title grey-text text-darken-4">${serviceName}
            <i class="material-icons right activator" style="cursor: pointer">more_vert</i>
            <c:if test="${edit}">
                <i class="material-icons right">delete</i>
                <i class="material-icons right">edit</i>
            </c:if>
        </span>
    </div>

    <div class="card-reveal">
        <span class="card-title grey-text text-darken-4">${serviceName}
            <i class="material-icons right">close</i>
        </span>
        <p>
            ${serviceDescription}
        </p>
    </div>

    <c:if test="${edit}">
        <div class="card-action center">
            <a class="blue-text" href="minha-conta/profissional/meus-anuncios/${serviceOffering.id}">ANUNCIAR</a>
        </div>
    </c:if>
</div>