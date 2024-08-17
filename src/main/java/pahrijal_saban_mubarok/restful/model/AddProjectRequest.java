package pahrijal_saban_mubarok.restful.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProjectRequest {
    @NotBlank
    @Size(max = 100)
    private String namaProyek;

    @NotBlank
    @Size(max = 100)
    private String client;

    @NotNull
    private Instant tanggalMulai;

    @NotNull
    private Instant tanggalSelesai;

    @NotBlank
    @Size(max = 100)
    private String pimpinanProyek;

    @NotBlank
    private String keterangan;

    @NotNull
    private Integer lokasiId;
}
