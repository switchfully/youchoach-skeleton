package com.switchfully.youcoach.file;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "DATABASE_FILE")
public class DBFile {

    @Id
    private String fileName;

    private String originalFileName;

    @Lob
    private byte[] fileContent;

    public DBFile(){

    }

    public DBFile(String fileName, String originalFileName, byte[] fileContent) {
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileContent = fileContent;
    }

    public byte[] getData() {
        return fileContent;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }
}
