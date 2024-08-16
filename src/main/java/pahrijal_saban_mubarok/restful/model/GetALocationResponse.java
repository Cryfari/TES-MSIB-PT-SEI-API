package pahrijal_saban_mubarok.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetALocationResponse {
    private Integer id;
    private String namaLokasi;
    private String kota;
    private String provinsi;
    private String negara;
}
