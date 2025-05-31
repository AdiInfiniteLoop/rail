package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.utils.UserServiceUtil;
import ticket.booking.services.TrainService;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello In Desi Rail!");
        System.out.println(System.getProperty("user.dir"));
        Scanner sc = new Scanner(System.in);
        int opt = 0;
        UserBookingService userBookingService;
        TrainService trainService;
        try {
            userBookingService = new UserBookingService();
            trainService = new TrainService();

        }
        catch (IOException ex) {
            System.out.println("Something went wrong");
            System.out.println(ex.getMessage());
            return;
        }


        while(opt <= 7) {
            System.out.println("Please enter your choice");
            System.out.println("1 - Create a new user");
            System.out.println("2 - Login");
            System.out.println("3 - Fetch Bookings");
            System.out.println("4 - Search Trains");
            System.out.println("5 - Book a Seat");
            System.out.println("6 - Cancel Booking");
            System.out.println("7 - Exit");
            opt = sc.nextInt();
            Train trainselectedforbooking = new Train();
            switch (opt) {
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = sc.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = sc.next();
                    User userToSignup = new User(nameToSignUp, passwordToSignUp, UserServiceUtil.hashPassword(passwordToSignUp), new ArrayList<>(), UUID.randomUUID().toString());
                    boolean signedUp = userBookingService.signup(userToSignup);
                    if (signedUp) {
                        System.out.println("Signup successful");
                    }
                    else {
                        System.out.println("Signup unsuccessful");
                    }
                    break;
                case 2:
                    System.out.println("Enter the username to login");
                    String nameToLogin = sc.next();
                    System.out.println("Enter the password to login");
                    String passwordToLogin = sc.next();
                    User userToLogin = new User(nameToLogin, passwordToLogin, UserServiceUtil.hashPassword(passwordToLogin), new ArrayList<>(), UUID.randomUUID().toString());
                    try{
                        userBookingService = new UserBookingService(userToLogin);
                        boolean loggedIn = userBookingService.login();
                        if (loggedIn) {
                            System.out.println("Logged in");
                        } else {
                            System.out.println("Something went wrong. Couldn't log in");
                        }
                    }catch (IOException ex){
                        break;
                    }
                    break;
                case 3:
                    System.out.println("Fetching Bookings");
                    userBookingService.fetchBookings();
                    break;
                case 4:
                    System.out.println("Type the source stations");
                    String src = sc.next();
                    System.out.println("Type the destination stations");
                    String dest = sc.next();
                    List<Train> trains = trainService.getTrains(src, dest);
                    for (Train train : trains) {
                        System.out.println(train.getTrainInfo());
                    }
                    System.out.println("select a train by typing 1, 2, 3....");
                    trainselectedforbooking = trains.get(sc.nextInt());
                    break;
                case 5:
                    System.out.println("Select a seat out of these seats");
                    List<List<Integer>> seats = userBookingService.fetchSeats(trainselectedforbooking);
                    for (List<Integer> row: seats){
                        for (Integer val: row){
                            System.out.print(val+" ");
                        }
                        System.out.println();
                    }
                    System.out.println("Select the seat by typing the row and column");
                    System.out.println("Enter the row");
                    int row = sc.nextInt();
                    System.out.println("Enter the column");
                    int col = sc.nextInt();
                    System.out.println("Booking your seat....");
                    Boolean booked = userBookingService.bookTrainSeat(trainselectedforbooking, row, col);
                    if(booked.equals(Boolean.TRUE)){
                        System.out.println("Booked! Enjoy your journey");
                    }else{
                        System.out.println("Can't book this seat");
                    }
                    break;
                case 6:
                    System.out.println("Cancel Booking");
                    String ticketId = sc.next();
                    boolean success = userBookingService.cancelBooking(ticketId);
                    if(success) {
                        System.out.println("Booking successful");
                    }
                    else {
                        System.out.println("Booking unsuccessful");
                    }

                    break;
                case 7:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
}


//21181