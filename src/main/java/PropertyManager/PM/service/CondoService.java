package PropertyManager.PM.service;

import PropertyManager.PM.dao.ApartmentDao;
import PropertyManager.PM.dao.CondoDao;
import PropertyManager.PM.model.Condo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CondoService {

    private final CondoDao condoDao;

    @Autowired
    public CondoService(@Qualifier("condo") CondoDao condoDao){
        this.condoDao = condoDao;
    }

    public UUID addProperty(Condo condo) {
        return condoDao.insertCondo(condo.getId(), condo);
    }

    public static Condo getCondo(UUID id) {
        return CondoDao.selectCondoById(id);
    }

    public static int deleteCondo(UUID id){
        return CondoDao.deleteCondoById(id);
    }

    public static Condo updateCondo(UUID id, Condo property) {
        return CondoDao.updateCondoById(id, property);
    }

}
