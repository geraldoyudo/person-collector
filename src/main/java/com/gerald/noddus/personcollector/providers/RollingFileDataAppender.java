package com.gerald.noddus.personcollector.providers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

public class RollingFileDataAppender implements DataAppender {

    private String folderPath;
    private String fileName;
    private PrintWriter writer;

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void openFile() throws FileNotFoundException {
        if (isOpen()) {
            throw new IllegalStateException("file is already open");
        }
        this.writer = new PrintWriter(new FileOutputStream(getAbsoluteFilePath(), true));
    }

    private String getAbsoluteFilePath() {
        return folderPath +  File.separator + fileName;
    }

    @Override
    public synchronized void saveData(String data) {
        writer.println(data);
    }

    public synchronized void rollOver(RolloverLabel rolloverLabel) throws FileNotFoundException,IOException{
        closeFile();
        String nameWithoutExtension = FilenameUtils.removeExtension(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        String labelledName = rolloverLabel.prefix() + nameWithoutExtension + rolloverLabel.suffix();
        if(!StringUtils.isEmpty(extension)){
            labelledName = labelledName + "." + extension;
        }
        File file = new File(getAbsoluteFilePath());
        file.renameTo(new File(folderPath + File.separator + labelledName));
        openFile();
    }

    public void closeFile() {
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }

    public boolean isOpen() {
        return writer != null;
    }
}
