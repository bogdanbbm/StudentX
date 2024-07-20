package students.x.studentsx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture")
    private String lecture;

    @Column(name = "grade")
    private int grade;

    @ElementCollection
    @CollectionTable(name = "feedback_pluses", joinColumns = @JoinColumn(name = "feedback_id"))
    @Column(name = "plus")
    private List<String> pluses;

    @Column(name = "text")
    private String text;
}