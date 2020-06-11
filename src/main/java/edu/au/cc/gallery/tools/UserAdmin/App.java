/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.au.cc.gallery.tools.UserAdmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.Buffer;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
//        System.out.println(new App().getGreeting());
        menu();
        command();


//	DB.demo();
//	DB.listUsers();

    }

    public static void menu() throws IOException {
        System.out.println("\n\n--==**Menu**==--");
        System.out.println("1)  List Users");
        System.out.println("2)  Add User");
        System.out.println("3)  Edit User");
        System.out.println("4)  Delete User");
        System.out.println("5)  Quit");
        System.out.print("Enter command :> ");
    }

    public static void command() throws NoSuchElementException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(br.readLine());

        if (input > 0 && input < 7) {
            switcher(input);
        } else {
            System.out.println("Not a valid menu option.");
            menu();
            command();
        }



    }

    public static void switcher(int choiceIn) throws IOException {
        if (choiceIn == 1) {
            try {
                DB.listUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
            menu();
            command();


        } else if (choiceIn == 2 || choiceIn == 3 || choiceIn == 4 || choiceIn == 5) {
            System.exit(0);
        }
    }




}
