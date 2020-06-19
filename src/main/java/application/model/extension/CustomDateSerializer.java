package application.model.extension;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        jgen.writeString(sdf.format(date));
    }
}
