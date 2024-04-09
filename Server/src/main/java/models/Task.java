package models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "sprint_id", referencedColumnName = "id")
    private Sprint sprint;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;

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
}
