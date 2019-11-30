package PropertyManager.PM.Application.Condo.impl;

import PropertyManager.PM.Data.dao.CondoDao;
import PropertyManager.PM.Application.Condo.CondoTO;
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

    public UUID addProperty(CondoTO condo) {
        return condoDao.insertCondo(condo.getId(), condo);
    }

    public static CondoTO getCondo(UUID id) {
        return CondoDao.selectCondoById(id);
    }

    public static int deleteCondo(UUID id){
        return CondoDao.deleteCondoById(id);
    }

    public static CondoTO updateCondo(UUID id, CondoTO property) {
        return CondoDao.updateCondoById(id, property);
    }

}
