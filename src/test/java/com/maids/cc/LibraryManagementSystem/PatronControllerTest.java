package com.maids.cc.LibraryManagementSystem;


import com.maids.cc.LibraryManagementSystem.controller.PatronController;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.models.PatronRequest.PatronRequestBody;
import com.maids.cc.LibraryManagementSystem.services.PatronService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatronControllerTest {
    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    @Test
    public void testGetAllPatrons() {
        List<Patron> patrons = Arrays.asList(
                new Patron(2007, "John Doe", "+0115678904 - john@gmail.com"),
                new Patron(2008, "Smith Jane", "+0105678904 - jane@gmail.com"),
                new Patron(2009, "Alice Johnson", "+01212345986 - alice@gmail.com")
        );
        when(patronService.findAllPatrons()).thenReturn(patrons);
        List<Patron> result = patronController.getAllPatrons();
        assertEquals(patrons, result);
    }

    @Test
    public void testGetPatronByID() {
        int patronID = 2007;
        String expectedJson = "{\"id\":2007,\"name\":\"John Doe\",\"contactInformation\":\"+0115678904 - john@gmail.com\"}";
        Patron patron=new Patron(2007, "John Doe", "+0115678904 - john@gmail.com");
        when(patronService.findPatronById(patronID)).thenReturn(patron);
        when(patronService.convertToJson(patron)).thenReturn(expectedJson);
        ResponseEntity<String> response = patronController.getPatronByID(patronID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedJson, response.getBody());
    }

    @Test
    public void testGetPatronByID_PatronNotFound() {
        int patronID = 2007;
        when(patronService.findPatronById(patronID)).thenReturn(null);
        ResponseEntity<String> response = patronController.getPatronByID(patronID);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Patron not found, Please Enter a correct PatronID", response.getBody());
    }

    @Test
    public void testAddNewPatron() {
        PatronRequestBody patronRequestBody = new PatronRequestBody(2007, "John Doe", "+0115678904 - john@gmail.com");
        Patron patron = new Patron(2007, "John Doe", "+0115678904 - john@gmail.com");
        when(patronService.extractPatronData(patronRequestBody)).thenReturn(patron);
        ResponseEntity<String> response = patronController.addNewPatron(patronRequestBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Patron Added Successfully", response.getBody());
        verify(patronService).savePatron(patron);
    }

    @Test
    public void testUpdatePatron() {
        int patronID = 2007;
        PatronRequestBody patronRequestBody = new PatronRequestBody(2007, "John Doe", "+0115678904 - john@gmail.com");
        Patron patron = new Patron(2007, "John Doe", "+0115678904 - john@gmail.com");
        when(patronService.extractPatronData(patronRequestBody)).thenReturn(patron);
        ResponseEntity<String> response = patronController.updatePatron(patronID, patronRequestBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Patron Updated successfully", response.getBody());
        verify(patronService).savePatron(patron);
    }

    @Test
    public void testUpdatePatron_DifferentId() {
        int patronID = 2007;
        int anotherID = 3000;
        PatronRequestBody patronRequestBody = new PatronRequestBody(anotherID, "John Doe", "+0115678904 - john@gmail.com");
        ResponseEntity<String> response = patronController.updatePatron(patronID, patronRequestBody);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(patronService, never()).savePatron(any());
    }

    @Test
    public void testDeletePatron() {
        int patronID = 2007;
        Patron existingPatron =new Patron(2007, "John Doe", "+0115678904 - john@gmail.com");
        when(patronService.findPatronById(patronID)).thenReturn(existingPatron);
        ResponseEntity<String> response = patronController.deletePatron(patronID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Patron Deleted successfully", response.getBody());
        verify(patronService).deletePatronById(patronID);
    }

    @Test
    public void testDeletePatron_PatronNotFound() {
        int incorrectID = 3000;
        when(patronService.findPatronById(incorrectID)).thenReturn(null);
        ResponseEntity<String> response = patronController.deletePatron(incorrectID);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Patron not found, Please Enter a correct PatronID", response.getBody());
        verify(patronService, never()).deletePatronById(anyInt());
    }
}
