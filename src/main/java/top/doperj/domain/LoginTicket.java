package top.doperj.domain;

public class LoginTicket {
    private String username;
    private String email;
    private String sessionId;

    public LoginTicket(Customer customer, String sessionId) {
        this.username = customer.getName();
        this.email = customer.getEmail();
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
