package ru.kpfu.itis.daniyar.idrisov.authortelegrambot;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.enums.DisputeType;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.services.DisputeService;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class AuthorTelegramBotApplicationTests {

    @Autowired
    DisputeService disputeService;

    @Test
    void contextLoads() {
    }

    @Test
    void createXlsByType() throws IOException {
        var file1 = disputeService.createXlsByType(DisputeType.DISCUSSION_PERFORMING);
        var file2 = disputeService.createXlsByType(DisputeType.RESULT_PREPARING);
        var file3 = disputeService.createXlsByType(DisputeType.DISCUSSION_FAKE);
        var file4 = disputeService.createXlsByType(DisputeType.ARCHIVED);
        saveFile(file1, "C://xls/DISCUSSION_PERFORMING.xls");
        saveFile(file2, "C://xls/RESULT_PREPARING.xls");
        saveFile(file3, "C://xls/DISCUSSION_FAKE.xls");
        saveFile(file4, "C://xls/ARCHIVED.xls");
    }

    private void saveFile(byte[] file, String path) throws IOException {
        FileUtils.writeByteArrayToFile(new File(path), file);
    }
}
