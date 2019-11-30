package PropertyManager.PM.Application.House;

import PropertyManager.PM.Application.House.impl.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/house")
@RestController
public class HouseIf {

    private final HouseService houseService;

    @Autowired
    public HouseIf(HouseService houseService) {

        this.houseService = houseService;
    }

    @PostMapping
    public UUID addProperty(@RequestBody HouseTO house) {
        return houseService.addProperty(house);
    }

    @GetMapping(path = "{id}")
    public HouseTO getPropertyById(@PathVariable("id") UUID id){
        return HouseService.getHouse(id);
    }

    @DeleteMapping(path="{id}")
    public void deleteProperty(@PathVariable("id") UUID id){
        HouseService.deleteHouse(id);
    }

    //Returns null if there is no property to update.
    @PutMapping(path="{id}")
    public HouseTO updateProperty(@PathVariable("id") UUID id, @RequestBody HouseTO house) {
        return HouseService.updateHouse(id, house);
    }
}
