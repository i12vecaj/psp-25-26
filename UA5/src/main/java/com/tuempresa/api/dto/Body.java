package com.tuempresa.api.dto;

public class Body {

    private String algorithm;
    private String text;
    private String hash;

    public Body(String algorithm, String text, String hash) {
        this.algorithm = algorithm;
        this.text = text;
        this.hash = hash;
    }

    public String getAlgorithm() { return algorithm; }
    public String getText() { return text; }
    public String getHash() { return hash; }
}
