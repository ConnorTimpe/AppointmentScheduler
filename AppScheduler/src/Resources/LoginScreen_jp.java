/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import java.util.ListResourceBundle;

/**
 *
 * @author connor
 * Translation by Kanami
 */
public class LoginScreen_jp extends ListResourceBundle{
    //Japanese resource bundle
    @Override
    protected Object[][] getContents() {
        return contents;
    }
    
static final Object[][] contents = {
    {"UsernameLabel", "ユーザーネーム"},
    {"PasswordLabel", "パスワード"},
    {"LogInButton", "ログイン"},
    {"PleaseLogInLabel", "ログインしてください"},
    {"alertTitle", "ログイン情報が違います"},
    {"alertContent", "ユーザーネームかパスワードに間違いがあります。やり直してください。"},
    {"infoTitle", "Upcoming appointment"},
    {"infoContent", "You have an upcoming appointment within the next 15 minutes."}

};
}
