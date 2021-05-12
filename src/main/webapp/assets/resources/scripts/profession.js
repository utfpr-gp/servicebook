$(document).ready(function () {
    $('.modal').modal({
        onOpenEnd: function (modal, trigger) {
            var url = $(trigger).data('url');
            var name = $(trigger).data('name');

            modal = $(modal);
            var form = modal.find('form');
            form.attr('action', url);
            modal.find('#strong-name').text(name);
        }
    });
});