<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statement</title>
    <link rel="stylesheet" href="\digital-statement\styles\main-style.css">
    <link rel="stylesheet" href="styles\main-style.css">
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
</head>
<body>
    <%
        String login = (String) request.getAttribute("login");
        String name = (String) request.getAttribute("name");
        String surname = (String) request.getAttribute("surname");
        String patronymic = (String) request.getAttribute("patronymic");
        String additInfo = (String) request.getAttribute("additInfo");
        Boolean isTeacher = (Boolean) session.getAttribute("isTeacher");
    %>
    <header>
        <span>Ведомость</span>
    </header>
    <aside>
        <form class="button" action="/digital-statement/home-page" method="get">На галвную</form>
        <form class="button" action="/digital-statement/user-info" method="post">Выйти</form>
    </aside>
    <main>
        <section>
            <article class="table-article">
                <div>
                    <p id="login">Логин: <%= login %></p>
                    <p id="name">Имя: <%= name %></p>
                    <p id="surname">Фамилия: <%= surname %></p>
                    <p id="patr">Отчество: <%= patronymic %></p>
                    <% if(isTeacher) { %>
                        <p id="dop-inf">Дисциплины: <%= additInfo %></p>
                    <% } else { %>
                        <p id="dop-inf">Группа: <%= additInfo %></p>
                    <% } %>
                </div>
            </article>
        </section>
    </main>
    <script>
        document.querySelectorAll(".button").forEach(button => {
            button.addEventListener("click", function(event) {
                event.preventDefault();
                button.closest("form").submit();
            });
        });
    </script>
</body>
</html>