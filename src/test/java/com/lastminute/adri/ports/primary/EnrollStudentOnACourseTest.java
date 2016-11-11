package com.lastminute.adri.ports.primary;

import com.lastminute.adri.ports.model.Course;
import com.lastminute.adri.ports.model.Enrollment;
import com.lastminute.adri.ports.model.Student;
import com.lastminute.adri.ports.secondary.CourseRepository;
import com.lastminute.adri.ports.secondary.EnrollmentRepository;
import com.lastminute.adri.ports.secondary.StudentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

public class EnrollStudentOnACourseTest {

    private static final String STUDENT_ID = "studentId";
    private static final String STUDENT_NAME = "Student name";
    private static final String COURSE_NAME = "Course1";
    private static final String COURSE_ID = "courseId";
    private static final BigDecimal COURSE_PRICE = BigDecimal.TEN;

    private EnrollmentRepository enrollmentRepository = Mockito.mock(EnrollmentRepository.class);
    private CourseRepository courseRepository = Mockito.mock(CourseRepository.class);
    private StudentRepository studentRepository = Mockito.mock(StudentRepository.class);

    private EnrollStudentOnACourseUseCase enrollOnACourseUseCase = new EnrollStudentOnACourseUseCase(studentRepository, courseRepository, enrollmentRepository);

    @Test
    public void foundStudentAndCourse() {
        Student foundStudent = new Student(STUDENT_ID, STUDENT_NAME);
        Course foundCourse = new Course(COURSE_ID, COURSE_NAME, COURSE_PRICE);
        Mockito.when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(foundStudent));
        Mockito.when(courseRepository.findById(COURSE_ID)).thenReturn(Optional.of(foundCourse));


        Enrollment savedEnrollment = enrollOnACourseUseCase.execute(STUDENT_ID, COURSE_ID);

        Enrollment createdEnrollment = new Enrollment(foundStudent, foundCourse);
        Mockito.verify(enrollmentRepository).save(createdEnrollment);
        Assert.assertEquals(createdEnrollment, savedEnrollment);
    }

    @Test
    public void notFoundStudent() {
        Course foundCourse = new Course(COURSE_ID, COURSE_NAME, COURSE_PRICE);
        Mockito.when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.empty());
        Mockito.when(courseRepository.findById(COURSE_ID)).thenReturn(Optional.of(foundCourse));

        Enrollment savedEnrollment = enrollOnACourseUseCase.execute(STUDENT_ID, COURSE_ID);

        Mockito.verify(enrollmentRepository, Mockito.never()).save(Mockito.any());
        Assert.assertNull(savedEnrollment);
    }

    @Test
    public void notFoundCourse() {
        Student foundStudent = new Student(STUDENT_ID, STUDENT_NAME);
        Mockito.when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(foundStudent));
        Mockito.when(courseRepository.findById(COURSE_ID)).thenReturn(Optional.empty());

        Enrollment savedEnrollment = enrollOnACourseUseCase.execute(STUDENT_ID, COURSE_ID);

        Mockito.verify(enrollmentRepository, Mockito.never()).save(Mockito.any());
        Assert.assertNull(savedEnrollment);
    }

    @Test
    public void notFoundStudentNorCourse() {
        Mockito.when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.empty());
        Mockito.when(courseRepository.findById(COURSE_ID)).thenReturn(Optional.empty());

        Enrollment savedEnrollment = enrollOnACourseUseCase.execute(STUDENT_ID, COURSE_ID);

        Mockito.verify(enrollmentRepository, Mockito.never()).save(Mockito.any());
        Assert.assertNull(savedEnrollment);
    }
}
