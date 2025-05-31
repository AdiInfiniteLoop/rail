package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import ticket.booking.entities.Train;
import ticket.booking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class UserBookingService {
    private User user;  //global  user that anyone can use: if 10000 logs in there will be 10000 objects for User class
    private static final String USER_PATH = "src/main/java/ticket/booking/localDB/users.json";

    /*To map user_id => userID, we use ObjectMapper to serialize the data*/
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static List<User> userLists;


    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    private void loadUsers() throws IOException {
        File users = new File(USER_PATH);
        /*We use TypeReference because we have to put in List and List accepts a generic(E)
        and TypeReference is used to deserialize at runtime*/
        userLists = OBJECT_MAPPER.readValue(users, new TypeReference<>() {
        });
    }

    public Boolean login() {
        if (user == null) {
            System.out.println("User not initialized");
            return Boolean.FALSE;
        }
        Optional<User> foundedUser = userLists.stream().filter(e -> e.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), e.getHashedPassword())).findFirst();

        return foundedUser.isPresent();
    }

    public Boolean signup(User user) {
        try {
            userLists.add(user);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException e) {
            return Boolean.FALSE;
        }
    }

    private static void saveUserListToFile() throws IOException {
        File usersfile = new File(USER_PATH);
        OBJECT_MAPPER.writeValue(usersfile, userLists);
    }

    public void fetchBookings() {
        user.printTicketsBookedByUser();
    }

    public Boolean cancelBooking(String ticketId) {
        try {
            if(ticketId == null || ticketId.isEmpty()) {
                System.out.println("Ticket id is empty");
                return Boolean.FALSE;
            }

            boolean isAvailable = user.getTicketsBookedByUser().stream().anyMatch(foundTicket(ticketId));
            if(!isAvailable) {
                System.out.println("No such Ticket is available");
                return Boolean.FALSE;
            }
            for(User user: userLists) {
                user.getTicketsBookedByUser().removeIf(ticket -> ticket.getTicketId().equals(ticketId));
            }
            saveUserListToFile();
            return Boolean.TRUE;
        } catch(IOException ex) {
            System.out.println("Error while saving in DB");
            return Boolean.FALSE;
        }
    }

    public Predicate<Ticket> foundTicket(String ticketId) {
        return a -> a.getTicketId().equals(ticketId);
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train trainselected, int row, int col) {
        try{
            TrainService trainService = new TrainService();
            List<List<Integer>> cols = trainselected.getSeats();
            if (row >= 0 && row < cols.size() && col >= 0 && col < cols.get(row).size()) {
                if (cols.get(row).get(col) == 0) {
                    cols.get(row).set(col, 1);
                    trainselected.setSeats(cols);
                    trainService.addTrain(trainselected);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }


}





/*
*   pass the array through a stream using stream api, then filter (lambda function)
*   e.x to get even numbers arr.stream().filter(ele => ele % 2 == 0).collect(arrList);
* */