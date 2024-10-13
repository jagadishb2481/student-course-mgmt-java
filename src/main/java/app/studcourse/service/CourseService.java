package app.studcourse.service;

import app.studcourse.entity.Course;
import app.studcourse.entity.Student;
import app.studcourse.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    EnrollmentService enrollmentService;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id).orElseThrow();
        course.setCourseName(courseDetails.getCourseName());
        course.setDescription(courseDetails.getDescription());
        course.setSchedule(courseDetails.getSchedule());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        List<Student> students =  enrollmentService.getStudentsByCourseId(id);
        if(CollectionUtils.isEmpty(students)){
            courseRepository.deleteById(id);
        }else{
            log.info("students:"+students);
            throw new RuntimeException("There are some students who enrolled this course. Please delete associated enrollments first");
        }
    }
}
