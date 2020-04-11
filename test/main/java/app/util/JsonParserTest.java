package main.java.app.util;

import main.java.app.model.AbstractSection;
import main.java.app.model.Resume;
import main.java.app.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import static main.java.app.storage.ResumeTestData.RESUME_ONE;

public class JsonParserTest {


    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_ONE);
        System.out.println(json);
        Resume expectedResume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_ONE, expectedResume);
    }

    @Test
    public void testSection() {
        AbstractSection section = new TextSection("Section for Testing");
        String json = JsonParser.write(section, AbstractSection.class);
        System.out.println(json);
        AbstractSection expectedSection = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section, expectedSection);
    }


}