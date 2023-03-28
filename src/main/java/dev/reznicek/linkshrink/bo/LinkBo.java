package dev.reznicek.linkshrink.bo;

import dev.reznicek.linkshrink.model.entity.LinkEntity;
import dev.reznicek.linkshrink.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class LinkBo {

    private final LinkRepository linkRepository;

    @Autowired
    public LinkBo(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public LinkEntity find(String encoded, HttpServletResponse response) {
        return linkRepository.findByEncoded(encoded);
    }

    public LinkEntity save(String decoded) {
        LinkEntity linkEntity = linkRepository.findByDecoded(decoded);
        if (linkEntity != null) {
            return linkEntity;
        }

        LinkEntity saveThis = new LinkEntity();
        saveThis.setDecoded(decoded);
        saveThis.setEncoded(encode());

        return linkRepository.save(saveThis);
    }

    private String encode() {
        String uuid;
        while (true) {
            uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
            LinkEntity exists = linkRepository.findByEncoded(uuid);
            if (exists == null) {
                break;
            }
        }
        return uuid;
    }

}
