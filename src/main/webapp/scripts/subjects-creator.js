document.addEventListener("DOMContentLoaded", function() {
    const subjectsCookie = getCookieValue("subjects");
    var decodedJson = decodeURIComponent(escape(atob(subjectsCookie)));
    const subjects = JSON.parse(decodedJson);

    const statementsCookie = getCookieValue("statements");
    decodedJson = decodeURIComponent(escape(atob(statementsCookie)));
    const statements = JSON.parse(decodedJson);

    var subjectsArticle = document.getElementById("teacher-subjects");
    var list = document.createElement('ul');
    subjects.forEach(element => {
        var listElement = document.createElement('li');
        listElement.className = "subject";

        var subjName = document.createElement('span');
        subjName.textContent = element;

        listElement.appendChild(subjName);
        listElement.appendChild(document.createElement('ul'));

        list.appendChild(listElement);
    });

    statements.forEach(element => {
        var listElements = list.getElementsByClassName("subject");
        for (let index = 0; index < listElements.length; index++) {
            var statementSubjName = element.substring(0, element.indexOf('-'));
            var spanContent = listElements[index].getElementsByTagName('span')[0].textContent
            if (spanContent === statementSubjName) {
                var subElement = document.createElement('li');
                subElement.textContent = element.substring(0, element.indexOf('.'));

                subElement.addEventListener("click", function() {
                    window.location.href = "/digital-statement/statement-page?src=" + element;
                });

                listElements[index].getElementsByTagName('ul')[0].appendChild(subElement);
                continue;
            }
        }
    });

    subjectsArticle.appendChild(list);
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