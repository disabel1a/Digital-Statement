package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.controllers.TableController;
import model.tables.Table;

@WebServlet(urlPatterns = {"/table-api"})
public class TableAPIServlet extends HttpServlet {
    private TableController tableController;
    private String mainFolder;

    @Override
    public void init() throws ServletException {
        //mainFolder = "G:\\VisualStudioCode\\Projects\\Java\\digital-statement\\digital-statement\\data";
        mainFolder = "D:\\VSCode\\Projects\\Digital-Statement\\data";
        tableController = new TableController(mainFolder);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("src");
        Table table = tableController.loadTable(fileName);
        req.getSession(false).setAttribute("src", fileName);

        Gson gson = new Gson();

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(gson.toJson(table.getTable()));
        out.flush(); 
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = (String) req.getSession(false).getAttribute("src");
 
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = req.getReader();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String jsonString = stringBuilder.toString();
        System.out.println("POST: jsonString: " + jsonString);

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> actionsList = gson.fromJson(jsonString, listType);
        
        actionsList.forEach(str -> {
            System.out.println("String: " + str);
        });

        try {
            tableController.run(fileName, actionsList);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
