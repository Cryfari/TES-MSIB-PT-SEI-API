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
import pahrijal_saban_mubarok.restful.model.AddLocationRequest;
import pahrijal_saban_mubarok.restful.model.WebResponse;
import pahrijal_saban_mubarok.restful.repository.LocationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

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
    void setUp() {
        locationRepository.deleteAll();
    }

    @Test
    void testAddLocationSuccess() throws Exception{
        AddLocationRequest request = new AddLocationRequest();
        request.setNamaLokasi("test");
        request.setNegara("Indonesia");
        request.setKota("Bandung");
        request.setProvinsi("Jawa Barat");

        mockMvc.perform(
                post("/api/location")
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

}