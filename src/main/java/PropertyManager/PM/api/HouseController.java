package PropertyManager.PM.api;

import PropertyManager.PM.model.House;

import PropertyManager.PM.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RequestMapping("api/v1/house")
@RestController
public class HouseController {

    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {

        this.houseService = houseService;
    }

    @PostMapping
    public void addProperty(@RequestBody House house) {
        houseService.addProperty(house);
    }

    @GetMapping(path = "{id}")
    public House getPropertyById(@PathVariable("id") UUID id){
        return HouseService.getHouse(id);
    }

    @DeleteMapping(path="{id}")
    public void deleteProperty(@PathVariable("id") UUID id){
        HouseService.deleteHouse(id);
    }

    //Returns null if there is no property to update.
    @PutMapping(path="{id}")
    public House updateProperty(@PathVariable("id") UUID id, @RequestBody House house) {
        return HouseService.updateHouse(id, house);
    }
}
