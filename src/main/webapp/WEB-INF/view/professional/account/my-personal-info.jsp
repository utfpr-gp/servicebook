<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css" rel="stylesheet">
</head>

<t:template title="Minhas Informações Pessoais">
    <jsp:body>
        <main>
            <!-- Container -->
            <div class="container">
                <!-- Mensagens -->
                <t:message-box/>
                <!-- Fim Mensagens -->

                <!-- Formulário -->
                <div class="row">
                    <div class="col s12 l6 offset-l3">
                        <h3 class="secondary-color-text">Minhas Informações Pessoais</h3>
                    </div>
                    <form
                            id="personal-info-form"
                            class="col s12 l6 offset-l3"
                            action="${pageContext.request.contextPath}/minha-conta/cadastra-informacoes-pessoais/${userDTO.id}"
                            method="post"
                    >
                        <input type="hidden" name="_method" value="PATCH"/>
                        <div class="row">
                            <div class="input-field">
                                <input id="name" name="name" value="${userDTO.name}" class="validate" required disabled>
                                <label for="name" class="active">Atualize seu Nome:</label>
                            </div>
                        </div>

                        <sec:authorize access="hasRole('USER')">
                            <div class="row">
                                <div class="input-field">
                                    <input id="cpf" name="cpf" value="${userDTO.cpf}" class="validate" required disabled type="text">
                                    <label for="cpf" class="active">Atualize seu CPF:</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field">
                                    <input type="date" id="birthDate" name="birthDate" disabled class="validate" value="${userDTO.birthDate}">
                                    <label for="birthDate" class="active">Atualize sua data de nascimento:</label>
                                </div>
                            </div>
                        </sec:authorize>

                        <sec:authorize access="hasRole('COMPANY')">
                            <div class="row">
                                <div class="input-field">
                                    <input id="cnpj" name="cnpj" value="${userDTO.cnpj}" class="validate" required disabled type="text">
                                    <label for="cnpj" class="active">Atualize seu CNPJ:</label>
                                </div>
                            </div>
                        </sec:authorize>

                        <div class="right">
                            <button id="edit-button" class="waves-effect waves-light btn" type="button">Editar</button>
                            <button id="save-button" class="waves-effect waves-light btn" type="submit"
                                    style="display: none">Salvar
                            </button>
                        </div>
                    </form>
                </div>
                <!-- Fim Formulário -->
            </div>
            <!-- Fim Container -->
        </main>

    </jsp:body>
</t:template>
<script src="assets/libraries/jquery.mask.js"></script>
<script>
    $(document).ready(function () {
        $('#cpf').mask('000.000.000-00', {reverse: true});
        $('#cnpj').mask('00.000.000/0000-00', {reverse: true});

        $('#edit-button').click(function () {
            const disabledInputs = $("input");
            disabledInputs.prop("disabled", false);

            console.log($(this))

            $(this).hide();
            $('#save-button').show();
        });
    });
</script>