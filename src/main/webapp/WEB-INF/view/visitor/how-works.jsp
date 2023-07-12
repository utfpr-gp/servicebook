<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/visitor/visitor.css" rel="stylesheet">
</head>

<t:template title="Como Funciona?">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Como Funciona?</h3>

                        <h4 class="center">Bora contratar um profissional para realizar o serviço!</h4>
                        <blockquote class="itens-list">
                            <p>Descreva o serviço que precisa;</p>
                            <p>Informe para quando precisa que o serviço seja realizado;</p>
                            <p>Defina o o número máximo de contatos de profissionais que deseja receber;</p>
                            <p>Receba uma lista de contatos dos profissionais interessados em realizar o serviço;</p>
                            <p>
                                Entre em contato com um ou mais destes profissionais para explicar melhor o serviço e
                                saber do orçamento;
                            </p>
                            <p>
                                Marque na lista o profissional escolhido para o serviço para que você possa avaliar o
                                serviço posteriorment;
                            </p>
                            <p>Pronto!</p>
                            <p>O pagamento do serviço é realizado diretamente ao profissional!</p>
                        </blockquote>
                        <div class="center">
                            <a class="waves-effect waves-light btn" href="requisicoes">Começar</a>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>

