package PropertyManager.PM.Application.Apartment.impl;

import PropertyManager.PM.Data.dao.ApartmentDao;
import PropertyManager.PM.Application.Apartment.ApartmentTO;
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

    public UUID addProperty(ApartmentTO apartment) {
        return apartmentDao.insertApartment(apartment.getId(), apartment);
    }

    public static ApartmentTO getApartment(UUID id) {
        return ApartmentDao.selectApartmentById(id);
    }

    public static int deleteApartment(UUID id){
        return ApartmentDao.deleteApartmentById(id);
    }

    public static ApartmentTO updateApartment(UUID id, ApartmentTO property) {
        return ApartmentDao.updateApartmentById(id, property);
    }

}
