<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<head>
  <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
  <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css"
        rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/ads.css"
        rel="stylesheet">
</head>

<t:template-side-nav title="ServiceBook - Minha conta">
  <jsp:body>
    <main>
      <div class="">
        <div class="section">
          <div class="row">
            <div class="breadcrumbs">
              <a href="${pageContext.request.contextPath}/">Início</a> &gt;
              <a href="${pageContext.request.contextPath}/minha-conta/profissional/meus-anuncios">Meus Anúncios</a> &gt;
              Pacotes de Serviços
            </div>

            <t:message-box/>

            <div class="col s12">
              <h3 class="secondary-color-text">Meus Anúncios </h3>
            </div>
            <blockquote class="light-blue lighten-5 info-headers">
              <p> Pacotes de Serviços.</p>
            </blockquote>

            <div class="input-field">
              <select id="service-select" name="id">
                <option disabled selected>Selecione um serviço</option>
                <c:forEach var="service" items="${services}">
                  <option value="${service.id}">${service.name}</option>
                </c:forEach>
              </select>
            </div>

            <c:forEach var="service" items="${servicesPackages}">
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
                    <p style="margin: 0"><fmt:formatNumber value="${service.price/100}"   type = "currency"/> </p>
                  </div>
                </div>
                <p class="label_especialidade">${service.service.name}</p>

              </div>
            </c:forEach>

          </div>
        </div>
      </div>
    </main>

  </jsp:body>
</t:template-side-nav>

<script>
    $(function () {

        $('.ads-price').mask('000.000.000.000.000,00', {reverse: true});

        //inicializa o select de serviços com os serviços da especialidade selecionada
        $('#expertise-select').change(function () {
            let expertiseId = $(this).val();
            let url = 'minha-conta/profissional/especialidades/' + expertiseId + '/servicos';

            $.get(url, function (data) {
                let options = '<option disabled selected>Selecione um serviço</option>';
                data.forEach(function (service) {
                    options += '<option value="' + service.id + '">' + service.name + '</option>';
                });
                $('#service-select').html(options);
                $('#service-select').formSelect();
            });
        });
    });
</script>