package main.java.app.storage.serializer;

import main.java.app.model.ContactType;
import main.java.app.model.Resume;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializerStrategy {
    @Override
    public void doWrite(Resume newResume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(newResume.getUuid());
            dos.writeUTF(newResume.getFullName());
            dos.writeInt(newResume.getContacts().size());
            for (Map.Entry<ContactType, String> entry : newResume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            //TODO implements sections
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            //TODO implements sections
            return resume;
        }
    }
}

