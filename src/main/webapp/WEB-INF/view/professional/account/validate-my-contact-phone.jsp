<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css" rel="stylesheet">
</head>

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

                <!-- Validação do telefone -->
                <div class="row">
                    <div class="col s12 l6 offset-l3">
                        <h3 class="secondary-color-text">Vamos validar o seu telefone?</h3>
                        <h5 class="secondary-color-text">
                            Enviamos um código para o seu telefone. Por favor, digite o código para validar esse telefone.
                        </h5>
                    </div>

                    <!-- Formulário de verificação do telefone -->
                    <form class="col s12 l6 offset-l3" method="post" action="minha-conta/valida-telefone/${user.id}">
                        <input type="hidden" name="id" value="${user.id}"/>
                        <div class="input-field">
                            <input id="code" name="code" type="text" class="validate" required>
                            <label for="code">Código</label>
                        </div>

                        <div class="input-field center">
                            <button id="code-button" class="btn-flat" type="button">
                                Reenviar o código!
                            </button>
                        </div>

                        <div class="right">
                            <button class="waves-effect waves-light btn" type="submit">Salvar</button>
                        </div>
                    </form>
                    <!-- Fim do formulário de verificação do telefone -->
                </div>
                <!-- Fim da validação do telefone -->
            </div>
            <!-- Fim Container -->
        </main>

    </jsp:body>
</t:template>
<script>
    $('#code-button').click(function () {

        //solicita o envio do código de verificação
        $.get("minha-conta/reenvia-codigo-verificacao/${user.id}").done(function () {
            swal({
                title: "Sucesso!",
                text: "Foi enviado um SMS para ${user.phoneNumber}.",
                icon: "success",
            });
        });
    });
</script>

