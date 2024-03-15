$(document).ready(function () {

    $('#select-city').change(function (i) {
        let selectedCountry = $(this).children("option:selected").val();
        $('#img-city').attr("src", selectedCountry);
    });

    $('#quantityCandidatorsMax').on('input', function () {
        $('#value-input-range').html($(this).val());
    });

    //Tempo de exibição de mensagens
    $(".msg-view").fadeTo(5000, 0.0);
    setTimeout(function () {
        $(".msg-view").addClass('hide');
    }, 5001);

    $("#show-area-perfil").click(function (i) {
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

    // if ($("#category-select").val() != '') {
    //     selectCategory($("#category-select").val())
    // }
    //
    // var element = $("#dto_expertise").val();
    //
    // function selectCategory(categoryId) {
    //     $.ajax({
    //         url: "especialidades/categoria/"+ categoryId,
    //         type: "GET",
    //         success: function (expertises) {
    //             $("#expertise-select").empty();
    //
    //             //se o array for vazio, coloca o option que não há especialidades
    //             if (expertises.length === 0){
    //                 $('#expertise-select').prop("disabled", true);
    //                 $("#expertise-select").append("<option disabled selected>Não há especialidades</option>");
    //             } else
    //                 $('#expertise-select').removeAttr("disabled");
    //                 $("#expertise-select").append("<option disabled selected>Selecione uma especialidade</option>");
    //
    //             $.each(expertises, function (index, expertise) {
    //
    //                 if(expertise.id == element){
    //                     $("#expertise-select").append("<option value='" + expertise.id + "'  selected>" + expertise.name + "</option>");
    //                 }else {
    //                     $("#expertise-select").append("<option value='" + expertise.id + "'>" + expertise.name + "</option>");
    //                 }
    //
    //             });
    //             $("#expertise-select").formSelect();
    //         }
    //     });
    // }
    // if ($("#expertise-select").val() != '') {
    //     selectExpertise(element)
    // }
    //
    // var element_service = $("#dto_service").val();
    // function selectExpertise(expertiseId) {
    //     $.ajax({
    //         url: "servicos/especialidade/"+ expertiseId,
    //         type: "GET",
    //         success: function (services) {
    //
    //             $("#service-select").empty();
    //
    //             //se o array for vazio, coloca o option que não há especialidades
    //             if (services.length === 0) {
    //                 $('#service-select').prop("disabled", true);
    //                 $("#service-select").append("<option disabled selected>Não há serviços</option>");
    //             } else
    //                 $('#service-select').removeAttr("disabled");
    //                 $("#service-select").append("<option disabled selected>Selecione um serviço</option>");
    //
    //             $.each(services, function (index, service) {
    //                 $("#service-select").append("<option value='" + service.id + "'>" + service.name + "</option>");
    //
    //                 if(service.id == element_service){
    //                     $("#service-select").append("<option value='" + service.id + "' selected>" + service.name + "</option>");
    //                 }else {
    //                     $("#service-select").append("<option value='" + service.id + "'>" + service.name + "</option>");
    //                 }
    //
    //             });
    //             $("#service-select").formSelect();
    //         }
    //     });
    // }
    //
    // // LISTA DE ESPECIALIDADE DE ACORDO COM A CATEGORIA
    // $("#category-select").change(function () {
    //     let categoryId = $(this).val();
    //     $.ajax({
    //         url: "especialidades/categoria/"+ categoryId,
    //         type: "GET",
    //         success: function (expertises) {
    //             console.log(expertises)
    //
    //             $("#expertise-select").empty();
    //
    //             //se o array for vazio, coloca o option que não há especialidades
    //             if (expertises.length === 0)
    //                 $("#expertise-select").append("<option disabled selected>Não há especialidades</option>");
    //             else
    //                 $("#expertise-select").append("<option disabled selected>Selecione uma especialidade</option>");
    //
    //             $.each(expertises, function (index, expertise) {
    //                 $("#expertise-select").append("<option value='" + expertise.id + "'>" + expertise.name + "</option>");
    //             });
    //             $("#expertise-select").formSelect();
    //         }
    //     });
    // });
    //
    // // LISTA DE SERVIÇOS DE ACORDO COM A ESPECIALIDADE
    // $("#expertise-select").change(function () {
    //     let expertiseId = $(this).val();
    //     $.ajax({
    //         url: "servicos/especialidade/"+ expertiseId,
    //         type: "GET",
    //         success: function (services) {
    //
    //             $("#service-select").empty();
    //
    //             //se o array for vazio, coloca o option que não há especialidades
    //             if (services.length === 0)
    //                 $("#service-select").append("<option disabled selected>Não há serviços</option>");
    //             else
    //                 $("#service-select").append("<option disabled selected>Selecione um serviço</option>");
    //
    //             $.each(services, function (index, service) {
    //                 $("#service-select").append("<option value='" + service.id + "'>" + service.name + "</option>");
    //
    //             });
    //             $("#service-select").formSelect();
    //         }
    //     });
    // });
})