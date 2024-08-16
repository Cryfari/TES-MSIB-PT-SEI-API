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
import pahrijal_saban_mubarok.restful.model.*;
import pahrijal_saban_mubarok.restful.repository.LocationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    @AfterEach
    void setUp() {
        locationRepository.deleteAll();
    }

    @Test
    void testAddLocationSuccess() throws Exception {
        AddLocationRequest request = new AddLocationRequest();
        request.setNamaLokasi("test");
        request.setNegara("Indonesia");
        request.setKota("Bandung");
        request.setProvinsi("Jawa Barat");

        mockMvc.perform(
                post("/lokasi")
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
                    assertEquals("Lokasi Berhasil Ditambahkan", response.getMessage());
                }
        );
    }

    @Test
    void testAddLocationAlreadyRegistered() throws Exception {
        Location location = new Location();
        location.setNamaLokasi("test");
        location.setNegara("Indonesia");
        location.setKota("Bandung");
        location.setProvinsi("Jawa Barat");
        locationRepository.save(location);

        AddLocationRequest request = new AddLocationRequest();
        request.setNamaLokasi("test");
        request.setNegara("Indonesia");
        request.setKota("Bandung");
        request.setProvinsi("Jawa Barat");

        mockMvc.perform(
                post("/lokasi")
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
                    assertEquals("location already registered", response.getMessage());
                }
        );
    }

    @Test
    void testAddLocationBadRequest() throws Exception {
        AddLocationRequest request = new AddLocationRequest();
        request.setNamaLokasi("");
        request.setNegara("");
        request.setKota("");
        request.setProvinsi("");

        mockMvc.perform(
                post("/lokasi")
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
    void testGetAllLocation() throws Exception {
        Location location = new Location();
        location.setNamaLokasi("test");
        location.setNegara("Indonesia");
        location.setKota("Bandung");
        location.setProvinsi("Jawa Barat");
        locationRepository.save(location);

        mockMvc.perform(
                get("/lokasi")
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
    void testGetALocationSuccess() throws Exception {
        Location location = new Location();
        location.setNamaLokasi("test");
        location.setNegara("Indonesia");
        location.setKota("Bandung");
        location.setProvinsi("Jawa Barat");
        locationRepository.save(location);
        Location lokasi = locationRepository.findByNamaLokasi("test");

        GetALocationResponse expectedData = new GetALocationResponse();
        expectedData.setId(lokasi.getId());
        expectedData.setKota("Bandung");
        expectedData.setNamaLokasi("test");
        expectedData.setNegara("Indonesia");
        expectedData.setProvinsi("Jawa Barat");

        mockMvc.perform(
                get("/lokasi/" + lokasi.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<GetALocationResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<GetALocationResponse>>() {
                    });
                    assertEquals("success", response.getStatus());
                    assertEquals(expectedData, response.getData());
                }
        );
    }

    @Test
    void testGetALocationNotFound() throws Exception {
        mockMvc.perform(
                get("/lokasi/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<GetALocationResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<GetALocationResponse>>() {
                    });
                    assertEquals("fail", response.getStatus());
                    assertEquals("lokasi tidak ditemukan", response.getMessage());
                }
        );
    }

    @Test
    void testUpdateLocationSuccess() throws Exception {
        Location location = new Location();
        location.setNamaLokasi("test");
        location.setNegara("Indonesia");
        location.setKota("Bandung");
        location.setProvinsi("Jawa Barat");
        locationRepository.save(location);
        Location lokasi = locationRepository.findByNamaLokasi("test");

        UpdateLocationRequest request = new UpdateLocationRequest();
        request.setId(lokasi.getId());
        request.setKota("cimahi");
        request.setNamaLokasi("test di ubah");
        request.setNegara("jerman");
        request.setProvinsi("jawa timur");

        mockMvc.perform(
                put("/lokasi/" + lokasi.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<LocationResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<LocationResponse>>() {
                    });
                    assertEquals("success", response.getStatus());
                    assertEquals(request.getNamaLokasi(), response.getData().getNamaLokasi());
                    assertEquals(request.getKota(), response.getData().getKota());
                    assertEquals(request.getProvinsi(), response.getData().getProvinsi());
                    assertEquals(request.getNegara(), response.getData().getNegara());
                }
        );
    }

    @Test
    void testUpdateLocationBadRequest() throws Exception {
        UpdateLocationRequest request = new UpdateLocationRequest();
        request.setKota("");
        request.setNamaLokasi("");
        request.setNegara("");

        mockMvc.perform(
                put("/lokasi/12345")
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