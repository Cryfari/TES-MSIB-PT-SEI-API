package pahrijal_saban_mubarok.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationResponse {
    private String namaLokasi;
    private String negara;
    private String provinsi;
    private String kota;
}
