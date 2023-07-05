(function (){
    const URL = "minha-conta/cliente/profissionais-favoritos";

    //submete a requisição para seguir
    if(document.querySelector("#follow-button")){
        document.querySelector("#follow-button").addEventListener("click", function (){
            fetch(URL, {
                method: 'post',
                body: new FormData(document.querySelector("#follow-form"))
            }).then((response)=>{
                //document.querySelector("#follow-button").textContent = "Deixar de seguir";
                location.reload();
            }).catch((error)=>{
                alert("Houve um problema, não foi possível seguir.");
            })
        })
    }

    let professionalId = this.dataset

    //submete a requisição para deixar de seguir
    if(document.querySelector("#unfollow-button")){
        document.querySelector("#unfollow-button").addEventListener("click", function () {
            let professionalId = this.dataset.professional;

            fetch(`${URL}/${professionalId}`, {
                method: 'delete'
            }).then((response) => {
                location.reload();
            }).catch((error) => {
                alert("Houve um problema, não foi possível executar a operação.");
            })
        })
    }
})();