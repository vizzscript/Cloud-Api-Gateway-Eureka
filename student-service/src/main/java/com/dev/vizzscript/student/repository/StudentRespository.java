package com.dev.vizzscript.student.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.vizzscript.student.model.Student;

@Repository
public interface StudentRespository extends MongoRepository<Student, String> {

}
