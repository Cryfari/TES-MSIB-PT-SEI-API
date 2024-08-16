package pahrijal_saban_mubarok.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pahrijal_saban_mubarok.restful.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    Project findByNamaProyek(String namaProyek);

    Boolean existsByNamaProyek(String namaProyek);
}
