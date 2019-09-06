package com.webapp.site;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.webapp.site.entities.Event;
import com.webapp.site.entities.Figure;

public class FigureSerializer extends StdSerializer<Figure> {

	public FigureSerializer(){
		this(null);
	}
	
	public FigureSerializer(Class<Figure> f){
		super(f);
	}
	
	 @Override
	 public void serialize(Figure figure, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		 String sBirthYear = "", sDeathYear = ""; 
		 jgen.writeStartObject();
		 jgen.writeStringField("idFigure", String.valueOf(figure.getIdFigure()));
		 jgen.writeStringField("firstName", figure.getFirstName());
		 jgen.writeStringField("lastName", figure.getLastName());
		 if(figure.getBirthDate()!=null) sBirthYear = String.valueOf(figure.getBirthDate().getYear());
		 if(figure.getDeathDate()!=null) sDeathYear = String.valueOf(figure.getDeathDate().getYear());
		 jgen.writeStringField("figureDates","("+sBirthYear+"-"+sDeathYear+")");
		 jgen.writeFieldName("events");
			jgen.writeStartArray();
			int i = 1;
			for(Event e : figure.getEvents()){
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
		 jgen.writeEndObject();
	 }
	
}
