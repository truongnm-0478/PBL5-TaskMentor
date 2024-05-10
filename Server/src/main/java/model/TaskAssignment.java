package model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_assignments")
public class TaskAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @Column(name = "deadline")
    private Timestamp deadline;

    @ManyToOne
    @JoinColumn(name = "assigned_to", referencedColumnName = "id")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "assigned_by", referencedColumnName = "id")
    private User assignedBy;

    @Column(name = "assignment_time")
    private Timestamp assignmentTime;

    @Column(name = "is_transferred")
    private boolean isTransferred;

    @Column(name = "submission_time")
    private Timestamp submissionTime;

    @Column(name = "is_late_submission")
    private boolean isLateSubmission;

    @Column(name = "insert_time")
    private Timestamp insertTime;

    @Column(name = "insert_by")
    private Integer insertBy;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "delete_time")
    private Timestamp deleteTime;

    @Column(name = "delete_by")
    private Integer deleteBy;

    @Column(name = "proof")
    private String proof;
}
