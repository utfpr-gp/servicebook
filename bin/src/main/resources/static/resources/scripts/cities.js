$(document).ready(function() {
    
    let selectedState = 'PR';
    let rota = 'https://servicodados.ibge.gov.br/api/v1/localidades/estados/'+ selectedState + '/distritos'
    findCities(rota);


    $('#select-state').change(function(i){
      
      selectedState = $(this).children("option:selected").val();
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