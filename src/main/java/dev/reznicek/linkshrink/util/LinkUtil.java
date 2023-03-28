package dev.reznicek.linkshrink.util;

import dev.reznicek.linkshrink.model.entity.LinkEntity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class LinkUtil {

    public static boolean isValid(LinkEntity linkEntity) {
        return isValidUrl(linkEntity.getDecoded()) && isValidUrl(linkEntity.getEncoded());
    }

    public static boolean isValidUrl(String urlString) {
        if (urlString == null) {
            return true;
        }
        try {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

}
