package project.movie.theater.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Table(name = "locations")
@Entity
@Getter
@ToString
public class Location {
    @Id
    @Column(length = 30)
    private String code;

    @Column(length = 50)
    private String name;

    @Min(1) // 최소값 1
    @Max(3) // 최대값 3
    @Column(name = "group_num")
    private Integer groupNum;
}
