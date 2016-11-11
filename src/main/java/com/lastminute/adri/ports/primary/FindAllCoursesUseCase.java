package com.lastminute.adri.ports.primary;

import com.lastminute.adri.ports.model.Course;
import com.lastminute.adri.ports.secondary.CourseRepository;

import java.util.List;

public class FindAllCoursesUseCase {

    private CourseRepository courseRepository;

    public FindAllCoursesUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> execute() {
        return courseRepository.findAll();
    }
}
