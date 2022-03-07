package iss.edu.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.ApplicationArguments;
import org.springframework.ui.Model;

import iss.edu.model.Contact;

public class Contacts {
    private Logger logger = Logger.getLogger(Contacts.class.getName());

    public void saveContact(Contact contact, Model model, ApplicationArguments applnArgs){
        String dataFilename = contact.getId();
        // PrintWriter and FileWriter are JAVA classes that allow writing data into files.
        // PrintWriter cannot write primitive data while FileWriter can
        PrintWriter printWriter = null;
        FileWriter fileWriter = null;
        String dataDirPath = "/Users/hozhiying/Documents";
        String[] optValuesArr = null;
        try{
            if(applnArgs != null){ // if there are application arguments
                Set<String> optnames = applnArgs.getOptionNames(); // get the names of these arguments and set them as String optnames
                if(optnames != null && optnames.size() > 0){ // if optnames isn't empty 
                    String[] optnamesArr = optnames.toArray(new String[optnames.size()]); // tells optNames to store its values in the new array of strings with the optnames.size() indexes available, and "optnamesArr" as those values
                    if(optnamesArr != null && optnamesArr.length > 0){ // if optnamesArr isn't empty
                        List<String> optValues = applnArgs.getOptionValues(optnamesArr[0]); // get the value of the application argument options in the first elemeent of optnamesArr and return it as a String list called optValues
                        if(optValues != null && optValues.size() > 0){ // if optValues isn't empty
                            optValuesArr = optValues.toArray(new String[optValues.size()]); // set the size of optValues as "optValuesArr" String
                        }
                    }
                }
            }

           if(optValuesArr != null){
                dataDirPath = optValuesArr[0]; // this sets your path directory in the argument line to the dataDirPath
                logger.log(Level.INFO, " optValuesArr[0] >>> " + optValuesArr[0]);
                logger.log(Level.INFO, " dataDirPath >>> " + dataDirPath);
             }
            
            fileWriter = new FileWriter(dataDirPath + "/" + dataFilename, Charset.forName("UTF-8")); // creating a new file in the directory
            printWriter = new PrintWriter(fileWriter);
            printWriter.println(contact.getName()); // println() is a method only printWriter has, but fileWriter doesn't. so you take the primitive types from fileWriter and convert it into printWriter
            printWriter.println(contact.getEmail());
            printWriter.println(contact.getPhoneNumber());
        }catch(IOException e){
            logger.log(Level.WARNING, e.getMessage());
        }finally{
            if(printWriter != null)
                printWriter.close(); // closing the stream
            try{
                if(fileWriter != null)
                    fileWriter.close(); // closing the stream
            }catch(IOException e){
                logger.log(Level.WARNING, e.getMessage());
            }
        }
        model.addAttribute("contact", new Contact(contact.getId(), contact.getName(), contact.getEmail(), contact.getPhoneNumber()));
    }
    
    // getContactById will look into the data directory for a file with the corresponding id in the resource
    public void getContactById(Model model, String contactId, ApplicationArguments applnArgs){
        Set<String> optnames = applnArgs.getOptionNames();
        String[] optnamesArr = optnames.toArray(new String[optnames.size()]);
        List<String> optValues = applnArgs.getOptionValues(optnamesArr[0]);
        String[] optValuesArr = optValues.toArray(new String[optValues.size()]);
        Contact cResp = new Contact();
        try{
            Path filePath = new File(optValuesArr[0] + "/" + contactId).toPath();
            Charset charset = Charset.forName("utf-8");
            List<String> stringListVal = Files.readAllLines(filePath, charset);
            cResp.setName(stringListVal.get(0));
            cResp.setEmail(stringListVal.get(1));
            cResp.setPhoneNumber(stringListVal.get(2));
            cResp.setId(contactId);
        }catch(IOException e){
            logger.log(Level.WARNING, e.getMessage());
        }
        model.addAttribute("contact", cResp);
    }
}
