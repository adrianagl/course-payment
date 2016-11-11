package com.lastminute.adri.ports.secondary;

import com.lastminute.adri.ports.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Optional<Course> findById(String id);

    List<Course> findAll();
}
