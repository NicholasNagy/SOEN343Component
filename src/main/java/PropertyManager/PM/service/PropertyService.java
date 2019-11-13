package PropertyManager.PM.service;



import PropertyManager.PM.dao.PropertyDao;
import PropertyManager.PM.model.Property;
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

    public int addProperty(Property property) {
        return propertyDao.insertProperty(property.getId(), property);
    }

    public Property getProperty(UUID id) {
        return propertyDao.selectPropertyById(id);
    }

    public List<Property> getAllProperties() {
        return propertyDao.selectAllProperties();
    }

    public int deleteProperty(UUID id){
        return propertyDao.deletePersonById(id);
    }

    public Property updateProperty(UUID id, Property property) {
        return propertyDao.updatePersonById(id, property);
    }
}
