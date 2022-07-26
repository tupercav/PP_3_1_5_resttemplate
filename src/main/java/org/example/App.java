package org.example;

import org.example.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);
        String result = "";
        List<String> cookies = communication.getCookie();

        //Saving newUser to get 1st part of result
        User newUser = new User(3L, "James", "Brown", (byte) 33);
        result += communication.saveUser(newUser, cookies);

        //Edititing newUser and saving to get 2nd part of result
        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        result += communication.editUser(newUser, cookies);

        //Deleting newUser to get 3rd part of result
        result += communication.deleteUser(3, cookies);

        System.out.println("Result is " + result);
        System.out.println("Qty of symbols in result: " + result.length());

    }
}
