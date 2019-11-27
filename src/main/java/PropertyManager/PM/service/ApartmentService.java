package PropertyManager.PM.service;

import PropertyManager.PM.dao.ApartmentDao;
import PropertyManager.PM.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApartmentService {

    private final ApartmentDao apartmentDao;

    @Autowired
    public ApartmentService(@Qualifier("apartment") ApartmentDao apartmentDao){
        this.apartmentDao = apartmentDao;
    }

    public UUID addProperty(Apartment apartment) {
        return apartmentDao.insertApartment(apartment.getId(), apartment);
    }

    public static Apartment getApartment(UUID id) {
        return ApartmentDao.selectApartmentById(id);
    }

    public static int deleteApartment(UUID id){
        return ApartmentDao.deleteApartmentById(id);
    }

    public static Apartment updateApartment(UUID id, Apartment property) {
        return ApartmentDao.updateApartmentById(id, property);
    }

}
