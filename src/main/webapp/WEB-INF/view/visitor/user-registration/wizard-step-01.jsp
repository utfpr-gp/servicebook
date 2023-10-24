<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
  <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
  <link href="${pageContext.request.contextPath}/assets/resources/styles/visitor/visitor.css" rel="stylesheet">
</head>

<t:template title="Servicebook - Cadastro - Passo 1">
  <jsp:body>

    <main>
      <div class="container">
        <c:if test="${not empty errors}">
          <div class="card-panel red">
            <c:forEach var="e" items="${errors}">
              <span class="white-text">${e.getDefaultMessage()}</span><br>
            </c:forEach>
          </div>
        </c:if>

        <div class="section">
          <div class="row">
            <!-- o endereço do formulário está atrelado a aba clicada pelo usuário, ou seja, para
            cadastro de empresa ou indivíduo. Por default, a aba empresa é mostrada. A atualização
             do endereço é realizado via JavaScript.-->
            <form method="post" action="cadastrar-se/empresa/passo-1" id="register-form">
              <div class="row">
                <div id="tabButton"
                     class="col s10 input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                  <ul class="tabs tabs_register">
                    <li id="tab_company" class="list tab col s5">
                      <a data-tabName="company" class="active" href="#company">Empresa</a>
                    </li>
                    <li id="tab_individual" class="list tab col s5">
                      <a data-tabName="individual" href="#individual">Cliente/Profissional</a>
                    </li>
                  </ul>
                </div>

                <div class="input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                  <h4 class="center secondary-color-text">
                    Qual o seu nome completo?
                  </h4>
                  <h5 class="center secondary-color-text">
                    O usuários precisam saber com quem estão negociando.
                  </h5>
                </div>

                <div class="div_email input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                  <label for="name">Nome completo</label>
                  <input id="name" name="name" type="text" value="${dto.name}" class="validate">
                </div>

                <div class="input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3 div_cpf">
                  <h4 class="center secondary-color-text">
                    Qual o seu CPF?
                  </h4>
                  <h5 class="center secondary-color-text">
                    Poderá ser usado para validar a veracidade dos dados pessoais.
                  </h5>
                </div>

                <div class="input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3 div_cpf">
                  <input id="cpf" name="cpf" type="text" value="${dto.cpf}" class="">
                  <label for="cpf">CPF</label>
                </div>
                <!-- verifica na sessão se o cadastro atual é de empresa -->
                <div class="input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3 div_cnpj">
                  <h4 class="center secondary-color-text">
                    Qual o seu CNPJ?
                  </h4>
                  <h5 class="center secondary-color-text">
                    Poderá ser usado para validar a veracidade dos dados pessoais.
                  </h5>
                </div>

                <div class="input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3 div_cnpj">
                  <input id="cnpj" name="cnpj" type="text" value="${dto.cnpj}" class="">
                  <label for="cnpj">CNPJ</label>
                </div>

                <div class="input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                  <h4 class="center secondary-color-text">
                    Qual o seu email?
                  </h4>
                  <h5 class="center secondary-color-text">
                    O seu email será utilizado para você acessar o sistema e receber informações sobre os
                    serviços!
                  </h5>
                </div>

                <div class="div_email input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                  <label for="email">Email</label>
                  <input id="email" name="email" type="text" value="${dto.email}" class="validate">
                </div>
                <div class="col s6 m3 offset-m3 spacing-buttons">
                  <div class="center">
                    <a class="waves-effect waves-light btn btn-gray" href="bem-vindo">Voltar</a>
                  </div>
                </div>

                <div class="col s6 m3 spacing-buttons">
                  <div class="center">
                    <button type="submit" class="waves-effect waves-light btn">Próximo</button>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </main>

  </jsp:body>
</t:template>
<script src="assets/libraries/jquery.mask.js"></script>

<script>
    $(".div_cpf").hide();
    $(document).ready(function () {
        $('#cpf').mask('000.000.000-00', {reverse: true});
        $('#cnpj').mask('00.000.000/0000-00', {reverse: true});
    });
    $("#tabButton ul li a").click(function (e) {
        // active deactivate tab buttons
        $("#tabButton ul li a").removeClass('active');
        $(this).addClass('active');

        // show hide tab content
        let tabName = $(this).attr('data-tabName');
        $("#tabContent .tab").removeClass('active');
        $("#tabContent #" + tabName).addClass('active');

        //altera o endereço do action do form de acordo com o tipo de usuário cadastrado
        if (tabName == 'company') {
            $('#register-form').attr('action', 'cadastrar-se/empresa/passo-1');
            $(".div_cpf").hide();
            $(".div_cnpj").show();
        } else {
            $('#register-form').attr('action', 'cadastrar-se/individuo/passo-1');
            $(".div_cpf").show();
            $(".div_cnpj").hide();
        }

        // stop reload
        e.preventDefault();
    })
</script>
