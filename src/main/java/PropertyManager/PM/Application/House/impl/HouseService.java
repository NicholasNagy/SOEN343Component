package PropertyManager.PM.Application.House.impl;

import PropertyManager.PM.Data.dao.HouseDao;
import PropertyManager.PM.Application.House.HouseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HouseService {

    private final HouseDao houseDao;

    @Autowired
    public HouseService(@Qualifier("house") HouseDao houseDao){
        this.houseDao = houseDao;
    }

    public UUID addProperty(HouseTO house) {
        return houseDao.insertHouse(house.getId(), house);
    }

    public static HouseTO getHouse(UUID id) {
        return HouseDao.selectHouseById(id);
    }

    public static int deleteHouse(UUID id){
        return HouseDao.deleteHouseById(id);
    }

    public static HouseTO updateHouse(UUID id, HouseTO property) {
        return HouseDao.updateHouseById(id, property);
    }
}
