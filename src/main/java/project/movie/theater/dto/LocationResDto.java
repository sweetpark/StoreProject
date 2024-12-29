package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.theater.domain.Location;

@Getter
@Setter
@ToString
@Builder
public class LocationResDto {
    @Schema(description = "위치코드", required = true, example = "GURO")
    private String code;

    @Schema(description = "위치명", required = true, example = "충주연수점")
    private String name;

    @Schema(description = "위치그룹", required = true, example = "1")
    private Integer groupNum;

    public LocationResDto(String code, String name, Integer groupNum) {
        this.code = code;
        this.name = name;
        this.groupNum = groupNum;
    }

    public static LocationResDto from(Location location) {
        return LocationResDto.builder()
                .code(location.getCode())
                .name(location.getName())
                .groupNum(location.getGroupNum())
                .build();
    }
}
