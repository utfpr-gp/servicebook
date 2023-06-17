<%@tag description="Servicebook - Search bar template" pageEncoding="UTF-8" %>

<div>
    <div class="container center-align">
        <div class="row">
            <div class="col s12">
                <h3>O QUE VOCÊ PRECISA?</h3>
            </div>
        </div>

            <form action="profissionais/busca" method="get">
                <div class="row">
                    <div class="col s12">
                        <nav class="grey lighten-4">
                            <div class="nav-wrapper">
                                <div class="input-field">
                                    <input id="search" type="search" name="termo-da-busca" placeholder="Digite aqui..." value="${searchTerm}" required>
                                    <i class="material-icons">close</i>
                                </div>
                            </div>
                        </nav>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12">
                        <button type="submit" class="waves-effect waves-light btn-large right">
                            <i class="material-icons left">search</i>Buscar
                        </button>
                    </div>
                </div>
            </form>
    </div>
</div>