package com.webapp.site;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.webapp.site.entities.City;
import com.webapp.site.entities.Event;

public class CitySerializer extends StdSerializer<City> {
	
	public CitySerializer(){
		this(null);
	}
	
	public CitySerializer(Class<City> c){
		super(c);
	}
	
	@Override
	public void serialize(City city, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		if(city!=null) {
			String countryName = "";
			if(city.getCountry()!=null)	countryName=city.getCountry().getName();	
			jgen.writeStartObject();
			jgen.writeStringField("idCity", String.valueOf(city.getIdCity()));
			jgen.writeStringField("name", city.getName());
			jgen.writeNumberField("latitude",city.getLatitude());
			jgen.writeNumberField("longitude",city.getLongitude());
			jgen.writeStringField("countryName",countryName);
			jgen.writeStringField("sCity", city.toString());
			String description = "";
			String eventCount = "";
			if(city.getDescription()!= null) {
				description = "<p>"+city.getDescription()+"</p>";
			}
			if(city.getEvents()!=null) {
				eventCount = "<p>La ville compte "+city.getEvents().size()+" evènements</p>";
			}
			
			jgen.writeStringField("infocontent", "<h3>"+city.getName()+"</h3>"+description+"</div>");
			jgen.writeStringField("titleCity", "<h3>"+city.getName()+"</h3>"+eventCount+"</div>");
			Integer eventNumber = 0;
			if(city.getEvents()!=null ) {
				eventNumber = city.getEvents().size();
				if(city.addEventsInJson) {
					jgen.writeFieldName("events");
					jgen.writeStartArray();
					int i = 1;
					for(Event e : city.getEvents()){
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
						jgen.writeEndObject();
					}
					jgen.writeEndArray();
				}
			}
			jgen.writeStringField("label", eventNumber.toString());
			jgen.writeEndObject();
		}
	}
}
