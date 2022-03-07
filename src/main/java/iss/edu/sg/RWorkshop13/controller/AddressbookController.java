package iss.edu.sg.RWorkshop13.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import iss.edu.model.Contact;
import iss.edu.util.Contacts;

@Controller
public class AddressbookController {
    private Logger logger = Logger.getLogger(AddressbookController.class.getName());

    @Autowired // Tells the application context to inject an instance of ApplicationArguments here
    private ApplicationArguments applicationArguments;

    @GetMapping("/")
    public String contactForm(Model model){
        logger.log(Level.INFO, "Show the contact form");
        model.addAttribute("contact", new Contact()); // creating new attributes in model class "contact"
        return "contact"; // directs the user straight to the contact.html page
    }
    
    @GetMapping("/getContact/{contactId}") // you can also access the contactId via this page which leads the user straight to showContact.html
    public String getContact(Model model, @PathVariable(value="contactId") String contactId) {
        logger.log(Level.INFO, "contactId " + contactId);
        Contacts ct = new Contacts();
        ct.getContactById(model, contactId, applicationArguments);
        return "showContact";
    }

    // this is the controller mapped to contacts
    @PostMapping("/contact") // after pressing submit on contact, it will be sent here and it will create a new contact, save it and return showContact.html
    public String contactSubmit(@ModelAttribute Contact contact, Model model, HttpServletResponse httpResponse){
        logger.log(Level.INFO, "Id : " + contact.getId());
        logger.log(Level.INFO, "Name : " + contact.getName());
        logger.log(Level.INFO, "Email : " + contact.getEmail());
        logger.log(Level.INFO, "Phone Number : " + contact.getPhoneNumber());
        Contacts ct = new Contacts();
        ct.saveContact(contact, model, applicationArguments);
        httpResponse.setStatus(HttpStatus.CREATED.value());
        return "showContact";
    }
}
