<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:professional title="ServiceBook - Minha conta">
    <jsp:body>

        <main>
            <div class="row">

                <t:side-panel></t:side-panel>

                <div class="col m10 offset-m1 l9">
                    <a id="show-area-perfil"
                       class="hide-on-large-only show-area-perfil waves-effect waves-light btn btn-floating grey darken-3 z-depth-A">
                        <i class="material-icons">compare_arrows</i>
                    </a>
                    <div class="row">
                        <div class="col s12">
                            <h2 class="secondary-color-text">Minhas especialidades</h2>
                            <blockquote class="light-blue lighten-5 info-headers">
                                <p> Se você é especialista em algum ou alguns serviços e deseja receber solicitações para realização destes tipos de serviços, por favor, nos informe as suas especialidades profissionais de acordo com as suportadas pelo SERVICEBOOK que logo você começará a receber pedido dos clientes.</p>
                            </blockquote>
                        </div>
                    </div>

                    <div id="modal-delete" class="modal">
                        <div class="modal-content">
                            <form action="" method="post">

                                <input type="hidden" name="_method" value="DELETE"/>

                                <div class="modal-content">
                                    <h4>Você tem certeza que deseja remover <strong id="strong-name"></strong> das suas especialidades?</h4>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                                    <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col s12">
                        <div class="row expertises">
                            <c:forEach var="professionalExpertises" items="${professionalExpertises}">
                                <div class="col s12 m5 offset-m1 card-expertise-list row">
                                    <div class="col s2 delete-exerpertise expertise-icon">
                                        <i class="material-icons">work</i>
                                    </div>
                                    <div class="col s8">
                                        <p class="center">
                                            <strong>
                                                    ${professionalExpertises.name}
                                            </strong>
                                        </p>
                                    </div>
                                    <div class="col s2 delete-exerpertise expertise-icon">
                                        <a href="#modal-delete" id="delete-exerpertise-professional" class="myclass waves-effect waves-teal btn-flat delete-exerpertise-professional modal-trigger"
                                           data-url="${pageContext.request.contextPath}/minha-conta/profissional/especialidades/${professionalExpertises.id.expertiseId}"
                                           data-name="${professionalExpertises.name}"><i class="myclass material-icons">delete</i></a>
                                    </div>
                                    <div class="col s12 right">
                                        <p class="center">
                                            Descrição da especialidade
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                    </div>
                    <div class="center spacing-buttons">
                        <button  class="waves-effect waves-light btn">
                            <a href="#modal-expertises" class="modal-trigger">
                                Adicionar nova especialidade
                            </a>
                        </button>
                    </div>

                    <div id="modal-expertises" class="modal">
                        <div class="modal-content ui-front">
                            <div class="row">
                                <div class="col s9">
                                    <h4>Escolha uma ou mais especialidades!</h4>
                                </div>
                                <div class="col s3">
                                    <button class="modal-close modal-expertise-close right">
                                        <i class="material-icons">close</i>
                                    </button>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col s12">
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <i class="material-icons prefix">work</i>
                                            <input type="text" id="txtBusca" class="autocomplete">
                                            <label for="txtBusca">Selecione sua especialidade</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <form class="s12" id="form-expertises" action="minha-conta/profissional/especialidades" method="post">
                                    <ul id="search-expertises">
                                        <c:forEach var="expertise" items="${expertises}">
                                            <li>
                                                <label class='card-expertise col s12 m10 offset-m1'>
                                                    <input id='ids' name='ids' type='checkbox' class='reset-checkbox' value="${expertise.id}">
                                                    <span class='center name-expertise'>
                                                    <i class='material-icons'>work</i>
                                                    ${expertise.name}
                                                </span>
                                                </label>
                                            </li>
                                        </c:forEach>
                                    </ul>


                                    <div class="input-field col s8 offset-s1">
                                        <button id="submit-expertise" type="submit" class="btn waves-effect waves-light left">Salvar</button>
                                    </div>
                                    <div class="input-field col s3">
                                        <a class="btn waves-effect waves-light modal-close">Fechar</a>
                                    </div>
                                </form>
                            </div>


                        </div>
                    </div>

                </div>
            </div>
        </main>

    </jsp:body>
</t:professional>

<script>

    $(function(){
        $("#txtBusca").keyup(function(){
            var texto = $(this).val();

            $("#search-expertises li").css("display", "block");
            $("#search-expertises li").each(function(){
                if($(this).text().toUpperCase().indexOf(texto.toUpperCase()) < 0)
                    $(this).css("display", "none");
            });
        });
    });

    $(".myclass").hover(function(e) {
        $(this).css("color",e.type === "mouseenter"?"red":"grey")
    })
</script>