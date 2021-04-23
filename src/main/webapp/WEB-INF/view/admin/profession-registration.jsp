<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:admin title="Cadastro de Profissão">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">

                    <!--   Icon Section   -->
                    <div class="row">

                        <h3 class="center secondary-color-text range-quantity">Cadastro de Profisssão</h3>


                        <form action="profissao" method="post">
                            <div class="col s12 l4 offset-l4 input-field range-quantity">
                                <div class="row">
                                    <div class="input-field  col s12">
                                        <i class="material-icons prefix primary-color-text">work</i>
                                        <input placeholder="Pedreiro" type="text" id="autocomplete-input" name="name" class="autocomplete">
                                        <label for="autocomplete-input">Profissão</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s6 m6 spacing-buttons">
                                    <div class="center">
                                        <a class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                    </div>
                                </div>
                                <div class="col s6 m6 spacing-buttons">
                                    <div class="center">
                                        <button class="waves-effect waves-light btn">Cadastrar</button>
                                    </div>
                                </div>
                            </div>
                    </form>



                </div>

            </div>
            </div>
        </main>

    </jsp:body>
</t:admin>
