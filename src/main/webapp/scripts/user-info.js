document.addEventListener("DOMContentLoaded", function() {
    fetch('/digital-statement/user-info', {
        method: 'POST'
    })
    .then(function(response) {
        if (!response.ok) {
        throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(function(data) {
        var user = data;
        document.getElementById();
    })
    .catch(function(error) {
        console.error('Error:', error);
    });

    // fetch('/digital-statement/table-api', {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify(actions)
    // });
});