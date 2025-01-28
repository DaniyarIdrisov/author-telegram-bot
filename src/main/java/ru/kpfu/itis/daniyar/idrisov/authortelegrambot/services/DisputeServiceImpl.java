package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.exceptions.XlsGenerationException;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.Dispute;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.enums.DisputeType;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.repositories.DisputeRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class DisputeServiceImpl implements DisputeService{

    private static final String ROOT_URL = "https://dispute.kzn.ru/api/disputes.json";
    private static final String GET_DISPUTE_URL = "https://dispute.kzn.ru/api/disputes/%s.json";
    private static final String GET_ARCHIVED_DISPUTES_URL = "https://dispute.kzn.ru/api/disputes.json?&type=archived";

    DisputeRepository repository;
    RestTemplate restTemplate;
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public byte[] createXlsByType(DisputeType disputeType) {
        var disputes = repository.getDisputesByType(disputeType);
        var file = generateXls(disputes, disputeType);
        return getDataFromXlsFile(file);
    }

    private Workbook generateXls(List<Dispute> disputes, DisputeType disputeType) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(disputeType.getTypeValue());
        createColumns(sheet);
        fillDisputes(sheet, disputes);
        return wb;
    }

    private void createColumns(Sheet sheet) {
        Row row = sheet.createRow(0);

        sheet.setColumnWidth(0, 15 * 256);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue("Идентификатор");

        sheet.setColumnWidth(1, 15 * 256);
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("Внешний ключ");

        sheet.setColumnWidth(2, 15 * 256);
        Cell cell2 = row.createCell(2);
        cell2.setCellValue("Описание");

        sheet.setColumnWidth(3, 15 * 256);
        Cell cell3 = row.createCell(3);
        cell3.setCellValue("Организация");

        sheet.setColumnWidth(4, 15 * 256);
        Cell cell4 = row.createCell(4);
        cell4.setCellValue("Тип");
    }

    private void fillDisputes(Sheet sheet, List<Dispute> disputes) {
        var index = 1;
        for (var dispute: disputes) {
            Row row = sheet.createRow(index);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(dispute.getId().toString());

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(dispute.getKey());

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(dispute.getTitle());

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(dispute.getOrganization());

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(dispute.getType().getTypeValue());
            index++;
        }
    }


    private byte[] getDataFromXlsFile(Workbook file) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (bos) {
            file.write(bos);
        } catch (IOException e) {
            String msg = String.format("Возникла ошибка при генерации файла XLS, прична: %s", e.getMessage());
            log.error(msg, e);
            throw new XlsGenerationException(msg);
        }
        return bos.toByteArray();
    }

    @Transactional
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void addDisputes() throws JsonProcessingException {
        repository.deleteAllByTypeNotLike(DisputeType.ARCHIVED);
        var rootResponse = restTemplate.getForEntity(ROOT_URL, String.class);
        var rootJson = mapper.readTree(rootResponse.getBody());
        if (rootJson.isArray()) {
            for (final JsonNode node : rootJson) {
                var id = node.get("id").asText();
                var response = restTemplate.getForEntity(String.format(GET_DISPUTE_URL, id), String.class);
                var json = mapper.readTree(response.getBody());
                saveDispute(json);
            }
        }
    }

    @Transactional
    @Scheduled(fixedDelay = 6, timeUnit = TimeUnit.HOURS)
    public void addArchivedDisputes() throws JsonProcessingException {
        var rootResponse = restTemplate.getForEntity(GET_ARCHIVED_DISPUTES_URL, String.class);
        var rootJson = mapper.readTree(rootResponse.getBody());
        if (rootJson.isArray()) {
            for (final JsonNode node : rootJson) {
                var id = node.get("id").asText();
                if (!repository.existsByKey(id)) {
                    var response = restTemplate.getForEntity(String.format(GET_DISPUTE_URL, id), String.class);
                    var json = mapper.readTree(response.getBody());
                    saveDispute(json);
                }
            }
        }
    }

    private void saveDispute(JsonNode json) {
        var dispute = Dispute.builder()
                .key(json.get("id").asText())
                .title(json.get("dispute_info").get("title_full").asText())
                .organization(json.get("dispute_info").get("organization").asText())
                .type(DisputeType.valueOf(json.get("state").get("key").asText().toUpperCase()))
                .build();
        repository.save(dispute);
    }
}

