package pahrijal_saban_mubarok.restful.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proyek")
public class Project {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama_proyek")
    private String namaProyek;

    private String client;

    @Column(name = "tgl_mulai")
    private LocalDateTime tanggalMulai;

    @Column(name = "tgl_selesai")
    private LocalDateTime tanggalSelesai;

    @Column(name = "pimpinan_proyek")
    private String pimpinanProyek;

    private String keterangan;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
