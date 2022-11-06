<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Cadastro - Passo 2">
    <jsp:body>

        <main>
            <div class="container">
                <c:if test="${not empty errors}">
                    <div class="card-panel red">
                        <c:forEach var="e" items="${errors}">
                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                        </c:forEach>
                    </div>
                </c:if>

                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">
                            Vamos validar o seu email?
                        </h3>
                        <h5 class="center secondary-color-text">
                            Enviamos um código para o seu email. Por favor, digite o código para validar este endereço
                            de email.
                        </h5>

                        <form method="post" action="cadastrar-se/passo-2">
                            <div class="row">
                                <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                    <input id="code" name="code" type="text" class="validate">
                                    <label for="code">Código</label>
                                </div>
                            </div>
                            <div class="row center spacing-buttons">
                                <div class="col s6 offset-s3">
                                    <div class="center">
                                        <a href="cadastrar-se?passo=3" class="waves-effect waves-light btn">
                                            Fazer Depois
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="col s6 m3 offset-m3 spacing-buttons">
                                <div class="center">
                                    <a href="cadastrar-se?passo=1"
                                       class="waves-effect waves-light btn btn-gray">Voltar</a>
                                </div>
                            </div>
                            <div class="col s6 m3 spacing-buttons">
                                <div class="center">
                                    <button class="waves-effect waves-light btn">Próximo</button>
                                </div>
                            </div>
                        </form>

                        <div class="row center spacing-buttons">
                            <div class="col s6 offset-s3">
                                <div class="sendEmail">
                                    <button class="waves-effect waves-light btn">
                                        Reenviar email
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:visitor>

<script>
    $('.sendEmail').click(function (){
        $.post("cadastrar-se/passo-1", { email:"${dto.email}"}).done(function(){
            swal({
                title: "Deu certo!",
                text: "Foi enviado um email para ${dto.email}.",
                icon: "success",
            });
        });
    });
</script>
