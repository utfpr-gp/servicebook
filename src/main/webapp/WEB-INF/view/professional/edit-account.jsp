<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:professional title="Editar perfil">
    <jsp:body>

        <main>
           <div class="row primary-background-color">
               <div class="container">
                   <div class="row">
                        <div class="col s12 m4 center">
                           <svg class="icon-person" viewBox="0 0 24 24">
                             <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                           </svg>
                           <h5 class="center edit-link tertiary-color-text"><a class="tertiary-color-text" href="">Editar foto</a></h5>
                        </div>
                        <div class="col s12 m8">
                            <h3 class="center secondary-color-text"><strong>PEDRO DE SOUZA</strong></h3>
                            <h5 class="center secondary-color-text"><i class="small material-icons">email</i> pedro@mail.com</h5>
                            <h5 class="center secondary-color-text"><i class="small material-icons">local_phone</i> (42) 9 9999-0000</h5>
                            <h5 class="center secondary-color-text"><i class="small material-icons">location_on</i> Bonsucesso, Guarapuava - PR</h5>

                       </div>
                   </div>
               </div>
           </div>
            <div class="row">
                <div class="container">
                    <div class="row">
                        <div class="col s12 l5 offset-l1 spacing-buttons">
                            <a href="#!" class="dark-color-text">
                                <div class="active-profission no-padding">
                                    <h3 class="center"><strong><i class="medium material-icons">chrome_reader_mode</i></strong></h3>
                                    <h4 class="center"><strong>Meu anúncio</strong></h4>
                                </div>
                            </a>
                        </div>
                        <div class="col s12 l5 spacing-buttons">
                            <a href="#!" class="dark-color-text">
                                <div class="active-profission no-padding">
                                    <h3 class="center"><strong><i class="medium material-icons">location_on</i></strong></h3>
                                    <h4 class="center"><strong>Meu endereço</strong></h4>
                                </div>
                            </a>
                        </div>
                        <div class="col s12 l5 offset-l1 spacing-buttons">
                            <a href="#!" class="dark-color-text">
                                <div class="active-profission no-padding">
                                    <h3 class="center"><strong><i class="medium material-icons">person</i></strong></h3>
                                    <h4 class="center"><strong>Meu dados pessoais</strong></h4>
                                </div>
                            </a>
                        </div>
                        <div class="col s12 l5 spacing-buttons">
                            <a href="#!" class="dark-color-text">
                                <div class="active-profission no-padding">
                                    <h3 class="center"><strong><i class="medium material-icons">stars</i></strong></h3>
                                    <h4 class="center"><strong>Minhas especialidades</strong></h4>
                                </div>
                            </a>
                        </div>
                        <div class="col s12 l5 offset-l1 spacing-buttons">
                            <a href="#!" class="dark-color-text">
                                <div class="active-profission no-padding">
                                    <h3 class="center"><strong><i class="medium material-icons">email</i></strong></h3>
                                    <h4 class="center"><strong>Meu email</strong></h4>
                                </div>
                            </a>
                        </div>
                        <div class="col s12 l5 spacing-buttons">
                            <a href="#!" class="dark-color-text">
                                <div class="active-profission no-padding">
                                    <h3 class="center"><strong><i class="medium material-icons">local_phone</i></strong></h3>
                                    <h4 class="center"><strong>Meu contato</strong></h4>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:professional>
