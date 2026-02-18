package com.tuempresa.api.dto;

public class Body {

    private final String algorithm;
    private final String text;
    private final String hash;

    public Body(String algorithm, String text, String hash) {
        this.algorithm = algorithm;
        this.text = text;
        this.hash = hash;
    }

    public String getAlgorithm() { return algorithm; }
    public String getText() { return text; }
    public String getHash() { return hash; }
}
