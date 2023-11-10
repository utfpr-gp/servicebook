<%@tag description="Servicebook - Search bar template" pageEncoding="UTF-8" %>
<%@attribute name="items" type="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/assets/resources/styles/visitor/visitor.css" rel="stylesheet">

<script
        src="https://code.jquery.com/jquery-3.7.0.min.js"
        integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g="
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/assets/resources/scripts/ads.js"></script>

<div>
  <div class="container center-align">
    <div class="row">
      <div class="col s12">
        <h4 style="font-weight: bold ">O QUE VOCÊ PRECISA?</h4>
        <h6>Nos informe de qual serviço você precisa e escolha o melhor profissional!</h6>
      </div>
    </div>
    <c:if test="${not empty msg}">
      <div class="row">
        <div class="col s12">
          <div class="card-panel green lighten-1 msg-view center-align">
            <span class="white-text">${msg}</span>
          </div>
        </div>
      </div>
    </c:if>

    <form action="profissionais/busca" method="get">
      <div class="row">
        <div class="input-field">
          <label for="category-select">Selecione uma categoria</label>
          <select name="categoryId" id="category-select">
            <option disabled selected>Selecione uma categoria</option>
            <c:forEach var="category" items="${items}">
              <option value="${category.id}"
                      <c:if test="${category.id == dto.id}">
                        selected
                      </c:if>
              >${category.name}</option>

            </c:forEach>
          </select>
        </div>
        <div class="input-field">
          <label for="expertise-select">Selecione uma especialidade</label>
          <select name="expertiseId" id="expertise-select" disabled>
            <option disabled selected value="null">Selecione uma especialidade</option>
          </select>
          <input type="hidden" value="${dto_expertise.id}" id="dto_expertise">
        </div>

        <div class="input-field">
          <label for="expertise-select">Selecione um serviço</label>
          <select name="serviceId" id="service-select" disabled required>
            <option disabled selected>Selecione um serviço</option>
          </select>
          <input type="hidden" value="${dto_service.id}" id="dto_service">

        </div>

        <div class="row">
          <div class="col s12">
            <button type="submit" class="waves-effect waves-light btn-large right">
              <i class="material-icons left">search</i>Buscar
            </button>
          </div>
        </div>
      </div>
    </form>
  </div>
</div>
