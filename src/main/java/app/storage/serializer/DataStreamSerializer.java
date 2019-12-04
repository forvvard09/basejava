package main.java.app.storage.serializer;

import main.java.app.exception.StorageException;
import main.java.app.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializerStrategy {
    @Override
    public void doWrite(Resume newResume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(newResume.getUuid());
            dos.writeUTF(newResume.getFullName());
            dos.writeInt(newResume.getContacts().size());
            writeContacts(dos, newResume.getContacts());
            dos.writeInt(newResume.getSections().size());
            writeSections(dos, newResume.getSections());
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int countContacts = dis.readInt();
            readContact(dis, resume, countContacts);
            int countSections = dis.readInt();
            readSections(dis, resume, countSections);
            return resume;
        }
    }

    //write

    private void writeContacts(DataOutputStream dos, Map<ContactType, String> mapContacts) {
        mapContacts.forEach((k, v) -> writeTextSection(dos, k.name(), v));
    }

    private void writeSections(DataOutputStream dos, Map<SectionType, AbstractSection> mapSections) {
        mapSections.forEach((k, v) -> {
            String nameSection = k.name();
            if (nameSection.equals("PERSONAL") || nameSection.equals("OBJECTIVE")) {
                writeTextSection(dos, nameSection, v.toString());
            } else if (nameSection.equals("ACHIEVEMENT") || nameSection.equals("QUALIFICATIONS")) {
                writeListSection(dos, nameSection, (ListSection) v);
            } else if (nameSection.equals("EXPERIENCE") || nameSection.equals("EDUCATION")) {
                writePeriodSection(dos, nameSection, (PeriodSection) v);
            }
        });
    }

    private void writeTextSection(DataOutputStream dos, String nameSection, String textSection) {
        try {
            dos.writeUTF(nameSection);
            dos.writeUTF(textSection);
        } catch (IOException e) {
            throw new StorageException("Global error be write TextSection", e);
        }
    }

    private void writeListSection(DataOutputStream dos, String nameSection, ListSection listSection) {
        try {
            dos.writeUTF(nameSection);
            int countRecords = listSection.getCountItems();
            dos.writeInt(countRecords);
            for(int i=0; i < countRecords; i++) {
                dos.writeUTF(listSection.getItems().get(i));
            }
        } catch (IOException e) {
            throw new StorageException("Global error be write ListSection", e);
        }
    }

    private void writePeriodSection(DataOutputStream dos, String nameSection, PeriodSection periodSection) {
        try {
            dos.writeUTF(nameSection);
            int countOrganizationPeriodList = periodSection.cuntPeriods();
            dos.writeInt(countOrganizationPeriodList);
            writeOrganizationPeriod(dos, periodSection.getItemsPeriod());

        } catch (IOException e) {
            throw new StorageException("Global error be write PeriodSection", e);
        }
    }

    private void writeOrganizationPeriod(DataOutputStream dos, List<OrganizationPeriod> organizationPeriodList) throws IOException {
        for (OrganizationPeriod items : organizationPeriodList) {
            dos.writeUTF(items.getHomePage().getName());
            dos.writeUTF(items.getHomePage().getUrl());
            dos.writeInt(items.getListPositionHeld().size());
            writePositionHeld(dos, items);
        }
    }

    private void writePositionHeld(DataOutputStream dos, OrganizationPeriod itemOrganizationPeriod) throws IOException {
        for (OrganizationPeriod.PositionHeld positionHeld : itemOrganizationPeriod.getListPositionHeld()) {
            dos.writeUTF(positionHeld.getStartData().toString());
            dos.writeUTF(positionHeld.getFinishData().toString());
            dos.writeUTF(positionHeld.getTitle());
            dos.writeUTF(positionHeld.getDescription());
        }
    }

    //read

    private void readSections(DataInputStream dis, Resume resume, int countSections) throws IOException {
        for (int i = 0; i < countSections; i++) {
            String nameSection = dis.readUTF();
            if (nameSection.equals("PERSONAL") || nameSection.equals("OBJECTIVE")) {
                resume.setSection(SectionType.valueOf(nameSection), (new TextSection(dis.readUTF())));
            } else if (nameSection.equals("ACHIEVEMENT") || nameSection.equals("QUALIFICATIONS")) {
                int countRecords = dis.readInt();
                resume.setSection(SectionType.valueOf(nameSection), new ListSection(readListSection(dis, countRecords)));
            } else if (nameSection.equals("EXPERIENCE") || nameSection.equals("EDUCATION")) {
                int countOrganizationPeriodList = dis.readInt();
                resume.setSection(SectionType.valueOf(nameSection), new PeriodSection(readPeriodSection(dis, countOrganizationPeriodList)));
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
        for(int j=0; j < countRecords; j++) {
            list.add(dis.readUTF());
        }
        return list;
    }

    private void readContact(DataInputStream dis, Resume resume, int size) throws IOException {
        for (int i = 0; i < size; i++) {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }


}

