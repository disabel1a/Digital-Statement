package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.controllers.NamedUserController;
import model.users.NamedUser;
import model.users.Student;
import model.users.Teacher;

@WebServlet(urlPatterns = {"/user-info"})
public class UserInfoServlet extends HttpServlet {
    private NamedUserController userController;
    private String mainFolder;

    @Override
    public void init() throws ServletException {
        mainFolder = "G:\\VisualStudioCode\\Projects\\Java\\digital-statement\\digital-statement\\data";
        userController = new NamedUserController(mainFolder);
    }

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
        req.getRequestDispatcher("/static/user-info.html").forward(req, resp);;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        boolean isTeacher = (Boolean) session.getAttribute("isTeacher");
        NamedUser namedUser = (NamedUser) userController.getUserInfo((String)session.getAttribute("login"));

        Gson gson = new Gson();
        String jsonUser = new String();

        if (isTeacher) {
            jsonUser = gson.toJson((Teacher) namedUser);
        } else {
            jsonUser = gson.toJson((Student) namedUser);
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(jsonUser);
        out.flush();
    }
}
