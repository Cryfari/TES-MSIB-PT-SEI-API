package pahrijal_saban_mubarok.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pahrijal_saban_mubarok.restful.model.*;
import pahrijal_saban_mubarok.restful.service.LocationService;

import java.util.List;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping(
            path = "/lokasi",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public WebResponse addLocationController(@RequestBody AddLocationRequest request){
        locationService.addLocation(request);
        return WebResponse.<String>builder()
                .status("success")
                .message("lokasi berhasil ditambahkan")
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

    @GetMapping(
            path = "/lokasi/{lokasiId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse getALocationController(@PathVariable("lokasiId") Integer id){
        GetALocationResponse getALocationResponse = locationService.getALocation(id);
        return WebResponse.<GetALocationResponse>builder()
                .status("success")
                .data(getALocationResponse)
                .build();
    }

    @PutMapping(
            path = "/lokasi/{lokasiId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse putAlocationController(
            @PathVariable("lokasiId") Integer id,
            @RequestBody UpdateLocationRequest request
    ){
        System.err.println("tes");
        request.setId(id);
        LocationResponse locationResponse = locationService.updateLocation(request);
        return WebResponse.<LocationResponse>builder()
                .status("success")
                .data(locationResponse)
                .build();
    }

    @DeleteMapping(
            path = "/lokasi/{lokasiId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse deleteLocationController(@PathVariable("lokasiId") Integer id){
        locationService.deleteLocation(id);
        return WebResponse.<String>builder()
                .status("success")
                .message("lokasi berhasil dihapus")
                .build();
    }
}
