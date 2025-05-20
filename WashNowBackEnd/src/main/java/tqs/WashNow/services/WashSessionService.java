package tqs.WashNow.services;

import org.springframework.stereotype.Service;

import tqs.WashNow.repositories.WashSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tqs.WashNow.entities.WashSession;
import java.util.List;


@Service
public class WashSessionService {

    private WashSessionRepository WashSessionRepository;

    @Autowired
    public WashSessionService(WashSessionRepository WashSessionRepository) {
        this.WashSessionRepository = WashSessionRepository;
    }

    // POST
    public WashSession createWashSession(WashSession WashSession) {
        return WashSessionRepository.save(WashSession);
    }

    // GET
    public WashSession getWashSessionById(Long id) {
        return WashSessionRepository.findById(id).orElse(null);
    }

    // PUT
    public WashSession updateWashSessionById(Long id, WashSession WashSession) {
        if (WashSessionRepository.existsById(id)) {
            WashSession.setId(id);
            return WashSessionRepository.save(WashSession);
        }
        return null;
    }

    // DELETE
    public void deleteWashSessionById(Long id) {
        WashSessionRepository.deleteById(id);
    }

    // GET ALL
    public List<WashSession> getAllWashSessions() {
        return WashSessionRepository.findAll();
    }
    
    
}
