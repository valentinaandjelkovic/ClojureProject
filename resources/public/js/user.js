$(document).ready(function () {

    $("#change_password").on('click', function () {
        var password = $("#password").val();
        var new_password = $("#new_password").val();
        var repeat_password = $("#repeat_password").val();

        $.ajax({
            type: "POST",
            url: "/user/changePassword",
            data: {
                password: password,
                new_password: new_password,
                repeat_password: repeat_password
            },
            success: function (data) {
                showMessageInfo("success", data.message);
                setTimeout(function () {
                    $("#modal_password").modal("toggle");
                    window.location = "/logout";
                }, 2000);
            },
            error: function (error) {
                var message = "";
                error.responseJSON.map(function (item) {
                    message += item[1] + "\n";
                });
                showMessageInfo("danger", message);
            }
        });
    });
});

function showMessageInfo(type, message) {
    $("#message_div").text(message);
    $("#message_div").removeClass("alert-danger");
    $("#message_div").removeClass("alert-success");
    $("#message_div").addClass("alert-" + type);
    $('#message_div').show();
}