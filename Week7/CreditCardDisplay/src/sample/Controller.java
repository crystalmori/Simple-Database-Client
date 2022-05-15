package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Controller {

    @FXML
    private TextField tf;

    @FXML
    private ImageView iv;

    public void buttonPressed(ActionEvent event) {
        // logic block:
        if (tf.getText().length() < 13 || tf.getText().length() > 16)
        {
            System.out.println("Invalid card number LENGTH.");
            Image image = new Image("icons/invalid.png");
            iv.setImage(image);
        }
        else if (isValid(tf.getText()))
        {
            if (startsWith(tf.getText(), "4"))
            {
                Image visaIcon = new Image("icons/visa.png");
                iv.setImage(visaIcon);
            }
            else if (startsWith(tf.getText(), "5"))
            {
                Image masterIcon = new Image("icons/master.png");
                iv.setImage(masterIcon);
            } else if (startsWith(tf.getText(), "35"))
            {
                Image jcbIcon = new Image("icons/jcb.png");
                iv.setImage(jcbIcon);
            }
            else if (startsWith(tf.getText(), "37"))
            {
                Image amexIcon = new Image("icons/amex.png");
                iv.setImage(amexIcon);
            }
            else if (startsWith(tf.getText(), "34"))
            {
                Image amexIcon = new Image("icons/amex.png");
                iv.setImage(amexIcon);
            }
            else if (startsWith(tf.getText(), "6"))
            {
                Image discoverIcon = new Image("icons/discover.png");
                iv.setImage(discoverIcon);
            }
            else
            {
                Image invalidIcon = new Image("icons/invalid.png");
                iv.setImage(invalidIcon);
                System.out.println("Unknown credit card company encountered.");
            }
        }
        else
        {
            Image invalidIcon = new Image("icons/invalid.png");
            iv.setImage(invalidIcon);
            System.out.println("INVALID card number.");
        }
    }


    // returns true if valid card number:
    public static boolean isValid(String cardNumber)
    {
        return (sumOfDoubleEvenPlace(cardNumber) + sumOfOddPlace(cardNumber)) % 10 == 0;
    }

    // returns result of step 2:
    private static int sumOfDoubleEvenPlace(String cardNumber)
    {
        int evenSum = 0;
        int digitPtr = cardNumber.length() - 2;

        while (digitPtr >= 0)
        {
            evenSum += getDigit(2 * Character.getNumericValue(cardNumber.charAt(digitPtr)));
            digitPtr -= 2;
        }

        return evenSum;
    }

    // step 1:
    // returns number if single digit:
    // else returns sum of two digits:
    private static int getDigit(int number)
    {
        if (number < 10)
        {
            return number;
        }
        else
        {
            return (number % 10) + (number % 100 / 10);
        }

    }

    // returns result of step 3:
    private static int sumOfOddPlace(String cardNumber)
    {
        int oddSum = 0;
        int digitPtr = cardNumber.length() - 1;

        while (digitPtr >= 0)
        {
            oddSum += Character.getNumericValue(cardNumber.charAt(digitPtr));
            digitPtr -= 2;
        }

        return oddSum;
    }

    // returns true if substr is the prefix for card number:
    public static boolean startsWith(String cardNumber, String subStr)
    {
        return (cardNumber.startsWith(subStr));
    }


}
