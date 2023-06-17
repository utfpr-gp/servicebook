<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
                                    <div id="tabButton" class="col s10 input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                        <ul class="tabs tabs_register">
                                            <li id="tab_company" class="list tab col s5">
                                                <a data-tabName="company" class="active" href="#company">Empresa</a>
                                            </li>
                                            <li id="tab_individual" class="list tab col s5">
                                                <a data-tabName="individual" class="" href="#individual">Cliente/Profissional</a>
                                            </li>
                                        </ul>
                                    </div>

                                    <div class="input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                        <h3 class="center secondary-color-text">
                                            Qual o seu email?
                                        </h3>
                                        <h5 class="center secondary-color-text">
                                            O seu email será utilizado para você acessar o sistema e receber informações sobre os
                                            serviços!
                                        </h5>

                                    </div>
                                    <c:if test="${not empty emailProfessional}">
                                    <div class="div_email input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                        <label for="emailProfessional">Email</label>
                                        <input id="emailProfessional" name="email" type="text" value="${emailProfessional}" class="validate">
                                    </div>
                                    </c:if>

                                    <c:if test="${empty emailProfessional}">
                                    <div class="div_email input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                        <label for="email">Email</label>
                                        <input id="email" name="email" type="text" value="${dto.email}" class="validate">
                                    </div>
                                    </c:if>

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
<script>
    if(typeof $("#emailProfessional").val() === 'undefined' || typeof $("#emailProfessional").val() === undefined){
        $("#tabButton ul li a").click(function(e){
            // active deactivate tab buttons
            $("#tabButton ul li a").removeClass('active');
            $(this).addClass('active');

            // show hide tab content
            let tabName = $(this).attr('data-tabName');
            $("#tabContent .tab").removeClass('active');
            $("#tabContent #"+tabName).addClass('active');
            // $("#tabButton #tab_individual").removeClass('register_individual_company');
            //altera o endereço do action do form de acordo com o tipo de usuário cadastrado
            if(tabName == 'company'){
                $('#register-form').attr('action', 'cadastrar-se/empresa/passo-1');
            }
            else{
                $('#register-form').attr('action', 'cadastrar-se/individuo/passo-1');
            }
            // stop reload
            e.preventDefault();
        })
        } else {
            // active deactivate tab buttons
            $("#tabButton #tab_company").removeClass('active');
            $("#tabContent #company").removeClass('active');
            // show hide tab content
            $("#tabButton .indicator").addClass('indicator_register_individual_company');

            let tabName = $(this).attr('data-tabName');
            $("#tabContent #tab_individual #individual").addClass('active');
            $("#tabButton #tab_individual").addClass('register_individual_company');
            $("#tabButton #tab_company").addClass('remove_active');
            //altera o endereço do action do form de acordo com o tipo de usuário cadastrado
            $('#register-form').attr('action', 'cadastrar-se/individuo/passo-1');

    }
</script>
