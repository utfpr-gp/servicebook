$(document).ready(function () {
    $('#postal-code').focus();

    function clearFormAddress() {
        $('#postal-code').val('');
        $('#neighborhood').val('');
        $('#street').val('');
        $('#number').val('');
        $('#city').val('');
        $('#state').val('');
    }

    $('#btn-search-cep').click(function () {
        debugger
        let cep = $('#postal-code').val().replace(/\D/g, '');

        if (cep != '') {
            let cepRegex = /^[0-9]{8}$/;

            if (cepRegex.test(cep)) {
                $.getJSON('https://viacep.com.br/ws/' + cep + '/json/?callback=?', function (data) {
                    if (!('erro' in data)) {
                        $('#postal-code').removeClass('invalid');
                        $('#error-cep').html('');

                        $('#neighborhood').val(data.bairro).focus();
                        $('#street').val(data.logradouro).focus();
                        $('#city').val(data.localidade).focus();
                        $('#state').val(data.uf).focus();
                        $('#number').focus();
                        validaCity(data);
                    } else {
                        $('#postal-code').addClass('invalid');
                        $('#errorPostalCode').removeClass('hide');
                        $('#errorPostalCode').html('CEP não encontrado!');
                        clearFormAddress();
                    }
                    $('select').formSelect();
                });
            } else {
                $('#postal-code').addClass('invalid');
                $('#errorPostalCode').removeClass('hide');
                $('#errorPostalCode').html('CEP inválido!');
                clearFormAddress();
            }
        } else {
            $('#postal-code').addClass('invalid');
            $('#errorPostalCode').removeClass('hide');
            $('#errorPostalCode').html('CEP inválido!');
            clearFormAddress();
        }
    });
    
    function validaCity(data) {
        var inputElement = document.querySelector('input[name="id"]');
        var professionalId = inputElement.value;
        const apiUrl = `minha-conta/meu-endereco/${professionalId}`
        $.ajax({
            url: apiUrl,
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                console.log(response);
            },
            error: function(error) {
                console.error('Ocorreu um erro:', error);
                if (error.status != 200) {
                    $("#errorCity").text(error.responseText)
                    $("#containerError").css("display", "block");
                } else {
                    $("#sucessCity").text(error.responseText)
                    $("#containerSucess").css("display", "block");
                }
            }
        });

    }
});
