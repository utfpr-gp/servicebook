<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Meu contato">
    <jsp:body>

        <main>
            <c:if test="${not empty msg}">
                <div class="container">
                    <div class="col s6">
                        <div class="card-panel green lighten-1 msg-view center-align">
                            <span class="white-text">${msg}</span>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty errors}">
                <div class="container">
                    <div class="col s6">
                        <div class="card-panel red lighten-1 msg-view center-align">
                            <span class="white-text">${errors}</span>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="row">
                <div class="container">
                    <div class="row">
                        <h3 class="center secondary-color-text">
                            Meu contato
                        </h3>
                    </div>

                    <form method="post" action="minha-conta/salvar-contato/${professional.id}">
                        <div class="row">
                            <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                <input id="phoneNumber" name="phoneNumber" type="text" value="${professional.phoneNumber}"
                                       class="validate" required>
                                <label for="phoneNumber">Telefone</label>
                            </div>
                        </div>

                        <div class="row col s6 offset-s6">
                            <div class="center">
                                <button type="submit" class="waves-effect waves-light btn">Salvar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <c:if test="${!professional.phoneVerified}">
                <div class="row">
                    <h3 class="center secondary-color-text">
                        Vamos validar seu numero?
                    </h3>
                    <h5 class="center secondary-color-text">
                        Enviamos um código para o seu telefone. Por favor, digite o código para validar esse telefone.
                    </h5>

                    <form method="post" action="minha-conta/validar-telefone/${professional.id}">
                        <div class="row">
                            <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                <input id="code" name="code" type="text" class="validate" required>
                                <label for="code">Código</label>
                            </div>
                        </div>
                        <div class="row col s12">
                            <div class="sendMessage col s6 center">
                                <button class="waves-effect waves-light btn" type="button">
                                    Reenviar mensagem
                                </button>
                            </div>
                            <div class="center col s6">
                                <button class="waves-effect waves-light btn" type="submit">Salvar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </c:if>
        </main>

    </jsp:body>
</t:template>
<script>
    $('.sendEmail').click(function () {
        $.post("minha-conta/salvar-contato/${professional.id}", {phoneNumber: "${professional.phoneNumber}"}).done(function () {
            swal({
                title: "Deu certo!",
                text: "Foi enviado um sms para ${professional.phoneNumber}.",
                icon: "success",
            });
        });
    });

    $(document).ready(function() {
        $('#phoneNumber').mask('(00) 00000-0000');
    });

</script>

