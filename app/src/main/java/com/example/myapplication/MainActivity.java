package com.example.myapplication;

import android.os.Bundle;
import android.webkit.ValueCallback;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String currentUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Intent intent = new Intent(this, MainActivity2.class);
//        intent.putExtra("keyName", "effi");
//        intent.putExtra("keyLast", "profus");
//        startActivity(intent);

        mAuth = FirebaseAuth.getInstance();
    }

    public void register(RegisterCallBack callBack){
        EditText email = findViewById(R.id.registerEmail);
        EditText password = findViewById(R.id.registerPassword);
        EditText phone = findViewById(R.id.editTextPhone);
        EditText address = findViewById(R.id.editTextTextPostalAddress);

        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        String phoneS = phone.getText().toString();
        String addressS = address.getText().toString();

        if (emailS.isEmpty() || passwordS.isEmpty() || phoneS.isEmpty() || addressS.isEmpty()) {
            Toast.makeText(this, "Email, password, phone number, address must not be empty", Toast.LENGTH_SHORT).show();
            callBack.onRegisterResult(false);
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setCurrentUserPassword(passwordS);
                            Toast.makeText(MainActivity.this, "Registered successfuly!", Toast.LENGTH_LONG).show();
                            callBack.onRegisterResult(true);

                        } else {
                            Toast.makeText(MainActivity.this, "Register Failed, Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void login(LoginCallback callback){
        EditText email = findViewById(R.id.loginEmail);
        EditText password = findViewById(R.id.loginPassword);

        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();

        if (emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show();
            callback.onLoginResult(false);
            return;
        }

        mAuth.signInWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setCurrentUserPassword(passwordS);
                            Toast.makeText(MainActivity.this, "Login Ok!", Toast.LENGTH_LONG).show();
                            callback.onLoginResult(true);
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed, Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    public void saveRegisteredUser(String email,String password,String phone,String address,String description){

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(password);
        dbRef.child("email").setValue(email);
        dbRef.child("password").setValue(password);
        dbRef.child("phone").setValue(phone);
        dbRef.child("address").setValue(address);
        dbRef.child("description").setValue(description);
    }
    public void saveUserString(String password, String description) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(password);
        dbRef.child("description").setValue(description).addOnSuccessListener(unused ->
                Toast.makeText(this, "Saved your description", Toast.LENGTH_SHORT).show()
        );
    }

    public void loadUserString(String password, ValueCallback callback) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(password);
        dbRef.child("description").get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                callback.onValueLoaded(snapshot.getValue(String.class));
            } else {
                callback.onValueLoaded("No description found.");
            }
        }).addOnFailureListener(e -> {
            callback.onValueLoaded("Failed to load.");
        });
    }
    public interface ValueCallback {
        void onValueLoaded(String value);
    }
    public interface RegisterCallBack {
        void onRegisterResult(boolean success);
    }
    public interface LoginCallback {
        void onLoginResult(boolean success);
    }
    public void setCurrentUserPassword(String password) {
        this.currentUserPassword = password;
    }

    public String getCurrentUserPassword() {
        return currentUserPassword;
    }
}
