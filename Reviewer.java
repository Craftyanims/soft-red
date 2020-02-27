/**
 * Author: Soft Red (Jeffery Yang, Dylan Wheeler, Steven Loppe, Thien-Kim Nguyen, Hafsa Zia) - 2020
 * Created for SENG 300 at The University of Calgary during the winter semester of 2020
 */

public class Reviewer extends User {

    public Reviewer(String name, String pass) {
        super(name, pass);
    }

    @Override
    public String getType() {
        return "Reviewer";
    }

    public String toString() {
        return "";
    }

}
