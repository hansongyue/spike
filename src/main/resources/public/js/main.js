function parseFormValues(form) {
    let values = {};
    for (let pair of $(form).serializeArray()) {
        values[pair.name] = pair.value;
    }
    return values;
}

$(document).ready(function () {
});
