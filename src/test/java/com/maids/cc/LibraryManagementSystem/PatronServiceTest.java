package com.maids.cc.LibraryManagementSystem;

import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.models.PatronRequest.PatronRequestBody;
import com.maids.cc.LibraryManagementSystem.repositories.PatronRepo;
import com.maids.cc.LibraryManagementSystem.services.PatronService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatronServiceTest {

    @Mock
    private PatronRepo patronRepo;

    @InjectMocks
    private PatronService patronService;

    @Before
    public void setUp() {
        List<Patron> patrons = Arrays.asList(
                new Patron(2007, "John Doe", "+0115678904 - john@gmail.com"),
                new Patron(2008, "Smith Jane", "+0105678904 - jane@gmail.com"),
                new Patron(2009, "Alice Johnson", "+01212345986 - alice@gmail.com")
        );

        when(patronRepo.findAll()).thenReturn(patrons);
        when(patronRepo.findById(2007)).thenReturn(Optional.of(patrons.get(0)));
        when(patronRepo.save(any(Patron.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testFindAllPatrons() {
        List<Patron> patronList = patronService.findAllPatrons();
        assertNotNull(patronList);
        assertEquals(3, patronList.size());
        assertEquals("John Doe", patronList.get(0).getName());
    }

    @Test
    public void testFindPatronById() {
        Patron patron = patronService.findPatronById(2007);
        assertNotNull(patron);
        assertEquals("John Doe", patron.getName());
    }

    @Test
    public void testSavePatron() {
        Patron patronToSave = new Patron(2004, "Johnson", "+01156789034 - Johnson@gmail.com");
        Patron savedPatron = patronService.savePatron(patronToSave);
        assertNotNull(savedPatron);
        assertEquals("Johnson", savedPatron.getName());
        assertNotEquals("John", savedPatron.getContactInformation());
        verify(patronRepo, times(1)).save(patronToSave);
    }

    @Test
    public void testDeletePatronById() {
        patronService.deletePatronById(2009);
        verify(patronRepo, times(1)).deleteById(2009);
    }

    @Test
    public void testExtractPatronData() {
        PatronRequestBody requestBody = new PatronRequestBody(2005, "Doe", "+0113456789 - Doe@example.com");
        Patron extractedPatron = patronService.extractPatronData(requestBody);
        assertNotNull(extractedPatron);
        assertEquals(2005, extractedPatron.getId());
        assertNotEquals("Johnson", extractedPatron.getName());
        assertEquals("Doe", extractedPatron.getName());
    }
}
