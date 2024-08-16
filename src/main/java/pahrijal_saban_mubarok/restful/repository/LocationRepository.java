package pahrijal_saban_mubarok.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pahrijal_saban_mubarok.restful.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Boolean existsByNamaLokasi(String namaLokasi);
}
