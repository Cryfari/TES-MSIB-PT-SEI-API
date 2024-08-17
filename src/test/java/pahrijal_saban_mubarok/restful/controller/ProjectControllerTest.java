package pahrijal_saban_mubarok.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pahrijal_saban_mubarok.restful.entity.Location;
import pahrijal_saban_mubarok.restful.entity.Project;
import pahrijal_saban_mubarok.restful.entity.ProjectLocation;
import pahrijal_saban_mubarok.restful.model.*;
import pahrijal_saban_mubarok.restful.repository.LocationRepository;
import pahrijal_saban_mubarok.restful.repository.ProjectLocationRepository;
import pahrijal_saban_mubarok.restful.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectLocationRepository projectLocationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        locationRepository.deleteAll();
        projectRepository.deleteAll();
        projectLocationRepository.deleteAll();

        Location location = new Location();
        location.setNamaLokasi("test");
        location.setNegara("Indonesia");
        location.setKota("Bandung");
        location.setProvinsi("Jawa Barat");
        locationRepository.save(location);

        Project project = new Project();
        project.setNamaProyek("test");
        project.setClient("test");
        project.setTanggalMulai(LocalDateTime.now());
        project.setTanggalSelesai(LocalDateTime.now());
        project.setPimpinanProyek("test");
        project.setKeterangan("test");
        projectRepository.save(project);

        Location lokasi = locationRepository.findByNamaLokasi("test");
        Project proyek = projectRepository.findByNamaProyek("test");
        ProjectLocation projectLocation = new ProjectLocation();
        projectLocation.setProyekId(proyek.getId());
        projectLocation.setLokasiId(lokasi.getId());
        projectLocationRepository.save(projectLocation);
    }

    @Test
    void testAddProjectSuccess() throws Exception {
        Location lokasi = locationRepository.findByNamaLokasi("test");


        AddProjectRequest request = new AddProjectRequest();
        request.setNamaProyek("test2");
        request.setClient("test");
        request.setTanggalMulai(LocalDateTime.now());
        request.setTanggalSelesai(LocalDateTime.now());
        request.setPimpinanProyek("test");
        request.setKeterangan("test");
        request.setLokasiId(lokasi.getId());

        mockMvc.perform(
                post("/proyek")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isCreated()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });

                    assertEquals("success", response.getStatus());
                    assertEquals("proyek Berhasil Ditambahkan", response.getMessage());
                }
        );
    }

    @Test
    void testAddProjectBadRequest() throws Exception {
        AddProjectRequest request = new AddProjectRequest();
        request.setNamaProyek("");
        request.setClient("");
        request.setTanggalMulai(LocalDateTime.now());
        request.setTanggalSelesai(LocalDateTime.now());
        request.setPimpinanProyek("");
        request.setKeterangan("");

        mockMvc.perform(
                post("/proyek")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });

                    assertEquals("fail", response.getStatus());
                    assertNotNull(response.getMessage());
                }
        );
    }

    @Test
    void testAddProjectAlreadyRegistered() throws Exception {
        Location lokasi = locationRepository.findByNamaLokasi("test");

        AddProjectRequest request = new AddProjectRequest();
        request.setNamaProyek("test");
        request.setClient("test");
        request.setTanggalMulai(LocalDateTime.now());
        request.setTanggalSelesai(LocalDateTime.now());
        request.setPimpinanProyek("test");
        request.setKeterangan("test");
        request.setLokasiId(lokasi.getId());

        mockMvc.perform(
                post("/proyek")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });

                    assertEquals("fail", response.getStatus());
                    assertNotNull(response.getMessage());
                }
        );
    }

    @Test
    void testGetAllProjectSuccess() throws Exception {
        Location lokasi = locationRepository.findByNamaLokasi("test");
        Project proyek = projectRepository.findByNamaProyek("test");

        ProjectLocation projectLocation = new ProjectLocation();
        projectLocation.setProyekId(proyek.getId());
        projectLocation.setLokasiId(lokasi.getId());
        projectLocationRepository.save(projectLocation);

        mockMvc.perform(
                get("/proyek")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<List>>() {
                    });

                    assertEquals("success", response.getStatus());
                    assertEquals(1, response.getData().size());
                }
        );
    }

    @Test
    void testGetAProjectSuccess() throws Exception {
        Location lokasi = locationRepository.findByNamaLokasi("test");
        Project proyek = projectRepository.findByNamaProyek("test");

        ProjectLocation projectLocation = new ProjectLocation();
        projectLocation.setProyekId(proyek.getId());
        projectLocation.setLokasiId(lokasi.getId());
        projectLocationRepository.save(projectLocation);

        mockMvc.perform(
                get("/proyek/" + proyek.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ProjectLocation> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ProjectLocation>>() {
                    });
                    assertEquals("success", response.getStatus());
                    assertEquals(proyek.getId(), response.getData().getProyek().getId());
                    assertEquals(lokasi.getId(), response.getData().getLokasi().getId());
                }
        );
    }

    @Test
    void testGetALocationNotFound() throws Exception {
        mockMvc.perform(
                get("/proyek/0")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<GetALocationResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<GetALocationResponse>>() {
                    });
                    assertEquals("fail", response.getStatus());
                    assertEquals("proyek tidak ditemukan", response.getMessage());
                }
        );
    }

    @Test
    void testUpdateProjectSuccess() throws Exception {
        Location location = new Location();
        location.setNamaLokasi("test2");
        location.setNegara("Indonesia");
        location.setKota("cimahi");
        location.setProvinsi("Jawa Barat");
        locationRepository.save(location);

        Location lokasi = locationRepository.findByNamaLokasi("test2");
        Project proyek = projectRepository.findByNamaProyek("test");

        UpdateProjectRequest request = new UpdateProjectRequest();
        request.setId(proyek.getId());
        request.setNamaProyek("test di ubah");
        request.setClient("test");
        request.setTanggalMulai(LocalDateTime.now());
        request.setTanggalSelesai(LocalDateTime.now());
        request.setPimpinanProyek("test");
        request.setKeterangan("test");
        request.setLokasiId(lokasi.getId());

        mockMvc.perform(
                put("/proyek/" + proyek.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ProjectLocation> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ProjectLocation>>() {
                    });
                    assertEquals("success", response.getStatus());
                    assertEquals(request.getNamaProyek(), response.getData().getProyek().getNamaProyek());
                    assertEquals(request.getClient(), response.getData().getProyek().getClient());
                    assertEquals(request.getTanggalMulai(), response.getData().getProyek().getTanggalMulai());
                    assertEquals(request.getTanggalSelesai(), response.getData().getProyek().getTanggalSelesai());
                    assertEquals(request.getPimpinanProyek(), response.getData().getProyek().getPimpinanProyek());
                    assertEquals(request.getKeterangan(), response.getData().getProyek().getKeterangan());
                    assertEquals(request.getLokasiId(), response.getData().getLokasiId());
                }
        );
    }
    @Test
    void testUpdateLocationBadRequest() throws Exception {
        UpdateProjectRequest request = new UpdateProjectRequest();
        request.setId(123);
        request.setNamaProyek("");
        request.setClient("");
        request.setTanggalMulai(LocalDateTime.now());
        request.setTanggalSelesai(LocalDateTime.now());
        request.setPimpinanProyek("test");
        request.setKeterangan("test");
        request.setLokasiId(123);

        mockMvc.perform(
                put("/lokasi/0")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertEquals("fail", response.getStatus());
                    assertNotNull(response.getMessage());
                }
        );
    }
}