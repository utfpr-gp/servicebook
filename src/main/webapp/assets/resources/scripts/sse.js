const URL = 'sse/subscribe';
const SSE = 'sse-data';
window.onload = function () {
    console.log("carregando...")

    //TODO criar boolean para verificar se esta aberto um canal emiter se tiver aberto não precisar criar um novo.

    let eventSource = new EventSource(URL)

    console.log("log eventosource... " + eventSource.readyState.toString())

    eventSource.addEventListener("message", function (event) {
        let data = JSON.parse(event.data);
        addblock(data.message, data.fromProfessionalName, data.descriptionServ);

        alert('mensagem chegou no cliente');
    });

    eventSource.addEventListener('error', function (event) {
        console.log("error : " + event.currentTarget.readyState);
        if (event.currentTarget.readyState == eventSource.CLOSED) {

        } else {
            eventSource.close();
        }
    });

    //ao receber notificação monta no html
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