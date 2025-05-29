package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.WashNow.entities.WashProgram;
import tqs.WashNow.entities.WashSession;
import tqs.WashNow.repositories.WashSessionRepository;
import tqs.WashNow.services.WashSessionService;

@ExtendWith(MockitoExtension.class)
class WashSessionServiceTest {

    @Mock
    private WashSessionRepository repository;

    @InjectMocks
    private WashSessionService service;

    private WashSession session;

    @BeforeEach
    void setup() {
        // exemplo de sessão já existente
        session = new WashSession(
            1L,
            100L,
            WashProgram.PRE_WASH,
            LocalDateTime.of(2025, 5, 1, 9, 0),
            LocalDateTime.of(2025, 5, 1, 9, 10),
            15.0
        );
    }

    @Test
    void whenCreate_thenSaveAndReturn() {
        WashSession toCreate = new WashSession(
            null,
            200L,
            WashProgram.FOAM_SOAP,
            LocalDateTime.of(2025, 5, 2, 10, 0),
            LocalDateTime.of(2025, 5, 2, 10, 20),
            25.0
        );
        WashSession saved = new WashSession(
            2L,
            200L,
            WashProgram.FOAM_SOAP,
            toCreate.getStartedAt(),
            toCreate.getEndedAt(),
            25.0
        );

        when(repository.save(toCreate)).thenReturn(saved);

        WashSession result = service.createWashSession(toCreate);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(WashProgram.FOAM_SOAP, result.getWashProgram());
        verify(repository, times(1)).save(toCreate);
    }

    @Test
    void whenGetByIdExists_thenReturnSession() {
        when(repository.findById(1L)).thenReturn(Optional.of(session));

        WashSession result = service.getWashSessionById(1L);

        assertNotNull(result);
        assertEquals(100L, result.getBookingId());
        assertEquals(15.0, result.getTotalCost());
        verify(repository).findById(1L);
    }

    @Test
    void whenGetByIdNotExists_thenReturnNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        WashSession result = service.getWashSessionById(99L);

        assertNull(result);
        verify(repository).findById(99L);
    }

    @Test
    void whenUpdateExists_thenSetIdAndSave() {
        Long id = 3L;
        WashSession incoming = new WashSession(
            null,
            300L,
            WashProgram.WAX,
            LocalDateTime.of(2025, 6, 5, 14, 0),
            LocalDateTime.of(2025, 6, 5, 14, 30),
            45.0
        );
        WashSession updated = new WashSession(
            id,
            300L,
            WashProgram.WAX,
            incoming.getStartedAt(),
            incoming.getEndedAt(),
            45.0
        );

        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(any(WashSession.class))).thenReturn(updated);

        WashSession result = service.updateWashSessionById(id, incoming);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(WashProgram.WAX, result.getWashProgram());

        ArgumentCaptor<WashSession> captor = ArgumentCaptor.forClass(WashSession.class);
        verify(repository).save(captor.capture());
        assertEquals(id, captor.getValue().getId());
        assertEquals(300L, captor.getValue().getBookingId());
    }

    @Test
    void whenUpdateNotExists_thenReturnNullAndDontSave() {
        Long id = 50L;
        WashSession incoming = new WashSession(
            null,
            400L,
            WashProgram.HIGH_PRESSURE_RINSE,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            30.0
        );

        when(repository.existsById(id)).thenReturn(false);

        WashSession result = service.updateWashSessionById(id, incoming);

        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void whenDelete_thenRepositoryDeleteById() {
        service.deleteWashSessionById(7L);
        verify(repository, times(1)).deleteById(7L);
    }

    @Test
    void whenGetAll_thenReturnList() {
        WashSession s1 = new WashSession(
            1L, 101L, WashProgram.SOAP,
            LocalDateTime.now().minusHours(1),
            LocalDateTime.now(), 20.0
        );
        WashSession s2 = new WashSession(
            2L, 102L, WashProgram.WATER_ONLY,
            LocalDateTime.now().minusHours(2),
            LocalDateTime.now().minusHours(1), 10.0
        );
        List<WashSession> list = Arrays.asList(s1, s2);

        when(repository.findAll()).thenReturn(list);

        List<WashSession> result = service.getAllWashSessions();

        assertEquals(2, result.size());
        assertTrue(result.contains(s1));
        assertTrue(result.contains(s2));
        verify(repository).findAll();
    }
}
