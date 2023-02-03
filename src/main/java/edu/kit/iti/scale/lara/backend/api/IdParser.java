package edu.kit.iti.scale.lara.backend.api;

import java.util.regex.Pattern;

public class IdParser {

    private static final String SEPARATOR = "$";

    public String encodedId(String apiPrefix, String id) {
        return apiPrefix + SEPARATOR + id;
    }

    public String decodedId(String id) {
        return id.split(Pattern.quote(SEPARATOR))[1];
    }

}
