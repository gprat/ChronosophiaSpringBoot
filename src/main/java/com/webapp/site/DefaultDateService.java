package com.webapp.site;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

import org.joda.time.chrono.GJChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.webapp.site.entities.Date;
import com.webapp.site.repositories.DateRepository;

@Service
public class DefaultDateService implements DateService {

	@Inject DateRepository dateRepository;
	
	@Override
	public List<Date> getAllDates() {
		List<Date> list = new ArrayList<>();
		this.dateRepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public Date getDate(long id) {
		return this.dateRepository.findById(id).get();
	}

	@Override
	public void save(Date date) {
		this.dateRepository.save(date);
	}

	@Override
	public void delete(long id) {
		this.dateRepository.deleteById(id);
	}
	
	@Override
	public boolean isValidDate(String date){
		String pattern = "dd-MM-yyyy";

	    try {
	        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
	        fmt.withChronology(GJChronology.getInstance());
	        fmt.parseDateTime(date);
	    } catch (Exception e) {
	        return false;
	    }
	    return true;
	}
	
	@Override
	public Date getDate(Integer day, Integer month, Integer year){
		Date date = new Date();
		if(month==null||month==0){
			date=this.dateRepository.getOneByYearAndMonthIsNull(year);
		}
		else if(day==null||day==0){
			date=this.dateRepository.getOneByMonthAndYearAndDayIsNull(month, year);
		}
		else{date=this.dateRepository.getOneByDayAndMonthAndYear(day, month, year);}
		return date;
	}
	
	@Override
	public Date setDate(Integer day, Integer month, Integer year){
		if(day!=null&&day==0) day = null;
		if(month!=null&&month==0) month = null;
		boolean bValid=false;
		if(year!=null){
			if((month==null&&day==null)||(day==null&&month!=null&&month<12&&month>0)){
				bValid = true;
			}
			else{
				String sDate = day+"-"+month+"-"+year;
				bValid=isValidDate(sDate);
			}
		}
		if(bValid){
			Date date = getDate(day,month,year);
			if(date==null){
				date = new Date(day,month,year);
				this.dateRepository.save(date);
				date = getDate(day,month,year);
			}
			return date;
		} else return null;
		
	}

}
