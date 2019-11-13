package PropertyManager.PM.api;

import PropertyManager.PM.model.Property;
import PropertyManager.PM.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/property")
@RestController
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public void addProperty(@RequestBody Property property) {
        propertyService.addProperty(property);
    }

    @GetMapping(path = "{id}")
    public Property getPropertyById(@PathVariable("id") UUID id){
        return propertyService.getProperty(id);
    }

    @GetMapping
    public List<Property> getAllProperties() { return propertyService.getAllProperties();}

    @DeleteMapping(path="{id}")
    public void deleteProperty(@PathVariable("id") UUID id){
        propertyService.deleteProperty(id);
    }

    //Returns null if there is no property to update.
    @PutMapping(path="{id}")
    public Property updateProperty(@PathVariable("id") UUID id, @RequestBody Property property) {
        return propertyService.updateProperty(id, property);
    }
}
