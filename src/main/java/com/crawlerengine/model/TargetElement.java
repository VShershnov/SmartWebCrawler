package com.crawlerengine.model;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

public class TargetElement {

    private Element element;
    private Attributes mutchingAttributes;

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Attributes getMutchingAttributes() {
        return mutchingAttributes;
    }

    public Attributes addMutchingAttributes(String key, String value) {
        return mutchingAttributes.put(key, value);
    }
}
