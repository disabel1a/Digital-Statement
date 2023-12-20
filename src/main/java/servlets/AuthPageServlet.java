package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.auth.AuthController;
import model.users.User;

@WebServlet(urlPatterns = {"/auth-page"})
public class AuthPageServlet extends HttpServlet{
    private String mainFolder;
    private AuthController authController;

    @Override
    public void init() throws ServletException {
        try {
            mainFolder = "G:\\VisualStudioCode\\Projects\\Java\\digital-statement\\digital-statement\\data";
            //mainFolder = "D:\\VSCode\\Projects\\Digital-Statement\\data";
            authController = new AuthController(mainFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("login") != null) {
            resp.sendRedirect("/digital-statement/home-page");
            return;
        }
        req.getRequestDispatcher("/static/auth.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (authController.verification(new User(login, password))) {
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            resp.sendRedirect("/digital-statement/home-page");
        } else {
            req.getRequestDispatcher("/static/auth-error.html").forward(req, resp);
        }
    }
}
