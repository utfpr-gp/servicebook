$(document).ready(function() {

    function limpa_formulário_cep() {
        $("#rua").val("");
        $("#cidade").val("");
        $("#uf").val("");
    }
    
    
    $("#btn-buscar").click(function() {


        var cep = $("#cep").val().replace(/\D/g, '');

        if (cep != "") {
            var validacep = /^[0-9]{8}$/;

            if(validacep.test(cep)) {

                $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                    if (!("erro" in dados)) {
                        $("#cep").removeClass('invalid');
                        $("#error-cep").html("");
                        $("#cep").addClass('valid');
                        $("#rua").val(dados.logradouro);
                        $("#cidade").val(dados.localidade);
                        $("#uf").val(dados.uf);
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