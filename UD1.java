/* AUTHOR:			Jerry Coe
 * COURSE:			CS 111 Intro to CS I
 * SECTION:			TuTh 1:30-3:20pm
 * HOMEWORK #:		4
 * LAST MODIFIED:	02/22/2018
 */

/*********************************************************************
 * UnitDeliverable1
 * *******************************************************************
 * PROGRAM DESCRIPTION:
 * A program that hashes and checks a user supplied password
 * (3 - 5 characters) for validity and authentication. Without the use
 * of data structures or looping
 * *******************************************************************
 * ALGORITHM:
 * MAIN DRIVER
 * DECLARE VARIABLES
 * 	- Scanner keyboard = new Scanner(System.in)
 * 	- String pass,passAlt,passHash,passTest,captcha;
 * 	- int difference,passLength,pepperInt1,pepperInt2,pepperInt3,
 * 	    pepperSum,lockoutCount;
 * 	- char stretch1,stretch2,stretch3,stretch4;
 * INITIALIZE VARIABLES
 * PROMPT user to INPUT password (between 3 and 5 characters)
 * in JOptionPane
 * 	- TEST if password is VALID
 * 	    - IF NOT VALID provide another opportunity to input valid
 * 	        password. ELSE END PROGRAM
 * 	    - IF VALID CONTINUE
 * 	- CALCULATE difference in length of password and HASH_LENGTH
 * 	- CONTROL SWITCH WITH difference variable
 * 	    - each CASE concatenate appropriate number of characters to
 * 	        stretch password to HASH_LENGTH
 * 	- BUILD new string from stretched password altering each character
 * 	    randomly based on PEPPER
 * REINITIALIZE important variables
 * REPEAT process using Scanner for input
 * COMPARE first hashed password to second hash password
 * DISPLAY CAPTCHA
 *  - PROMPT user for INPUT
 *  - PARSE INPUT to integer and compare to lockoutCount
 * END PROGRAM
 * *******************************************************************
 * ALL IMPORTED PACKAGES NEEDED AND PURPOSE
 * 	- javax.swing.JOptionPane - for windowed user input
 * 	- java.util.Scanner - for console user input
 * *******************************************************************/

import javax.swing.JOptionPane;
import java.util.Scanner;

public class UD1
{

    //CONSTANTS

    //can be any three digit integer
    //pepper is used for stretching and encrypting
    public static final int PEPPER = 374;

    //must be 7
    public static final int HASH_LENGTH = 7;

    //to not include non-printing characters
    //incomplete-refactor, some keyboard characters can produce a space. space should be invalid
    public static final int ASCII_FLOOR = 33;
    public static final int ASCII_CEIL = 126;

