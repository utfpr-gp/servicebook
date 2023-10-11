<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col s12">
  <form action="minha-conta/profissional/meus-anuncios/novo/combinado" method="post">

    <div class="row">
      <div class="input-field col s6">
        <p for="ads-service" class="label-ads">Quais os serviços? </p>
        <select class="service-select" name="serviceId" multiple id="mySelect" multiple onchange="mostrarSelecionados()">
          <option disabled selected  value="">Selecione um serviço</option>
          <c:forEach var="service" items="${services}">
            <option value="${service.id}">${service.name}</option>
          </c:forEach>
        </select>
      </div>

      <div class="input-field col s12">
        <p for="ads-name"  class="label-ads">Titulo </p>
        <input type="text" id="descriptionarray" name="" class="ads-name-individual" disabled onblur="nameServiceCombined()">
        <input type="hidden" id="description" name="description" class="ads-name-individual">
        <input type="hidden" id="descriptions" name="descriptions" class="ads">

        <p>
          <label>
            <input type="checkbox" id="sobrescrever" class="sobrescrever"/>
            <span>Sobrescrever</span>
          </label>
        </p>

      </div>

      <div class="input-field col s6">
        <p for="ads-uni" class="label-ads">Qual a unidade de preço do serviço? </p>
        <select id="ads-uni" name="unit">
          <option disabled selected>Selecione</option>
          <option>Hora</option>
          <option>Metro Quadrado</option>
          <option>Unidade</option>
        </select>
      </div>

      <div class="input-field col s6">
        <p for="ads-price" class="label-ads">Quanto você cobra por este serviço? </p>
        <input id="ads-price-combined" class="ads-price" type="text" name="" onblur="return RemoveMaskCombined(event)"/>
        <input id="price-service-combined" class="price-service" type="hidden" name="price"/>

      </div>

      <div class="input-field col s6">
        <p class="label-ads">Qual a Duração do serviço?</p>
        <select id="ads-duracao" name="duration">
          <option disabled selected>Selecione</option>
          <option>Sem Agendamento</option>
          <option>1 Hora</option>
          <option>1 Hora e meia</option>
          <option>2 Horas</option>
          <option>3 Horas</option>
          <option>Um período do dia</option>
          <option>O dia inteiro</option>
        </select>
      </div>

    </div>
    <div class="right">
      <a href="minha-conta/profissional/meus-anuncios" class="btn-flat">Cancelar</a>
      <button type="submit"
              class="btn waves-effect waves-light">Salvar
      </button>
    </div>

  </form>
</div>
<script>
    function nameServiceCombined() {
        str = $("#descriptionarray").val();
        $("#description").val(str);
    }
</script>