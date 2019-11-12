package PropertyManager.PM.service;



import PropertyManager.PM.dao.PropertyDao;
import PropertyManager.PM.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {
    private final PropertyDao propertyDao;

    @Autowired
    public PropertyService(@Qualifier("property") PropertyDao propertyDao){
        this.propertyDao = propertyDao;
    }

    public int addProperty(Property property) {
        return propertyDao.insertProperty(property.getId(), property);
    }

}
