(function($){
  $(function(){

    M.AutoInit();
    $('.carousel.carousel-slider').carousel({
      fullWidth: true,
      indicators: true

    });

    $('.moveNextCarousel').click(function(e){
      e.preventDefault();
      e.stopPropagation();
      $('.carousel').carousel('next');
    });

    $('.movePrevCarousel').click(function(e){
      e.preventDefault();
      e.stopPropagation();
      $('.carousel').carousel('prev');
    });
  });
})(jQuery);
