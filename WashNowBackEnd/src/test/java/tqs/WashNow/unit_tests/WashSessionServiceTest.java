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
    private WashSessionRepository washSessionRepository;

    @InjectMocks
    private WashSessionService washSessionService;

    private WashSession session;

    private Long id = 1L;

    @BeforeEach
    void setup() {
        // exemplo de sessão já existente
        session = new WashSession(
            id,
            100L,
            WashProgram.PRE_WASH,
            LocalDateTime.of(2025, 5, 1, 9, 0),
            LocalDateTime.of(2025, 5, 1, 9, 10),
            15.0
        );
    }

    @Test
    void testCreateBooking() {
        when(washSessionRepository.save(session)).thenReturn(session);
        WashSession created = washSessionService.createWashSession(session);
        assertNotNull(created);
        assertEquals(id, created.getId());
        verify(washSessionRepository, times(1)).save(session);
    }

    @Test
    void testCreateBookingWhenExists() {
        when(washSessionRepository.existsById(1L)).thenReturn(true);
        washSessionRepository.save(session);
        assertTrue(washSessionRepository.existsById(1L));

        WashSession created = washSessionService.createWashSession(session);
        assertNull(created);
        verify(washSessionRepository, times(1)).save(session);
    }

    @Test
    void testGetBookingById() {
        when(washSessionRepository.findById(1L)).thenReturn(Optional.of(session));

        WashSession found = washSessionService.getWashSessionById(1L);
        assertNotNull(found);
        assertEquals(session.getId(), found.getId());
        verify(washSessionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookingByIdWhenNotExists() {
        when(washSessionRepository.findById(1L)).thenReturn(Optional.empty());
        WashSession found = washSessionService.getWashSessionById(1L);
        assertNull(found);
        verify(washSessionRepository).findById(1L);
    }    

    @Test
    void testUpdateBookingByIdWhenExists() {
        when(washSessionRepository.existsById(1L)).thenReturn(true);
        when(washSessionRepository.save(session)).thenReturn(session);

        WashSession updated = washSessionService.updateWashSessionById(1L, session);
        assertNotNull(updated);
        assertEquals(session.getId(), updated.getId());
        verify(washSessionRepository, times(1)).existsById(1L);
        verify(washSessionRepository, times(1)).save(session);
    }

    @Test
    void testUpdateBookingByIdWhenNotExists() {
        when(washSessionRepository.existsById(1L)).thenReturn(false);

        WashSession updated = washSessionService.updateWashSessionById(1L, session);
        assertNull(updated);
        verify(washSessionRepository, times(1)).existsById(1L);
        verify(washSessionRepository, never()).save(session);
    }

    @Test
    void testDeleteBookingById() {
        washSessionService.deleteWashSessionById(1L);
        verify(washSessionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllBookings() {
        List<WashSession> allBookings = Arrays.asList(session, new WashSession());
        when(washSessionRepository.findAll()).thenReturn(allBookings);

        List<WashSession> result = washSessionService.getAllWashSessions();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(session));
        verify(washSessionRepository, times(1)).findAll();
    }
}
