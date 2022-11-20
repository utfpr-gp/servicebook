$(document).ready(function() {
    $('#select-expertise').formSelect();

    $('#select-expertise').change(function () {
        $('#form-expertise').submit();
    });

    $("#form-expertise").submit(function (e) { 
        e.preventDefault();

        const expertiseId = e.target.elements[1].value;
        const apiUrl = `/servicebook/especialidades/api/get-by-professional/${expertiseId}`

        $.ajax({
            type: "get",
            url: apiUrl,
            data: `id=${expertiseId}`,
            success: function (response) {
                $("#expertise-jobs").text(response.jobs);
                $("#expertise-ratings").text(response.ratings);
                $("#expertise-comments").text(response.comments);
                
                $(".expertise-rating-star").each(function (index, element) {
                    response.ratingScore >= (index + 1) ?  element.innerText = 'star' : element.innerText = 'star_border'
                });
            }
        });
    });
})
