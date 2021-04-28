$(document).ready(function() {

    function limpa_formulário_cep() {
        $('#endereco-area').addClass('hide');
        $("#rua").html("");
        $("#bairro").html("");
        $("#cidade").html("");
        $("#uf").html("");
    }
    
    
    $("#cep").blur(function() {


        var cep = $(this).val().replace(/\D/g, '');

        if (cep != "") {
            var validacep = /^[0-9]{8}$/;

            if(validacep.test(cep)) {

                $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                    if (!("erro" in dados)) {

                        $('#endereco-area').removeClass('hide');
                        $("#cep").removeClass('invalid');
                        $("#error-cep").html("");
                        $("#cep").addClass('valid');
                        $("#rua").html(dados.logradouro);
                        $("#bairro").html(dados.bairro);
                        $("#cidade").html(dados.localidade);
                        $("#uf").html(dados.uf);
                    } 
                    else {
                        limpa_formulário_cep();
                        $("#cep").addClass('invalid');
                        $("#error-cep").removeClass('hide');
                        $("#error-cep").html("CEP não encontrado.");
                        
                    }
                });
            }
            else {
                limpa_formulário_cep();
                $("#cep").addClass('invalid');
                $("#error-cep").removeClass('hide');
                $("#error-cep").html("Formato de CEP inválido.");
            }
        }
        else {
            $("#cep").addClass('invalid');
            $("#error-cep").removeClass('hide');
            $("#error-cep").html("Formato de CEP inválido.");
            limpa_formulário_cep();
        }
    });
});