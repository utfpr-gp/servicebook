<%@tag description="Servicebook - Banner template" pageEncoding="UTF-8" %>
<%@attribute name="cities" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="message" required="false" %>
<c:set var="message" value='${empty message ? "Não há resultados" : message}' />
<style>
    .empty-icon {
        text-align: center;
        margin-top: 50px;
    }
    .empty-message {
        text-align: center;
        font-size: 2em;
    }
</style>

<div class="empty-icon">
    <i class="material-icons large">sentiment_dissatisfied</i>
</div>
<div class="empty-message">
    <p>${message}</p>
</div>