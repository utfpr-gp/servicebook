<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template-side-nav title="ServiceBook - Minha conta">
    <jsp:body>
        <main class="">
            <div class="row">
                <div class="col s12">
                    <h2 class="secondary-color-text">Anúncios de serviços</h2>
                </div>
                <div class="col s12">
                    <t:message-box/>
                </div>
                <div class="col s12">
                    <div class="row">
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
        </main>
    </jsp:body>
</t:template-side-nav>

<script>
    $(document).ready(function () {
        $('.tabs').tabs();

        $('#disponiveis').load($('.tab .active').attr("data-url"), function (result) {
            window.location.hash = "#disponiveis";
            $('#tab-default').click();
        });
    });

</script>
