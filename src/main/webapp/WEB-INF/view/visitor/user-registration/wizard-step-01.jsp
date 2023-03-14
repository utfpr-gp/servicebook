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
                            <form method="post" action="cadastrar-se/passo-1">
                                <div class="row">
                                    <div id="tabButton" class="col s10 input-field col s12 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                        <ul class="tabs tabs_register">
                                            <li id="tab_company" class="list tab col s5">
                                                <a data-tabName="company" class="active" href="#company">Empresa</a>
                                            </li>
                                            <li id="tab_individual" class="list tab col s5">
                                                <a data-tabName="individual" href="#individual">Cliente/Profissional</a>
                                            </li>
                                        </ul>

                                        <input type="hidden" name="type" value="" id="result">
                                        <input type="hidden" name="profile" value="" id="role">

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

<script>
    $("#result").val('company');
    $("#role").val('ROLE_COMPANY');

    $("#tabButton ul li a").click(function(e){
        // active deactivate tab buttons
        $("#tabButton ul li a").removeClass('active');
        $(this).addClass('active');

        // show hide tab content
        var tabName = $(this).attr('data-tabName');
        $("#tabContent .tab").removeClass('active');
        $("#tabContent #"+tabName).addClass('active');

        $("#result").val(tabName);

        if(tabName == 'company') {
            $("#role").val('ROLE_COMPANY');
        }

        if(tabName == 'individual') {
            $("#role").val('ROLE_USER');
        }

        // stop reload
        e.preventDefault();
    })
</script>
