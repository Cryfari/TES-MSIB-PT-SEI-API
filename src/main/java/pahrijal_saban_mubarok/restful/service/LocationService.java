package pahrijal_saban_mubarok.restful.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pahrijal_saban_mubarok.restful.entity.Location;
import pahrijal_saban_mubarok.restful.model.AddLocationRequest;
import pahrijal_saban_mubarok.restful.repository.LocationRepository;

import java.util.Set;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private Validator validator;

    @Transactional
    public void addLocation(AddLocationRequest request){
        Set<ConstraintViolation<AddLocationRequest>> constraintViolations = validator.validate(request);
        if(constraintViolations.size() != 0){
            throw new ConstraintViolationException(constraintViolations);
        }
        if (locationRepository.existsByNamaLokasi(request.getNamaLokasi())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "location already registered");
        }
        Location location = new Location();
        location.setNamaLokasi(request.getNamaLokasi());
        location.setNegara(request.getNegara());
        location.setProvinsi(request.getProvinsi());
        location.setKota(request.getKota());

        locationRepository.save(location);
    }
}
