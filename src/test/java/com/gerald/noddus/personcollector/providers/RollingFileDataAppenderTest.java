package com.gerald.noddus.personcollector.providers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.springframework.util.FileCopyUtils;

public class RollingFileDataAppenderTest {

    public static final String SAMPLE_FILE_NAME = "sample.txt";
    public static final String TEST_DATA = "test-data";
    public static final String CUSTOM_DATA = "custom-data";

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private File testFolder;
    private File testFile;
    private RollingFileDataAppender rollingFileDataAppender;

    @Before
    public void setUp() throws Exception {
        testFolder = temporaryFolder.newFolder();
        rollingFileDataAppender = new RollingFileDataAppender();
        rollingFileDataAppender.setFolderPath(testFolder.getAbsolutePath());
        rollingFileDataAppender.setFileName(SAMPLE_FILE_NAME);
        testFile = new File(testFolder, SAMPLE_FILE_NAME);
    }

    @Test
    public void whenOpenFileDataFileShouldBeOpened() throws Exception {
        rollingFileDataAppender.openFile();
        assertThat(rollingFileDataAppender.isOpen(), equalTo(true));
        rollingFileDataAppender.closeFile();
    }

    @Test
    public void whenCloseFileDataFileShouldBeClosed() throws Exception {
        rollingFileDataAppender.openFile();
        rollingFileDataAppender.closeFile();
        assertThat(rollingFileDataAppender.isOpen(), equalTo(false));
    }

    @Test
    public void whenDataAppendContentShouldBeWrittenToFileWithNewLine() throws Exception {
        rollingFileDataAppender.openFile();
        saveToAppender();
        rollingFileDataAppender.closeFile();

        assertThat(FileCopyUtils.copyToString(new FileReader(testFile)),
                is(equalTo(TEST_DATA)));
    }

    private void saveToAppender() throws IOException {
        rollingFileDataAppender.saveData(TEST_DATA.getBytes());
    }

    @Test
    public void whenDataAppendMultipleTimesContentShouldBeAppended() throws Exception {
        rollingFileDataAppender.openFile();
        saveToAppender();
        saveToAppender();
        rollingFileDataAppender.closeFile();

        assertThat(FileCopyUtils.copyToString(new FileReader(testFile)),
                is(equalTo(TEST_DATA + TEST_DATA)));
    }

    @Test
    public void whenDataAppendMultipleTimesWithCloseInBetweenContentShouldBeAppended() throws Exception {
        rollingFileDataAppender.openFile();
        saveToAppender();
        rollingFileDataAppender.closeFile();
        rollingFileDataAppender.openFile();
        saveToAppender();
        rollingFileDataAppender.closeFile();

        assertThat(FileCopyUtils.copyToString(new FileReader(testFile)),
                is(equalTo(TEST_DATA + TEST_DATA)));
    }

    @Test
    public void whenRollOverContentShouldBeTransferredToAnotherFileAndLabelled() throws Exception {
        RolloverLabel rolloverLabel = createRolloverLabel();
        rollingFileDataAppender.openFile();
        saveToAppender();
        rollingFileDataAppender.rollOver(rolloverLabel);
        rollingFileDataAppender.closeFile();

        File rolledOverFile = new File(testFolder, "data-sample-1.txt");
        assertThat(rolledOverFile.isFile(), is(true));
        assertThat(FileCopyUtils.copyToString(new FileReader(rolledOverFile)),
                equalTo(TEST_DATA));
    }

    private RolloverLabel createRolloverLabel() {
        CountRollover rolloverLabel = new CountRollover();
        rolloverLabel.increase();
        rolloverLabel.setPrefix("data");
        return rolloverLabel;
    }

    @Test
    public void whenRollOverSubsequentWriteShouldBeNew() throws Exception {
        RolloverLabel rolloverLabel = createRolloverLabel();
        rollingFileDataAppender.openFile();
        saveToAppender();
        rollingFileDataAppender.rollOver(rolloverLabel);
        saveToAppender(CUSTOM_DATA);
        rollingFileDataAppender.closeFile();

        assertThat(FileCopyUtils.copyToString(new FileReader(testFile)),
                is(equalTo(CUSTOM_DATA)));
    }

    private void saveToAppender(String data) throws IOException{
        rollingFileDataAppender.saveData(data.getBytes());
    }

    @Test
    public void givenDataAppenderOpenedWhenOpenFileShouldThrowIllegalStateException() throws Exception{
        expectedException.expectMessage("file is already open");
        expectedException.expect(IllegalStateException.class);

        rollingFileDataAppender.openFile();
        rollingFileDataAppender.openFile();
    }
}