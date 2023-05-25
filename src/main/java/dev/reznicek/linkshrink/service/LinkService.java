package dev.reznicek.linkshrink.service;

import dev.reznicek.linkshrink.bo.LinkBo;
import dev.reznicek.linkshrink.model.entity.LinkEntity;
import dev.reznicek.linkshrink.model.rest.Link;
import dev.reznicek.linkshrink.util.LinkUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LinkService {

    private static final Logger logger = LogManager.getLogger(LinkService.class);

    private final LinkBo linkBo;

    @Autowired
    public LinkService(LinkBo linkBo) {
        this.linkBo = linkBo;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Resource> getHtmlFile() {
        logger.info("GET Request");

        try {
            Resource resource = new ClassPathResource("static/ui.html");
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{encoded}")
    public void redirect(@PathVariable("encoded") String encoded, HttpServletResponse response) {
        logger.info("GET Request " + encoded);

        LinkEntity redirect = linkBo.find(encoded, response);
        if (redirect != null) {
            response.setHeader("Location", redirect.getDecoded());
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @PostMapping("/")
    @ResponseBody
    public String save(@RequestBody Link link, HttpServletResponse response) {
        logger.info("POST Request " + link.getDecoded());

        if (LinkUtil.isValidUrl(link.getDecoded())) {
            LinkEntity linkEntity = linkBo.save(link.getDecoded());
            return linkEntity.getEncoded();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

}
