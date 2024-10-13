package app.studcourse.controller;

import app.studcourse.entity.Course;
import app.studcourse.entity.Enrollment;
import app.studcourse.entity.Student;
import app.studcourse.exception.InvalidReuestException;
import app.studcourse.model.EnrollmentRequest;
import app.studcourse.service.EnrollmentService;
import app.studcourse.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@CrossOrigin(origins = "*")
@Slf4j
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enrollStudentInCourses")
    public ResponseEntity<List<Enrollment>> enrollStudentInCourses(@RequestBody EnrollmentRequest enrollmentRequest) {
        log.info("enrollmentRequest:{}", CommonUtils.getJsonString(enrollmentRequest));
        try {
            List<Enrollment> enrollments = enrollmentService.enrollStudentInCourses(enrollmentRequest.getStudentId(), enrollmentRequest.getCourseIds());
            return ResponseEntity.ok(enrollments);
        } catch (Exception e) {
            log.error("exception occured :", e);
           throw new InvalidReuestException(e.getMessage());
        }
    }
    @PostMapping
    public Enrollment enrollStudent(@RequestBody Enrollment enrollment) {
        return enrollmentService.enrollStudent(enrollment);
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @DeleteMapping("/{id}")
    public void unenroll(@PathVariable Long id) {
        enrollmentService.unenroll(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }

    @GetMapping("/course/{courseId}/students")
    public List<Student> getStudentsByCourseId(@PathVariable Long courseId) {
        return enrollmentService.getStudentsByCourseId(courseId);
    }

    @GetMapping("/student/{studentId}/courses")
    public List<Course> getCoursesByStudentId(@PathVariable Long studentId) {
        return enrollmentService.getCoursesByStudentId(studentId);
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable Long studentId, @PathVariable Long courseId) {
        enrollmentService.deleteEnrollment(studentId, courseId);
        return ResponseEntity.ok().build();
    }
}
