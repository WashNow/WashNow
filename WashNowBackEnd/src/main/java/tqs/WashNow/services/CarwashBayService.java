package tqs.WashNow.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.WashNow.entities.CarwashBay;
import tqs.WashNow.repositories.CarwashBayRepository;

@Service
public class CarwashBayService {

    private CarwashBayRepository CarwashBayRepository;

    @Autowired
    public CarwashBayService(CarwashBayRepository carwashBayRepository){
        this.CarwashBayRepository = carwashBayRepository;
    }

    // POST
    public CarwashBay createCarwashBay(CarwashBay CarwashBay) {
        return CarwashBayRepository.save(CarwashBay);
    }

    // GET
    public CarwashBay getCarwashBayById(Long id) {
        return CarwashBayRepository.findById(id).orElse(null);
    }

    // PUT
    public CarwashBay updateCarwashBayById(Long id, CarwashBay CarwashBay) {
        if (CarwashBayRepository.existsById(id)) {
            CarwashBay.setId(id);
            return CarwashBayRepository.save(CarwashBay);
        }
        return null;
    }

    // DELETE
    public void deleteCarwashBayById(Long id) {
        CarwashBayRepository.deleteById(id);
    }

    // GET ALL
    public List<CarwashBay> getAllCarwashBays() {
        return CarwashBayRepository.findAll();
    }
    
}
