package gp.web.and.mobile.preparationtask_1;

import android.view.View;

import com.facebook.login.LoginResult;

import org.json.JSONObject;

/**
 * Created by Mayada on 4/9/2018.
 */

public interface Contract {

    interface MyView {
        void fungdedatany(String name);
        String getMail();
        String getPassword();
        void initView();
        void getUserDetailsusingFBView(JSONObject json_object);
        void getValidationResult(String s);

    }

    interface MyModel {
        void getloginDatafromJson(String mail, String password);
    }

    interface MyPresenter {
        void getUserDetailsusingFBPresenter(LoginResult loginResult);
        void checkjson(String mail, String password);

        void funcgdeda(String name);
    }


}


