<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Servicebook - Início">
    <jsp:body>
        <main>
                <%--            <t:banner cities="${cities}"></t:banner>--%>
            <div id="panel-heading">
                <div class="row">
                    <div class="col s12" id="painel">
                        <div class="city-name" onclick="toggleCityPanel()">
                            <i id="iconeSeta" class="material-icons">keyboard_arrow_up</i>
                            <span>${cities[0].name}</span>
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

<script>
    function toggleCityPanel() {
        var cityPanel = document.getElementById('city-panel');
        var cityName = document.querySelector('.city-name');
        var setaPraCima = document.getElementById('iconeSeta');

        if (cityPanel.style.display === 'none') {
            cityPanel.style.display = 'block';
            setaPraCima.classList.add('expanded');
        } else {
            cityPanel.style.display = 'none';
            setaPraCima.classList.remove('expanded');
        }
    }
</script>

<style>
    .city-name {
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 10px;
        background-color: #f5f5f5;
    }

    .expanded {
        transform: rotate(180deg);
    }

    #city-panel {
        display: none;
    }

    #painel {
        padding: 0;
    }

</style>
