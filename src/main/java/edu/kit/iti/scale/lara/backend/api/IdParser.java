package edu.kit.iti.scale.lara.backend.api;

import java.util.regex.Pattern;

/**
 * Class for parsing the IDs. The IDs are encoded with the API prefix and a separator.
 *
 * @author uefjv
 */
public class IdParser {

    private static final String SEPARATOR = "$";

    public String encodedId(String apiPrefix, String id) {
        return apiPrefix + SEPARATOR + id;
    }

    public String decodedId(String id) {
        return id.split(Pattern.quote(SEPARATOR))[1];
    }

}
