<%@tag description="Servicebook - Search bar template" pageEncoding="UTF-8" %>

<div>
    <div class="container center-align">
        <div class="row">
            <div class="col s12">
                <h3>O que você precisa?</h3>
            </div>
        </div>
        <div class="row">
            <form action="profissionais/busca" method="get">
                <div class="col s10">
                    <nav class="grey lighten-4">
                        <div class="nav-wrapper">
                            <div class="input-field">
                                <input id="search" type="search" name="termo-da-busca" placeholder="Digite aqui..." required>
                                <label class="label-icon" for="search"><i class="material-icons black-text">search</i></label>
                                <i class="material-icons">close</i>
                            </div>
                        </div>
                    </nav>
                </div>
                <div class="col s2">
                    <button type="submit" class="waves-effect waves-light btn-large">Buscar</button>
                </div>
            </form>
        </div>
    </div>

</div>