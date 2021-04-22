$(document).ready(function (){

    $('#select-city').change(function(i){
        var selectedCountry = $(this).children("option:selected").val();
        if (selectedCountry == 1) {
            $('#img-city').attr("src", "assets/resources/images/guarapuava.jpg")
        }
        if (selectedCountry == 2) {
            $('#img-city').attr("src", "assets/resources/images/curitiba.jpg")
        }
        if (selectedCountry == 3) {
            $('#img-city').attr("src", "assets/resources/images/saoPaulo.jpg")
        }
    });

    $(document).on('input change', '#input-range', function() {
        $('#value-input-range').html( $(this).val() );
    });

    $(".msg-view").fadeTo(4000, 0.0);

});
