<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/ads.css"
          rel="stylesheet">
</head>

<t:template-side-nav title="ServiceBook - Novo Anúncio">
    <jsp:body>
        <main>
            <div class="">
                <div class="section">
                    <div class="row">

                        <t:message-box/>

                        <div class="col s12">
                            <h3 class="secondary-color-text">Anunciar Serviços</h3>
                        </div>
                        <blockquote class="light-blue lighten-5 info-headers">
                            <p>
                                <strong>Serviço Individual</strong>, você adiciona um anúncio simples, podendo alterar o titulo do anúncio caso ache necessário.
                            </p>
                            <p>
                                <strong> Combinação de Serviços</strong>, você pode adicionar mais de um serviço no anúncio, desde que todos estejam ativados para anúncio,
                                como por exemplo <i> balanceamento + geometria </i>.
                            </p>
                            <p>
                                <strong> Pacote de Serviços</strong>, você pode adicionar uma quantidade do mesmo produto, como por exemplo <i>  5 sessões de massagens </i>.
                            </p>
                        </blockquote>
                        <!-- Formulário de adição de especialidade -->
                        <div class="row">
                            <div class="input-field col s6">
                                <p for="service-type" class="label-ads">Qual o tipo do serviço?</p>
                                <select id="service-type" name="type">
                                    <option disabled selected>Qual o tipo do serviço?</option>
                                    <option value="INDIVIDUAL">Serviço Individual</option>
                                    <option value="COMBINED_PACKAGE">Combinação de Serviços</option>
                                    <option value="SIMPLE_PACKAGE">Pacote de Serviços</option>
                                </select>
                            </div>

                            <div class="col s12" id="type-package" style="display: none">
                                <jsp:include page="my-ads-register-package.jsp"/>
                            </div>
                            <div class="col s12" id="type-combined" style="display: none">
                                <jsp:include page="my-ads-register-combined.jsp"/>
                            </div>
                        </div>
                        <div class="col s12" id="type-individual">
                            <form action="minha-conta/profissional/meus-anuncios/novo/individual"
                                  method="post">

                                <div class="row">

                                    <div class="input-field col s6">
                                        <p for="ads-service" class="label-ads">Qual o serviço? </p>
                                        <select id="service-select-individual" class="service-select" name="serviceId">
                                            <option disabled selected>Selecione um serviço</option>
                                            <c:forEach var="service" items="${services}">
                                                <option value="${service.id}">${service.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="input-field col s12">
                                        <p for="ads-name"  class="label-ads">Titulo </p>
                                        <input id="ads-name-individual" class="ads-name" type="text" name="" disabled onblur="nameService()"/>
                                        <input id="name-service-individual" class="name-service" type="hidden" name="description"/>
                                        <p>
                                            <label>
                                                <input type="checkbox" id="sobrescrever" class="sobrescrever"/>
                                                <span>Sobrescrever</span>
                                            </label>
                                        </p>
                                    </div>

                                    <div class="input-field col s6">
                                        <p for="ads-uni" class="label-ads">Qual a unidade de preço do serviço? </p>
                                        <select id="ads-uni" name="unit">
                                            <option disabled selected>Selecione</option>
                                            <option>Hora</option>
                                            <option>Metro Quadrado</option>
                                            <option>Unidade</option>
                                        </select>
                                    </div>

                                    <div class="input-field col s6">
                                        <p for="ads-price" class="label-ads">Quanto você cobra por este serviço? </p>
                                        <input id="ads-price-individual" class="ads-price" type="text" name="" onblur="return RemoveMaskIndividual(event)"/>
                                        <input id="price-service-individual" class="price-service" type="hidden" name="price"/>
                                    </div>

                                    <div class="input-field col s6">
                                        <p class="label-ads">Qual a Duração do serviço?</p>
                                        <select id="ads-duracao" name="duration">
                                            <option disabled selected>Selecione</option>
                                            <option>Sem Agendamento</option>
                                            <option>1 Hora</option>
                                            <option>1 Hora e meia</option>
                                            <option>2 Horas</option>
                                            <option>3 Horas</option>
                                            <option>Um período do dia</option>
                                            <option>O dia inteiro</option>
                                        </select>
                                    </div>

                                </div>
                                <div class="right">
                                    <a href="minha-conta/profissional/meus-anuncios" class="btn-flat">Cancelar</a>
                                    <button type="submit"
                                            class="btn waves-effect waves-light">Salvar
                                    </button>
                                </div>

                            </form>
                        </div>
                        <!-- Fim do formulário de adição de especialidade -->
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template-side-nav>

<script>
    function nameService() {
        str = $("#ads-name-individual").val();
        $("#name-service-individual").val(str);
    }
</script>