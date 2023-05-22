// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import modelclass.Converter;
//
// Then you can deserialize a JSON string with
//
//     List<Review> data = Converter.fromJsonString(jsonString);

package modelclass;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

        // Serialize/deserialize helpers

	private static ObjectReader reader;
    private static ObjectWriter writer;

    public static List<Review> fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(List<Review> obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        reader = mapper.reader(List.class);
        writer = mapper.writer();
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}
