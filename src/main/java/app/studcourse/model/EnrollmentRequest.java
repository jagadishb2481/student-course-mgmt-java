package app.studcourse.model;

import lombok.Data;

import java.util.List;

@Data
public class EnrollmentRequest {
    private Long studentId;
    private List<Long> courseIds;

}
