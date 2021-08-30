$(document).ready(function() {

        $('#disponiveis').load($('.tab .active').attr("data-url"), function (result) {
            window.location.hash = "#disponiveis";
            $('#tab-default').click();
        });

    $('.tab a').click(function (e) {
        e.preventDefault();

        let url = $(this).attr("data-url");
        let href = this.hash;
        window.location.hash = href;
        $(href).load(url, function (result) {
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


