
$(document).ready(function(){
    $("#select-city option[value='" + localStorage.getItem('selectCityOption') + "']").attr("selected", "selected")
    $("#select-city-name").text($("#select-city option[value ='" + localStorage.getItem("selectCityOption") +"']").text().trim());
    $("#toggle-city-id").click(function(e){
        e.preventDefault();
        $("#city-panel").slideToggle(350);
    });

    $("#select-city").change(function () {
        let selectCityOption = $('#select-city').val();
        let selectCity = $("#select-city option[value ='" + this.value +"']").text().trim();
        console.log(selectCityOption)
        localStorage.setItem("selectCityOption", selectCityOption);
        $("#select-city option[value='" + localStorage.getItem('selectCityOption') + "']").attr("selected", "selected")
        $("#select-city-name").text(selectCity);

    });
});