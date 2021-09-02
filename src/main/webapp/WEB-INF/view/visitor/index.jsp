<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Início">
    <jsp:body>

        <div id="index-banner" class="parallax-container">
            <div class="section no-pad-bot">
                <div class="container">
                    <br><br>
                    <h3 class="header center logo-text logo-text-parallax">ServiceBook</h3>
                    <c:if test="${cities.size() > 1}">
                        <div class="row center">
                            <div class="col s10 offset-s1 m4 offset-m4  input-field">
                                <select id="select-city" class="white-text select-city">
                                    <c:forEach var="city" items="${cities}">
                                        <option value="${city.image}">${city.name} - ${city.state.uf}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </c:if>
                    <h4 class="header center">O MELHOR PROFISSIONAL QUE VOCÊ PRECISA ESTÁ AQUI!</h4>
                    <h4 class="header center">São + de 3000 profissionais cadastrados!</h4>
                </div>
            </div>
            <div class="parallax">
                <c:if test="${not empty cities}">
                    <img id="img-city" src="${cities[0].image}" alt="Imagem das cidades">
                </c:if>
                <c:if test="${empty cities}">
                    <img id="img-city" src="assets/resources/images/default.jpeg" alt="Imagem das cidades">
                </c:if>
            </div>
        </div>

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
