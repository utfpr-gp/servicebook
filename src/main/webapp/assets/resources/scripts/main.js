$(document).ready(function (){

    $('#select-city').change(function(i){
        let selectedCountry = $(this).children("option:selected").val();
        $('#img-city').attr("src", selectedCountry);
    });

    $('#quantityCandidatorsMax').on('input', function() {
        $('#value-input-range').html($(this).val());
    });

    //Tempo de exibição de mensagens
    $(".msg-view").fadeTo(5000, 0.0);
    setTimeout(function() {
        $(".msg-view").addClass('hide');
    }, 5001);

    $("#show-area-perfil").click(function(i){
        $("#area-perfil").toggleClass('hide-on-med-and-down');
    });
  
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
})