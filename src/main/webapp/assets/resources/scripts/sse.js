const URL = 'sse/subscribe';
const SSE = 'sse-data';
const SSE_ISOPEN = 'SSE_ISOPEN';

window.onload = function () {
    console.log("carregando...")
    //TODO criar boolean para verificar se esta aberto um canal emiter se tiver aberto não precisar criar um novo.
    //TODO - WIP - dando erro ainda validação de existencia do eventSource.

    //verificação stream
    if (sessionStorage.getItem(SSE_ISOPEN) == "true"){
        console.log("entrou no if.... ");

        eventSource.addEventListener("message", function (event) {
            let data = JSON.parse(event.data);
            console.log(data);
            sessionStorage.setItem(SSE, JSON.stringify(data));
            alert('mensagem chegou no cliente');
        });

        eventSource.addEventListener('error', function (event) {
            console.log("error : " + event.currentTarget.readyState);
        });
    }else {
        console.log("entrou no else .... ");

        let eventSource = new EventSource(URL)

        sessionStorage.setItem(SSE_ISOPEN, true);

        eventSource.addEventListener("message", function (event) {
            let data = JSON.parse(event.data);
            console.log(data);
            sessionStorage.setItem(SSE, JSON.stringify(data));
            alert('mensagem chegou no cliente');
        });

        eventSource.addEventListener('error', function (event) {
            console.log("error : " + event.currentTarget.readyState);
        });
    }
};


/*
*
const URL = 'sse/subscribe';
const SSE = 'sse-data';
window.onload = function () {
    console.log("carregando...")

    //TODO criar boolean para verificar se esta aberto um canal emiter se tiver aberto não precisar criar um novo.

    let eventSource = new EventSource(URL)

    eventSource.addEventListener("message", function (event) {
        let data = JSON.parse(event.data);
        console.log(data);
        sessionStorage.setItem(SSE, JSON.stringify(data));
        alert('mensagem chegou no cliente');
    });

    eventSource.addEventListener('error', function (event) {
        console.log("error : " + event.currentTarget.readyState);
    });
};*/