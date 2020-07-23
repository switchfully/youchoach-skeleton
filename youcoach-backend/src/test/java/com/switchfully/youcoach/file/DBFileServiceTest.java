package com.switchfully.youcoach.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
class DBFileServiceTest {

    @Autowired
    private DBFileService dbFileService;

    @Test
    void upAndDownloadFile() {
        dbFileService.uploadFile("tim", new MockMultipartFile("someFileName", "hello world".getBytes()));

        DBFile dbFile = dbFileService.downloadFile("tim").orElse(null);

        Assertions.assertThat(new String(dbFile.getData())).isEqualTo("hello world");
    }
}
