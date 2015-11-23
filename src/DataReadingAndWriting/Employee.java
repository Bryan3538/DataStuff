package DataReadingAndWriting;

/**
 * Created by Bryan on 11/18/2015.
 */
public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private int departmentNumber;
    private int locationId;

    public Employee(int id, String firstName, String lastName, int departmentNumber, int locationId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentNumber = departmentNumber;
        this.locationId = locationId;
    }

    public Employee() {
        id = -1;
        firstName = "";
        lastName = "";
        departmentNumber = -1;
        locationId = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id >= 0)
            this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
            this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(int departmentNumber) {
        if(departmentNumber >= 0)
            this.departmentNumber = departmentNumber;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        if(locationId > 0)
            this.locationId = locationId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Employee) {
            Employee other = (Employee) obj;
            return (firstName.equals(other.firstName) && lastName.equals(other.lastName)
                && departmentNumber == other.departmentNumber && locationId == other.locationId);
        } else {
            return false;
        }
    }
}
