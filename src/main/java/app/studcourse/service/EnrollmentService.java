package app.studcourse.service;

import app.studcourse.entity.Course;
import app.studcourse.entity.Enrollment;
import app.studcourse.entity.Student;
import app.studcourse.repository.CourseRepository;
import app.studcourse.repository.EnrollmentRepository;
import app.studcourse.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    public Enrollment enrollStudent(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public void unenroll(Long id) {
        enrollmentRepository.deleteById(id);
    }

    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
    public List<Student> getStudentsByCourseId(Long courseId) {
        return enrollmentRepository.findStudentsByCourseId(courseId);
    }
    // Method to get list of courses by student ID
    public List<Course> getCoursesByStudentId(Long studentId) {
        return enrollmentRepository.findCoursesByStudentId(studentId);
    }

    public List<Enrollment> enrollStudentInCourses(Long studentId, List<Long> courseIds) {
        // Fetch the student by ID
        List<Enrollment> enrollments = new ArrayList<>();
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (!studentOpt.isPresent()) {
            throw new RuntimeException("Student not found");
        }
        Student student = studentOpt.get();
        // Fetch the courses by the list of course IDs
        List<Course> courses = courseRepository.findAllById(courseIds);
        if (courses.isEmpty()) {
            throw new RuntimeException("No valid courses found for the given course IDs");
        }
        // Create new enrollments for the student
        for (Course course : courses) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollments.add(enrollment);
        }
        return enrollmentRepository.saveAll(enrollments);
    }

    @Transactional
    public void deleteEnrollment(Long studentId, Long courseId) {
        enrollmentRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }
}
