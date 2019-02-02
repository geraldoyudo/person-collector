package com.gerald.noddus.personcollector.providers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RollingFileDataAppender implements DataAppender, RolloverCapable {

    @Value("${person.service.folder}")
    private String folderPath;
    @Value("${person.service.file}")
    private String fileName;

    private OutputStream outputStream;

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @PostConstruct
    public void openFile() throws IOException {
        if (isOpen()) {
            throw new IllegalStateException("file is already open");
        }
        initializeAndGetFile();
        this.outputStream = new FileOutputStream(getAbsoluteFilePath(), true);
    }

    private void initializeAndGetFile() throws IOException {
        File file = new File(getAbsoluteFilePath());
        FileUtils.forceMkdirParent(file);
    }

    private String getAbsoluteFilePath() {
        return folderPath + File.separator + fileName;
    }

    @Override
    public synchronized void saveData(byte[] data) throws IOException {
        outputStream.write(data);
    }

    @Override
    public synchronized void rollOver(RolloverLabel rolloverLabel) throws RolloverException {
        try {
            closeFile();
            String nameWithoutExtension = FilenameUtils.removeExtension(fileName);
            String extension = FilenameUtils.getExtension(fileName);
            String labelledName = rolloverLabel.prefix() + nameWithoutExtension + rolloverLabel.suffix();
            if (!StringUtils.isEmpty(extension)) {
                labelledName = labelledName + "." + extension;
            }
            File file = new File(getAbsoluteFilePath());
            file.renameTo(new File(folderPath + File.separator + labelledName));
            openFile();
        }catch (IOException ex){
            throw new RolloverException("Could not rollover output file", ex);
        }
    }

    @PreDestroy
    public void closeFile() throws IOException {
        if (outputStream != null) {
            outputStream.close();
            outputStream = null;
        }
    }

    public boolean isOpen() {
        return outputStream != null;
    }
}
