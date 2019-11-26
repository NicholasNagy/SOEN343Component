package PropertyManager.PM.api;

import PropertyManager.PM.model.Apartment;
import PropertyManager.PM.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/apartment")
@RestController
public class ApartmentController {

    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public void addProperty(@RequestBody Apartment apartment) {
        apartmentService.addProperty(apartment);
    }

    @GetMapping(path = "{id}")
    public Apartment getPropertyById(@PathVariable("id") UUID id){
        return ApartmentService.getApartment(id);
    }

    @DeleteMapping(path="{id}")
    public void deleteProperty(@PathVariable("id") UUID id){
        ApartmentService.deleteApartment(id);
    }

    //Returns null if there is no property to update.
    @PutMapping(path="{id}")
    public Apartment updateProperty(@PathVariable("id") UUID id, @RequestBody Apartment apartment) {
        return ApartmentService.updateApartment(id, apartment);
    }
}
