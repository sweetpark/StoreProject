package project.movie.theater.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.movie.theater.dto.ScreenResDto;
import project.movie.theater.dto.SeatResDto;
import project.movie.theater.dto.TheaterResDto;

@NoArgsConstructor
@Table(name = "seats")
@Entity
@Getter
@ToString
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @Column(name = "seat_group", length = 10)
    private String seatGroup;

    @Column(name = "seat_no")
    private Integer seatNo;

    @Column(name = "seat_line_no")
    private Integer seatLineNo;

    @Transient
    private Boolean isBookable;

    @Builder
    public Seat(Long id, Screen screen, Theater theater, String seatGroup, Integer seatNo, Integer seatLineNo, Boolean isBookable) {
        this.id = id;
        this.screen = screen;
        this.theater = theater;
        this.seatGroup = seatGroup;
        this.seatNo = seatNo;
        this.seatLineNo = seatLineNo;
        this.isBookable = isBookable;
    }

    public static SeatResDto from(Seat seat) {
        return SeatResDto.builder()
                .id(seat.getId())
                .screenResDto(ScreenResDto.from(seat.getScreen()))
                .theaterResDto(TheaterResDto.from(seat.getTheater()))
                .seatGroup(seat.getSeatGroup())
                .seatNo(seat.getSeatNo())
                .seatLineNo(seat.getSeatLineNo())
                .isBookable(seat.isBookable)
                .build();
    }
}
