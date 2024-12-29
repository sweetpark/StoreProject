package project.movie.theater.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Table(name = "screens")
@Entity
@Getter
@ToString
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_id")
    private Long id;

    @Column(name = "screen_number")
    private Integer screenNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @Column(name = "seat_count")
    private Integer seatCount; // 좌석 수

    public Screen(Long id, Integer screenNumber, Theater theater, Integer seatCount) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.theater = theater;
        this.seatCount = seatCount;
    }
}
