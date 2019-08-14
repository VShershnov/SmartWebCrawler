import com.crawlerengine.extractors.JsoupFindByIdSnippet;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.crawlerengine.model.SimilarityLevel.TWO_THIRTY;

public class SmartCrawlerApplication {
    private static final String CSS_QUERY = "div[id=\"success\"] button[class*=\"btn-primary\"]";
    private static final String TARGET_ELEMENT_ID = "sendMessageButton";

    private static Logger LOGGER = LoggerFactory.getLogger(SmartCrawlerApplication.class);

    public static void main(String[] args) {

        //TODO GET parameters from exec command
        String input_origin_file_path = args[0];
        String input_other_sample_file_path = args[1];


        //TODO crawle base html

        JsoupFindByIdSnippet extractor = new JsoupFindByIdSnippet();
        Optional<Element> sourceElement = extractor.findById(input_origin_file_path, TARGET_ELEMENT_ID);

        //TODO crawle and compare diff-case

//        sourceElement.ifPresent(elesourceElement);

        //TODO write result
        System.out.printf("Element finded with similarity level: %s%n and path %m", TWO_THIRTY, CSS_QUERY);

    }

}
