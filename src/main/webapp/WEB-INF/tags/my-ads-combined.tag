<%@tag description="Servicebook - Banner template" pageEncoding="UTF-8" %>
<%@attribute name="cities" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="items" required="false" type="java.util.ArrayList"%>


<div class="col s12">
  <span class="left-align" style="font-weight: bold">Serviços Combinados</span>
  <a href="minha-conta/profissional/meus-anuncios/combinados" class="btn-flat" style="float: right">ver mais</a>

</div>
<c:forEach var="service" items="${items}">
  <div class="col s4">
    <div class="card">
      <div class="card-content">
        <span class="card-title grey-text text-darken-4">${service.name}</span>
        <p class="lead texto-com-elipse">${service.name}</p>
      </div>
      <div class="card-reveal">
        <span class="card-title grey-text text-darken-4" style="font-size: 1rem">${service.name}</span>
        <small>${service.description}</small>
      </div>
      <div class="card-content grey lighten-4">
        <div class="row">
          <div class="col" style="display: inline-flex">
            <span style="margin: 12px">R$</span> <input type="text" class="ads-price ads-price-label" value="${service.price}" disabled>
          </div>

          <div class="col">
            <span class="grey-text text-darken-4 left"><i class="material-icons left">access_time</i> ${service.duration} </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</c:forEach>
