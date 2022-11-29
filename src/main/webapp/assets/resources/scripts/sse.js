
const URL = 'sse/subscribe';
const SSE = 'sse-data';
window.onload = function () {
    console.log("carregando...")

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
};

