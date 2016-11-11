package com.lastminute.adri.ports.primary;

import com.lastminute.adri.ports.model.Course;
import com.lastminute.adri.ports.secondary.CourseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

public class FindCourseByIdTest {

    private static final String NAME = "Course1";
    private static final String ID = "id";
    private static final BigDecimal PRICE = BigDecimal.TEN;

    private CourseRepository courseRepository = Mockito.mock(CourseRepository.class);

    private FindCourseByIdUseCase useCase = new FindCourseByIdUseCase(courseRepository);

    @Test
    public void found() {
        Course expectedCourse = new Course(ID, NAME, PRICE);
        Mockito.when(courseRepository.findById(ID)).thenReturn(Optional.of(expectedCourse));

        Optional<Course> found = useCase.execute(ID);

        Assert.assertEquals(Optional.of(expectedCourse), found);
    }

    @Test
    public void notFound() {
        Mockito.when(courseRepository.findById(ID)).thenReturn(Optional.empty());

        Optional<Course> notFound = useCase.execute(ID);

        Assert.assertEquals(Optional.empty(), notFound);
    }
}
