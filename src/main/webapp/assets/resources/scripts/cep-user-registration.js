$(document).ready(function () {
    $('#postalCode').focus();

    function clearFormAddress() {
        $('#postalCode').val('');
        $('#neighborhood').val('');
        $('#street').val('');
        $('#number').val('');
        $('#city').val('');
        $('#state').val('');
    }

    $('#btn-search-cep').click(function () {
        let cep = $('#postalCode').val().replace(/\D/g, '');

        if (cep != '') {
            let cepRegex = /^[0-9]{8}$/;

            if (cepRegex.test(cep)) {
                $.getJSON('https://viacep.com.br/ws/' + cep + '/json/?callback=?', function (data) {
                    if (!('erro' in data)) {
                        $('#postalCode').removeClass('invalid');
                        $('#error-cep').html('');

                        $('#neighborhood').val(data.bairro).focus();
                        $('#street').val(data.logradouro).focus();
                        $('#city').val(data.localidade).focus();
                        $('#state').val(data.uf).focus();
                        $('#number').focus();
                    } else {
                        $('#postalCode').addClass('invalid');
                        $('#errorPostalCode').removeClass('hide');
                        $('#errorPostalCode').html('CEP não encontrado!');
                        clearFormAddress();
                    }
                });
            } else {
                $('#postalCode').addClass('invalid');
                $('#errorPostalCode').removeClass('hide');
                $('#errorPostalCode').html('CEP inválido!');
                clearFormAddress();
            }
        } else {
            $('#postalCode').addClass('invalid');
            $('#errorPostalCode').removeClass('hide');
            $('#errorPostalCode').html('CEP inválido!');
            clearFormAddress();
        }
    });
});
