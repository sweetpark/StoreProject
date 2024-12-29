package project.movie.theater.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Table(name = "theaters")
@Entity
@Getter
@ToString
public class Theater {
    @Schema(description = "영화관 고유 번호", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Integer id;

    @Schema(description = "위치 코드", required = true, example = "java.lang.Object")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code", nullable = false)
    private Location location;

    // 영화관 사진
    @Schema(description = "영화관 사진", example = "/images/theater/63xYQj1BwRFielxsBDXvHIJyXVm.jpg")
    private String img;

    // 영화관명
    @Schema(description = "영화관명", required = true, example = "충주보은점")
    @Column(name = "name", nullable = false)
    private String name;

    // 주소
    @Schema(description = "주소", required = true, example = "충청북도 보은군 뱃들로 68-22")
    @Column(name = "address", nullable = false)
    private String address;

    // X좌표 (경도)
    @Schema(description = "X좌표 (경도)", example = "35.18433")
    private String x;

    // Y좌표 (위도)
    @Schema(description = "Y좌표 (위도)", example = "125.2312")
    private String y;

    public Theater(Integer id, Location location, String img, String name, String address, String x, String y) {
        this.id = id;
        this.location = location;
        this.img = img;
        this.name = name;
        this.address = address;
        this.x = x;
        this.y = y;
    }
}
