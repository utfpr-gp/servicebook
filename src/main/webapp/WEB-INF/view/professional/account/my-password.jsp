<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css" rel="stylesheet">
</head>

<t:template title="Minha senha">
    <jsp:body>
        <main>
            <!-- Container -->
            <div class="container">

                <!-- Mensagens -->
                <div class="row">
                    <div class="col s12 l6 offset-l3 spacing-buttons">
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

                <!-- Formulário -->
                <div class="row">
                    <div class="col s12 l6 offset-l3">
                        <h3 class="secondary-color-text">Minha senha</h3>
                    </div>
                    <form id="password-form" class="col s12 l6 offset-l3" action="minha-conta/edita-senha/${user.id}" method="post">
                        <input type="hidden" name="_method" value="PATCH"/>
                        <input type="hidden" name="id" value="${user.id}"/>

                        <div class="input-field">
                            <input id="password" name="password" type="password" value=""
                                   class="validate" required disabled>
                            <label for="password">Nova senha</label>
                        </div>

                        <div class="input-field">
                            <input id="repassword" name="repassword" type="password" value=""
                                   class="validate" required disabled>
                            <label for="repassword">Repetir nova senha</label>
                        </div>

                        <div class="right">
                            <button id="edit-button" class="waves-effect waves-light btn" type="button">Editar</button>
                            <button id="save-button" class="waves-effect waves-light btn" type="submit" style="display: none">Salvar</button>
                        </div>
                    </form>
                </div>
                <!-- Fim Formulário -->
            </div>
            <!-- Fim Container -->
        </main>

    </jsp:body>
</t:template>
<script>
    $(document).ready(function() {
        $('#edit-button').click(function(){
            console.log($(this))
            $('#password-form input').prop('disabled', false);
            $(this).hide();
            $('#save-button').show();
        });
    });
</script>