import { getCookieValue } from "./cookie-controller.js";

document.addEventListener('DOMContentLoaded', function() {
    const subjectsCookie = getCookieValue("subjects");
    var decodedJson = decodeURIComponent(escape(atob(subjectsCookie)));
    const subjects = JSON.parse(decodedJson);

    var selector = document.getElementById('subject-selector');

    subjects.forEach(subject => {
        var option = document.createElement('option');
        option.value = subject;
        option.textContent = subject;
        selector.appendChild(option);
    });
});