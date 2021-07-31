const formatName = str => {
    let regex = /(?:^|\s)(?!e|da|di|do|das|dos|de)\S/g
    let strFormatted = str.toLowerCase().replace(regex, letter => letter.toUpperCase());
    return strFormatted
};

$(document).ready(function() {

    $("#nameClient").on("change paste keyup", function () {
        const nameFormatted = formatName($(this).val());
        $(this).val(nameFormatted)
    });
});