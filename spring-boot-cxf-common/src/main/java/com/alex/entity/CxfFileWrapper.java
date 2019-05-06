package com.alex.entity;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlMimeType;

/**
 * 描述:
 *
 * @author hany
 * @create 2018-12-10 9:22
 */
public class CxfFileWrapper {
    private String fileName;
    private String fileExtension ;
    private DataHandler file;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @XmlMimeType("application/octet-stream")
    public DataHandler getFile() {
        return file;
    }

    public void setFile(DataHandler file) {
        this.file = file;
    }


    @Override
    public String toString() {
        return "CxfFileWrapper{" +
                "fileName='" + fileName + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", file=" + file +
                '}';
    }
}
