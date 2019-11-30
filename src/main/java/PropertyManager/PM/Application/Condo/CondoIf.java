package PropertyManager.PM.Application.Condo;
import PropertyManager.PM.Application.Condo.impl.CondoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/condo")
@RestController
public class CondoIf {

    private final CondoService condoService;

    @Autowired
    public CondoIf(CondoService condoService) {

        this.condoService = condoService;
    }

    @PostMapping
    public UUID addProperty(@RequestBody CondoTO condo) {
        return condoService.addProperty(condo);
    }

    @GetMapping(path = "{id}")
    public CondoTO getPropertyById(@PathVariable("id") UUID id){
        return CondoService.getCondo(id);
    }

    @DeleteMapping(path="{id}")
    public void deleteProperty(@PathVariable("id") UUID id){
        CondoService.deleteCondo(id);
    }

    //Returns null if there is no property to update.
    @PutMapping(path="{id}")
    public CondoTO updateProperty(@PathVariable("id") UUID id, @RequestBody CondoTO condo) {
        return CondoService.updateCondo(id, condo);
    }
}
