package pahrijal_saban_mubarok.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pahrijal_saban_mubarok.restful.entity.Lokasi;

@Repository
public interface LocationRepository extends JpaRepository<Lokasi, String> {
}
