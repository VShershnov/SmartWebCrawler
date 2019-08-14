package com.crawlerengine.extractors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;


public class JsoupFindByIdSnippet {
    private static final String RESOURCE_PATH = "./samples/startbootstrap-freelancer-gh-pages-cut.html";
    private static final String TARGET_ELEMENT_ID = "sendMessageButton";
    private static final String CHARSET_NAME = "utf8";

    private static Logger LOGGER = LoggerFactory.getLogger(JsoupFindByIdSnippet.class);

    public Optional<String> findById(String resourcePath, String targetElementId) {

        // Jsoup requires an absolute file path to resolve possible relative paths in HTML,
        // so providing InputStream through classpath resources is not a case
        resourcePath = RESOURCE_PATH;
        targetElementId = TARGET_ELEMENT_ID;

        Optional<Element> buttonOpt = findElementById(new File(resourcePath), targetElementId);

        Optional<String> stringifiedAttributesOpt = buttonOpt.map(button ->
                button.attributes().asList().stream()
                        .map(attr -> attr.getKey() + " = " + attr.getValue())
                        .collect(Collectors.joining(", "))
        );

        stringifiedAttributesOpt.ifPresent(attrs ->
                LOGGER.info("Target element attrs: [{}]", attrs));

        return stringifiedAttributesOpt;
    }

    private Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }
}