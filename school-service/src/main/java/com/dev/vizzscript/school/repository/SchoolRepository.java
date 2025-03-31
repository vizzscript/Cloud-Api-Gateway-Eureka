package com.dev.vizzscript.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.vizzscript.school.model.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

}
