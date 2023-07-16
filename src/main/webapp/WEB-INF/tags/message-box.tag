<%@tag description="Servicebook - Banner template" pageEncoding="UTF-8" %>
<%@attribute name="cities" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="msg" required="false" %>
<%@ attribute name="msgError" required="false" %>
<%@ attribute name="errors" required="false" %>

<!-- Mensagens -->
<div class="row">
    <div class="col s12">
        <c:if test="${not empty msg}">
            <div class="card-panel green lighten-1 msg-view center-align">
                <span class="white-text">${msg}</span>
            </div>
        </c:if>
        <c:if test="${not empty msgError}">
            <div class="card-panel red msg-view center-align">
                <span class="white-text">${msgError}</span>
            </div>
        </c:if>
        <c:if test="${not empty errors}">
            <div class="card-panel red msg-view center-align">
                <c:forEach var="e" items="${errors}">
                    <span class="white-text">${e.getDefaultMessage()}</span><br>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
<!-- Fim Mensagens -->