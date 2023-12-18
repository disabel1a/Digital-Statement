package servlets;

import java.io.IOException;
import java.util.ArrayList;
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
import model.users.NamedUser;
import model.users.Teacher;

@WebServlet(urlPatterns = {"/home-page"})
public class HomePageServlet extends HttpServlet {
    private NamedUserController userController;
    private String mainFolder;

    @Override
    public void init() throws ServletException {
        mainFolder = "G:\\VisualStudioCode\\Projects\\Java\\digital-statement\\digital-statement\\data";
        userController = new NamedUserController(mainFolder);
    }

    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        if (arg0.getSession(false) == null || arg0.getSession(false).getAttribute("login") == null) {
            arg0.getRequestDispatcher("/static/auth-error.html").forward(arg0, arg1);
            return;
        }
        super.service(arg0, arg1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String login = (String) session.getAttribute("login");

        boolean isTeacher = false;
        NamedUser currentUser = null;
        ArrayList<String> guiElements = new ArrayList<>();

        currentUser = (NamedUser) userController.getUserInfo(login);

        Gson gson = new Gson();

        if (currentUser instanceof Teacher) {
            guiElements.add("Ведомости");
            guiElements.add("Создать ведомость");
            isTeacher = true;
        } else {
            userController.refreshStatements(currentUser);
            for (String v : currentUser.getStatements()) {
                guiElements.add(v);
            }
        }

        session.setAttribute("isTeacher", isTeacher);
        Cookie isTeacherCookie = new Cookie("isTeacher", String.valueOf(isTeacher));

        String guiElementsBase64 = Base64.getEncoder().encodeToString(gson.toJson(guiElements).getBytes("UTF-8"));
        Cookie guiElementsCookie = new Cookie("guiElements", guiElementsBase64);

        resp.addCookie(isTeacherCookie);
        resp.addCookie(guiElementsCookie);

        req.getRequestDispatcher("/static/home-page.html").forward(req, resp);
    }
}
