package ticket.booking.utils;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {

    public static String hashPassword(String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Hashed: " + hashed);
        return hashed;
    }

    public static Boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static void main(String[] args) {
        String hashed = hashPassword("123456");
        System.out.println("Password matches: " + checkPassword("123456", hashed));
        System.out.println("Wrong password matches: " + checkPassword("wrongness", hashed));
    }
}
