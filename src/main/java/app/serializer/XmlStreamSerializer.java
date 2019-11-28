package main.java.app.serializer;

import main.java.app.model.*;
import main.java.app.storage.serializer.StreamSerializerStrategy;
import main.java.app.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializerStrategy {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
            Resume.class, OrganizationPeriod.class, Link.class,
            TextSection.class, PeriodSection.class, ListSection.class, OrganizationPeriod.PositionHeld .class
        );
    }

    @Override
    public void doWrite(Resume newResume, OutputStream os) throws IOException {
        try(Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(newResume, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
