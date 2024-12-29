package project.movie.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PasswordChangeReqDto {
    @Schema(description = "비밀번호", required = true, example = "wlsrud1234")
    @NotNull
    @Size(min = 4, max = 20)
    private String password;

    @Schema(description = "새로운 비밀번호", required = true, example = "wlsrud1234")
    @NotNull
    @Size(min = 4, max = 20)
    private String newPassword;

    @Schema(description = "새로운 비밀번호 확인", required = true, example = "wlsrud1234")
    @NotNull
    @Size(min = 4, max = 20)
    private String confirmPassword;

    @Builder
    public PasswordChangeReqDto(String password, String newPassword, String confirmPassword) {
        this.password = password;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public boolean checkPasswordAndConfirmPassword() {
        return newPassword.equals(confirmPassword);
    }
}
