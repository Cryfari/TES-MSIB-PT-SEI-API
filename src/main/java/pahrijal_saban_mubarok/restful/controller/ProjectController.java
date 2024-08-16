package pahrijal_saban_mubarok.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pahrijal_saban_mubarok.restful.model.AddProjectRequest;
import pahrijal_saban_mubarok.restful.model.WebResponse;
import pahrijal_saban_mubarok.restful.service.ProjectService;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping(
            path = "/proyek",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public WebResponse postProjectController(@RequestBody AddProjectRequest request){
        projectService.addProject(request);
        return WebResponse.<String>builder()
                .status("success")
                .message("proyek Berhasil Ditambahkan")
                .build();
    }

    @GetMapping(
            path = "/proyek",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse getAllLocationController(){
        List data = projectService.getAllProject();
        return WebResponse.<List>builder()
                .status("success")
                .data(data)
                .build();
    }
}