    public static void main(String[] args) {

        //
        // VARIABLE DECLARATION
        //

        Scanner keyboard = new Scanner(System.in);

        String
            pass,
            passAlt,
            passHash,
            passTest,
            captcha;
        int
            difference,
            passLength,
            pepperInt1,
            pepperInt2,
            pepperInt3,
            pepperSum,
            lockoutCount;
        char
            stretch1,
            stretch2,
            stretch3,
            stretch4;

        //
        // VARIABLE INITIALIZATION
        //

        pass            = "";
        passAlt         = "";
        passHash        = "";
        passTest        = "";
        difference      = 0;
        lockoutCount    = 0;

        //store each digit of salt in variables
        pepperInt1      = PEPPER / 100;
        pepperInt2      = (PEPPER / 10) % 10;
        pepperInt3      = PEPPER % 10;
        pepperSum       = (pepperInt1 + pepperInt2 + pepperInt3);

        //create 4 random stretch characters to stretch input length to HASH_LENGTH
        //store stretch as char
        stretch1        = (char) (pepperSum + ASCII_FLOOR);
        stretch2        = (char) (ASCII_CEIL - pepperSum);
        stretch3        = (char) (pepperSum + (ASCII_FLOOR * 2));
        stretch4        = (char) (pepperSum + (ASCII_FLOOR * 3));

        //prompt for password
        pass = JOptionPane.showInputDialog("Password");
        passLength = pass.length();

        //
        // PROCESSING
        //

        //check input length is between 3 and 5 characters
        if (passLength < 3 || passLength > 5)
        {

            System.out.println("Password must be between 3-5 characters [Attempts Remaining: 1]");

            pass = JOptionPane.showInputDialog("Password");
            passLength = pass.length();

            //count failed attempt
            lockoutCount++;

            //length out of range, repeat
            if (passLength < 3 || passLength > 5)
            {

                lockoutCount++;
                System.out.println("LOCKOUT TIME!");

                //end program
                System.exit(0);
            }
        }



        //get difference of input and HASH_LENGTH constant
        //must be between 2 and 4 else default and exit
        difference = HASH_LENGTH - passLength;

        //randomize stretched password depending on case
        switch (difference)
        {

            case 2:

                //insert 2 characters
                passAlt = "" + stretch1 + stretch2 + pass;

                break;

            case 3:

                //insert 3 characters
                passAlt = "" + pass + stretch1 + stretch2 + stretch3;
                break;

            case 4:

                //insert 4 characters
                passAlt = "" + stretch1 + pass + stretch2 + stretch3 + stretch4;
                break;

            default:

                //end program
                System.out.println("Some weird stuff you input. Goodbye.");
                System.exit(0);
        }

        //convert each character in altered password to an integer
        //randomly subtract or add one of the pepper digits to each character integer
        //store in a concatenated string the result integer converted back to character type
        //can be altered somewhat but be mindful of ascii table
        passHash += String.format("%c", ((int) passAlt.charAt(0) + pepperInt1));
        passHash += String.format("%c", ((int) passAlt.charAt(1) + pepperInt2));
        passHash += String.format("%c", ((int) passAlt.charAt(2) + pepperInt3));
        passHash += String.format("%c", ((int) passAlt.charAt(3) - pepperInt3));
        passHash += String.format("%c", ((int) passAlt.charAt(4) - pepperInt2));
        passHash += String.format("%c", ((int) passAlt.charAt(5) - pepperInt1));
        passHash += String.format("%c", ((int) passAlt.charAt(6) + pepperInt1));

        //display encrypted password
        System.out.println("\nYour password encrypted: " + passHash + "\n");

        //re-initialize important variables
        pass = "";
        passAlt = "";
        difference = 0;


        // CHECK PASSWORD

        //using Scanner class for input
        System.out.print("Please re-enter your password: ");
        pass = keyboard.nextLine();
        passLength = pass.length();

        if (passLength < 3 || passLength > 5)
        {

            System.out.println("Password must be between 3-5 characters [Attempts Remaining: 1]");
            pass = keyboard.nextLine();
            passLength = pass.length();
            lockoutCount++;

            if (passLength < 3 || passLength > 5)
            {
                lockoutCount++;
                System.out.println("LOCKOUT TIME!");
                System.exit(0);
            }

        }

        difference = HASH_LENGTH - passLength;

        switch (difference)
        {
            case 2:

                //insert 2 characters
                passAlt = "" + stretch1 + stretch2 + pass;

                break;
            case 3:

                //insert 3 characters

                passAlt = "" + pass + stretch1 + stretch2 + stretch3;
                break;

            case 4:

                //insert 4 characters

                passAlt = "" + stretch1 + pass + stretch2 + stretch3 + stretch4;
                break;

            default:
                System.out.println("How did we get here?");
                System.exit(0);

        }

        passTest += String.format("%c", ((int) passAlt.charAt(0) + pepperInt1));
        passTest += String.format("%c", ((int) passAlt.charAt(1) + pepperInt2));
        passTest += String.format("%c", ((int) passAlt.charAt(2) + pepperInt3));
        passTest += String.format("%c", ((int) passAlt.charAt(3) - pepperInt3));
        passTest += String.format("%c", ((int) passAlt.charAt(4) - pepperInt2));
        passTest += String.format("%c", ((int) passAlt.charAt(5) - pepperInt1));
        passTest += String.format("%c", ((int) passAlt.charAt(6) + pepperInt1));

        //
        // OUTPUT
        //

        //display original password vs tested password
        System.out.println();
        System.out.printf("%33s %s %n", "Your password encrypted: ", passHash);
        System.out.printf("%33s %s %n %n", "Test password encrypted: ", passTest);

        //test passwords
        if (passTest.compareTo(passHash) == 0)
        {

            System.out.println("Password Verified!\n");

        }
        else
        {

            System.out.println("Not Authorized. You only get one chance. Goodbye.\n");

        }

        // CAPTCHA

        System.out.println("Password Attempts: " + lockoutCount + "\n");
        System.out.println("CAPTCHA TIME\n");
        System.out.print("Password attempts: ");
        captcha = keyboard.next();

        //parse string input to int
        //use of data parsing
        if( Integer.parseInt(captcha) == lockoutCount )
        {
            System.out.println("Ok. You are human");
        }
        else
        {
            System.out.println("Captcha verification failed. Goodbye");
            System.exit(0);
        }

    }

}