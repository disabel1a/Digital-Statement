document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const srcParam = urlParams.get('src');

    const url = "/digital-statement/table-api?src=" + srcParam;

    fetch(url)
    .then(function(response) {
        if (!response.ok) {
        throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(function(data) {
        console.log('Response data:', data);
        const tableData = data;
        var tbody = document.createElement('tbody');
        tableData.forEach((subarray, index) => {
            var row = document.createElement('tr');
            if (index === 0) {
                subarray.forEach(element => {
                    var cell = document.createElement('th');
                    cell.textContent = element.content;
                    row.appendChild(cell);
                });
            } else {
                subarray.forEach(element => {
                    var cell = document.createElement('td');
                    cell.textContent = element.content;
                    row.appendChild(cell);
                });
            }
            tbody.appendChild(row);
        });
        document.getElementById("statement-table").appendChild(tbody);

        if(getCookieValue("isTeacher") === "false") {
            document.getElementById("opt-button").style.display = "none";
            document.getElementById("add-row").style.display = "none";
            document.getElementById("add-column").style.display = "none";
            return;
        }

        const script = document.createElement('script');
        script.src = "\\digital-statement\\scripts\\table-controller.js";
        document.body.appendChild(script);
    })
    .catch(function(error) {
        console.error('Error:', error);
    });
});

function getCookieValue(cookieName) {
    const name = cookieName + "=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');
    for (let i = 0; i < cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}