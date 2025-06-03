package tqs.WashNow.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tqs.WashNow.entities.WashSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class WashSessionRepositoryIT {

    @Autowired
    private WashSessionRepository washSessionRepository;

    @Test
    void testSaveWashSession() {
        WashSession washSession = new WashSession();

        WashSession savedWashSession = washSessionRepository.save(washSession);

        assertThat(savedWashSession).isNotNull();
        assertThat(savedWashSession.getId()).isNotNull();
    }

    @Test
    void testFindWashSessionById() {
        WashSession washSession = new WashSession();
        washSession = washSessionRepository.save(washSession);

        Optional<WashSession> retrievedWashSession = washSessionRepository.findById(washSession.getId());

        assertThat(retrievedWashSession).isPresent();
    }

    @Test
    void testDeleteWashSession() {
        WashSession washSession = new WashSession();
        washSession = washSessionRepository.save(washSession);

        washSessionRepository.deleteById(washSession.getId());

        Optional<WashSession> deletedWashSession = washSessionRepository.findById(washSession.getId());
        assertThat(deletedWashSession).isNotPresent();
    }
}