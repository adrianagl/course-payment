package com.lastminute.adri.ports.secondary;

import com.lastminute.adri.ports.model.Enrollment;

import java.util.Optional;

public interface EnrollmentRepository {

    Optional<Enrollment> findById(String enrollmentId);

    void save(Enrollment enrollment);
}




