package DataReadingAndWriting;

import java.io.File;

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

    //this was supposed to eventually evaluate the size of the file to determine the more efficient parser
    public static EmployeeReader newInstance(File infile) {
        return null;
    }

    public static EmployeeReader newInstance(String infilePath) {
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
