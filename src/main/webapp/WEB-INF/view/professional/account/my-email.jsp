<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:professional title="Meu email">
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

            <c:if test="${not empty msgError}">
                <div class="container">
                    <div class="col s6">
                        <div class="card-panel red lighten-1 msg-view center-align">
                            <span class="white-text">${msgError}</span>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="row">
                <div class="container">
                    <div class="row">
                        <h3 class="center secondary-color-text">
                            Email
                        </h3>
                        <h5 class="center secondary-color-text">
                            O seu email será utilizado para você acessar o sistema e receber informações sobre os
                            serviços!
                        </h5>
                    </div>

                    <form method="post" action="minha-conta/salvar-email/${professional.id}">
                        <div class="row">
                            <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                <input id="email" name="email" type="text" value="${professional.email}"
                                       class="validate" required>
                                <label for="email">Email</label>
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

            <c:if test="${!professional.emailVerified}">
                <div class="row">
                    <h3 class="center secondary-color-text">
                        Vamos validar o seu email?
                    </h3>
                    <h5 class="center secondary-color-text">
                        Enviamos um código para o seu email. Por favor, digite o código para validar este endereço
                        de email.
                    </h5>

                    <form method="post" action="minha-conta/validar-email/${professional.id}">
                        <div class="row">
                            <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                <input id="code" name="code" type="text" class="validate" required>
                                <label for="code">Código</label>
                            </div>
                        </div>
                        <div class="row col s12">
                            <div class="sendEmail col s6 center">
                                <button class="waves-effect waves-light btn" type="button">
                                    Reenviar email
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
</t:professional>
<script>
    $('.sendEmail').click(function () {
        $.post("minha-conta/salvar-email/${professional.id}", {email: "${professional.email}"}).done(function () {
            console.log('foi no evento');
            swal({
                title: "Deu certo!",
                text: "Foi enviado um email para ${professional.email}.",
                icon: "success",
            });
        });
    });
</script>
