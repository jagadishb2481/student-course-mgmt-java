package app.studcourse.controller;

import app.studcourse.entity.Course;
import app.studcourse.entity.Enrollment;
import app.studcourse.entity.Student;
import app.studcourse.service.EnrollmentService;
import app.studcourse.service.StudentService;
import app.studcourse.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    EnrollmentService enrollmentService;
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentService.updateStudent(id, studentDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        List<Enrollment> enrollmentsByStudentId = enrollmentService.getEnrollmentsByStudentId(id);
        if(enrollmentsByStudentId!=null && !enrollmentsByStudentId.isEmpty()){
            log.info("deleting student's enrollments");
            enrollmentsByStudentId.forEach(enrollment -> enrollmentService.unenroll(enrollment.getId()));
        }
        studentService.deleteStudent(id);
    }
}
