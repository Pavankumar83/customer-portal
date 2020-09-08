package com.pavan.web.rest.ext;

import com.pavan.dto.PersonDTO;
import com.pavan.dto.PersonDTO2;
import com.pavan.service.FileReaderService;
import com.pavan.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonResourceExt {


    @Autowired
    private PersonService personService;

    @Autowired
    private FileReaderService service;

    @GetMapping(path = "/savefile")
    public void saveFile(){
          // service.readMutlipleFilesFromDirectory();
    }

    @GetMapping(path = "/person-by-id/{id}")
    public List<PersonDTO> getPersonById(@PathVariable("id") Long id){
        System.out.println("id = " + id);

        // return ResponseUtil.wrapOrNotFound(personList);

        return personService.findPersonById(id);
    }
    @GetMapping(path = "/person-info-by-id/{id}")
    public PersonDTO2 getPersonInfoById(@PathVariable("id") Long id){
        System.out.println("id = " + id);

        // return ResponseUtil.wrapOrNotFound(personList);

        return personService.findPersonInfoById(id);
    }

}
