package com.pavan.service;

import com.pavan.domain.Person;
import com.pavan.domain.enumeration.Gender;
import com.pavan.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileReaderService {

    String directory = "Z:\\pavan\\Files";


    @Autowired
    private PersonRepository personRepository;

    @Scheduled(fixedRate = 30000,initialDelay = 10000)
    public void readMutlipleFilesFromDirectory() {
        System.out.println("directory = " + directory);
        System.out.println("@Scheduled Task running");
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length > 0) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    readCSVFile(listOfFiles[i].toString());
                    listOfFiles[i].delete();
                }
            }
        }

    }

    public void readCSVFile(String fileName) {
        ArrayList<Person> personArrayList = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        try {
            BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8);
            String line = reader.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                Person person = createPerson(attributes);
                personArrayList.add(person);
                line = reader.readLine();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        this.savePerson(personArrayList);
    }

    private Person createPerson(String[] attributes) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Instant now = Instant.now();
        Person person = new Person();
        person.setFirstName(attributes[0].substring(1, attributes[0].length() - 1));
        person.setLastName(attributes[1].substring(1, attributes[1].length() - 1));
        person.setTitle(attributes[2].substring(1, attributes[2].length() - 1));
        person.setGender(Gender.valueOf(attributes[3].substring(1, attributes[3].length() - 1)));
        person.setDateOfBirth(Instant.parse(attributes[4].substring(1, attributes[4].length() - 1)));
        person.setDeleted(Boolean.parseBoolean(attributes[5].substring(1, attributes[5].length() - 1)));
        return person;
    }

    public void savePerson(List<Person> listOfPerson) {
        personRepository.saveAll(listOfPerson);
        System.out.println("Saved");

    }
}
