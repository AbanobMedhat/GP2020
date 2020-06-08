package com.example.future.healthapp;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.future.healthapp.Utils.FriendlyMessage;
import com.example.future.healthapp.Utils.GoogleSignInActivity;
import com.example.future.healthapp.databinding.ActivityGoogleBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {
private static final String TAG = "HEBA";
//UI
Button doctor_button;
Button patient_button;
Button send;
TextView req;
Intent intent;
EditText email;
String mDoctor="";
    ArrayList<String>users;
    ArrayList<String>id=new ArrayList<String>();

    private ActivityGoogleBinding mBinding;
    private ChildEventListener mChildEventListener;
    DatabaseReference docRef ;
    ArrayAdapter<String> adapter;
//firebase auth
private FirebaseAuth.AuthStateListener mAuthStateListener;
private static final int RC_SIGN_IN = 9001;
private GoogleSignInClient mGoogleSignInClient;
private FirebaseAuth mAuth;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        //////////////////////User_Listener///////////////////////////////
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setContentView(R.layout.activity_main);
                    main_view(user,savedInstanceState);
                } else {
                    Google_signIn();
                }
            }
        };
    }
    //////////////////////////////////Patient_selection view
    void sec_view(){
        Group group= (Group) findViewById(R.id.group);
        group.setVisibility(View.GONE);
        Group group2= (Group) findViewById(R.id.group2);
        revealEditText(group2);
       // group2.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signin) {
            Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
            //startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
////////////////////////////////////////////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
//                Log.d(TAG, "onActivityResult: "+mAuth.getCurrentUser().getDisplayName());
                //            mintent(mAuth.getCurrentUser());

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

        }

    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        // [START_EXCLUDE silent]
        mBinding.progressBar.setVisibility(View.VISIBLE);
        // [END_EXCLUDE]
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        mBinding.progressBar.setVisibility(View.GONE);

                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
    }
    //////////////////////////////////////////////////////////////////////////////
    private void updateUI(FirebaseUser user) {
        mBinding.progressBar.setVisibility(View.GONE);
        if (user != null) {
            mBinding.status.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            mBinding.signInButton.setVisibility(View.GONE);
            mBinding.signOutAndDisconnect.setVisibility(View.VISIBLE);
        } else {
            mBinding.status.setText(R.string.signed_out);
            mBinding.detail.setText(null);

            mBinding.signInButton.setVisibility(View.VISIBLE);
            mBinding.signOutAndDisconnect.setVisibility(View.GONE);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signInButton) {
            signIn();
        } else if (i == R.id.signOutButton) {
            signOut();
        } else if (i == R.id.disconnectButton) {
            revokeAccess();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    void main_view(final FirebaseUser user, Bundle savedInstanceState)
    {
        ConstraintLayout constraintLayout=(ConstraintLayout)findViewById(R.id.mlayout) ;
        constraintLayout.getBackground().setAlpha(120);  // here the value is an integer not float
        doctor_button=(Button)findViewById(R.id.doctor_button);
        patient_button=(Button)findViewById(R.id.patient_button);
        req=(TextView)findViewById(R.id.request);
        send=(Button)findViewById(R.id.button);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        users=new ArrayList<String>();
        listen();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        email=(EditText)findViewById(R.id.editText);
        if(savedInstanceState!=null){
            sec_view();}
        //to save user info
        final Bundle mbundle=new Bundle();
        doctor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbundle.putString("UID",user.getUid());
                mbundle.putString("NAME",user.getDisplayName());
                intent = new Intent(MainActivity.this, doctor_view.class);
                intent.putExtra("HEBA",mbundle);
                startActivity(intent);
            }
        });
        patient_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sec_view();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(getCurrentFocus()!=null){
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);}
                req.setVisibility(View.VISIBLE);
                intent = new Intent(MainActivity.this, patient_view.class);
                mbundle.putString("UID",user.getUid());
                mbundle.putString("NAME",user.getDisplayName());
                mbundle.putString("MDR",mDoctor);
                intent.putExtra("HEBA",mbundle);
                startActivity(intent);
            }
        });
    }
    /////////////////////////////////////////////////////////////////////////////
    void Google_signIn(){
        mBinding = ActivityGoogleBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        //setProgressBar=(mBinding.progressBar);

        // Button listeners
        mBinding.signInButton.setOnClickListener(MainActivity.this);
        mBinding.signOutButton.setOnClickListener(MainActivity.this);
        mBinding.disconnectButton.setOnClickListener(MainActivity.this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.ClientID))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
    }
//////////////////////////Animation///////////////////////////
public static void revealEditText (final Group text) {
    // Get x and y positions of the view with a slight offset
    // to give the illusion of reveal happening from FAB.
    int cx = text.getRight() - 30;
    int cy = text.getBottom() - 60;

    // Radius gives the reveal the circular outline.
    int finalRadius = Math.max(text.getWidth(),
            text.getHeight());

    // This creates a circular reveal that is used starting from
    // cx and cy with a radius of 0 and then expanding to finalRadius.
    Animator anim =
            ViewAnimationUtils.createCircularReveal(text,
                    cx,
                    cy,
                    0,
                    finalRadius);
    anim.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            text.setVisibility(View.VISIBLE);
        }
    });
    anim.start();
}
/////////////////////////////////////////
void listen(){
    docRef= FirebaseDatabase.getInstance().getReference().child("Doctors");
    mChildEventListener=new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            //if(dataSnapshot.getKey().equals("name")){
            FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
            Log.d(TAG, "onChildAddedSpinner: "+dataSnapshot.getValue().toString());

            users.add(friendlyMessage.getName());
            id.add(friendlyMessage.getUid());
            adapter.notifyDataSetChanged();
           // }
           // Log.d(TAG, "onChildAddedSpinner: Noo ");

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            String friendlyMessage = dataSnapshot.getValue(String.class);
            Log.d(TAG, "onChildChanged: "+friendlyMessage);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }
        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    docRef.addChildEventListener(mChildEventListener);
}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
        Toast.makeText(getApplicationContext(), "Selected User: "+id.get(position) ,Toast.LENGTH_SHORT).show();
        mDoctor=id.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

