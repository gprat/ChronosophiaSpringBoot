package com.webapp.site.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.Chronology;

public interface ChronologyRepository extends CrudRepository<Chronology,Long>{

	List<Chronology> findByUser_username(String username);
}
