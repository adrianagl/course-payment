package com.lastminute.adri.ports.primary;

import com.lastminute.adri.ports.model.Course;
import com.lastminute.adri.ports.secondary.CourseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public class FindAllCoursesTest {

    private static final String NAME = "Course1";
    private static final String ID = "id";
    private static final BigDecimal PRICE = BigDecimal.TEN;

    private CourseRepository courseRepository = Mockito.mock(CourseRepository.class);

    private FindAllCoursesUseCase useCase = new FindAllCoursesUseCase(courseRepository);

    @Test
    public void found() {
        List<Course> expectedCourses = Arrays.asList(createCourseMock());
        Mockito.when(courseRepository.findAll()).thenReturn(expectedCourses);

        List<Course> foundCourses = useCase.execute();

        Assert.assertEquals(expectedCourses, foundCourses);
    }

    @Test
    public void notFound() {
        Mockito.when(courseRepository.findAll()).thenReturn(emptyList());

        Assert.assertEquals(emptyList(), useCase.execute());
    }

    private Course createCourseMock() {
        return new Course(ID, NAME, PRICE);
    }
}
