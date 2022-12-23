<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Servicebook - Início">
    <jsp:body>
        <main>
        <t:banner cities="${cities}"></t:banner>

        <t:search-bar></t:search-bar>

        <div id="panel-heading" class="container">
            <div class="row">
                <div class="col s12">

                </div>
            </div>
        </div>
        </main>
    </jsp:body>
</t:template>
