package com.lastminute.adri.infrastructure.repository;

import com.lastminute.adri.ports.model.Course;
import com.lastminute.adri.ports.secondary.CourseRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCourseRepository implements CourseRepository {

    private static final String FILE_NAME = "course.csv";
    private static final int ID_LINE_POS = 0;
    private static final int NAME_LINE_POS = 1;
    private static final int PRICE_LINE_POS = 2;

    @Override
    public Optional<Course> findById(String id) {
        CsvReader reader = new CsvReader(FILE_NAME);

        Optional<String[]> nextLine;
        while ((nextLine = reader.getNextLine()).isPresent()) {
            if(id.equals(nextLine.get()[0])) {
                return Optional.of(buildCourseFromLine(nextLine.get()));
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();

        CsvReader reader = new CsvReader(FILE_NAME);
        Optional<String[]> nextLine;
        while ((nextLine = reader.getNextLine()).isPresent()) {
            courses.add(buildCourseFromLine(nextLine.get()));
        }

        return courses;
    }

    private static Course buildCourseFromLine(String[] line) {
        return new Course(line[ID_LINE_POS], line[NAME_LINE_POS], new BigDecimal(line[PRICE_LINE_POS]));
    }
}
