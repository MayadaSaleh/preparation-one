package gp.web.and.mobile.preparationtask_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Mayada on 4/9/2018.
 */

public class PresenterClass implements Contract.MyPresenter {

    Contract.MyView myView;
    Contract.MyModel model;

    public PresenterClass(Contract.MyView v) {
        myView = v;
        model = new ModelClass();
        myView.initView();
    }

    //
    @Override
    public String checkjson(String mail, String password) {
        String jsondataStatus = model.getloginDatafromJson(mail, password);
        return jsondataStatus;
    }

    @Override
    public void getUserDetailsusingFBPresenter(LoginResult loginResult){
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject json_object, GraphResponse response) {
                        myView.getUserDetailsusingFBView(json_object);
                    }
                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }
}
