package com.gerald.noddus.personcollector.services;

import static org.hamcrest.Matchers.equalTo;

import com.gerald.noddus.entities.PersonEntity;
import com.gerald.noddus.personcollector.models.Person;
import com.gerald.noddus.personcollector.providers.ProtobufPersonSerializer;
import com.gerald.noddus.personcollector.providers.RollingFileDataAppender;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.TemporaryFolder;

public class PersonServiceImplTest {

    public static final String SAMPLE_FILE = "sample.txt";
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    private ProtobufPersonSerializer serializer = new ProtobufPersonSerializer();
    private RollingFileDataAppender appender = new RollingFileDataAppender();
    private PersonServiceImpl personService = new PersonServiceImpl();
    private File testFolder;
    private File testFile;

    @Before
    public void setUp() throws Exception {
        testFolder = temporaryFolder.newFolder();
        testFile = new File(testFolder, SAMPLE_FILE);
        appender.setFileName(SAMPLE_FILE);
        appender.setFolderPath(testFolder.getAbsolutePath());
        personService.setAppender(appender);
        personService.setSerializer(serializer);
    }

    @Test
    public void givenPersonWhenSavePersonShouldPersistPersonToFile() throws Exception {
        appender.openFile();
        personService.savePerson(createPerson(1L, "Gerald"));
        personService.savePerson(createPerson(2L, "Layo"));
        appender.closeFile();
        checkThatDataIsSaved();
    }

    private Person createPerson(long id, String name) {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        return person;
    }

    private void checkThatDataIsSaved() throws IOException {
        try(FileInputStream inputStream = new FileInputStream(testFile)){
            PersonEntity.JSONmsg personOne = PersonEntity.JSONmsg.parseDelimitedFrom(inputStream);
            PersonEntity.JSONmsg personTwo = PersonEntity.JSONmsg.parseDelimitedFrom(inputStream);
            collector.checkThat(personOne.getId(), equalTo(1L));
            collector.checkThat(personOne.getName(), equalTo("Gerald"));
            collector.checkThat(personTwo.getId(), equalTo(2L));
            collector.checkThat(personTwo.getName(), equalTo("Layo"));
        }
    }
}