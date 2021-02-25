package helpers;

import model.User;
import java.util.Random;

public class DataHelper {

    public static String generate_email(){
        return String.format("%s@hotmail.com",generate_random_String(7));
    }
    public static String generate_title(){
        return String.format("%s",generate_random_String(10));
    }
    public static String generate_content(){
        return String.format("%s",generate_random_String(100));
    }



    public static String generate_random_String(int lenght){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = lenght;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static User getTestUser(){

        return new User("jesus","jesrojas@hotmail.com","jesrojas");
    }

    public static User getTestAuthUser(){
        return new User("jesus","testuser","testpass");
    }
}
