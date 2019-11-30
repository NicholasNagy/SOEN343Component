package PropertyManager.PM.Application.Property.impl;



import PropertyManager.PM.Data.dao.PropertyDao;
import PropertyManager.PM.Application.Property.PropertyTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PropertyService {
    private final PropertyDao propertyDao;

    @Autowired
    public PropertyService(@Qualifier("property") PropertyDao propertyDao){
        this.propertyDao = propertyDao;
    }

    public int addProperty(PropertyTO property) {
        return propertyDao.insertProperty(property.getId(), property);
    }

    public PropertyTO getProperty(UUID id) {
        return propertyDao.selectPropertyById(id);
    }

    public List<PropertyTO> getAllProperties() {
        return propertyDao.selectAllProperties();
    }

    public int deleteProperty(UUID id){
        return propertyDao.deletePropertyById(id);
    }

    public PropertyTO updateProperty(UUID id, PropertyTO property) {
        return propertyDao.updatePropertyById(id, property);
    }
}
