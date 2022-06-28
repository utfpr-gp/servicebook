function deleteImage(url){
    let path = url.split('/');
    let filename = path[path.length - 1];

    $.ajax({
        type: 'DELETE',
        url: 'requisicoes/session/image/' + filename,
        complete: function() {
            location. reload();
        }
    });
}


