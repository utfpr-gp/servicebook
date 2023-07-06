$(document).ready(function() {
    $('#form-upload-images').submit(function(event) {
        event.preventDefault(); 
    
        var formData = new FormData(this);
        $.ajax({
            url: `requisicoes/upload-fotos`,
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                let data = response.data;
                
                if(data.successImages){
                    swal({
                        text: "Sucesso ao inserir foto(s)!",
                        icon: "success",
                    }).then((result) => {
                        location. reload();
                    })
                }
            },
            error: function(xhr, status, error) {
                let response = JSON.parse(xhr.responseText);
                swal({
                    title: "Opss",
                    text: response.message,
                    icon: "error",
                });
            }
        });
    });
});

function deleteImage(url){
    let path = url.split('/');
    let filename = path[path.length - 1];

    $.ajax({
        type: 'DELETE',
        url: 'requisicoes/session/image/' + filename,
        success: function (response) {
            let data = response.data;
            
            if(data.url){
                swal({
                    text: "Imagem excluída com sucesso!",
                    icon: "success",
                }).then((result) => {
                    location. reload();
                })
            }
          },
          error: function(xhr, status, error) {
            let response = JSON.parse(xhr.responseText);
            
            swal({
              title: "Opss",
              text: response.message,
              icon: "error",
            });
          }
    });
}