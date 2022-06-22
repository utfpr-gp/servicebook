<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Cadastro - Passo 8">
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
                        <h3 class="row center secondary-color-text">
                            Escolha uma especialidade!
                        </h3>
                        <h5 class="row center secondary-color-text">
                            Se você tem alguma habilidade e deseja receber solicitações para execução de serviços, então
                            nos informe suas especialidades.
                        </h5>

                        <div class="row">
                            <c:choose>
                                <c:when test="${empty professionalExpertises}">
                                    <div class="row center spacing-buttons">
                                        <div class="col s12 l4 offset-l4 spacing-buttons">
                                            <div class="none-profission">
                                                <h5 class="center">Nenhuma profissão foi selecionada!</h5>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="col s12">
                                        <div class="row center expertises">
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

                                                <div class="col s12 right">
                                                    <p class="center">
                                                        Descrição da especialidade
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <div class="center spacing-buttons">


                                <c:choose>
                                    <c:when test="${empty professionalExpertises}">
                                        <button  class="waves-effect waves-light btn">
                                            <a href="#modal-expertises" class="modal-trigger">
                                                Adicionar especialidade
                                            </a>
                                        </button>

                                    </c:when>
                                    <c:otherwise>
                                        <button  class="waves-effect waves-light btn">
                                            <a href="#modal-expertises" class="modal-trigger">
                                                Alterar especialidade
                                            </a>
                                        </button>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>

                            <div class="row">
                                <div class="col s6 m3 offset-m3 spacing-buttons">
                                    <div class="center">
                                        <a href="cadastrar-se?passo=7" class="waves-effect waves-light btn btn-gray">
                                            Voltar
                                        </a>
                                    </div>
                                </div>
                                <div class="col s6 m3 spacing-buttons">
                                    <form action="cadastrar-se/passo-9" method="post">
                                        <div class="center">
                                            <button type="submit" class="waves-effect waves-light btn">Fim</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                    </div>
                </div>
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
                        <form class="s12" id="form-expertises" action="cadastrar-se/passo-8" method="post">
                            <ul id="search-expertises">
                                <c:forEach var="expertise" items="${expertises}">
                                    <li>
                                        <label class='card-expertise col s12 m10 offset-m1'>
                                            <input id='ids' name='ids' type='radio' class='reset-checkbox' value="${expertise.id}">
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
        </main>

    </jsp:body>
</t:visitor>
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