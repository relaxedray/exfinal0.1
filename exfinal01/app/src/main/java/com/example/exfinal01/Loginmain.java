package com.example.exfinal01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginmain extends AppCompatActivity {

EditText edittxtusuario, edittxtpass;
Button btniniciarsesion, btnregistro;
TextView txtdebbug;
public static final String SHARED_PREF = "sharedprefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        ini();

        btniniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edittxtusuario.getText().toString()) || TextUtils.isEmpty(edittxtpass.getText().toString())){
                    Toast.makeText(Loginmain.this, "usuario o contraseña vacio", Toast.LENGTH_LONG).show();
                }
                else{
                    login();
                }

            }
        });
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void ini(){
    edittxtusuario = findViewById(R.id.edittxtusuario);
    edittxtpass = findViewById(R.id.edittxtpass);
    btniniciarsesion = findViewById(R.id.btniniciarsesion);
    btnregistro = findViewById(R.id.btnregistro);
    }
    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsuario(edittxtusuario.getText().toString());
        loginRequest.setContrasena(edittxtpass.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userlogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(Loginmain.this, "exito en inicio de sesion", Toast.LENGTH_LONG).show();
                    savedata(loginResponse);
                    if (loginResponse.getUsuario().getRole() == 1){
                        startActivity(new Intent(Loginmain.this, Admin.class));
                    }
                    else if (loginResponse.getUsuario().getRole() == 2){
                        startActivity(new Intent(Loginmain.this, AssistGroups.class));
                    }
                    else
                    startActivity(new Intent(Loginmain.this, AssistGroups.class));
                }
                else{
                    Toast.makeText(Loginmain.this, "inicio de sesion fallido", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Loginmain.this, "throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
    public void savedata(LoginResponse loginResponse){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("usuid", loginResponse.getUsuario().getId());
        editor.putInt("rol",loginResponse.getUsuario().getRole());
        if (loginResponse.getUsuario().getDocente_id() != null){
            editor.putInt("profid",loginResponse.getUsuario().getDocente_id());
        }
        editor.apply();
        editor.commit();
    }
}