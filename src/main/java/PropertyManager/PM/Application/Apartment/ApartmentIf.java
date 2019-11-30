package PropertyManager.PM.Application.Apartment;

import PropertyManager.PM.Application.Apartment.impl.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/apartment")
@RestController
public class ApartmentIf {

    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentIf(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public UUID addProperty(@RequestBody ApartmentTO apartment) {
        return apartmentService.addProperty(apartment);
    }

    @GetMapping(path = "{id}")
    public ApartmentTO getPropertyById(@PathVariable("id") UUID id){
        return ApartmentService.getApartment(id);
    }

    @DeleteMapping(path="{id}")
    public void deleteProperty(@PathVariable("id") UUID id){
        ApartmentService.deleteApartment(id);
    }

    //Returns null if there is no property to update.
    @PutMapping(path="{id}")
    public ApartmentTO updateProperty(@PathVariable("id") UUID id, @RequestBody ApartmentTO apartment) {
        return ApartmentService.updateApartment(id, apartment);
    }
}
