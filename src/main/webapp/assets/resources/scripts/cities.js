$(document).ready(function() {
    let ufState = 'AC';
    let rota = 'https://servicodados.ibge.gov.br/api/v1/localidades/estados/'+ ufState+ '/distritos'
    findCities(rota);

    $('#select-state').change(function(i){
        let selectedState = $(this).children("option:selected").text().split('-');

        rota = 'https://servicodados.ibge.gov.br/api/v1/localidades/estados/'+ selectedState[1] +'/distritos';
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