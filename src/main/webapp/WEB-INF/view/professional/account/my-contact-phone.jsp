<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Meu contato">
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
                        <h3 class="secondary-color-text">Meu contato</h3>
                    </div>
                    <form id="phone-form" class="col s12 l6 offset-l3" action="minha-conta/edita-telefone/${user.id}" method="post">
                        <input type="hidden" name="_method" value="PATCH"/>
                        <input type="hidden" name="id" value="${user.id}"/>

                        <div class="input-field">
                            <input id="phoneNumber" name="phoneNumber" type="text" value="${user.phoneNumber}"
                                   class="validate" required disabled>
                            <label for="phoneNumber">Telefone</label>
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
        $('#phoneNumber').mask('(00) 00000-0000');
        $('#edit-button').click(function(){
            console.log($(this))
            $('#phone-form input').prop('disabled', false);
            $(this).hide();
            $('#save-button').show();
        });
    });
</script>

