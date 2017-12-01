$( document ).ready(function() {

    // SUBMIT FORM
    $("#postsForm").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });


    function ajaxPost(){

        var threadId = $('#thread').val();
        var description=$('#description').val();

        //
        // var formData = {
        //     //need to find out how to fill out id.
        //     description:$("#description").val(),
        //     image_url_path:null,
        //     _csrf: $('#csrf').val()
        // };
        // DO POST
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

                console.log(result);
            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });

        // Reset FormData after Posting
        // resetData();

    }

    function resetData(){
        $("#description").val("");
    }
});