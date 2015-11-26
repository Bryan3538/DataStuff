package DataReadingAndWriting;

import java.io.File;
import java.io.IOException;

/**
 * Created by Bryan on 11/18/2015.
 */
public class EmployeeReaderFactory {
    //Didn't consider adding JSON to this project at first, so this needs some real redoing here...
    public static final int DOM = 1;
    public static final int SAX = 2;
    public static final int STAX = 3;
    public static final int JSONOBJ = 4;
    public static final int JSONSTR = 5;


    public static EmployeeReader newInstance(File infile) throws IOException {

        if(infile.getAbsolutePath().endsWith(".xml")) {
            return new EmployeeStaxParser();
        } else if(infile.getAbsolutePath().endsWith(".json")) {
            return new EmployeeJsonStreamParser();
        } else {
            throw new IOException("Only files of the following type are accepted: { .xml, .json }");
        }
    }

    public static EmployeeReader newInstance(String infilePath) throws IOException {
        File infile = new File(infilePath);
        return newInstance(infile);
    }

    public static EmployeeReader newInstance(int type) {
        switch(type) {
            case DOM:
                return new EmployeeDomParser();
            case SAX:
                return new EmployeeSaxParser();
            case STAX:
                return new EmployeeStaxParser();
            case JSONOBJ:
                return new EmployeeJsonObjectParser();
            case JSONSTR:
                return new EmployeeJsonStreamParser();
            default:
                return new EmployeeDomParser();
        }
    }
}
