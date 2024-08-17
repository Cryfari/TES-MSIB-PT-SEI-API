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
import pahrijal_saban_mubarok.restful.model.GetAProjectResponse;
import pahrijal_saban_mubarok.restful.model.UpdateProjectRequest;
import pahrijal_saban_mubarok.restful.repository.ProjectLocationRepository;
import pahrijal_saban_mubarok.restful.repository.ProjectRepository;

import java.util.ArrayList;
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
    public List<GetAProjectResponse> getAllProject(){
        List<GetAProjectResponse> proyek = new ArrayList<>();
        for(ProjectLocation proyekLocation: projectLocationRepository.findAll()){
            proyek.add(toGetAProjectResponse(proyekLocation));
        }
        return proyek;
    }

    private GetAProjectResponse toGetAProjectResponse(ProjectLocation proyek){
        return GetAProjectResponse.builder()
                .id(proyek.getProyek().getId())
                .client(proyek.getProyek().getClient())
                .namaProyek(proyek.getProyek().getNamaProyek())
                .tanggalMulai(proyek.getProyek().getTanggalMulai())
                .tanggalSelesai(proyek.getProyek().getTanggalSelesai())
                .pimpinanProyek(proyek.getProyek().getPimpinanProyek())
                .keterangan(proyek.getProyek().getKeterangan())
                .lokasi(proyek.getLokasi())
                .createdAt(proyek.getProyek().getCreatedAt())
                .build();
    }
    @Transactional(readOnly = true)
    public GetAProjectResponse getAProject(Integer id){
        ProjectLocation proyek = projectLocationRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "proyek tidak ditemukan"));
        return toGetAProjectResponse(proyek);
    }
    @Transactional
    public ProjectLocation updateProject(UpdateProjectRequest request){
        Set<ConstraintViolation<UpdateProjectRequest>> constraintViolations = validator.validate(request);
        if(constraintViolations.size() != 0){
            throw new ConstraintViolationException(constraintViolations);
        }


        System.err.println("test service : "+ request.getId());
        ProjectLocation proyek = projectLocationRepository.findById(String.valueOf(request.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "proyek tidak ditemukan"));

        proyek.getProyek().setNamaProyek(request.getNamaProyek());
        proyek.getProyek().setClient(request.getClient());
        proyek.getProyek().setTanggalMulai(request.getTanggalMulai());
        proyek.getProyek().setTanggalSelesai(request.getTanggalSelesai());
        proyek.getProyek().setPimpinanProyek(request.getPimpinanProyek());
        proyek.getProyek().setKeterangan(request.getKeterangan());

        proyek.setLokasiId(request.getLokasiId());
        projectLocationRepository.save(proyek);
        return projectLocationRepository.findById(String.valueOf(request.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "proyek tidak ditemukan"));
    }

    public void deleteProject(Integer id){
        Project proyek = projectRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "proyek tidak ditemukan"));

        projectRepository.delete(proyek);
    }
}
