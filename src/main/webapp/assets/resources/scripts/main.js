$(document).ready(function (){

    $('#select-city').change(function(i){
        let selectedCountry = $(this).children("option:selected").val();
        $('#img-city').attr("src", selectedCountry);
    });

    $(document).on('input change', '#input-range', function() {
        $('#value-input-range').html( $(this).val() );
    });

    //Tempo de exibição de mensagens
    $(".msg-view").fadeTo(5000, 0.0);
    setTimeout(function() {
        $(".msg-view").addClass('hide');
    }, 5001);

});