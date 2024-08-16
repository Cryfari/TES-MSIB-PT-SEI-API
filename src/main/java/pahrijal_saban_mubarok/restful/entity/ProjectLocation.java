package pahrijal_saban_mubarok.restful.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proyek_lokasi")
public class ProjectLocation {
    @Id
    @Column(name = "proyek_id")
    private Integer proyekId;

    @Column(name = "lokasi_id")
    private Integer lokasiId;
}
