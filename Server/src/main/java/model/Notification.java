package model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClassRoom classRoom;

    @Type(type = "util.IntegerArrayType")
    @Column(name = "user_ids", columnDefinition = "integer[]")
    private Integer[] userIds;

    @Column(name = "is_seen")
    private boolean isSeen;

    @Column(name = "content")
    private String content;

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
