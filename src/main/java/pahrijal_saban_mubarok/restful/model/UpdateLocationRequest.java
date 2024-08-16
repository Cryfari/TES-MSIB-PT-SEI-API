package pahrijal_saban_mubarok.restful.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateLocationRequest {
    @NotNull
    private Integer id;

    @NotBlank
    @Size(max = 100)
    private String namaLokasi;

    @NotBlank
    @Size(max = 100)
    private String negara;

    @NotBlank
    @Size(max = 100)
    private String provinsi;

    @NotBlank
    @Size(max = 100)
    private String kota;
}
