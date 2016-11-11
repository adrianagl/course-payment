package com.lastminute.adri.ports.primary;

import com.lastminute.adri.ports.model.Course;
import com.lastminute.adri.ports.model.Enrollment;
import com.lastminute.adri.ports.model.Student;
import com.lastminute.adri.ports.secondary.CourseRepository;
import com.lastminute.adri.ports.secondary.EnrollmentRepository;
import com.lastminute.adri.ports.secondary.StudentRepository;

import java.util.Optional;

public class EnrollStudentOnACourseUseCase {

    private StudentRepository studentRepository;

    private CourseRepository courseRepository;

    private EnrollmentRepository enrollmentRepository;

    public EnrollStudentOnACourseUseCase(StudentRepository studentRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment execute(String studentId, String courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);

        if(student.isPresent() && course.isPresent()) {
            Enrollment enrollment = new Enrollment(student.get(), course.get());
            enrollmentRepository.save(enrollment);

            return enrollment;
        }

        return null;
    }
}
