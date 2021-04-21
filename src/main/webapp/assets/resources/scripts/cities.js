$(document).ready(function() {
    let selectedState = 'AC';
    let rota = 'https://servicodados.ibge.gov.br/api/v1/localidades/estados/'+ selectedState + '/distritos'
    findCities(rota);

    let ufStates = new Map();
    ufStates.set("1", "AC");
    ufStates.set("2", "AL");
    ufStates.set("3", "AP");
    ufStates.set("4", "AM");
    ufStates.set("5", "BA");
    ufStates.set("6", "CE");
    ufStates.set("7", "DF");
    ufStates.set("8", "ES");
    ufStates.set("9", "GO");
    ufStates.set("10", "MA");
    ufStates.set("11", "MT");
    ufStates.set("12", "MS");
    ufStates.set("13", "MG");
    ufStates.set("14", "PA");
    ufStates.set("15", "PB");
    ufStates.set("16", "PR");
    ufStates.set("17", "PE");
    ufStates.set("18", "PI");
    ufStates.set("19", "RJ");
    ufStates.set("20", "RN");
    ufStates.set("21", "RS");
    ufStates.set("22", "RO");
    ufStates.set("23", "RR");
    ufStates.set("24", "SC");
    ufStates.set("25", "SP");
    ufStates.set("26", "SE");
    ufStates.set("27", "TO");


    $('#select-state').change(function(i){
      selectedState = $(this).children("option:selected").val();

      for(let [key, value] of ufStates){
          if(key == selectedState){
              selectedState = value;
          }
      }

      rota = 'https://servicodados.ibge.gov.br/api/v1/localidades/estados/'+ selectedState + '/distritos'
      findCities(rota);

    });

    function findCities(rota){
      $.ajax({
        type: 'GET',
        url: rota,
        success: function(response) {
          let countryArray = response;
          
          let dataCountry = {};
          for (let i = 0; i < countryArray.length; i++) {
            dataCountry[countryArray[i].nome] = null;
          }
          $('input.autocomplete').autocomplete({
            data: dataCountry, limit: 3,
            
          });
        }
      });
    }
   
  
  
  });