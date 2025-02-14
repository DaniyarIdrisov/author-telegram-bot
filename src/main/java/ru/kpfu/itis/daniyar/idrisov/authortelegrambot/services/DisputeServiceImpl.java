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
import java.time.LocalDate;
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
        createColumns(sheet, disputeType);
        fillDisputes(sheet, disputes, disputeType);
        return wb;
    }

    private void createColumns(Sheet sheet, DisputeType disputeType) {
        Row row = sheet.createRow(0);

        if (!DisputeType.NOTIFICATION_SHOWN.equals(disputeType)) {
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

            sheet.setColumnWidth(5, 15 * 256);
            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Начало обсуждения");

            sheet.setColumnWidth(6, 15 * 256);
            Cell cell6 = row.createCell(6);
            cell6.setCellValue("Конец обсуждения");

            sheet.setColumnWidth(7, 15 * 256);
            Cell cell7 = row.createCell(7);
            cell7.setCellValue("Количество дней");
        } else {
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
            cell3.setCellValue("Департамент");

            sheet.setColumnWidth(4, 15 * 256);
            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Тип");

            sheet.setColumnWidth(5, 15 * 256);
            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Начало обсуждения");

            sheet.setColumnWidth(6, 15 * 256);
            Cell cell6 = row.createCell(6);
            cell6.setCellValue("Конец обсуждения");

            sheet.setColumnWidth(7, 15 * 256);
            Cell cell7 = row.createCell(7);
            cell7.setCellValue("Дата оповещения");

            sheet.setColumnWidth(8, 15 * 256);
            Cell cell8 = row.createCell(8);
            cell8.setCellValue("Дата заключения");
        }
    }

    private void fillDisputes(Sheet sheet, List<Dispute> disputes, DisputeType disputeType) {
        var index = 1;
        for (var dispute: disputes) {

            if (!DisputeType.NOTIFICATION_SHOWN.equals(disputeType)) {
                Row row = sheet.createRow(index);

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(dispute.getId().toString());

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(dispute.getKey());

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(dispute.getTitle());

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(dispute.getOrganizationOrDepartment());

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(dispute.getType().getTypeValue());

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(dispute.getDisputeStartedAt());

                Cell cell6 = row.createCell(6);
                cell6.setCellValue(dispute.getDisputeEndedAt());

                Cell cell7 = row.createCell(7);
                cell7.setCellValue(dispute.getDisputeDays());

                index++;
            } else {
                Row row = sheet.createRow(index);

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(dispute.getId().toString());

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(dispute.getKey());

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(dispute.getTitle());

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(dispute.getOrganizationOrDepartment());

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(dispute.getType().getTypeValue());

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(dispute.getDisputeStartedAt());

                Cell cell6 = row.createCell(6);
                cell6.setCellValue(dispute.getDisputeEndedAt());

                Cell cell7 = row.createCell(7);
                cell7.setCellValue(dispute.getNotificationPublishedAt());

                Cell cell8 = row.createCell(8);
                cell8.setCellValue(dispute.getConclusionPublishedAt());

                index++;
            }
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
        var type = DisputeType.valueOf(json.get("state").get("key").asText().toUpperCase());
        Dispute dispute;
        if (!DisputeType.NOTIFICATION_SHOWN.equals(type)) {
            dispute = Dispute.builder()
                    .key(json.get("id").asText())
                    .type(type)
                    .title(json.get("dispute_info").get("title_full").asText())
                    .organizationOrDepartment(json.get("dispute_info").get("organization").asText())
                    .disputeStartedAt(LocalDate.parse(json.get("dispute_info").get("dispute_started_at").asText()))
                    .disputeEndedAt(LocalDate.parse(json.get("dispute_info").get("dispute_ended_at").asText()))
                    .disputeDays(Integer.valueOf(json.get("dispute_info").get("dispute_days").asText()))
                    .build();
        } else {
            dispute = Dispute.builder()
                    .key(json.get("id").asText())
                    .type(type)
                    .title(json.get("notification").get("title_full").asText())
                    .organizationOrDepartment(json.get("notification").get("department").asText())
                    .disputeStartedAt(LocalDate.parse(json.get("notification").get("dispute_started_at").asText()))
                    .disputeEndedAt(LocalDate.parse(json.get("notification").get("dispute_ended_at").asText()))
                    .notificationPublishedAt(LocalDate.parse(json.get("notification").get("notification_published_at").asText()))
                    .conclusionPublishedAt(LocalDate.parse(json.get("notification").get("conclusion_published_at").asText()))
                    .build();
        }
        repository.save(dispute);
    }
}

