package model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_approval")
public class ProjectApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Column(name = "status")
    private boolean status;

    @Column(name = "comments")
    private String comments;

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
