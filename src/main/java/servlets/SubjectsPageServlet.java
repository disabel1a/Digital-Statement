package servlets;

import java.io.IOException;
import java.util.Base64;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.controllers.NamedUserController;
import model.users.Teacher;

@WebServlet(urlPatterns = {"/subjects-page"})
public class SubjectsPageServlet extends HttpServlet {
    private NamedUserController userController;
    private String mainFolder;

    @Override
    public void init() throws ServletException {
        mainFolder = "G:\\VisualStudioCode\\Projects\\Java\\digital-statement\\digital-statement\\data";
        //mainFolder = "D:\\VSCode\\Projects\\Digital-Statement\\data";
        userController = new NamedUserController(mainFolder);
    }

    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        HttpSession session = arg0.getSession();
        if (session.getAttribute("login") != null) {
            super.service(arg0, arg1);
        } else {
            arg1.sendRedirect("/digital-statement/static/auth-error.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession(false).getAttribute("login");
        Teacher teacher = (Teacher) userController.getUserInfo(login);

        userController.refreshStatements(teacher);

        Gson gson = new Gson();

        String subjectsBase64 = Base64.getEncoder().encodeToString(gson.toJson(teacher.getSubjects()).getBytes("UTF-8"));
        String statementsBase64 = Base64.getEncoder().encodeToString(gson.toJson(teacher.getStatements()).getBytes("UTF-8"));

        Cookie subjectsCookie = new Cookie("subjects", subjectsBase64);
        Cookie statementsCookie = new Cookie("statements", statementsBase64);

        resp.addCookie(subjectsCookie);
        resp.addCookie(statementsCookie);

        req.getRequestDispatcher("/static/subjects-page.html").forward(req, resp);
    }
}
