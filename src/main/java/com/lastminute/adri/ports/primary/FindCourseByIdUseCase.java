package com.lastminute.adri.ports.primary;

import com.lastminute.adri.ports.model.Course;
import com.lastminute.adri.ports.secondary.CourseRepository;

import java.util.Optional;

public class FindCourseByIdUseCase {

    private CourseRepository courseRepository;

    public FindCourseByIdUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public Optional<Course> execute(String id) {
        Optional<Course> course = courseRepository.findById(id);
        return course;
    }
}
