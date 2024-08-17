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
import pahrijal_saban_mubarok.restful.model.*;
import pahrijal_saban_mubarok.restful.repository.LocationRepository;

import java.util.List;
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

    @Transactional(readOnly = true)
    public List<Location> getAllLocation(){
        return locationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public GetALocationResponse getALocation(Integer id) {
        Location lokasi = locationRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lokasi tidak ditemukan"));

        return toGetLocationResponse(lokasi);
    }

    private GetALocationResponse toGetLocationResponse(Location lokasi){
        return GetALocationResponse.builder()
                .id(lokasi.getId())
                .namaLokasi(lokasi.getNamaLokasi())
                .negara(lokasi.getNegara())
                .kota(lokasi.getKota())
                .provinsi(lokasi.getProvinsi())
                .build();
    }

    @Transactional
    public LocationResponse updateLocation(UpdateLocationRequest request){
        Set<ConstraintViolation<UpdateLocationRequest>> constraintViolations = validator.validate(request);
        System.err.println("tes");
        if(constraintViolations.size() != 0){
            throw new ConstraintViolationException(constraintViolations);
        }
        Location location = locationRepository.findById(String.valueOf(request.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lokasi tidak ditemukan"));

        location.setNamaLokasi(request.getNamaLokasi());
        location.setNegara(request.getNegara());
        location.setKota(request.getKota());
        location.setProvinsi(request.getProvinsi());
        locationRepository.save(location);

        return toLocationResponse(location);
    }

    private LocationResponse toLocationResponse(Location location){
        return LocationResponse.builder()
                .kota(location.getKota())
                .namaLokasi(location.getNamaLokasi())
                .negara(location.getNegara())
                .provinsi(location.getProvinsi())
                .build();
    }

    public void deleteLocation(Integer id){
        Location location = locationRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lokasi tidak ditemukan"));

        locationRepository.delete(location);
    }
}
