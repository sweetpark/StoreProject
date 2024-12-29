package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.theater.domain.Theater;

@Getter
@Setter
@ToString
@Builder
public class TheaterResDto {
    @Schema(description = "영화관 고유 번호", required = true, example = "1")
    private Integer id;

    @Schema(description = "위치 코드", required = true, example = "java.lang.Object")
    private LocationResDto locationResDto;

    // 영화관 사진
    @Schema(description = "영화관 사진", example = "/images/theater/63xYQj1BwRFielxsBDXvHIJyXVm.jpg")
    private String img;

    // 영화관명
    @Schema(description = "영화관명", required = true, example = "충주보은점")
    private String name;

    // 주소
    @Schema(description = "주소", required = true, example = "충청북도 보은군 뱃들로 68-22")
    private String address;

    // X좌표 (경도)
    @Schema(description = "X좌표 (경도)", example = "35.18433")
    private String x;

    // Y좌표 (위도)
    @Schema(description = "Y좌표 (위도)", example = "125.2312")
    private String y;

    public TheaterResDto(Integer id, LocationResDto location, String img, String name, String address, String x, String y) {
        this.id = id;
        this.locationResDto = location;
        this.img = img;
        this.name = name;
        this.address = address;
        this.x = x;
        this.y = y;
    }

    @Builder
    public TheaterResDto(Theater theater) {
        this.id = theater.getId();
        this.locationResDto = LocationResDto.from(theater.getLocation());
        this.img = theater.getImg();
        this.name = theater.getName();
        this.address = theater.getAddress();
        this.x = theater.getX();
        this.y = theater.getY();
    }

    public static TheaterResDto from(Theater theater) {
        return TheaterResDto.builder()
                .id(theater.getId())
                .locationResDto(LocationResDto.from(theater.getLocation()))
                .img(theater.getImg())
                .name(theater.getName())
                .address(theater.getAddress())
                .x(theater.getX())
                .y(theater.getY())
                .build();
    }
}
