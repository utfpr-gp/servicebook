<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

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
                    <div class="row">
                        <div class="row">
                            <div class="col s12 m6 left area-info-request-client-left">
                                <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">person</i> Pedreiro</p>
                                <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">access_time</i> Próxima semana</p>
                                <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">location_on</i> Santana, Guarapuava - PR</p>
                            </div>
                            <div class="col s12 m6 left area-info-request-client-right">
                               <div class="row">
                                   <div class="col s12 m7">
                                       <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">person</i> Maria de Lourdes</p>
                                       <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">phone</i> (42) 9 9999-0000</p>
                                       <p class="text-area-info-cli primary-color-text"><i class="small material-icons dark-color-text">mail</i> maria@mail.com</p>
                                   </div>
                                   <div class="col s6 offset-s3 m5 area-foto center">
                                       <svg width="150px" height="150px" viewBox="0 0 24 24">
                                           <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                                       </svg>
                                   </div>
                               </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col s12">
                                <p class="text-area-info-cli primary-color-text">Descrição do serviço...</p>
                            </div>
                            <div class="col s12">
                                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d637.164143085935!2d-51.450982613492!3d-25.39327015419766!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x94ef3618cba4dc95%3A0x6ad7b00212a4f63b!2sPar%C3%B3quia%20Santana!5e0!3m2!1spt-BR!2sbr!4v1620666186774!5m2!1spt-BR!2sbr" width="100%" height="350" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
                            </div>
                            <div class="col s6 m6 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn  pink darken-1" href="#!">Excluir</a>
                                </div>
                            </div>
                            <div class="col s6 m6 spacing-buttons">
                                <div class="center">
                                    <button class="waves-effect waves-light btn">Quero me candidatar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:professional>
