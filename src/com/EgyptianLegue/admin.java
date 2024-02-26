package com.EgyptianLegue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class admin {
    private String username;
    private String Password;

    public admin(String username, String password) {
        this.username = username;
        Password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return username + "/" +  Password;
    }
    public static admin fromString(String s)
    {
        String[] adminData = s.split("/") ;
        admin Admin = new admin(adminData[0],adminData[1]);
        return  Admin;
    }
    public static void writeAdminsToFile(List<admin> adminList , String filePath)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (admin admin : adminList) {
                writer.write(admin.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file writing error
        }
    }

}
