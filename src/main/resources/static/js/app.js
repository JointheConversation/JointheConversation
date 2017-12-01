var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function ajaxStart(){
    $.ajax({
        type: "GET",
        url: '/api/post/all',
        success: function (result) {
            if(result.status=="Done") {
                var custList = "";
                $.each(result.data, function (i, post) {
                    showGreeting(post.description)
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

            // var socket = new SockJS('/gs-guide-websocket');
            ws=new SockJS('/questions');
            stompClient = Stomp.over(ws);

            stompClient.connect({}, function (frame) {
                setConnected(true);
                ajaxStart(); //Starts Ajax request get the descriptions
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/questions', function (message) {
                        console.log("Received "+message);


                    // ws.onmessage=function (message) {
                        showGreeting(message.body)//make sure your printing out a string.

                    // }

                    //     ws.onmessage(post);
                    // showGreeting(JSON.parse((post.body).description));
                    }
                )

                },
                function (error) {
                    console.log("STOMP protocol error "+error);
            })

};


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    sendForm();
    sendPost();


}
function sendForm(){
    stompClient.send("/app/questions",{},$('#description').val());
}; //This will send to the subscription in the socket
// This code comes from the posts.js file REMEMBER THAT!!!!!!

$("#postsForm").submit(function(event) {
    // Prevent the form from submitting via the browser.
    event.preventDefault();
    sendName();
});

function sendPost(){
    // var threadId = $('#thread').val();
    // $.ajax({
    //     type : "GET",
    //     contentType : "application/json",
    //     url : "api/post/all",
    //     data : $("#postsForm").serialize(),
    //     dataType : 'json',
    //     success : function (post) {
    //         // console.log(post);
    //         stompClient.send("/app/livefeed", {}, JSON.stringify({
    //             'posts': post.description
    //         }));
    //     },
    //     error : function(e) {
    //         console.log("ERROR: ", e);
    //     }
    // });

// This code comes from the posts.js file REMEMBER THAT!!!!!!
    var threadId = $('#thread').val();
    var description=$('#description').val();
    $.ajax({


        type : "post",
        url : "/api/post/save/" + threadId+'/'+description,
        data : $("#postsForm").serialize(),
        success : function(result) {
            if(result.status == "Done"){
                $("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" +
                    "Post Successfully! <br>" +
                    "---> Post Description = " +
                    result.data.description + " ,User = " + result.data.user.username + "</p>");
            }else{
                $("#postResultDiv").html("<strong>Error</strong>");
            }
        },
        error : function(e) {
            alert("Post field can't be empty!")
            console.log("ERROR: ", e);
        }
    });
    // resetData();
} //This will send to the server to be stored.

function resetData(){
    $("#description").val("");
}


function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $(".form-post").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function (e) {
        e.preventDefault();
        sendName();
    });
});

// PREPARE FORM DATA
// var formData = {
//     id: $(), //need to find out how to fill out id.
//     description: $("#description").val(),
//     image_url_path: null,
//     date: new Date(),
//     user: $("#user").val, //will get the val of the logged in user
//     thread: $
// };



