public abstract class User {

    private String username;
    private String password;

    public abstract User(String name, String pass) {
        this.username = name;
        this.password = pass;
    }

}
