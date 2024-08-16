package pahrijal_saban_mubarok.restful.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "proyek_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "lokasi_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Location location;

}
