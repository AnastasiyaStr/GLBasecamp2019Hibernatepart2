package entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="worker")
public class Worker implements Serializable{
    @Id
    @Column(name="worker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="full_name")
    private String fullName;

    @Column(name="age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name="availability")
    private Availability availability;

    @ManyToOne
    @JoinColumn(name ="department_id")
    private Department department;

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", availability=" + availability +
                '}';
    }
}
