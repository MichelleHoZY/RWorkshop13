package iss.edu.sg.RWorkshop13.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import iss.edu.model.Contact;
import iss.edu.util.Contacts;

// you do not need to use @ResponseBody on each and every handler method once you annotate the class with @RestController (unlike @Controller)
// RESTful web services

@RestController
public class ContactRESTController {
    private Logger logger = Logger.getLogger(ContactRESTController.class.getName());

    @Autowired // Tells the application context to inject an instance of ApplicationArguments here
    private ApplicationArguments applicationArguments;

    // The @RequestBody annotation allows us to retrieve the request's body. We can then return it as a String or deserialize it into a Plain Old Java Object (POJO)

    @PostMapping("/contact2")
    public Contact contactSubmitRest(@RequestBody Contact contact, Model model, HttpServletResponse httpResponse) {
        logger.log(Level.INFO, "Id : "+ contact.getId());
        logger.log(Level.INFO, "Name : "+ contact.getName());
        logger.log(Level.INFO, "Email : "+ contact.getEmail());
        logger.log(Level.INFO, "Phone Number : "+ contact.getPhoneNumber());
        Contacts ct = new Contacts();  // creating an instance of contacts "ct"
        ct.saveContact(contact, model, applicationArguments); // calls saveContact method from Contacts.java and saves instance ct there
        httpResponse.setStatus(HttpStatus.CREATED.value());
        return contact; 
    }
}
