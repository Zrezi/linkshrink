package dev.reznicek.linkshrink.repository;

import dev.reznicek.linkshrink.model.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, Integer> {
    LinkEntity findByEncoded(String encoded);
    LinkEntity findByDecoded(String decoded);
}
