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

    @Column(name = "title")
    String title;

    @Column(name = "organization")
    String organization;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    DisputeType type;
}
