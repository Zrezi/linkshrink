package dev.reznicek.linkshrink.service;

import dev.reznicek.linkshrink.bo.LinkBo;
import dev.reznicek.linkshrink.model.rest.Link;
import dev.reznicek.linkshrink.model.entity.LinkEntity;
import dev.reznicek.linkshrink.util.LinkUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LinkService {

    private static final Logger logger = LogManager.getLogger(LinkService.class);

    private final LinkBo linkBo;

    @Autowired
    public LinkService(LinkBo linkBo) {
        this.linkBo = linkBo;
    }

    @GetMapping("/{encoded}")
    public void redirect(@PathVariable("encoded") String encoded, HttpServletResponse response) {
        logger.info("GET Request " + encoded);
        System.out.println("GET");
        LinkEntity redirect = linkBo.find(encoded, response);
        if (redirect != null) {
            response.setHeader("Location", redirect.getDecoded());
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public String save(@RequestBody Link link, HttpServletResponse response) {
        logger.info("POST Request " + link.getDecoded());
        System.out.println("POST");
        if (LinkUtil.isValidUrl(link.getDecoded())) {
            LinkEntity linkEntity = linkBo.save(link.getDecoded());
            return linkEntity.getEncoded();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

}
