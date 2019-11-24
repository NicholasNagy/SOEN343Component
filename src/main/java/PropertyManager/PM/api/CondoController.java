package PropertyManager.PM.api;
import PropertyManager.PM.model.Condo;
import PropertyManager.PM.service.CondoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/condo")
@RestController
public class CondoController {

    private final CondoService condoService;

    @Autowired
    public CondoController(CondoService condoService) {

        this.condoService = condoService;
    }

    @PostMapping
    public void addProperty(@RequestBody Condo condo) {
        condoService.addProperty(condo);
    }

    @GetMapping(path = "{id}")
    public Condo getPropertyById(@PathVariable("id") UUID id){
        return CondoService.getCondo(id);
    }

    @DeleteMapping(path="{id}")
    public void deleteProperty(@PathVariable("id") UUID id){
        CondoService.deleteCondo(id);
    }

    //Returns null if there is no property to update.
    @PutMapping(path="{id}")
    public Condo updateProperty(@PathVariable("id") UUID id, @RequestBody Condo condo) {
        return CondoService.updateCondo(id, condo);
    }
}
