<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="ServiceBook - Adicionar profissional">
    <jsp:body>
        <main class="container">
            <div class="row">
                <t:side-panel followdto="${followdto}" ></t:side-panel>
                    <div class="col m10 offset-m1 l9">
                        <a id="show-area-perfil"
                           class="hide-on-large-only show-area-perfil waves-effect waves-light btn btn-floating grey darken-3 z-depth-A">
                            <i class="material-icons">compare_arrows</i>
                        </a>

                        <div class="row">
                            <div class="col s12">
                                <h2 class="secondary-color-text">Todos os Funcionários</h2>
                                <blockquote class="light-blue lighten-5 info-headers">
                                    <p> Adicione novos funcionários em sua empresa.</p>
                                </blockquote>
                            </div>
                            <div class="center spacing-buttons">
                                <button  class="waves-effect waves-light btn">
                                    <a href="#modal-professionals" class="modal-trigger">
                                        <i class="material-icons" style="font-size: .8rem">add</i>
                                        Adicionar novo funcionário
                                    </a>
                                </button>
                            </div>


                            <div id="modal-delete" class="modal">
                                <div class="modal-content">
                                    <form action="" method="POST">
                                        <input type="hidden" name="_method" value="DELETE"/>

                                        <div class="modal-content">
                                            <h4>Você tem certeza que deseja remover o profissional da lista de funcionários?</h4>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                                            <button type="submit" class="modal-close btn waves-effect waves-light gray remove">Sim</button>
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <c:forEach var="professional" items="${professionalExpertises}">
                            <div class="col s12">
                                    <div class="card">
                                        <div class="card-infos row">
                                            <div class="col s2 img-professional-company">

                                                <c:if test="${professional.profilePicture == null}">
                                                    <img src="https://assets.website-files.com/5e51c674258ffe10d286d30a/5e5355ed4600809f5a8dad51_peep-37.svg"
                                                         alt="Profissional - Imagem de perfil."
                                                         style="width:100px;height:100px">
                                                </c:if>

                                                <c:if test="${professional.profilePicture != null}">
                                                    <div class="row">
                                                        <img src="${professional.profilePicture}" alt="Profissional - Imagem de perfil."
                                                             style="width:100px;height:100px;border-radius: 15px; margin-left: 10px">
                                                    </div>
                                                </c:if>

                                            </div>

                                            <div class="col s5" style="text-align: left">
                                                <p class="name-professional-company">${professional.name}</p>
                                                <span class="email-professional-company">${professional.email}</span>
                                            </div>

                                            <div class="col s5 button-remove-professional-company">
                                                <a href="#modal-delete" id="delete-exerpertise-professional" class="myclass waves-effect waves-teal btn-flat delete-exerpertise-professional modal-trigger"
                                                   data-url="${pageContext.request.contextPath}/minha-conta/empresa/profissionais/${professional.id}"
                                                   data-name="${professional.name}"><i class="myclass material-icons">delete</i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <div id="modal-professionals" class="modal">
                                <div class="modal-content ui-front">
                                    <div class="row">
                                        <div class="col s9">
                                            <h4>Adicionar Funcionário</h4>
                                        </div>
                                        <div class="col s3">
                                            <button class="modal-close modal-expertise-close right">
                                                <i class="material-icons">close</i>
                                            </button>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col s12">
                                            <label style="font-size: 1rem">Para adicionar um novo funcionário, é necessário que ele possua cadastro na plataforma.</label>
                                        </div>
                                        <div class="col s12" style="margin-top: 20px">
                                            <form class="s12" id="form-professionals-company" action="minha-conta/empresa/profissionais" method="post">
                                                <select id="single" class="js-example-responsive" style="width: 100%" name='ids'>
                                                    <c:forEach var="profissional" items="${professionals}">
                                                        <option></option>
                                                        <option value="${profissional.id}"> ${profissional.name}</option>
                                                    </c:forEach>
                                                </select>

                                                <div class="input-field col s8 offset-s1" style="margin-top: 12%">
                                                    <button id="submit-professional-company" type="submit" class="btn waves-effect waves-light left">Salvar</button>
                                                </div>
                                                <div class="input-field col s3" style="margin-top: 12%">
                                                    <a class="btn waves-effect waves-light modal-close">Fechar</a>
                                                </div>
                                            </form>
                                        </div>

                                        <div class="col s12" style="margin-top: 10px">
                                            <div class="row">
                                                <div class="input-field col s12">
                                                    <i class="material-icons prefix" style="margin: 3px 0 0">link</i>
                                                    <label for="getCopyLink">
                                                        Convidar funcionário para fazer parte do ServiceBook.
                                                        <a id="getCopyLink" class="waves-effect waves-light" style="font-size: 1rem">
                                                            <i class="material-icons left" style="font-size: 1rem; margin: 3px 0 0">content_copy</i>
                                                            Copiar Link
                                                        </a>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
            </div>
        </main>

    </jsp:body>
</t:template>

<style>
    select{ display: block !important; } input.select-dropdown.dropdown-trigger{ display: none; !important; }
</style>
<script>

    $("#single").select2({
        placeholder: "Nome do profissional",
        allowClear: true,
        width: 'resolve',
        dropdownParent: $('#modal-professionals'),
    });

    $('#getCopyLink').on('click', function() {
        const link = "http://localhost:8080${pageContext.request.contextPath}/cadastrar-se";
        navigator.clipboard.writeText(link);
        $("#getCopyLink").text('Link copiado');
    })

    $('a.remove').click(function(e) {
        e.preventDefault();
        var form = $(this).next('form');
        form.submit();
        window.location.href = '/users';
    });

</script>