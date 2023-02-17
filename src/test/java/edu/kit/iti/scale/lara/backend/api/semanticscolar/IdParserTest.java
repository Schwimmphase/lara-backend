package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import edu.kit.iti.scale.lara.backend.api.IdParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdParserTest {

    private static final String S2_PREFIX = "S2";
    private static IdParser idParser = new IdParser();

    // set up
    private static final String S2_ID = "731843bf908de278188174d2b755ff9f682da14f";
    private static final String LARA_ID = "S2$731843bf908de278188174d2b755ff9f682da14f";

    @Test
    void encodedIdTest() {



        // execute
        String encodedId = idParser.encodedId(S2_PREFIX, S2_ID);

        // test
        Assertions.assertEquals(LARA_ID, encodedId);
    }

    @Test
    void decodedIdTest() {

        // execute
        String decodedId = idParser.decodedId(LARA_ID);

        // test
        Assertions.assertEquals(S2_ID, decodedId);
    }
}
