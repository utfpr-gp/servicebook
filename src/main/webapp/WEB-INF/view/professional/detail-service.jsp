<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:professional title="Detalhes do serviço">
    <jsp:body>

        <main>
            <div class="row primary-background-color">
                <div class="carousel carousel-slider center">
                    <div class="carousel-fixed-item center">
                        <div class="left">
                            <a href="Previo" class="movePrevCarousel middle-indicator-text waves-effect waves-light content-indicator"><i class="material-icons left  middle-indicator-text">chevron_left</i></a>
                        </div>

                        <div class="right">
                            <a href="Siguiente" class=" moveNextCarousel middle-indicator-text waves-effect waves-light content-indicator"><i class="material-icons right middle-indicator-text">chevron_right</i></a>
                        </div>
                    </div>
                    <div class="carousel-item blue white-text" href="#one!">
                        <h2>01</h2>
                    </div>
                    <div class="carousel-item amber white-text" href="#two!">
                        <h2>02</h2>
                    </div>
                    <div class="carousel-item green white-text" href="#three!">
                        <h2>03</h2>
                    </div>
                    <div class="carousel-item red white-text" href="#four!">
                        <h2>04</h2>
                    </div>
                    <div class="carousel-item blue white-text" href="#one!">
                        <h2>05</h2>
                    </div>
                    <div class="carousel-item amber white-text" href="#two!">
                        <h2>06</h2>
                    </div>
                    <div class="carousel-item green white-text" href="#three!">
                        <h2>07</h2>
                    </div>
                    <div class="carousel-item blue white-text" href="#four!">
                        <h2>08</h2>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="container">
                    <div class="progress-bar">
                        <div class="progress">
                            <div class="determinate" style="width: ${percentCandidatesApplied}%"></div>
                        </div>
                        <p>${candidatesApplied}/${maxCandidates}</p>
                    </div>
                    <div class="row">
                        <div class="row">
                            <div class="col s12 m6 left area-info-request-client-left">
                                <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">person</i> ${job.expertise.name}</p>
                                <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">access_time</i>${job.textualDate}</p>
                                <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">location_on</i> ${job.individual.address.street}, ${job.individual.address.neighborhood}, ${job.individual.address.number}, ${city} - ${state}</p>
                            </div>
                            <div class="col s12 m6 left area-info-request-client-right">
                               <div class="row">
                                   <div class="col s12 m7">
                                       <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">person</i>${client.name}</p>
                                       <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">phone</i> ${client.phoneNumber}</p>
                                       <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">mail</i>${client.email}</p>
                                   </div>
                                   <div class="col s6 offset-s3 m5 area-foto center">
                                       <img src="${client.profilePicture}" width="150px" height="150px" alt="${client.name}">
                                   </div>
                               </div>
                            </div>

                        </div>

                        <c:if test="${isJobToHired}">
                            <div class="row">
                                <div class="col s12">
                                    <p class="text-area-info-cli primary-color-text">Confirmação do serviço:</p>
                                </div>

                                <form id="hiredForm" method="post">
                                    <label>
                                        <input id="confirmHired" name="chosenByBudget" type="radio" value="true"/>
                                        <span>Confirmo a realização do serviço</span>
                                    </label>

                                    <label>
                                        <input id="notConfirmHired" name="chosenByBudget" type="radio" value="false"/>
                                        <span>Não poderei realizar o serviço</span>
                                    </label>

                                    <div class="row" id="confirmHiredForm" style="display: none; margin-top: 15px">
                                        <p>Adicione abaixo a data para a realização do serviço e confirme sua ação:</p>
                                        <input class="col s6" type="date" id="hiredDateFormConfirm" name="hiredDate">
                                    </div>

                                    <div class="row">
                                        <a id="hiredFormButton" href="#modal-confirm-hired" data-url="${pageContext.request.contextPath}/candidaturas/contratacao/${job.id}" class="waves-effect waves-light btn spacing-buttons modal-trigger">Contratação</a>
                                    </div>
                                </form>

                                <div id="modal-confirm-hired" class="modal">
                                    <div class="modal-content">
                                        <form action="" method="post">
                                            <input type="hidden" name="_method" value="POST"/>
                                            <input id="chosenByBudget" name="chosenByBudget" type="hidden" value=""/>
                                            <input class="col s6" type="date" id="hiredDateFormConfirmModal" name="hiredDate" hidden>

                                            <div class="modal-content" id="modal-content-confirm-hired" style="display: none">
                                                <h5>Você tem certeza que deseja confirmar a contratação desse serviço?</h5>

                                                <p class="no-margin"><strong>Descrição do serviço:</strong> ${job.description}</p>
                                                <p class="no-margin"><strong>Cliente:</strong> ${client.name}</p>
                                                <p class="no-margin"><strong>Data de realização:</strong> <span id="date-confirmed-span"></span></p>
                                            </div>

                                            <div class="modal-content" id="modal-content-not-confirm-hired" style="display: none">
                                                <h4>Você tem certeza que deseja desistir da contratação desse serviço?</h4>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                                                <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <div class="row">
                            <c:if test="${hasHiredDate}">
                                <div class="col s12">
                                    <p class="text-area-info-cli primary-color-text">Data de realização do serviço: ${jobCandidateHiredDate}</p>
                                </div>
                            </c:if>

                            <div class="col s12">
                                <p class="text-area-info-cli primary-color-text">Descrição do serviço: ${job.description}</p>
                            </div>
                            <div class="col s12">
                                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d637.164143085935!2d-51.450982613492!3d-25.39327015419766!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x94ef3618cba4dc95%3A0x6ad7b00212a4f63b!2sPar%C3%B3quia%20Santana!5e0!3m2!1spt-BR!2sbr!4v1620666186774!5m2!1spt-BR!2sbr" width="100%" height="350" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
                            </div>
                            <div class="col s6 m6 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn  pink darken-1" href="#!">Excluir</a>
                                </div>
                            </div>
                            <c:if test="${isAvailableJobRequest}">
                                <form action="candidaturas" method="post">
                                    <input name="id" type="hidden" value="${job.id}">
                                    <div class="col s6 m6 spacing-buttons">
                                        <div class="center">
                                            <button class="waves-effect waves-light btn">Quero me candidatar</button>
                                        </div>
                                    </div>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:professional>

<script>
    $(document).ready(function() {
        $('.modal').modal({
            onOpenEnd: function (modal, trigger){
                var url = $(trigger).data('url');
                var name = $(trigger).data('name');

                modal = $(modal);
                var form = modal.find('form');
                form.attr('action', url);
                modal.find('#strong-name').text(name);
            }

        });

        $('#hiredFormButton').attr("disabled", "disabled");

        $('#confirmHired').click(function () {
            $('#hiredFormButton').removeAttr("disabled");
            $("#confirmHiredForm").show();
        });

        $('#notConfirmHired').click(function () {
            $("#hiredDateFormConfirm").val(null);
            $('#hiredFormButton').removeAttr("disabled");
            $("#confirmHiredForm").hide();
        });

        $('#hiredFormButton').click(function () {
            if ($("#confirmHired").is(":checked")) {
                $("#date-confirmed-span").text($("#hiredDateFormConfirm").val());
                $("#modal-content-confirm-hired").show();
                $("#modal-content-not-confirm-hired").hide();

                let date = $("#hiredDateFormConfirm").val();
                $("#hiredDateFormConfirmModal").val(date);
                $("#chosenByBudget").val(true);
            } else {
                $("#modal-content-confirm-hired").hide();
                $("#modal-content-not-confirm-hired").show();
                $("#chosenByBudget").val(false);
            }
        });
    });
</script>
