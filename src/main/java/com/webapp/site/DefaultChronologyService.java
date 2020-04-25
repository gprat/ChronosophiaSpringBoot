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
	public Chronology getChronology(long id, String username){
		Chronology chronology = chronologyRepository.findById(id).get();
		if (chronology.getUser().getUsername().equals(username)) {
			return chronology;
		}
		else return null; 
	}

	
	@Override
	public void save(Chronology chronology){
		chronologyRepository.save(chronology);
	}
	
	@Override
	public void delete(long id, String username){
		Chronology chronology = chronologyRepository.findById(id).get();
		if (chronology.getUser().getUsername().equals(username)) {
			chronologyRepository.deleteById(id);
		}
	}
	
	@Override
	public ChronologyForm getChronologyForm(long id, String username){
		Chronology chronology = getChronology(id, username);
		ChronologyForm chronologyForm = new ChronologyForm();
		if(chronology!=null){
			String eventList="";
			for(Event e: chronology.getEvents()){
				eventList=eventList.concat(String.valueOf(e.getIdEvent())+",");
			}
			if(eventList!="") eventList.substring(1,eventList.length()-1);
			chronologyForm.eventList=eventList;
			chronologyForm.events=eventList;
			Category category = chronology.getCategory();
			if(category!=null) {
				chronologyForm.category=String.valueOf(category.getIdCategory());
			}
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
	
	@Override
	public boolean ExistChronology(String name, int size, String username) {
		List<Chronology> chronologies = this.chronologyRepository.findByNameAndUser_username(name, username);
		if(chronologies!=null&&!chronologies.isEmpty()) {
			for(Chronology chrono : chronologies) {
				if(chrono.getEvents().size()==size) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public List<Chronology> getChronologiesByUsernameAndIds(String username, List<Long> IdChronologies){
		return this.chronologyRepository.findByUser_usernameAndIdChronologyIn(username, IdChronologies);
	}
	
	
}
