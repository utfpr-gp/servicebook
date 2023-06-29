function updateStatistics(selectElement) {
    var expertiseId = selectElement.value;
      
    const apiUrl = `/servicebook/minha-conta/profissional/especialidades/estatistica/${expertiseId}`
  
    $.ajax({
      type: "get",
      url: apiUrl,
      success: function (response) {
        let data = response.data;
  
        $(".expertise-jobs").text(data.jobs);
        $(".expertise-ratings").text(data.ratings);
        $(".expertise-comments").text(data.comments);
        
        let stars = "";
        for (let star = 1; star <= 5; star++) {
          if(star <= data.ratingScore)
            stars += `<i class="material-icons yellow-text small col s2">star</i>`;
          else
            stars += `<i class="material-icons yellow-text small col s2">star_border</i>`;
        }
  
        $(".expertise-rating-stars").attr('data-tooltip', `${data.ratingScore} estrela (s).` );
        $(".expertise-rating-stars").html(stars);
      },
      error: function(xhr, status, error) {
        let response = JSON.parse(xhr.responseText);
        console.log(response);
        swal({
          title: "Opss",
          text: response.message,
          icon: "error",
        });
      }
    });
  }