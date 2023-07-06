
var jobCompanySpans = $(".companyCep");
$(document).ready(function(){
    for(i=0; i < jobCompanySpans.length; i++){
       getCompanyAddressByCep(jobCompanySpans[i].innerText, i);
    }
});

function getCompanyAddressByCep(cep, index) {
    var cep = cep.replace(/[^0-9]/, '');
    if(cep){
        var url = 'https://viacep.com.br/ws/'+cep+'/json/';
        $.ajax({
            url: url,
            dataType: 'jsonp',
            crossDomain: true,
            contentType: "application/json",
            success : function(json){
                if(json.logradouro){
                    jobCompanySpans[index].innerText = json.localidade;
                  return json.localidade;
                }
            }
        });
    }
}

$(document).ready(function(){
    $('select').on('change', function() {
        let expertise = this.value;
        if(expertise != '--'){
            $('.card').hide(300)
            $('span:contains(\''+expertise+'\')').parent().parent().parent().parent().show(300)
        }else{
            $('.card').show(300)
        }
    });
});

