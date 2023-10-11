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
    // get card width dynamically
    cardWidth = $(".slide-card").width();
    // next-arrow
    $('#next-arrow').click(function () {
        var firstChildAppend = $(".slide-card:first-child()");
        $(".slide-card").animate({ left: -cardWidth }, function () {
            $('.slider-wrap').append(firstChildAppend);
            $(".slide-card").css({
                left: 0,
            })
        })
    });
    // previous-arrow
    $('#previous-arrow').click(function () {
        var lastChildPrepend = $(".slide-card:last-child()");
        $(".slide-card").animate({ left: cardWidth }, function () {
            $('.slider-wrap').prepend(lastChildPrepend);
            $(".slide-card").css({
                left: 0,
            })
        })
    });


    // MEUS ANUNCIOS
    $(function () {
        // adiciona mascara no campo de valor
        $('.ads-price').mask('000.000.000.000.000,00', {reverse: true});

        // inicializa os campos nome e descrição com os dados do serviço selecionado
        $('#service-select-individual').change(function () {
            let serviceId = $(this).val();
            let url = 'servicos/' + serviceId;

            $.get(url, function (data) {
                $('#name-input').val(data.name);
                $('#description-textarea').val(data.description);
                $('#description-blockquote').show();
            });
        });

        // inicializa o select de serviços com os serviços da especialidade selecionada
        $('#expertise-select').change(function () {
            let expertiseId = $(this).val();
            let url = 'minha-conta/profissional/especialidades/' + expertiseId + '/servicos';

            $.get(url, function (data) {
                let options = '<option disabled selected>Selecione um serviço</option>';
                data.forEach(function (service) {
                    options += '<option value="' + service.id + '">' + service.name + '</option>';
                });
                $('#service-select-individual').html(options);
                $('#service-select-individual').formSelect();
            });
        });
    });
    //seleciona o tipo do serviço, habilitando desta forma cada formulario de acordo com o tipo
    $('#service-type').change(function () {
        let type = $(this).val();
        if(type == 'SIMPLE_PACKAGE'){
            $('#type-package').show();
        } else {
            $('#type-package').hide();
        }

        if(type == 'COMBINED_PACKAGE') {
            $('#type-combined').show();
            $('#ads-name').hide();
            $('#label-ads').hide();
        } else {
            $('#type-combined').hide();
        }
        if(type == 'INDIVIDUAL'){
            $('#type-individual').show();
        }else {
            $('#type-individual').hide();
        }

    });
    //adiciona o valor do select e adiciona no campo de texo
    $('#service-select-individual').change(function () {
        var select = document.getElementById("service-select-individual");
        var opcaoTexto = select.options[select.selectedIndex].text;
        var opcaoValor = select.options[select.selectedIndex].value;
        $(".ads-name").val(opcaoTexto);
        $(".name-service").val(opcaoTexto);
    });

    $('#service-select-package').change(function () {
        var select = document.getElementById("service-select-package");
        var opcaoTexto = select.options[select.selectedIndex].text;
        var opcaoValor = select.options[select.selectedIndex].value;
        $(".ads-name").val(opcaoTexto);
        $(".name-service").val(opcaoTexto);
    });

    $('#service-select-combined').change(function () {
        var selectElement = document.getElementById("service-select-combined");
        var opcaoTexto = select.options[select.selectedIndex].text;
        var opcaoValor = select.options[select.selectedIndex].value;


        var selectedOptions = selectElement.selectedOptions;
        //
        console.log(selectedOptions)
        // // Cria um array para armazenar os textos selecionados
        // var selectedTexts = [];
        //
        // // Loop pelas opções selecionadas e adiciona seus textos ao array
        // for (var i = 0; i < selectedOptions.length; i++) {
        //     selectedTexts.push(selectedOptions[i].text);
        // }
        //
        // // Obtém o campo input
        // $(".ads-name").val(selectedTexts.join(", "));
        // $(".name-service").val(selectedTexts.join(", "));
    });

    // quando selecionado ele habilita o campo de texto para editar o texto
    $('.sobrescrever').change(function () {
        var elements = document.getElementsByClassName('ads-name-individual');
        var package = document.getElementById('ads-name-package');

        if (!package.disabled) {
            package.disabled = true;
        } else {
            package.disabled = false;
        }

        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];
            if (!element.disabled) {
                element.disabled = true;
            } else {
                element.disabled = false;
            }
        }

    });

});

// remove a mascara no campo de valor, para quando enviar pro controller não dar erro de conversão
function RemoveMaskIndividual() {
    str = $("#ads-price-individual").val();
    str = str.replace(/[^\d]+/g,"");
    $("#price-service-individual").val(str);
}

function RemoveMaskCombined() {
    str = $("#ads-price-combined").val();
    str = str.replace(/[^\d]+/g,"");
    $("#price-service-combined").val(str);
}

function RemoveMaskPackage() {
    str = $("#ads-price-package").val();
    str = str.replace(/[^\d]+/g,"");
    $("#price-service-package").val(str);
}

function mostrarSelecionados() {
    var select = document.getElementById("mySelect");
    var selecionados = [];
    var selecionadosIds = [];

    // Adiciona os valores selecionados como itens de lista
    for (var i = 0; i < select.options.length; i++) {
        if (select.options[i].selected && select.options[i].value !== "") {
            selecionados.push(select.options[i].text);
            selecionadosIds.push(select.options[i].value);
        }
    }
    var resultadoDiv = document.getElementById("descriptionarray");
    resultadoDiv.value = selecionados.join(", ");

    var resultadoDiv2 = document.getElementById("descriptions");
    resultadoDiv2.value = selecionadosIds.join(", ");

    var resultadoDiv1 = document.getElementById("description");
    resultadoDiv1.value = selecionados.join(", ");
}

