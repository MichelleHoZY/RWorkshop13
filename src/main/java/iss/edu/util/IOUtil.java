package iss.edu.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

// this file creates a new directory

public class IOUtil {
    private static final Logger logger = Logger.getLogger(IOUtil.class.getName());

    public static void createDir(String path){
        logger.log(Level.INFO, "create directory");
        // file handling in java implies reading from and writing data to a file
        File dir = new File(path); // creating a new object of a file "path"
        dir.mkdir(); // used to create a new directory denoted by the abstract pathname
        String osName = System.getProperty("os.name"); // gets the os name
        if(!osName.contains("Windows")){ // if it is not a windows os system (uses unix system)
            try{
                String perm = "rwxrwx---"; // this means the file's owner may read (r), write (w) and execute (x) the file but nobody else may access it
                Set<PosixFilePermission> permission = PosixFilePermissions.fromString(perm); 
                Files.setPosixFilePermissions(dir.toPath(), permission); // making a new path in the path in the command line
                // setPosixFilePermissions(Path path, Set<PosixFilePermission> perms)
            }catch(IOException e){
                logger.log(Level.SEVERE, "Error creating directory!");
            }
        }
    }
}