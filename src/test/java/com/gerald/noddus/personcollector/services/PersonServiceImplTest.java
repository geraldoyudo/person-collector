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
    public static final String PERSON_ONE_NAME = "Gerald";
    public static final String PERSON_TWO_NAME = "Layo";
    public static final long PERSON_ONE_ID = 1L;
    public static final long PERSON_TWO_ID = 2L;

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    private final ProtobufPersonSerializer serializer = new ProtobufPersonSerializer();
    private final RollingFileDataAppender appender = new RollingFileDataAppender();
    private final PersonServiceImpl personService = new PersonServiceImpl();
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
        personService.savePerson(createPerson(PERSON_ONE_ID, PERSON_ONE_NAME));
        personService.savePerson(createPerson(PERSON_TWO_ID, PERSON_TWO_NAME));
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
            collector.checkThat(personOne.getId(), equalTo(PERSON_ONE_ID));
            collector.checkThat(personOne.getName(), equalTo(PERSON_ONE_NAME));
            collector.checkThat(personTwo.getId(), equalTo(PERSON_TWO_ID));
            collector.checkThat(personTwo.getName(), equalTo(PERSON_TWO_NAME));
        }
    }
}