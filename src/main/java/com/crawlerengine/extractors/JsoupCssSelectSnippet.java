package com.crawlerengine.extractors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class JsoupCssSelectSnippet {

    private static final String RESOURCE_PATH = "./samples/startbootstrap-freelancer-gh-pages-cut.html";
    private static final String CSS_QUERY = "div[id=\"success\"] button[class*=\"btn-primary\"]";
    private static final String CHARSET_NAME = "utf8";

    private static Logger LOGGER = LoggerFactory.getLogger(JsoupCssSelectSnippet.class);

    public Optional<List<String>> findByCssQuery(String resourcePath, String cssQuery) {

        // Jsoup requires an absolute file path to resolve possible relative paths in HTML,
        // so providing InputStream through classpath resources is not a case
        resourcePath = RESOURCE_PATH;
        cssQuery = CSS_QUERY;

        Optional<Elements> elementsOpt = findElementsByQuery(new File(resourcePath), cssQuery);

        Optional<List<String>> elementsAttrsOpts = elementsOpt.map(buttons ->
                {
                    List<String> stringifiedAttrs = new ArrayList<>();

                    buttons.iterator().forEachRemaining(button ->
                            stringifiedAttrs.add(
                                    button.attributes().asList().stream()
                                            .map(attr -> attr.getKey() + " = " + attr.getValue())
                                            .collect(Collectors.joining(", "))));

                    return stringifiedAttrs;
                }
        );


        elementsAttrsOpts.ifPresent(attrsList ->
                attrsList.forEach(attrs ->
                        LOGGER.info("Target element attrs: [{}]", attrs)
                )
        );

        return elementsAttrsOpts;
    }

    private Optional<Elements> findElementsByQuery(File htmlFile, String cssQuery) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.select(cssQuery));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

}