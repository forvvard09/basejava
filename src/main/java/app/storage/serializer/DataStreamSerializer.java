package main.java.app.storage.serializer;

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
            readContact(dis, resume);
            readSection(dis, resume);
            return resume;
        }
    }



    //write

    private void writeSections(DataOutputStream dos, Resume newResume) throws IOException {
        writeWithException(newResume.getSections().entrySet(), dos, sections -> {
            String nameSection = sections.getKey().name();
            dos.writeUTF(nameSection);
            switch (nameSection) {
                case "PERSONAL":
                case "OBJECTIVE":
                    dos.writeUTF(sections.getValue().toString());
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    writeWithException(((ListSection) sections.getValue()).getItems(), dos, dos::writeUTF);
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
        writeWithException(section.getItemsPeriod(), dos, listPeriods -> {
            dos.writeUTF(listPeriods.getHomePage().getName());
            dos.writeUTF(listPeriods.getHomePage().getUrl());
            writePositionHeld(dos, listPeriods);
        });
    }

    private void writePositionHeld(DataOutputStream dos, OrganizationPeriod tt) throws IOException {
        writeWithException(tt.getListPositionHeld(), dos, currentHeld -> {
            dos.writeUTF(currentHeld.getStartData().toString());
            dos.writeUTF(currentHeld.getFinishData().toString());
            dos.writeUTF(currentHeld.getTitle());
            dos.writeUTF(currentHeld.getDescription());
        });
    }

    private void writeContacts(DataOutputStream dos, Resume newResume) throws IOException {
        writeWithException(newResume.getContacts().entrySet(), dos, contact -> {
            dos.writeUTF(contact.getKey().name());
            dos.writeUTF(contact.getValue());
        });
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, MyConsumer<T> myConsumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            myConsumer.accept(t);
        }
    }

    //read

    private void readContact(DataInputStream dis, Resume resume) throws IOException {
        readWithException(dis, ()-> {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        });
    }

    private <T> List<T> readListSection(DataInputStream dis, MySupplier<T> mySupplier ) throws IOException {
        int sizeList = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < sizeList; i++) {
            list.add(mySupplier.get());
        }
        return list;
    }

    private void readSection(DataInputStream dis, Resume resume) throws IOException {
        readWithException(dis, ()-> {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            resume.setSection(sectionType, getSection(sectionType.name(), dis));
        });
    }

    private AbstractSection getSection(String nameSection, DataInputStream dis) throws IOException {
        AbstractSection currentAbstractSection = null;
        switch (nameSection) {
            case "PERSONAL":
            case "OBJECTIVE":
                currentAbstractSection = new TextSection(dis.readUTF());
                break;
            case "ACHIEVEMENT":
            case "QUALIFICATIONS":
                currentAbstractSection = new ListSection(readListSection(dis, dis::readUTF));
                break;
            case "EXPERIENCE":
            case "EDUCATION":
                currentAbstractSection = new PeriodSection(readPeriodSection(dis));
                break;
            default:
                break;
        }
        return currentAbstractSection;
    }

    private void readWithException(DataInputStream dis, Reader mySupplier) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            mySupplier.apply();
        }
    }

    private List<OrganizationPeriod> readPeriodSection(DataInputStream dis) throws IOException {
        int countOrganizationPeriodList = dis.readInt();
        List<OrganizationPeriod> organizationPeriodList = new ArrayList<>();
        readOrganizationPeriod(dis, countOrganizationPeriodList, organizationPeriodList);
        return organizationPeriodList;
    }

    private void readOrganizationPeriod(DataInputStream dis, int countOrganizationPeriodList, List<OrganizationPeriod> organizationPeriodList) throws IOException {
        for (int j = 0; j < countOrganizationPeriodList; j++) {
            Link link = new Link(dis.readUTF(), dis.readUTF());
            organizationPeriodList.add(new OrganizationPeriod(link, getListPositionHeld(dis)));
        }
    }

    private List<OrganizationPeriod.PositionHeld> getListPositionHeld (DataInputStream dis) throws IOException {
        List<OrganizationPeriod.PositionHeld> listPositionHeld = new ArrayList<>();
        readWithException(dis, ()-> {
            YearMonth startData = YearMonth.parse(dis.readUTF());
            YearMonth finishData = YearMonth.parse(dis.readUTF());
            String title = dis.readUTF();
            String description = dis.readUTF();
            listPositionHeld.add(new OrganizationPeriod.PositionHeld(startData, finishData, title, description));
        });
        return listPositionHeld;
    }
}

