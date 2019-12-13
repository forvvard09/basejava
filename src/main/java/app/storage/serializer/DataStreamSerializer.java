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
              //write contacts
              writeCollectionWithException(newResume.getContacts().entrySet(), dos, item -> {
                  dos.writeUTF(item.getKey().name());
                  dos.writeUTF(item.getValue());
              });

            writeSections(dos, newResume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            //read contacts
            readItemsWithException(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readSection(dis, resume);
            return resume;
        }
    }

    //write
    private <T> void writeCollectionWithException(Collection<T> collection, DataOutputStream dos, ElementWriter<T> myConsumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            myConsumer.write(t);
        }
    }

    private void writeSections(DataOutputStream dos, Resume newResume) throws IOException {
        writeCollectionWithException(newResume.getSections().entrySet(), dos, sections -> {
            String nameSection = sections.getKey().name();
            dos.writeUTF(nameSection);
            switch (nameSection) {
                case "PERSONAL":
                case "OBJECTIVE":
                    dos.writeUTF(sections.getValue().toString());
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    writeCollectionWithException(((ListSection) sections.getValue()).getItems(), dos, item -> dos.writeUTF(item));
                    break;
                case "EXPERIENCE":
                case "EDUCATION":
                    writeOrganizationSection(dos, (OrganizationSection) sections.getValue());
                    break;
                default:
                    break;
            }
        });
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection section) throws IOException {
        writeCollectionWithException(section.getListOrganizations(), dos, oneOrg -> {
            dos.writeUTF(oneOrg.getHomePage().getName());
            dos.writeUTF(oneOrg.getHomePage().getUrl());
            writeCollectionWithException(oneOrg.getListPosition(), dos, position -> {
                dos.writeUTF(position.getStartData().toString());
                dos.writeUTF(position.getFinishData().toString());
                dos.writeUTF(position.getTitle());
                dos.writeUTF(position.getDescription());
            });
        });
    }

    //read
    private void readSection(DataInputStream dis, Resume resume) throws IOException {
        readItemsWithException(dis, () -> {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            AbstractSection currentAbstractSection = null;
            switch (sectionType.name()) {
                case "PERSONAL":
                case "OBJECTIVE":
                    currentAbstractSection = new TextSection(dis.readUTF());
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    currentAbstractSection = new ListSection(readList(dis, dis::readUTF));
                    break;
                case "EXPERIENCE":
                case "EDUCATION":
                    currentAbstractSection = new OrganizationSection(readOrganizationSection(dis));
                    break;
                default:
                    throw new IllegalStateException();
            }
            resume.setSection(sectionType, currentAbstractSection);
        });
    }

    private void readItemsWithException(DataInputStream dis, ElementProcessor elementProcessor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            elementProcessor.process();
        }
    }

    private List<Organization> readOrganizationSection(DataInputStream dis) throws IOException {
        //read List<Organization>
        return
            readList(dis, () ->
                //read one Organization
                new Organization(
                        new Link(dis.readUTF(), dis.readUTF()),
                        //read List<Organization.Position>
                        readList(dis, () ->
                                //read item held position in organization
                                new Organization.Position(
                                        YearMonth.parse(dis.readUTF()),
                                        YearMonth.parse(dis.readUTF()),
                                        dis.readUTF(),
                                        dis.readUTF()
                                )
                        )
                )
            );
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> elementReader) throws IOException {
        int sizeList = dis.readInt();
        List<T> sectionWithList = new ArrayList<>();
        for (int i = 0; i < sizeList; i++) {
            sectionWithList.add(elementReader.read());
        }
        return sectionWithList;
    }

    @FunctionalInterface
    private interface ElementReader<T> {
        T read() throws IOException;
    }
}

