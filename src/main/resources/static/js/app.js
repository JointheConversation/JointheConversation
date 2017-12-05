var stompClient = null;
connect();
// function setConnected(connected) {
//     $("#connect").prop("disabled", connected);
//     $("#disconnect").prop("disabled", !connected);
//     if (connected) {
//         $("#conversation").show();
//     }
//     else {
//         $("#conversation").hide();
//     }
//     $("#greetings").html("");
// }

function ajaxStart(){ //Gets all Posts from the Dao and displays them
    $.ajax({
        type: "GET",
        url: '/api/post/all',
        success: function (result) {

            if(result.status=="Done") {
                var custList = "";
                $.each(result.data, function (i, post) {
                    showGreeting(post.description, post.user.username, moment().startOf(post.date).fromNow())
                })

            }

            else{

                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("Fail: ", result);
            }
        },
        error : function(e) {
            $("#getResultDiv").html("<strong>Error</strong>");
            console.log("ERROR: ", e);
        }

    });

}
function connect() {
            ws=new SockJS('/liveThread');
            stompClient = Stomp.over(ws);

            stompClient.connect({}, function (frame) {
                // setConnected(true);
                ajaxStart(); //Starts Ajax request get the descriptions
                console.log('Connected: ' + frame);
                stompClient.subscribe('/thread/liveThread', function (message) {
                        console.log("Received "+message);
                        showGreeting(message.body, $('#loggedinuser').val(), moment().startOf(new Date()).fromNow())//make sure your printing out a string.
                    }
                )
                },
                function (error) {
                    console.log("STOMP protocol error "+error);
            })

};




function sendName() {
    sendForm();
    sendPostToPostDao();

}
function sendForm(){
    stompClient.send("/thread/liveThread",{},$('#description').val());
}; //This will send to the subscription in the socket
// This code comes from the posts.js file REMEMBER THAT!!!!!!

// $("#postsForm").submit(function(event) {
//     // Prevent the form from submitting via the browser.
//     event.preventDefault();
//     sendName();
// });
s
function sendPostToPostDao(){
// This code comes from the posts.js file REMEMBER THAT!!!!!!
    var threadId = $('#thread').val();
    var description=$('#description').val();
    $.ajax({
        type : "post",
        url : "/api/post/save/" + threadId+'/'+description,
        data : $("#postsForm").serialize()
    });
    // resetData();
} //This will send to the server to be stored.

function resetData(){
    $("#description").val("");
}


function showGreeting(message, user, date) {
    $("#greetings").append("<tr scope='row'><td>" + message + "</td><td>By: "+user+"</td><td>"+date+"</td></tr>");
}

$(function () {
    $("#postsForm").on('submit', function (e) {
        e.preventDefault();
    });

    $("#send").click(function (e) {
        e.preventDefault();
        sendName();
        resetData();
    });
});



