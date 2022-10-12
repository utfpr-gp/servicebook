<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Início">
    <jsp:body>

        <t:banner cities="${cities}"></t:banner>

        <t:search-bar></t:search-bar>

        <div class="container">
            <div class="section">
                <div class="row">
                    <div class="col s12">
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
        </div>

    </jsp:body>
</t:visitor>
