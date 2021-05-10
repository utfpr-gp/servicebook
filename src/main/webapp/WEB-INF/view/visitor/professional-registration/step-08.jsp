<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:visitor title="Cadastro de Profissional">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Escolha uma ou mais especialidades!</h3>
                        <h5 class="center secondary-color-text">Selecione apenas as especialidades quais possui experiência para ofertar serviços!</h5>


                        <form action="">
                            <div class="row center spacing-buttons">
                                <h4><strong>Categoria: Construção e Reformas</strong></h4>
                                <div class="col s12 l4 offset-l4 spacing-buttons">
                                   <div class="none-profission">
                                       <h5 class="center">Nenhuma profissão foi selecionada!</h5>
                                   </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 l4 spacing-buttons">
                                    <div class="active-profission">
                                        <h4 class="center"><strong>ELETRICISTA</strong></h4>
                                        <h5 class="center">Instalação elétrica, tomadas...</h5>
                                    </div>
                                </div>
                                <div class="col s12 l4 spacing-buttons">
                                    <div class="active-profission">
                                        <h4 class="center"><strong>PEDREIRO</strong></h4>
                                        <h5 class="center">Instalação elétrica, tomadas...</h5>
                                    </div>
                                </div>

                               <div class="col s12">
                                   <div class="center">
                                       <a class="waves-effect waves-light btn modal-trigger" href="#modal1">ADICIONAR ESPECIALIDADE</a>
                                   </div>
                               </div>
                            </div>

                            <div class="col s6 m3 offset-m3 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                </div>
                            </div>
                            <div class="col s6 m3 spacing-buttons">
                                <div class="center">
                                    <button class="waves-effect waves-light btn">Fim</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div id="modal1" class="modal modal-fixed-footer">
                <div class="modal-content">
                    <h4 class="center secondary-color-text">Escolha uma ou mais profissões!</h4>
                    <div>
                        <form action="#">
                            <div class="row">
                                <div class="col s12 l6 spacing-buttons">
                                    <div class="selected-profission">
                                       <div class="row">
                                           <div class="area-check-profission col s2">
                                               <label>
                                                   <input type="checkbox" checked/>
                                                   <span></span>
                                               </label>
                                           </div>
                                           <div class="col s10">
                                               <h4 class="center"><strong>ELETRICISTA</strong></h4>
                                               <h5 class="center">Instalação elétrica, tomadas...</h5>
                                           </div>
                                       </div>
                                     </div>
                                </div>
                                <div class="col s12 l6 spacing-buttons">
                                    <div class="selected-profission">
                                        <div class="row">
                                            <div class="area-check-profission col s2">
                                                <label>
                                                    <input type="checkbox" />
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col s10">
                                                <h4 class="center"><strong>ENCANADOR</strong></h4>
                                                <h5 class="center">Instalação elétrica, tomadas...</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col s12 l6 spacing-buttons">
                                    <div class="selected-profission">
                                        <div class="row">
                                            <div class="area-check-profission col s2">
                                                <label>
                                                    <input type="checkbox" />
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col s10">
                                                <h4 class="center"><strong>JARDINEIRO</strong></h4>
                                                <h5 class="center">Instalação elétrica, tomadas...</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col s12 l6 spacing-buttons">
                                    <div class="selected-profission">
                                        <div class="row">
                                            <div class="area-check-profission col s2">
                                                <label>
                                                    <input type="checkbox" checked/>
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col s10">
                                                <h4 class="center"><strong>PEDREIRO</strong></h4>
                                                <h5 class="center">Instalação elétrica, tomadas...</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>
                <div class="modal-footer center">
                    <a href="#!" class="modal-close waves-effect waves-green btn">Fechar</a>
                </div>
            </div>

        </main>

    </jsp:body>
</t:visitor>
