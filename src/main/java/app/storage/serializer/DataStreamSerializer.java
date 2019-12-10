package main.java.app.storage.serializer;

import main.java.app.exception.ResumeException;
import main.java.app.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DataStreamSerializer implements StreamSerializerStrategy {

    @Override
    public void doWrite(Resume newResume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(newResume.getUuid());
            dos.writeUTF(newResume.getFullName());
            writeContacts(dos, newResume);
            writeSections(dos, newResume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContact(dis, resume, dis.readInt());
            readSections(dis, resume, dis.readInt());
            return resume;
        }
    }

    //write

    private void writeSections(DataOutputStream dos, Resume newResume) throws IOException {
        writeWithExeption(newResume.getSections().entrySet(), dos, sections -> {
            String nameSection = sections.getKey().name();
            dos.writeUTF(nameSection);
            switch (nameSection) {
                case "PERSONAL":
                case "OBJECTIVE":
                    dos.writeUTF(sections.getValue().toString());
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    writeWithExeption(((ListSection) sections.getValue()).getItems(), dos, dos::writeUTF);
                    break;
                case "EXPERIENCE":
                case "EDUCATION":
                    writePeriodSection(dos, (PeriodSection) sections.getValue());
                    break;
                default:
                    break;
            }
        });
    }

    private void writePeriodSection(DataOutputStream dos, PeriodSection section) throws IOException {
        writeWithExeption(section.getItemsPeriod(), dos, listPeriods -> {
            dos.writeUTF(listPeriods.getHomePage().getName());
            dos.writeUTF(listPeriods.getHomePage().getUrl());
            writePositionHeld(dos, listPeriods);
        });
    }

    private void writePositionHeld(DataOutputStream dos, OrganizationPeriod tt) throws IOException {
        writeWithExeption(tt.getListPositionHeld(), dos, currentHeld -> {
            dos.writeUTF(currentHeld.getStartData().toString());
            dos.writeUTF(currentHeld.getFinishData().toString());
            dos.writeUTF(currentHeld.getTitle());
            dos.writeUTF(currentHeld.getDescription());
        });
    }

    private void writeContacts(DataOutputStream dos, Resume newResume) throws IOException {
        writeWithExeption(newResume.getContacts().entrySet(), dos, contact -> {
            dos.writeUTF(contact.getKey().name());
            dos.writeUTF(contact.getValue());
        });
    }

    //read

    private void readSections(DataInputStream dis, Resume resume, int countSections) throws IOException {
        for (int i = 0; i < countSections; i++) {
            String nameSection = dis.readUTF();
            AbstractSection currentAbstractSection = null;
            switch (nameSection) {
                case "PERSONAL":
                case "OBJECTIVE":
                    currentAbstractSection = new TextSection(dis.readUTF());
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    currentAbstractSection = new ListSection(readListSection(dis, dis.readInt()));
                    break;
                case "EXPERIENCE":
                case "EDUCATION":
                    currentAbstractSection = new PeriodSection(readPeriodSection(dis, dis.readInt()));
                    break;
                default:
                    break;
            }
            if (currentAbstractSection != null) {
                resume.setSection(SectionType.valueOf(nameSection), currentAbstractSection);
            } else {
                throw new ResumeException("For read section run error, nameSection: " + nameSection, new NullPointerException());
            }
        }
    }

    private List<OrganizationPeriod> readPeriodSection(DataInputStream dis, int countOrganizationPeriodList) throws IOException {
        List<OrganizationPeriod> organizationPeriodList = new ArrayList<>();
        readOrganizationPeriod(dis, countOrganizationPeriodList, organizationPeriodList);
        return organizationPeriodList;
    }

    private void readOrganizationPeriod(DataInputStream dis, int countOrganizationPeriodList, List<OrganizationPeriod> organizationPeriodList) throws IOException {
        for (int j = 0; j < countOrganizationPeriodList; j++) {
            List<OrganizationPeriod.PositionHeld> listPositionHeld = new ArrayList<>();
            Link link = new Link(dis.readUTF(), dis.readUTF());
            int countPositionHeld = dis.readInt();
            readPositionHelp(dis, listPositionHeld, countPositionHeld);
            organizationPeriodList.add(new OrganizationPeriod(link, listPositionHeld));
        }
    }

    private void readPositionHelp(DataInputStream dis, List<OrganizationPeriod.PositionHeld> listPositionHeld, int countPositionHeld) throws IOException {
        for (int k = 0; k < countPositionHeld; k++) {
            YearMonth startData = YearMonth.parse(dis.readUTF());
            YearMonth finishData = YearMonth.parse(dis.readUTF());
            String title = dis.readUTF();
            String description = dis.readUTF();
            listPositionHeld.add(new OrganizationPeriod.PositionHeld(startData, finishData, title, description));
        }
    }

    private List<String> readListSection(DataInputStream dis, int countRecords) throws IOException {
        List<String> list = new ArrayList<>();
        for (int j = 0; j < countRecords; j++) {
            list.add(dis.readUTF());
        }
        return list;
    }

    private void readContact(DataInputStream dis, Resume resume, int size) throws IOException {
        for (int i = 0; i < size; i++) {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private <T> void writeWithExeption(Collection<T> collection, DataOutputStream dos, MyConsumer<T> myConsumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            myConsumer.accept(t);
        }
    }
}

