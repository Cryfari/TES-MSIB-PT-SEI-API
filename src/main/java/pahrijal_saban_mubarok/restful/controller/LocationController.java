package pahrijal_saban_mubarok.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pahrijal_saban_mubarok.restful.model.AddLocationRequest;
import pahrijal_saban_mubarok.restful.model.WebResponse;
import pahrijal_saban_mubarok.restful.service.LocationService;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping(
            path = "/api/location",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public WebResponse addLocationController(@RequestBody AddLocationRequest request){
        locationService.addLocation(request);
        return WebResponse.<String>builder()
                .status("success")
                .message("Lokasi Berhasil Ditambahkan")
                .build();
    }
}
