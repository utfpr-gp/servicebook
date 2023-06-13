<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Servicebook - Início">
    <jsp:body>
        <main>
            <div id="panel-heading">
                <div class="row">
                    <div class="col s12" id="painel">
                        <div class="city-name">
                            <div class="container cityNameContent">
                                <span id="selectCityName">${cities[0].name} - ${cities[0].state.uf}</span>
                                <span id="togleCityId">Alterar a cidade</span>
                            </div>
                        </div>
                        <div id="city-panel">
                            <t:banner cities="${cities}"></t:banner>
                        </div>
                    </div>
                </div>
            </div>
            <t:search-bar></t:search-bar>
        </main>
    </jsp:body>
</t:template>

<script></script>