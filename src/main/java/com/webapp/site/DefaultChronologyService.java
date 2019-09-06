package com.webapp.site;



import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.webapp.site.entities.Category;
import com.webapp.site.entities.Chronology;
import com.webapp.site.entities.Event;
import com.webapp.site.repositories.ChronologyRepository;


@Service
public class DefaultChronologyService implements ChronologyService {
	
	@Inject ChronologyRepository chronologyRepository;
	
	@Override
	public List<Chronology> getAllChronologies(){
		List<Chronology> list = new ArrayList<>();
		chronologyRepository.findAll().forEach(e->list.add(e));
		return list;
	}
	
	@Override
	public Chronology getChronology(long id){
		return chronologyRepository.findById(id).get();
	}

	
	@Override
	public void save(Chronology chronology){
		chronologyRepository.save(chronology);
	}
	
	@Override
	public void delete(long id){
		chronologyRepository.deleteById(id);
	}
	
	@Override
	public ChronologyForm getChronologyForm(long id){
		Chronology chronology = getChronology(id);
		ChronologyForm chronologyForm = new ChronologyForm();
		if(chronology!=null){
			String eventList="";
			for(Event e: chronology.getEvents()){
				eventList=eventList.concat(String.valueOf(e.getIdEvent())+",");
			}
			if(eventList!="") eventList.substring(1,eventList.length()-1);
			chronologyForm.eventList=eventList;
			chronologyForm.category=String.valueOf(chronology.getCategory().getIdCategory());
			chronologyForm.id=chronology.getIdChronology();
			chronologyForm.name=chronology.getName();
			chronologyForm.description=chronology.getDescription();
			chronologyForm.url=chronology.getUrl();
		}
		return chronologyForm;
	}

	
	@Override
	public List<Chronology> getChronologiesByUsername(String username){
		return chronologyRepository.findByUser_username(username);
	}
}
