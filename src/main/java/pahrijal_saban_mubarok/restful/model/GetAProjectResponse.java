package pahrijal_saban_mubarok.restful.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pahrijal_saban_mubarok.restful.entity.Location;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAProjectResponse {
    private Integer id;
    private String namaProyek;
    private String client;
    private Instant tanggalMulai;
    private Instant tanggalSelesai;
    private String pimpinanProyek;
    private String keterangan;
    private Timestamp createdAt;
    private Location lokasi;
}
