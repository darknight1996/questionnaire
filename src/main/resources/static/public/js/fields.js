function openEditPopup(id, label, type, options, required, active) {

    if (type == "RADIO_BUTTON" || type == "COMBOBOX") {
        $("#optionsDivEdit").css("display", "block");
        var textAreaContent = "";
        Array.from(options).forEach(function(item) {
            textAreaContent += item;
        });
        $("#fieldEditForm textarea[name=options]").val(textAreaContent);
    } else {
        $("#optionsDivEdit").css("display", "none");
    }

    $("#overlay").css("display", "flex");
    $("#editFieldDiv").css("display", "block");

    $("#fieldEditForm input[name=id]").val(id);
    $("#fieldEditForm input[name=label]").val(label);
    $("#fieldEditForm select[name=type]").val(type);

    if (required == 'true')
        $("#fieldEditForm input[name=required]").prop('checked', 'true');
    if (active == 'true')
        $("#fieldEditForm input[name=active]").prop('checked', 'true');

}

$(document).ready(function() {

    $("#addNewFieldButton").click(function() {
        $("#overlay").css("display", "flex");
        $("#addFieldDiv").css("display", "block");
    });

    $("#closeAdd").click(function() {
        $("#overlay").css("display", "none");
        $("#addFieldDiv").css("display", "none");
    });

    $("#closeEdit").click(function() {
        $("#overlay").css("display", "none");
        $("#editFieldDiv").css("display", "none");
    });

    $("#fieldTypeSelect").change(function(e) {
        var currentValue = e.target.options[e.target.selectedIndex].text;
        if (currentValue == "Radio Button" || currentValue == "Combobox") {
            $("#optionsDiv").css("display", "block");
        } else {
            $("#optionsDiv").css("display", "none");
        }
    });

    $("#fieldTypeSelectEdit").change(function(e) {
        var currentValue = e.target.options[e.target.selectedIndex].text;
        if (currentValue == "Radio Button" || currentValue == "Combobox") {
            $("#optionsDivEdit").css("display", "block");
        } else {
            $("#optionsDivEdit").css("display", "none");
        }
    });

});