package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/statement-page"})
public class StatementPageServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        if (arg0.getSession(false) == null || arg0.getSession().getAttribute("login") == null) {
            arg0.getRequestDispatcher("/static/auth-error.html").forward(arg0, arg1);
            return;
        }
        super.service(arg0, arg1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/static/statement-page.html").forward(req, resp);
    }
}
