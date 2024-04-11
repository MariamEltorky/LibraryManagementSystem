package com.maids.cc.LibraryManagementSystem.controller;

import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.models.PatronRequest.PatronRequestBody;
import com.maids.cc.LibraryManagementSystem.services.PatronService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@AllArgsConstructor
public class PatronController {
    private final PatronService patronService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Patron> getAllPatrons() {
        return patronService.findAllPatrons();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getPatronByID(@PathVariable int id) {
        Patron patron = patronService.findPatronById(id);
        String jsonPatron = patronService.convertToJson(patron);
        if (patron != null) {
            return ResponseEntity.status(HttpStatus.OK).body(jsonPatron);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patron not found, Please Enter a correct PatronID");
        }
    }

    @PostMapping
    public ResponseEntity<String> addNewPatron(@Valid @RequestBody PatronRequestBody patronRequestBody) {
        Patron patron=patronService.extractPatronData(patronRequestBody);
        patronService.savePatron(patron);
        return ResponseEntity.status(HttpStatus.OK).body("Patron Added Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatron(@PathVariable int id,@Valid @RequestBody PatronRequestBody patronRequestBody) {
        if (id == patronRequestBody.getId()) {
            Patron patron=patronService.extractPatronData(patronRequestBody);
            patronService.savePatron(patron);
            return ResponseEntity.status(HttpStatus.OK).body("Patron Updated successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Enter a correct PatronID");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatron(@PathVariable int id) {
        Patron patron = patronService.findPatronById(id);
        if (patron != null) {
            patronService.deletePatronById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Patron Deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patron not found, Please Enter a correct PatronID");
        }
    }
}
