package main.java.app.web;

import main.java.Config;
import main.java.app.model.ContactType;
import main.java.app.model.Resume;
import main.java.app.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    // предпочительнее делать так
    //private Storage storage = Config.get().getStorage();

    //в качестве примера
    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.get().getStorage();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // принимаем русские буквы
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume editResume = storage.get(uuid);
        editResume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                editResume.setContact(type, value);
            } else {
                editResume.removeContact(type);
            }
        }
        storage.update(editResume);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case ("delete"):
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case ("view"):
            case ("edit"):
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + "is legal.");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }
}
