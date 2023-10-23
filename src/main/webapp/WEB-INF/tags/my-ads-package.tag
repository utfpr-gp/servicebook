<%@tag description="Servicebook - Banner template" pageEncoding="UTF-8" %>
<%@attribute name="cities" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="itemsService" required="false" type="java.util.ArrayList" %>
<link href="${pageContext.request.contextPath}/assets/resources/styles/professional/ads.css"
      rel="stylesheet">

<div class="col s12">
  <span class="left-align" style="font-weight: bold; font-size: 1.5rem">Pacote de Serviços</span>
  <a href="minha-conta/profissional/meus-anuncios/pacotes" class="btn-flat btn_view">ver mais <i
          class="material-icons right">navigate_next</i></a>
</div>
<c:forEach var="service" items="${itemsService}">
  <div class="col s4 div_cards_services">
    <p class="label_especialidade"> ${service.service.expertise.name} </p>

    <div class="card" style="margin: 0">
      <div class="label_duration">
        <span class="">${service.duration}</span>
      </div>
      <div class="card-content">
        <div class="row">
          <c:if test="${not empty service.service.pathIcon}">
            <div class="col s12 center">
              <img id="previewImage" src="${service.service.pathIcon}" width="60%">
            </div>
          </c:if>

          <div class="col s12">
            <p>${service.description}</p>
            <p>Pacote com ${service.amount}</p>
          </div>
        </div>

      </div>
      <div class="label_price">
        <p style="margin: 0"><fmt:formatNumber value="${service.price/100}" type="currency"/></p>
      </div>
    </div>
    <p class="label_especialidade">${service.service.name}</p>
  </div>
</c:forEach>
