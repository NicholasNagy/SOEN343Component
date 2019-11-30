package PropertyManager.PM.Application.Property;

import PropertyManager.PM.Application.Property.impl.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/property")
@RestController
public class PropertyIf {

    private final PropertyService propertyService;

    @Autowired
    public PropertyIf(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public void addProperty(@RequestBody PropertyTO property) {
        propertyService.addProperty(property);
    }


    @GetMapping(path = "{id}")
    public PropertyTO getPropertyById(@PathVariable("id") UUID id){
        return propertyService.getProperty(id);
    }

    @GetMapping
    public List<PropertyTO> getAllProperties() { return propertyService.getAllProperties();}

    @DeleteMapping(path="{id}")
    public void deleteProperty(@PathVariable("id") UUID id){
        propertyService.deleteProperty(id);
    }

    //Returns null if there is no property to update.
    @PutMapping(path="{id}")
    public PropertyTO updateProperty(@PathVariable("id") UUID id, @RequestBody PropertyTO property) {
        return propertyService.updateProperty(id, property);
    }
}
