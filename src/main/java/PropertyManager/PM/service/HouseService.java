package PropertyManager.PM.service;

import PropertyManager.PM.dao.HouseDao;
import PropertyManager.PM.model.House;
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

    public int addProperty(House house) {
        return houseDao.insertHouse(house.getId(), house);
    }

    public static House getHouse(UUID id) {
        return HouseDao.selectHouseById(id);
    }

    public static int deleteHouse(UUID id){
        return HouseDao.deleteHouseById(id);
    }

    public static House updateHouse(UUID id, House property) {
        return HouseDao.updateHouseById(id, property);
    }
}
