package reusables;

import org.apache.commons.codec.binary.Base64;

import java.util.Scanner;

public class EncryptionUtility {

    /**
     * Encrypt password
     */
    public static String encryptPassword(String originalString){
        return new String(Base64.encodeBase64(originalString.getBytes()));
    }

    /**
     * Decrypt password
     */
    public static String decryptPassword(String originalString){
        return new String(Base64.decodeBase64(originalString.getBytes()));
    }

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Enter password for encryption");
            System.out.println(encryptPassword(scanner.nextLine()));

            // To decrypt password
//            System.out.println("Enter password for decryption");
//            System.out.println(decryptPassword(scanner.nextLine()));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
