<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:template title="Vagas de Emprego">
    <jsp:body>
        <!-- Lista de vagas de emprego -->
        <div class="row">
            <div class="col s12 l6 offset-l3">
                <h3 class="secondary-color-text">Vagas de Emprego</h3>
            </div>
            <div class="col s12 l6 offset-l3 spacing-buttons">
                <c:if test="${not empty jobs}">
                    <table class="striped centered">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Título da vaga</th>
                            <th>Descrição</th>
                            <th>Salário</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="p" items="${jobs}">
                            <tr>
                                <td>${p.id}</td>
                                <td>${p.title}</td>
                                <td>${p.description}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.salary != null}">
                                            <fmt:formatNumber value="${p.salary / 100}" type="currency" currencySymbol="R$"/>
                                        </c:when>
                                        <c:otherwise>À combinar</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <div class="center"><t:pagination pagination="${pagination}" relativePath="/c/vagas-de-emprego"></t:pagination></div>
            </div>
        </div>

    </jsp:body>
</t:template>
<script>
    $(document).ready(function() {

    });

    function getImageUrl(searchParam){
        a = $.getJSON( `http://ajax.googleapis.com/ajax/services/search/images?v=2.0&hl=pt-br&cr=countryBR&q=${searchParam}`)
            .done(function( json ) {
                b = jQuery.parseJSON(a.responseText)
                return b.responseData.results > 1 ? b.responseData.results[0].unescapedUrl : "https://turbologo.com/articles/wp-content/uploads/2019/05/no-logo.png";
            })
            .fail(function( jqxhr, textStatus, error ) {
                console.log('Deu ruim')
            });
    }


</script>
