package com.webapp.site;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.webapp.site.entities.Category;
import com.webapp.site.entities.Event;

public class EventSerializer extends StdSerializer<Event> {

	public EventSerializer() {
		this(null);
	}
	
	public EventSerializer(Class<Event> E) {
		super(E);
	}
	
	@Override
	public void serialize(Event event, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeStringField("idEvent", String.valueOf(event.getIdEvent()));
		jgen.writeStringField("eventyear", event.getDate().getYear().toString());
		jgen.writeObjectFieldStart("date");
		jgen.writeNumberField("year", event.getDate().getYear());
		if(event.getDate().getMonth()!=null) jgen.writeNumberField("month", event.getDate().getMonth());
		if(event.getDate().getDay()!=null) jgen.writeNumberField("day", event.getDate().getDay());
		jgen.writeEndObject();
		jgen.writeStringField("name",event.getName());
		jgen.writeStringField("description",event.getDescription());
		if(event.getUrl()!=null) jgen.writeStringField("url",event.getUrl());
		jgen.writeFieldName("categories");
		jgen.writeStartArray();
		for(Category c : event.getCategories()) {
			jgen.writeStartObject();
			jgen.writeStringField("name", c.getName());
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
		if(event.getCity()!=null) {
			jgen.writeObjectFieldStart("city");
			jgen.writeStringField("name", event.getCity().getName());
			jgen.writeStringField("country", event.getCity().getCountry().getName());
			jgen.writeNumberField("latitude",event.getCity().getLatitude());
			jgen.writeNumberField("longitude",event.getCity().getLongitude());
			jgen.writeStringField("description", event.getCity().getDescription());
			jgen.writeEndObject();
		}
		jgen.writeEndObject();
	}
}
