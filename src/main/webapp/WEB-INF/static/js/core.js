function loadTasks() {
    $.ajax({
        url: "/tasks",
        dataType: "html",
        beforeSend: function (data) {
            $('#ajax-tasks').html('');
        },
        success: function (data) {
            $('#ajax-tasks').html(data);
        }
    });
    return false;
}

function loadForm() {
    $.ajax({
        url: "/tasks/form",
        dataType: "html",
        beforeSend: function (data) {
            $('#ajax-form').html('');
        },
        success: function (data) {
            $('#ajax-form').html(data);
        }
    });
    return false;
}

function createTask() {
    $(".ajaxCreateTask").unbind("click");
    $(".ajaxCreateTask").bind("click", function () {

        let taskDescription = $('#taskDescription').val();
        let taskStatus = $('#taskStatus').val();

        if (taskDescription == "") {
            $("#descript-alert").html("<div class=\"alert alert-danger\"><label>Добавьте описание!</label></div>");
            return false;
        } else {
            $("#descript-alert").html("");
        }

        $.ajax({
            url: "/tasks/create",
            type:"POST",
            async:false,
            dataType: "html",
            data: {
              "taskDescription":taskDescription,
              "taskStatus":taskStatus
            },
            beforeSend: function (data) {
                $('#ajax-form').html('');
            },
            success: function (data) {
                loadTasks();
                loadForm();
            }
        });
        return false;
    });
}

function editTask() {
    $(".btnEdit").unbind("click");
    $(".btnEdit").bind("click", function () {

        let taskId = $(this).attr("data-id");

        $.ajax({
            url: "/tasks/edit",
            type:"POST",
            async:false,
            dataType: "html",
            data: {
                "taskId":taskId
            },
            beforeSend: function (data) {
                $('#ajax-tasks').html('');
            },
            success: function (data) {
                $('#ajax-tasks').html(data);
            }
        });
        return false;
    });
}

function updateTask() {
    $(".ajaxUpdateTask").unbind("click");
    $(".ajaxUpdateTask").bind("click", function () {

        let taskId = $('#taskIdEdit').val();
        let taskDescription = $('#taskDescriptionEdit').val();
        let taskStatus = $('#taskStatusEdit').val();

        if (taskDescription == "") {
            $("#editDescript-alert").html("<div class=\"alert alert-danger\"><label>Добавьте описание!</label></div>");
            return false;
        } else {
            $("#editDescript-alert").html("");
        }

        $.ajax({
            url: "/tasks/update",
            type:"POST",
            async:false,
            dataType: "html",
            data: {
                "taskId":taskId,
                "taskDescription":taskDescription,
                "taskStatus":taskStatus
            },
            beforeSend: function (data) {
                $('#ajax-form').html('');
            },
            success: function (data) {
                loadTasks();
                loadForm();
            }
        });
        return false;
    });
}
function deleteTask() {
    $(".btnDelete").unbind("click");
    $(".btnDelete").bind("click", function () {

        let taskId = $(this).attr("data-id");

        $.ajax({
            url: "/tasks/delete",
            type:"POST",
            async:false,
            dataType: "html",
            data: {
                "taskId":taskId
            },
            beforeSend: function (data) {
                $('#ajax-form').html('');
            },
            success: function (data) {
                loadTasks();
                loadForm();
            }
        });
        return false;
    });
}

function editFormBack() {
    $(".btnEditFormBack").unbind("click");
    $(".btnEditFormBack").bind("click", function () {
        loadTasks();
        loadForm();
        return false;
    });
}

function pagination() {
    $(".ajaxPagination").unbind("click");
    $(".ajaxPagination").bind("click", function () {

        let url = $(this).attr("href")

        $.ajax({
            url: url,
            dataType: "html",
            beforeSend: function (data) {
                $('#ajax-tasks').html('');
            },
            success: function (data) {
                $('#ajax-tasks').html(data);
            }
        });
        return false;
    });
}