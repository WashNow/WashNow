package tqs.WashNow.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.WashNow.entities.CarwashStation;
import tqs.WashNow.repositories.CarwashStationRepository;

@Service
public class CarwashStationService {

    private CarwashStationRepository CarwashStationRepository;

    @Autowired
    public CarwashStationService(CarwashStationRepository carwashStationRepository){
        this.CarwashStationRepository = carwashStationRepository;
    }

    // POST
    public CarwashStation createCarwashStation(CarwashStation CarwashStation) {
        if (CarwashStationRepository.existsById(CarwashStation.getId())) return null;
        
        return CarwashStationRepository.save(CarwashStation);
    }

    // GET
    public CarwashStation getCarwashStationById(Long id) {
        return CarwashStationRepository.findById(id).orElse(null);
    }

    // PUT
    public CarwashStation updateCarwashStationById(Long id, CarwashStation CarwashStation) {
        if (CarwashStationRepository.existsById(id)) {
            CarwashStation.setId(id);
            return CarwashStationRepository.save(CarwashStation);
        }
        return null;
    }

    // DELETE
    public void deleteCarwashStationById(Long id) {
        CarwashStationRepository.deleteById(id);
    }

    // GET ALL
    public List<CarwashStation> getAllCarwashStations() {
        return CarwashStationRepository.findAll();
    }
    
}
