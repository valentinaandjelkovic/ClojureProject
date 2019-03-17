$(document).ready(function () {
    $(".update").on('click', function () {
        var row = $(this).closest('tr');
        $("#street_name").val(row.find(".name").text());
        $("#street_community").val(row.find(".community").text());
        $("#street_id").val(row.attr("id"));
    });
    $(".delete").on('click', function () {
        var row = $(this).closest('tr');
        console.log("CLick");
        console.log("Id ", row.attr("id"));
        $("#delete_street_id").val(row.attr("id"));
    });
    $("#edit_button").on('click', function () {
        var id = $("#street_id").val();
        var name = $("#street_name").val();
        var community = $("#street_community").val();
        $.ajax({
            type: "POST",
            url: "/street/update",
            data: {
                id: id,
                name: name,
                community: community
            },
            success: function (data) {
                showMessageInfo("success", data.message, "message_update_div");
                setTimeout(function () {
                    $("#edit").modal("toggle");
                    window.location = "/street";
                }, 1000);
            },
            error: function (error) {
                var message = "";
                error.responseJSON.map(function (item) {
                    message += item[1] + ".";
                });
                showMessageInfo("danger", message, "message_update_div");
            }
        });
    });

    $("#delete_button").on('click', function () {
        var id = $("#delete_street_id").val();
        $.ajax({
            type: "POST",
            url: "/street/delete",
            data: {
                id: id
            },
            success: function (data) {
                $('#' + id).remove();
                showMessageInfo("success", data.message, "message_div");
            },
            error: function (error) {
                var message = "";
                error.responseJSON.map(function (item) {
                    message += item[1] + ".";
                });
                showMessageInfo("danger", message, "message_divs");
            }
        });
    });
});

function showMessageInfo(type, message, id) {
    $("#" + id).text(message);
    $("#" + id).removeClass("alert-danger");
    $("#" + id).removeClass("alert-success");
    $("#" + id).addClass("alert-" + type);
    $("#" + id).show();
}