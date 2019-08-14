package com.crawlerengine.extractors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class JsoupFindByAttribute {

    private static final String TARGET_ELEMENT_ATTRIBUTE_KEY = "id";
    private static final String TARGET_ELEMENT_ATTRIBUTE_VALUE = "sendMessageButton";
    private static final String CHARSET_NAME = "utf8";

    private static Logger LOGGER = LoggerFactory.getLogger(JsoupFindByAttribute.class);

    public Optional<Map<String, Elements>> findByAttributes(String resourcePath, Attributes attributes) {

        // Jsoup requires an absolute file path to resolve possible relative paths in HTML,
        // so providing InputStream through classpath resources is not a case
        Attribute attribute = new Attribute(TARGET_ELEMENT_ATTRIBUTE_KEY, TARGET_ELEMENT_ATTRIBUTE_VALUE);

//        Optional<Elements> buttonOpt = findElementsByAttribute(new File(resourcePath), attribute);

        Optional<Map<String, Elements>> elementsOpts = Optional.of(attributes)
                .map(attrs -> {
                            Map<String, Elements> attrsElementsMap = new HashMap<>();

                            attrs.iterator().forEachRemaining(attr ->
                                    attrsElementsMap.put(attr.getKey(), findElementsByAttribute(new File(resourcePath), attr)));

                            return attrsElementsMap;
                        }
                );

        return elementsOpts;
    }

    private Elements findElementsByAttribute(File htmlFile, Attribute attribute) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return doc.getElementsByAttributeValueContaining(attribute.getKey(), attribute.getValue());

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return new Elements();
        }
    }
}