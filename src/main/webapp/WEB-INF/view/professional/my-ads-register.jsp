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
                            <h3 class="secondary-color-text">Anúnciar Serviço</h3>
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
                        <div class="col s12">
                            <form action="minha-conta/profissional/meus-anuncios/novo"
                                  method="post">

                                <div class="row">
                                    <div class="input-field col s6">
                                        <p for="service-type" class="label-ads">Qual o tipo do serviço?</p>
                                        <select id="service-type" name="id">
                                            <option disabled selected>Qual o tipo do serviço?</option>
                                            <option value="INDIVIDUAL">Serviço Individual</option>
                                            <option value="COMBINED_PACKAGE">Combinação de Serviços</option>
                                            <option value="SIMPLE_PACKAGE">Pacote de Serviços</option>
                                        </select>
                                    </div>

                                    <div class="col s12"></div>

<%--                                    <div class="input-field col s6">--%>
<%--                                        <p for="ads-expertise" class="label-ads">Qual a especialidade? </p>--%>
<%--                                        <select id="expertise-select" name="expertiseId" value="${expertise.id}">--%>
<%--                                            <option disabled selected>Selecione uma especialidade</option>--%>
<%--                                            <c:forEach var="e" items="${expertises}">--%>
<%--                                                <option value="${e.id}" ${e.id eq expertise.id ? 'selected="selected"' : ''}>${e.name}</option>--%>
<%--                                            </c:forEach>--%>
<%--                                        </select>--%>
<%--                                    </div> --%>

                                    <div class="input-field col s6">
                                        <p for="ads-service" class="label-ads">Qual o serviço? </p>
                                        <select id="service-select" name="id">
                                            <option disabled selected>Selecione um serviço</option>
                                            <c:forEach var="service" items="${services}">
                                                <option value="${service.id}">${service.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="input-field col s12">
                                        <p for="ads-name"  class="label-ads">Titulo </p>
                                        <input id="ads-name" type="text" name="ads-name"/>
                                        <p>
                                        <label>
                                            <input type="checkbox" />
                                            <span>Sobrescrever</span>
                                        </label>
                                        </p>

                                    </div>

                                    <div class="input-field col s6">
                                        <p for="ads-uni" class="label-ads">Qual a unidade de preço do serviço/ </p>
                                        <select id="ads-uni" name="ads-uni">
                                            <option disabled selected>Selecione</option>
                                            <option>Hora</option>
                                            <option>Metro Quadrado</option>
                                            <option>Unidade</option>
                                        </select>
                                    </div>

                                    <div class="input-field col s6">
                                        <p for="ads-price" class="label-ads">Quanto você cobra por este serviço? </p>
                                        <input id="ads-price" type="text" name="ads-price"/>
                                    </div>

                                    <div class="input-field col s6" style="display: none" id="show-service-package">
                                        <p for="ads-qtde" class="label-ads">Qual a quantidade para o pacote? </p>
                                        <input id="ads-qtde" type="number" name="ads-qtde"/>
                                    </div>

                                    <div class="input-field col s6">
                                        <p class="label-ads">Qual a Duração do serviço?</p>
                                        <select id="ads-duracao" name="ads-uni">
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

    $(function () {
        $('#ads-price').mask('000.000.000.000.000,00', {reverse: true});

        //inicializa os campos nome e descrição com os dados do serviço selecionado
        $('#service-select').change(function () {
            let serviceId = $(this).val();
            let url = 'servicos/' + serviceId;

            $.get(url, function (data) {
                $('#name-input').val(data.name);
                $('#description-textarea').val(data.description);
                $('#description-blockquote').show();
            });
        });

        //inicializa o select de serviços com os serviços da especialidade selecionada
        $('#expertise-select').change(function () {
            let expertiseId = $(this).val();
            let url = 'minha-conta/profissional/especialidades/' + expertiseId + '/servicos';

            $.get(url, function (data) {
                let options = '<option disabled selected>Selecione um serviço</option>';
                data.forEach(function (service) {
                    options += '<option value="' + service.id + '">' + service.name + '</option>';
                });
                $('#service-select').html(options);
                $('#service-select').formSelect();
            });
        });
    });

    $('#service-type').change(function () {
        let type = $(this).val();
        if(type == 'SIMPLE_PACKAGE'){
            $('#show-service-package').show();
        } else {
            $('#show-service-package').hide();


        }

        console.log($(this).val())

    //     show-service-package
    });
</script>