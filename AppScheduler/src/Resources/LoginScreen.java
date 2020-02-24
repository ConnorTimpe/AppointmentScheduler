/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import java.util.ListResourceBundle;

/**
 *
 * @author conno
 */
public class LoginScreen extends ListResourceBundle {
//English resource bundle
    @Override
    protected Object[][] getContents() {
        return contents;
    }
    
static final Object[][] contents = {
    {"UsernameLabel", "Username"},
    {"PasswordLabel", "Password"},
    {"LogInButton", "Log In"},
    {"PleaseLogInLabel", "Please Log In"},
    {"alertTitle", "Incorrect Login Information"},
    {"alertContent", "Incorrect username or password. Please try again. \n (Username and password are case sensitive) \n Username = test \n Password = test"},
    {"infoTitle", "Upcoming appointment"},
    {"infoContent", "You have an upcoming appointment within the next 15 minutes."}

};
}
