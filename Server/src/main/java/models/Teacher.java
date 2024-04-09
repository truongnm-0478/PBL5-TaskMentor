package models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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
