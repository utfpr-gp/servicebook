const URL = 'sse/subscribe';
const SSE = 'sse-data';
window.onload = function () {
    console.log("carregando...")

    //TODO criar boolean para verificar se esta aberto um canal emiter se tiver aberto não precisar criar um novo.

    let eventSource = new EventSource(URL)

    console.log("log eventosource... " + eventSource.readyState.toString())

    eventSource.addEventListener("message", function (event) {
        let data = JSON.parse(event.data);
        //console.log(data);
        /*{id: null, message: 'Uma nova candidatura', localDateTime: '2022-12-05T23:33:55.410037147', toUserEmail: 'cliente2@gmail.com', readStatus: false}*/

        console.log(data.message, data.id, data.localDateTime, data.toUserEmail, data.readStatus)
        /*Uma nova candidatura null 2022-12-05T23:36:20.824950276 cliente2@gmail.com false*/

        //console.log(data.getMessage())
        //sessionStorage.setItem(SSE, JSON.stringify(data));

        function cambiarra(){
            console.log("function cambiarra")
        }

        setTimeout(() => {  cambiarra(); }, 30000);


        alert('mensagem chegou no cliente');


    });

    eventSource.addEventListener('error', function (event) {
        console.log("error : " + event.currentTarget.readyState);
        if (event.currentTarget.readyState == eventSource.CLOSED) {

        } else {
            eventSource.close();
        }
    });
};


/*const URL = 'sse/subscribe';
const SSE = 'sse-data';
const SSE_ISOPEN = 'SSE_ISOPEN';

window.onload = function () {
    console.log("carregando...")
    //TODO criar boolean para verificar se esta aberto um canal emiter se tiver aberto não precisar criar um novo.
    //TODO - WIP - dando erro ainda validação de existencia do eventSource.

    let eventSource = null;

    //verificação stream
    if (eventSource === null) {
        console.log("entrou no if null .... ");
        eventSource = new EventSource(URL)
        //sessionStorage.setItem(SSE_ISOPEN, eventSource);
        console.log("log event  " + eventSource.readyState)

        eventSource.addEventListener("message", function (event) {
            let data = JSON.parse(event.data);
            console.log(data);
            sessionStorage.setItem(SSE, JSON.stringify(data));
            alert('mensagem chegou no cliente');
        });

        eventSource.addEventListener('error', function (event) {
            console.log("error : " + event.currentTarget.readyState);
            if (event.currentTarget.readyState == eventSource.CLOSED) {

            } else {
                eventSource.close();
            }
        });
    }
    if (eventSource.readyState != 2) {
        console.log("entrou no if.... ");
        //eventSource = sessionStorage.getItem(SSE_ISOPEN);
        console.log("log event  " + eventSource.readyState)

        eventSource.addEventListener("message", function (event) {
            let data = JSON.parse(event.data);
            console.log(data);
            sessionStorage.setItem(SSE, JSON.stringify(data));
            alert('mensagem chegou no cliente');
        });

        eventSource.addEventListener('error', function (event) {
            console.log("error : " + event.currentTarget.readyState);
            if (event.currentTarget.readyState == eventSource.CLOSED) {

            } else {
                eventSource.close();
            }
        });
    }

};*/