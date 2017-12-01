
var ws;
var stompClient;

$(document).ready(function(){


    ws=new SockJS('ws://localhost:8080/questions');
    stompClient= Stomp.over(ws);

    stompClient.connect({},function(frame) {
        stompClient.subscribe("/topic/questions", function (message) {
            console.log("Recieved: " + message);
        });
    },
        function (error) {
        console.log("STOMP protocol error "+error);

    });


    ws.onmessage=function (data) {
        showGreeting(data)//make sure your printing out a string.

    }
});
function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendForm(){
    stompClient.send("/app/questions",{},$('#description').val());
};