package main.java.app.web;

import main.java.Config;
import main.java.app.model.*;
import main.java.app.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html");
//        response.setContentType("text/html, charset=UTF-8");
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            response.getWriter().write(printTableResumeById(String.format("%s%s", "Info. Resume uuid: ", uuid), uuid));
        } else {
            response.getWriter().write(printTableAllResume("List all resumes"));
        }
    }

    //lines - строки <tr>
    //columns - столбцы <td>
    //System.lineSeparator() - перевод коретки
    private String printTableAllResume(final String textTitle) {
        String bodyTable = "";
        List<Resume> resumes = storage.getAllSorted();
        bodyTable += "<td><b>" + "Resumes" + "</b></td>\n";
        for (int i = 0; i < resumes.size(); i++) {
            bodyTable += "<tr>\n";
            bodyTable += "<td>" + (1 + i) + "</td>\n";
            bodyTable += "<td> <a href=\"resume?uuid=" + resumes.get(i).getUuid() + "\">" + resumes.get(i).getFullName() + " </a></td>\n";
            bodyTable += "<td>" + resumes.get(i).getContacts().get(ContactType.PHONE) + "</td>\n";

        }
        bodyTable += "</tr>\n";
        String table = String.format("<head>" + "<title>" + "%s" + "</title>" + "</head>" + "<body>\n" +
                "<table border=\"1\">\n" + "%s" + "</table>\n" + "</body>", textTitle, bodyTable);
        return table;
    }

    private String printTableResumeById(final String textTitle, final String uuid) {
        String bodyTable = "";
        Resume resume = storage.get(uuid);
        bodyTable += "<td><b>" + "Resume" + "</b></td>\n";
        for (int i = 0; i < 1; i++) {
            bodyTable += "<tr>\n";
            bodyTable += "<td>" + resume.getUuid() + "</td>\n";
            bodyTable += "<td>" + resume.getFullName() + "</td>\n";
            bodyTable += "</tr>\n";

            bodyTable += "<td><b>" + "CONTACTS" + "</b></td>\n";
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                bodyTable += "<tr>\n";
                bodyTable += "<td>" + entry.getKey() + "</td>\n";
                bodyTable += "<td>" + entry.getValue() + "</td>\n";
                bodyTable += "</tr>\n";
            }
            bodyTable += "<td width=\"80%\"><b>" + "SECTIONS" + "</b></td>\n";
            bodyTable += "<tr>\n";
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                bodyTable += "<td><b>" + entry.getKey().getTitle() + "</b></td>\n";
                bodyTable += "<tr>\n";
                if (entry.getValue() instanceof ListSection) {
                    for (String item : ((ListSection) entry.getValue()).getItems()) {
                        bodyTable += "<tr>\n";
                        bodyTable += "<td>" + "* " + item + "</td>\n";
                        bodyTable += "</tr>\n";
                    }

                } else {
                    bodyTable += "<td>" + entry.getValue() + "</td>\n";
                }
                bodyTable += "</tr>\n";
            }
        }

        String title = String.format("<head>" + "<title>" + "%s" + "</title>" + "</head>" + "<body>\n" +
                "<table border=\"1\" width=\"1024\" height=\"480\">\n" + "%s" + "</table>\n" + "</body>", textTitle, bodyTable);
        return title;
    }
}
