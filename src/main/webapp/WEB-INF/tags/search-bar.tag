<%@tag description="Servicebook - Search bar template" pageEncoding="UTF-8" %>
<%@attribute name="items" type="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script
        src="https://code.jquery.com/jquery-3.7.0.min.js"
        integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g="
        crossorigin="anonymous"></script>

<style>

</style>
<div>
  <div class="container center-align">
    <div class="row">
      <div class="col s12">
        <h4 style="font-weight: bold ">O QUE VOCÊ PRECISA?</h4>
        <h6>Nos informe de qual serviço você precisa e escolha o melhor profissional!</h6>
      </div>
    </div>
    <form action="profissionais/busca" method="get">
      <div class="row">
        <div class="col s12">

          <nav class="grey lighten-4">
            <div class="nav-wrapper">
              <div class="input-field">
                <input id="search" type="search" name="termo-da-busca" placeholder="Digite aqui..."
                       value="${searchTerm}" required>
                <i class="material-icons">close</i>
              </div>
            </div>
          </nav>
        </div>
      </div>

      <div class="row">
        <div class="input-field">
          <label for="category-select">Selecione uma categoria</label>
          <select name="categoryId" id="category-select">
            <option disabled selected>Selecione uma categoria</option>
            <c:forEach var="category" items="${categories}">
              <option value="${category.id}">${category.name}</option>
            </c:forEach>
          </select>
        </div>
        <div class="input-field">
          <label for="expertise-select">Selecione uma especialidade</label>
          <select name="expertiseId" id="expertise-select">
            <option disabled selected>Selecione uma especialidade</option>
            <c:forEach var="expertise" items="${expertises}">
              <option value="${expertise.id}">${expertise.name}</option>
            </c:forEach>
          </select>
        </div>
        <div class="input-field">
<%--          datalist--%>
          <select name="serviceId" id="service-select" class="js-select2">
<%--            <option disabled selected>Selecione um serviço</option>--%>
<%--            <c:forEach var="service" items="${services}">--%>
<%--              <option value="${service.id}">${service.name}</option>--%>
<%--            </c:forEach>--%>
          </select>

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

<script>
    // In your Javascript (external .js resource or <script> tag)
    $(document).ready(function() {
        $(".select-wrapper").css("display", "none");

        $('.js-select2').select2({
            placeholder: "Selecione um serviço",
        });
    });
</script>