package com.webapp.site.repositories;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.Monument;

public interface MonumentRepository extends CrudRepository<Monument,Long> {

}
