package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.enums.DisputeType;

import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "dispute")
public class Dispute {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    UUID id;

    @Column(name = "key")
    String key;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    DisputeType type;

    @Column(name = "title")
    String title;

    @Column(name = "organization_or_department")
    String organizationOrDepartment;

    @Column(name = "dispute_started_at")
    LocalDate disputeStartedAt;

    @Column(name = "dispute_ended_at")
    LocalDate disputeEndedAt;

    @Column(name = "dispute_days")
    Integer disputeDays;

    @Column(name = "notification_published_at")
    LocalDate notificationPublishedAt;

    @Column(name = "conclusion_published_at")
    LocalDate conclusionPublishedAt;
}
