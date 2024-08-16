package pahrijal_saban_mubarok.restful.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proyek")
public class proyek {
    @Id
    private Integer id;

    @Column(name = "nama_proyek")
    private String namaProyek;

    private String client;

    @Column(name = "tgl_mulai")
    private String tanggalMulai;

    @Column(name = "tgl_selesai")
    private String tanggalSelesai;

    @Column(name = "pimpinan_proyek")
    private String pimpinanProyek;

    private String keterangan;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
