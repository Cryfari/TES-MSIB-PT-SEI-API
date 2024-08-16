package pahrijal_saban_mubarok.restful.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pahrijal_saban_mubarok.restful.entity.Project;
import pahrijal_saban_mubarok.restful.entity.ProjectLocation;
import pahrijal_saban_mubarok.restful.model.AddProjectRequest;
import pahrijal_saban_mubarok.restful.repository.ProjectLocationRepository;
import pahrijal_saban_mubarok.restful.repository.ProjectRepository;

import java.util.List;
import java.util.Set;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectLocationRepository projectLocationRepository;

    @Autowired
    private Validator validator;

    @Transactional
    public void addProject(AddProjectRequest request){
        Set<ConstraintViolation<AddProjectRequest>> constraintViolations = validator.validate(request);
        if(constraintViolations.size() != 0){
            throw new ConstraintViolationException(constraintViolations);
        }
        if (projectRepository.existsByNamaProyek(request.getNamaProyek())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project already registered");
        }
        Project project = new Project();
        project.setNamaProyek(request.getNamaProyek());
        project.setClient(request.getClient());
        project.setTanggalMulai(request.getTanggalMulai());
        project.setTanggalSelesai(request.getTanggalSelesai());
        project.setPimpinanProyek(request.getPimpinanProyek());
        project.setKeterangan(request.getKeterangan());

        projectRepository.save(project);

        Project currentProject = projectRepository.findByNamaProyek(request.getNamaProyek());
        ProjectLocation projectLocation = new ProjectLocation();
        projectLocation.setProyekId(currentProject.getId());
        projectLocation.setLokasiId(request.getLokasiId());

        projectLocationRepository.save(projectLocation);
    }

    @Transactional(readOnly = true)
    public List<ProjectLocation> getAllProject(){

        return projectLocationRepository.findAll();
    }
}
