package com.webapp.site;

import java.io.IOException;
import java.security.Principal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.webapp.site.entities.Chronology;
import com.webapp.site.entities.Event;

public class ChronologySerializer  extends StdSerializer<Chronology> {

	public ChronologySerializer(){
		this(null);
	}
	
	public ChronologySerializer(Class<Chronology> c){
		super(c);
	}
	
	@Override
	public void serialize(Chronology chronology,JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeObjectFieldStart("title");
		jgen.writeObjectFieldStart("text");
		jgen.writeStringField("headline",chronology.getName());
		jgen.writeStringField("text",chronology.getDescription());
		jgen.writeEndObject();
		if(chronology.getUrl()!=null){
			jgen.writeObjectFieldStart("media");
			jgen.writeStringField("url",chronology.getUrl());
			jgen.writeEndObject();
		}
		jgen.writeEndObject();
		jgen.writeFieldName("events");
		jgen.writeStartArray();
		int i = 1;
		for(Event e : chronology.getEvents()){
			jgen.writeStartObject();
			jgen.writeObjectFieldStart("start_date");
			jgen.writeStringField("year", e.getDate().getYear().toString());
			jgen.writeStringField("month", e.getDate().getMonth().toString());
			jgen.writeStringField("day", e.getDate().getDay().toString());
			jgen.writeEndObject();
			jgen.writeObjectFieldStart("end_date");
			jgen.writeStringField("year", e.getDate().getYear().toString());
			jgen.writeStringField("month", e.getDate().getMonth().toString());
			jgen.writeStringField("day", e.getDate().getDay().toString());
			jgen.writeEndObject();
			jgen.writeObjectFieldStart("text");
			jgen.writeStringField("headline",e.getName());
			jgen.writeStringField("text",e.getDescription());
			jgen.writeEndObject();
			
			if(e.getUrl()!=null){
				jgen.writeObjectFieldStart("media");
				jgen.writeStringField("url",e.getUrl());
				jgen.writeStringField("thumbnail",e.getUrl());
				jgen.writeEndObject();
			}
			
			
			//jgen.writeStringField("unique_id","chrono"+String.valueOf(chronology.getIdChronology())+"_"+String.valueOf(i));
			i++;
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
		jgen.writeFieldName("eventsToImport");
		ObjectMapper mapper = (ObjectMapper) jgen.getCodec();
		String stringValue = mapper.writeValueAsString(chronology.getEvents());
		jgen.writeRawValue(stringValue);
		if(chronology.getCategory()!=null) jgen.writeStringField("category", chronology.getCategory().getName());
		jgen.writeStringField("name", chronology.getName());
		jgen.writeStringField("description", chronology.getDescription());
		jgen.writeStringField("url", chronology.getUrl());
		jgen.writeEndObject();
	}
}
