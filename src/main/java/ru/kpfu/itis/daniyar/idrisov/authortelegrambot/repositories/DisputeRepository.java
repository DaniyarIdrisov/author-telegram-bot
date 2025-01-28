package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.Dispute;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.enums.DisputeType;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisputeRepository extends JpaRepository<Dispute, UUID> {

    void deleteAllByTypeNotLike(DisputeType type);

    boolean existsByKey(String key);

    List<Dispute> getDisputesByType(DisputeType type);
}
