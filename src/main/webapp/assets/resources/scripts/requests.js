$(document).ready(function() {
    //configuração das tabs
    let tabs = $('.tabs');
    let options = {duration: 600};

    //leitura da tab ativa em redirecionamento vindo do servidor como parâmetro ?tab=paraOrcamento
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const tabParam = urlParams.get('tab');

    //aba ativa
    let hashActive = undefined;

    //verifica se tem o parâmetro vindo do redirecionamento referente à tab
    if(tabParam){
        hashActive = '#' + tabParam;
        let url = new URL(window.location);
        url.searchParams.delete('tab');
        window.history.pushState({}, '', url);
        window.location.hash = hashActive;
    }

    //Se existir a hash na URL, dispara o evento hashchange
    if (window.location.hash) {
        hashActive = window.location.hash;
        $(window).trigger('hashchange');
    }
    else{
        hashActive = '#disponiveis';
        window.location.hash = hashActive;
    }

    /**
     * Trata do vento de mudança de hash na URL
     */
    $(window).on('hashchange',  (e) => {
        let hashActive = window.location.hash;

        //seleção da tab pelo seu id
        M.Tabs.init(tabs, options);
        let tabInstance = M.Tabs.getInstance(tabs);
        tabInstance.select($(`a[href='${hashActive}']`).parent().attr("id"));
        let url = $(`a[href='${hashActive}']`).attr("data-url")
        $(hashActive).load(url, function (result) {
            //window.scrollTo(0, 0);
        });
    })



    //carrega o conteúdo inicial para a tab padrão #disponiveis antes de qualquer evento de clique em abas
    $(hashActive).load($(`a[href='${hashActive}']`).attr("data-url"), function (result) {
        window.location.hash = hashActive;
        //$('#tab-default').click();
        //seleção da tab pelo seu id
        M.Tabs.init(tabs, options);
        let tabInstance = M.Tabs.getInstance(tabs);
        tabInstance.select($(`a[href='${hashActive}']`).parent().attr("id"));

    });


    $('.tab a').click(function (e) {
        e.preventDefault();
        console.log("clicou");

        let url = $(this).attr("data-url");
        let href = this.hash;
        window.location.hash = href;
        $(href).load(url, function (result) {
            //window.scrollTo(0, 0);
        });

    });

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
});


