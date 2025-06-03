package tqs.WashNow.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.WashNow.entities.WashSession;
import tqs.WashNow.services.WashSessionService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WashSessionControllerIT {

    @Mock
    private WashSessionService washSessionService;

    @InjectMocks
    private WashSessionController washSessionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWashSession() {
        WashSession washSession = new WashSession();
        when(washSessionService.createWashSession(washSession)).thenReturn(washSession);

        ResponseEntity<WashSession> response = washSessionController.createWashSession(washSession);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        verify(washSessionService, times(1)).createWashSession(washSession);
    }

    @Test
    void testGetAllWashSessions() {
        List<WashSession> washSessions = Arrays.asList(new WashSession(), new WashSession());
        when(washSessionService.getAllWashSessions()).thenReturn(washSessions);

        ResponseEntity<List<WashSession>> response = washSessionController.getAllWashSessions();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        verify(washSessionService, times(1)).getAllWashSessions();
    }

    @Test
    void testGetWashSessionById() {
        Long id = 1L;
        WashSession washSession = new WashSession();
        when(washSessionService.getWashSessionById(id)).thenReturn(washSession);

        ResponseEntity<WashSession> response = washSessionController.getWashSessionById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(washSessionService, times(1)).getWashSessionById(id);
    }

    @Test
    void testUpdateWashSession() {
        Long id = 1L;
        WashSession updatedWashSession = new WashSession();
        when(washSessionService.updateWashSessionById(id, updatedWashSession)).thenReturn(updatedWashSession);

        ResponseEntity<WashSession> response = washSessionController.updateWashSession(id, updatedWashSession);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(washSessionService, times(1)).updateWashSessionById(id, updatedWashSession);
    }

    @Test
    void testDeleteWashSession() {
        Long id = 1L;
        WashSession washSession = new WashSession();
        when(washSessionService.getWashSessionById(id)).thenReturn(washSession);
        doNothing().when(washSessionService).deleteWashSessionById(id);

        ResponseEntity<Void> response = washSessionController.deleteWashSession(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(washSessionService, times(1)).deleteWashSessionById(id);
    }
}