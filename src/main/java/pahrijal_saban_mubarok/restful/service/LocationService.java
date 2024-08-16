package pahrijal_saban_mubarok.restful.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pahrijal_saban_mubarok.restful.entity.Lokasi;
import pahrijal_saban_mubarok.restful.model.AddLocationRequest;
import pahrijal_saban_mubarok.restful.repository.LocationRepository;

import java.util.Set;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private Validator validator;
    public void addLocation(AddLocationRequest request){
        Set<ConstraintViolation<AddLocationRequest>> constraintViolations = validator.validate(request);
        if(constraintViolations.size() != 0){
            throw new ConstraintViolationException(constraintViolations);
        }
        Lokasi lokasi = new Lokasi();
        lokasi.setNamaLokasi(request.getNamaLokasi());
        lokasi.setNegara(request.getNegara());
        lokasi.setProvinsi(request.getProvinsi());
        lokasi.setKota(request.getKota());

        locationRepository.save(lokasi);
    }
}
