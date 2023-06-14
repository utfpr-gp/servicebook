$(document).ready(function(){

    $("#toggle-city-id").click(function(e){
        e.preventDefault();
        $("#city-panel").slideToggle(350);
    });

    $("#select-city").change(function () {
        $("#select-city-name").text($("#select-city option[value ='" + this.value +"']").text().trim())
    });
});