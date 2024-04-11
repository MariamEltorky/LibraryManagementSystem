package com.maids.cc.LibraryManagementSystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.models.PatronRequest.PatronRequestBody;
import com.maids.cc.LibraryManagementSystem.repositories.PatronRepo;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PatronService {
    static Logger logger = LogManager.getLogger(PatronService.class);

    private final PatronRepo patronRepo;
//    @Cacheable("patrons")
    public List<Patron> findAllPatrons() {
        return patronRepo.findAll();
    }
    public Patron findPatronById(int id) {
        Patron patron=patronRepo.findById(id).orElse(null);
        return patron;
    }

    @Transactional(rollbackFor = {Exception.class})
    public Patron savePatron(Patron patron) {
        return patronRepo.save(patron);
    }
    @Transactional
    public void deletePatronById(int id) {

        patronRepo.deleteById(id);
    }

    public Patron extractPatronData(PatronRequestBody patronRequestBody) {
        int id= patronRequestBody.getId();
        String name = patronRequestBody.getName();
        String contactInformation= patronRequestBody.getContactInformation();
        Patron patron=new Patron(id , name , contactInformation);
        return patron;
    }

    public String convertToJson(Patron patron) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(patron);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
