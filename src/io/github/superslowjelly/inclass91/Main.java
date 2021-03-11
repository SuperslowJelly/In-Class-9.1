package io.github.superslowjelly.inclass91;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("getPassword():");
        long begTimeGetPassword = System.currentTimeMillis();
        for (String encryptedPassword : getPasswords())
            System.out.println(encryptedPassword);
        long endTimeGetPassword = System.currentTimeMillis();

        System.out.println("\ngetPasswordUsingLambda():");
        long begTimeGetPasswordUsingLambda = System.currentTimeMillis();
        for (String encryptedPassword : getPasswordsUsingLambda())
            System.out.println(encryptedPassword);
        long endTimeGetPasswordUsingLambda = System.currentTimeMillis();

        System.out.println("\n\ngetPassword() time: " + (endTimeGetPassword - begTimeGetPassword) + "\ngetPasswordUsingLambda() time: " + (endTimeGetPasswordUsingLambda - begTimeGetPasswordUsingLambda));
    }

    private static List<String> getPasswords() {
        Field[] fields = Passwords.class.getDeclaredFields();
        Field.setAccessible(fields, true);
        List<String> passwords = new ArrayList<>();
        for (Field field : fields)
            try { passwords.add(((String) field.get(null)).replace("@", ""));
            } catch (IllegalAccessException ignored) {}
        return passwords;
    }

    private static List<String> getPasswordsUsingLambda() {
        List<String> passwords = new ArrayList<>();
        Arrays.stream(Passwords.class.getDeclaredFields()).forEach(str -> {
            str.setAccessible(true);
            try { passwords.add(((String) str.get(null)).replace("@", ""));
            } catch (IllegalAccessException ignored) {}
        });
        return passwords;
    }

}