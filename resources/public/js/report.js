$(document).ready(function () {

    $("#street_div").hide();

    $("#save_report").click(function () {
        $(".error").empty();
        report = {
            description: $('#description').val(),
            type: $('#type').val(),
            city: $('#city').val(),
            street: $('#street').val()
        };
        $.ajax({
            type: "POST",
            url: "/reports/add",
            data: report,
            success: function (data) {
                window.location = "/reports";
            },
            error: function (error) {
                error.responseJSON.map(function (item) {
                    console.log(item[0], item[1]);
                    $("#error_" + item[0]).text(item[1]);
                });
            }
        });
    });
    $("#city").select2({
        multiple: false,
        closeOnSelect: true,
        ajax: {
            url: "/city/search",
            dataType: "json",
            type: "GET",
            data: function (params) {
                var queryParameters = {
                    search: params.term
                }
                return queryParameters;
            },
            processResults: function (data) {
                return {
                    results: $.map(data, function (item) {
                        console.log(item);
                        return {
                            text: item.name,
                            id: item.id
                        }
                    })
                };
            }
        }
    });
    $('#city').on('select2:select', function (e) {
        $("#street_div").show();
        var data = e.params.data;
        $('#street').select2({
            minimumInputLength: 1,
            placeholder: '',
            multiple: false,
            closeOnSelect: true,
            ajax: {
                url: "/street/search",
                dataType: "json",
                type: "GET",
                data: function (params) {
                    var queryParameters = {
                        search: params.term,
                        id: data.id
                    }
                    return queryParameters;
                },
                processResults: function (data) {
                    return {
                        results: $.map(data, function (item) {
                            return {
                                text: item.name + ' (' + item.community + ')',
                                id: item.id
                            }
                        })
                    };
                }
            }
        });
    });
});