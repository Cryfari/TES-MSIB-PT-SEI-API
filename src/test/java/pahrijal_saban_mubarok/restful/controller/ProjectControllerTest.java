package pahrijal_saban_mubarok.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pahrijal_saban_mubarok.restful.entity.Location;
import pahrijal_saban_mubarok.restful.entity.Project;
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
    @AfterEach
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
    }

    @Test
    void testAddProjectSuccess() throws Exception {
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
    void testAddLocationBadRequest() throws Exception {
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
    void testAddLocationAlreadyRegistered() throws Exception {
        Location lokasi = locationRepository.findByNamaLokasi("test");


        Project project = new Project();
        project.setNamaProyek("test");
        project.setClient("test");
        project.setTanggalMulai(LocalDateTime.now());
        project.setTanggalSelesai(LocalDateTime.now());
        project.setPimpinanProyek("test");
        project.setKeterangan("test");
        projectRepository.save(project);

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
}