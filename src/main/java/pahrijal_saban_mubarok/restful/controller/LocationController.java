package pahrijal_saban_mubarok.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pahrijal_saban_mubarok.restful.entity.Location;
import pahrijal_saban_mubarok.restful.model.AddLocationRequest;
import pahrijal_saban_mubarok.restful.model.WebResponse;
import pahrijal_saban_mubarok.restful.service.LocationService;

import java.util.List;

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

    @GetMapping(
            path = "/lokasi",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse getAllLocationController(){
        List data = locationService.getAllLocation();
        return WebResponse.<List>builder()
                .status("success")
                .data(data)
                .build();
    }
}
