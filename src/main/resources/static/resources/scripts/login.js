var a = "admin";
var b = "profissional";
var c = "usuario";

$(document).ready(function() {
  $('#logar').on('click', function() {
    if (a == $('#username').val()) {
      window.location.href = "../../pages/admin/city-registration.html";
    }else if (b == $('#username').val()) {
      window.location.href = "../../pages/professional/service-order.html";
    }else if (c == $('#username').val()) {
      window.location.href = "../../pages/requests/requests.html";
    }else {
      window.location.href = "../../pages/login/login-sucess.html";
    }
    
    
  });

});
