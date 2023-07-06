const URL_SSE = 'sse/subscribe';
const SSE = 'sse-data';

/**
 * No carregamento da página, faz a solicitação para abrir um canal de push via SSE.
 */
window.onload = function () {

    let eventSource = new EventSource(URL_SSE)

    //aguarda uma mensagem global.
    eventSource.addEventListener("message", function (event) {
        let data = JSON.parse(event.data);
        addblock(data.message, data.fromProfessionalName, data.descriptionServ);
        alert('mensagem chegou no cliente');
    });

    //aguarda um erro
    eventSource.addEventListener('error', function (event) {
        console.log("error : " + event.currentTarget.readyState);
        if (event.currentTarget.readyState == eventSource.CLOSED) {

        } else {
            eventSource.close();
        }
    });

    /**
     * Constrói o trecho de HTML da notificação a ser apresentada.
     * @param message
     * @param fromProfessionalName
     * @param descriptionServ
     */
    function addblock(message, fromProfessionalName, descriptionServ){
        console.log("adblock function...")
        let li = document.createElement("li");
        let diva = document.createElement("div.card");
        let divb = document.createElement("div.card-content");
        let h = document.createElement("h5");
        let p = document.createElement("p");
        p.innerHTML(fromProfessionalName + " para o serviço " + descriptionServ);
        h.innerHTML(message);
        divb.appendChild(h, p);
        diva.appendChild(divb);
        li.appendChild(diva);
        document.getElementById("dropdown4").appendChild(li);
    };
};