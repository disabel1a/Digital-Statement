package servlets;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.TableFileManager;
import model.tables.Table;

@WebServlet(urlPatterns = {"/table-creator"})
public class CreateTableServlet extends HttpServlet {
    private TableFileManager fileManager;
    private String mainFolder;

    @Override
    public void init() throws ServletException {
        mainFolder = "G:\\VisualStudioCode\\Projects\\Java\\digital-statement\\digital-statement\\data";
        //mainFolder = "D:\\VSCode\\Projects\\Digital-Statement\\data";
        fileManager = new TableFileManager(mainFolder);
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
        req.getRequestDispatcher("/static/create-table.html").forward(req, resp);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subject = req.getParameter("subject");
        String group = req.getParameter("group");

        String src = subject + "-" + group + ".txt";

        if(!new File(mainFolder + "\\" + src).exists()) {
            Table table = new Table(1, 1);
            fileManager.saveToTextFile(table, src);
        }

        String redirectURL = "/digital-statement/statement-page";
        redirectURL += "?src=" + URLEncoder.encode(src, "UTF-8");

        resp.sendRedirect(redirectURL);
    }
}
