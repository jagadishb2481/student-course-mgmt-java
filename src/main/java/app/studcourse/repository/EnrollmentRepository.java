package app.studcourse.repository;

import app.studcourse.entity.Course;
import app.studcourse.entity.Enrollment;
import app.studcourse.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);

    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
    List<Student> findStudentsByCourseId(Long courseId);
    @Query("SELECT e.course FROM Enrollment e WHERE e.student.id = :studentId")
    List<Course> findCoursesByStudentId(Long studentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
}
