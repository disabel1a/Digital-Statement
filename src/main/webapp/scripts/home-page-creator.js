document.addEventListener("DOMContentLoaded", function() {
    const guiElementsCookie = getCookieValue("guiElements");
    const decodedJson = decodeURIComponent(escape(atob(guiElementsCookie)));
    const guiElements = JSON.parse(decodedJson);

    console.log(guiElements);

    guiElements.forEach(element => {
        var str;
        if (element.includes('.txt'))
            str = element.substring(0, element.indexOf('-'));
        else
            str = element;

        var form = document.createElement('form');
        var content = document.createElement('p');
        content.textContent = str;
        form.appendChild(content);

        form.className = 'button';
        form.method = 'get';

        if (str == 'Ведомости') {
            form.action = '/digital-statement/subjects-page';
        }
        else if (str == 'Создать ведомость') {
            form.action = '/digital-statement/statement-creator';
        }
        else {
            var hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.name = 'src';
            hiddenInput.value = element;
            form.appendChild(hiddenInput);
            form.action = '/digital-statement/statement-page';
        }

        form.addEventListener("click", function(event) {
            event.preventDefault();
            form.submit();
        });
        
        document.getElementsByTagName('aside')[0].appendChild(form);
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