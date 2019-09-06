package com.webapp.site.repositories;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.Painting;

public interface PaintingRepository extends CrudRepository<Painting,Long> {

}
