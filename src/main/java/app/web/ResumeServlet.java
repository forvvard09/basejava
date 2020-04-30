package main.java.app.web;

import main.java.Config;
import main.java.app.model.*;
import main.java.app.storage.Storage;
import main.java.app.util.DateUtil;
import main.java.app.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        boolean isCreateResume = false;

        request.setCharacterEncoding("UTF-8"); // принимаем русские буквы
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume editResume;
        isCreateResume = (uuid == null || uuid.length() == 0);
        if (isCreateResume) {
            editResume = new Resume(fullName);
        } else {
            editResume = storage.get(uuid);
            editResume.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                editResume.removeContact(type);
            } else {
                editResume.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                editResume.removeSection(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        editResume.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        editResume.setSection(type, new ListSection(value.split(System.lineSeparator())));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> org = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] description = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < description.length; j++) {
                                    if (!HtmlUtil.isEmpty(description[j])) {
                                        positions.add(new Organization.Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], description[j]));
                                    }
                                }
                                org.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        editResume.setSection(type, new OrganizationSection(org));
                        break;
                }
            }
        }
        if (isCreateResume) {
            storage.save(editResume);
        } else {
            storage.update(editResume);
        }
        response.sendRedirect("resume");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume = null;
        switch (action) {
            case ("delete"):
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case ("view"):
                resume = storage.get(uuid);
                break;
            case ("add"):
                resume = Resume.EMPTY;
                break;
            case ("edit"):
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSections().get(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:

                            OrganizationSection orgSection = (OrganizationSection) resume.getSections().get(type);
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization org : orgSection.getListOrganizations()) {
                                    List<Organization.Position> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Organization.Position.EMPTY);
                                    emptyFirstPositions.addAll(org.getListPosition());
                                    emptyFirstOrganizations.add(new Organization(org.getHomePage(), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganizations);
                            break;
                    }
                    resume.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + "is legal.");

        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
