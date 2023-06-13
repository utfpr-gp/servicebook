$(document).ready(function(){
    $("#togleCityId").click(function(){
        $("#city-panel").slideToggle(350);
    });
});


$(document).ready(function(){
    $("select").change(function () {
        $("#selectCityName").text($("#select-city option[value ='" + this.value +"']").text().trim())
    });
});
