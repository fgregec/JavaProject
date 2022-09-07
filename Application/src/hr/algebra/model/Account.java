/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

/**
 *
 * @author Komp
 */
public class Account {
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;

    public Account() {
    }

    public Account(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Account(int id, String username, String password, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsAdmin(boolean role) {
        this.isAdmin = role;
    }
    
    
    
    
    
}
