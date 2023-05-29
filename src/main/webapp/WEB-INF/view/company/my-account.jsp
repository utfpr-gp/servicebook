<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="ServiceBook - Minha conta">
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
                            <h2 class="secondary-color-text">Anúncios de serviços</h2>
                        </div>
                        <div class="col s12">
                            <c:if test="${not empty msg}">
                                <div class="row">
                                    <div class="col s12 l4 offset-l4">
                                        <div class="card-panel green lighten-1 msg-view center-align">
                                            <span class="white-text">${msg}</span>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="row">
                                <div class="container center">
                                    <a href="minha-conta/empresa" class="waves-effect waves-light btn"><i class="material-icons right">sync</i>ATUALIZAR</a>
                                </div>

                                <ul class="tabs tabs-fixed-width center">
                                    <li class="tab">
                                        <a id="tab-default" data-url="minha-conta/empresa/disponiveis"
                                           href="#disponiveis" class="truncate">
                                            DISPONÍVEIS
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div id="disponiveis" class="col s12">
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>

<script>
    $(document).ready(function () {
        $('.tabs').tabs();

        $('#disponiveis').load($('.tab .active').attr("data-url"), function (result) {
            window.location.hash = "#disponiveis";
            $('#tab-default').click();
        });
    });

</script>
