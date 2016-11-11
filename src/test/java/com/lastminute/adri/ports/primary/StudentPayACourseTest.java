package com.lastminute.adri.ports.primary;

import com.lastminute.adri.ports.model.Course;
import com.lastminute.adri.ports.model.Enrollment;
import com.lastminute.adri.ports.model.Student;
import com.lastminute.adri.ports.secondary.EnrollmentRepository;
import com.lastminute.adri.infrastructure.payment.PaymentService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

public class StudentPayACourseTest {

    private static final String ENROLLMENT_ID = "id";
    private static final String STUDENT_ID = "studentId";
    private static final String STUDENT_NAME = "Student name";
    private static final String COURSE_NAME = "Course1";
    private static final String COURSE_ID = "courseId";
    private static final BigDecimal COURSE_PRICE = BigDecimal.TEN;

    private EnrollmentRepository enrollmentRepository = Mockito.mock(EnrollmentRepository.class);

    private PaymentService paymentService = Mockito.mock(PaymentService.class);

    private StudentPayACourseUseCase useCase = new StudentPayACourseUseCase(enrollmentRepository, paymentService);

    @Test
    public void foundEnrollment() {
        Student student = new Student(STUDENT_ID, STUDENT_NAME);
        Course course = new Course(COURSE_ID, COURSE_NAME, COURSE_PRICE);
        Enrollment enrollment = new Enrollment(student, course);
        Mockito.when(enrollmentRepository.findById(ENROLLMENT_ID)).thenReturn(Optional.of(enrollment));
        Mockito.when(paymentService.makePayment(COURSE_PRICE)).thenReturn(true);

        boolean isValidPayment = useCase.execute(ENROLLMENT_ID);

        Assert.assertTrue(isValidPayment);
        Mockito.verify(paymentService).makePayment(COURSE_PRICE);
    }

    @Test
    public void notFoundEnrollment() {
        Mockito.when(enrollmentRepository.findById(ENROLLMENT_ID)).thenReturn(Optional.empty());

        boolean isValidPayment = useCase.execute(ENROLLMENT_ID);

        Assert.assertFalse(isValidPayment);
        Mockito.verify(paymentService, Mockito.never()).makePayment(COURSE_PRICE);
    }

    @Test
    public void errorWhenPaying() {
        Student student = new Student(STUDENT_ID, STUDENT_NAME);
        Course course = new Course(COURSE_ID, COURSE_NAME, COURSE_PRICE);
        Enrollment enrollment = new Enrollment(student, course);
        Mockito.when(enrollmentRepository.findById(ENROLLMENT_ID)).thenReturn(Optional.of(enrollment));
        Mockito.when(paymentService.makePayment(COURSE_PRICE)).thenReturn(false);

        boolean isValidPayment = useCase.execute(ENROLLMENT_ID);

        Assert.assertFalse(isValidPayment);
        Mockito.verify(paymentService).makePayment(COURSE_PRICE);
    }
}

