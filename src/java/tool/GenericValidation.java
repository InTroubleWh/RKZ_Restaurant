package tool;

import java.util.regex.Pattern;

public class GenericValidation {

    // Regex pattern for validating email addresses
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    // Validate email address
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // Validate phone number (should not have leading zeros and max length of 15)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        // Check length and format
        return phoneNumber.matches("^[1-9][0-9]{0,14}$"); // Adjusted to allow up to 15 digits
    }
}

