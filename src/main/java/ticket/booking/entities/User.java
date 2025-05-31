package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {
    private String name;
    private String password;
    @JsonProperty("hashed_password")
    private String hashedPassword;
    @JsonProperty("tickets_booked")
    private List<Ticket> ticketsBookedByUser;
    @JsonProperty("user_id")
    private String userId;

    public User(String name, String password, String hashedPassword, List<Ticket> ticketsBookedByUser, String userId) {
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBookedByUser = ticketsBookedByUser;
        this.userId = userId;
    }

    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public List<Ticket> getTicketsBookedByUser() {
        return ticketsBookedByUser;
    }

    public void setTicketsBookedByUser(List<Ticket> ticketsBookedByUser) {
        this.ticketsBookedByUser = ticketsBookedByUser;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public void printTicketsBookedByUser() {
        if (ticketsBookedByUser != null) {
            for (Ticket tick : ticketsBookedByUser) {
                System.out.println(tick.getTicketInfo());
            }
        } else {
            System.out.println("No tickets booked by user.");
        }
    }
}
