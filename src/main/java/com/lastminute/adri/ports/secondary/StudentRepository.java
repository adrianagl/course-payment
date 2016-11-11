package com.lastminute.adri.ports.secondary;

import com.lastminute.adri.ports.model.Student;

import java.util.Optional;

public interface StudentRepository {

    Optional<Student> findById(String studentId);
}
