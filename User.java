/**
 * Author: Soft Red (Jeffery Yang, Dylan Wheeler, Steven Loppe, Thien-Kim Nguyen, Hafsa Zia) - 2020
 * Created for SENG 300 at The University of Calgary during the winter semester of 2020
 */

abstract class User {

    private String ID;
    private String username;
    private String password;

    public User(String name, String pass) {
        this.username = name;
        this.password = pass;
    }

    public abstract String getType();

    public abstract String toString();

}
