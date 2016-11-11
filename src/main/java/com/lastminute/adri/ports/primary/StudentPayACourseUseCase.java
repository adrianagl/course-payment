package com.lastminute.adri.ports.primary;

import com.lastminute.adri.ports.model.Enrollment;
import com.lastminute.adri.ports.secondary.EnrollmentRepository;
import com.lastminute.adri.infrastructure.payment.PaymentService;

import java.math.BigDecimal;
import java.util.Optional;

public class StudentPayACourseUseCase {

    private EnrollmentRepository enrollmentRepository;

    private PaymentService paymentService;

    public StudentPayACourseUseCase(EnrollmentRepository enrollmentRepository, PaymentService paymentService) {
        this.enrollmentRepository = enrollmentRepository;
        this.paymentService = paymentService;
    }

    public boolean execute(String enrollmentId) {
        Optional<Enrollment> foundEnrollment = enrollmentRepository.findById(enrollmentId);

        if(foundEnrollment.isPresent()) {
            BigDecimal coursePrice = foundEnrollment.get().getCoursePrice();

            return paymentService.makePayment(coursePrice);
        }

        return false;
    }
}

