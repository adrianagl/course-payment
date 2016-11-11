package com.lastminute.adri.infrastructure.repository;

import com.lastminute.adri.ports.model.Course;
import com.lastminute.adri.ports.secondary.CourseRepository;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCourseRepositoryTest {

    private static final String COURSE_ID_1 = "1";
    private static final String COURSE_ID_2 = "2";
    private static final String COURSE_NAME_JAVA = "Java 8";
    private static final String COURSE_NAME_ANGULAR = "Angular";
    private static final BigDecimal COURSE_PRICE_100 = new BigDecimal("100");
    private static final BigDecimal COURSE_PRICE_200 = new BigDecimal("200");
    private static final String NOT_EXISTING_COURSE_ID = "OtherId";

    private CourseRepository courseRepository = new FileCourseRepository();

    @Test
    public void findByIdExisting() {
        Course expectedCourse = new Course(COURSE_ID_1, COURSE_NAME_JAVA, COURSE_PRICE_100);

        Optional<Course> foundCourse = courseRepository.findById(COURSE_ID_1);

        Assert.assertEquals(Optional.of(expectedCourse), foundCourse);
    }

    @Test
    public void findByIdNotExisting() {
        Optional<Course> foundCourse = courseRepository.findById(NOT_EXISTING_COURSE_ID);

        Assert.assertEquals(Optional.empty(), foundCourse);
    }

    @Test
    public void findAll() {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course(COURSE_ID_1, COURSE_NAME_JAVA, COURSE_PRICE_100));
        expectedCourses.add(new Course(COURSE_ID_2, COURSE_NAME_ANGULAR, COURSE_PRICE_200));

        List<Course> foundCourses = courseRepository.findAll();

        Assert.assertEquals(expectedCourses.size(), foundCourses.size());
        Assert.assertEquals(expectedCourses.get(0), foundCourses.get(0));
        Assert.assertEquals(expectedCourses.get(1), foundCourses.get(1));
    }
}
