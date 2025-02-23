package com.iti.java.foodplannerbykhalidamr.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.api.ApiException;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.model.GoogleAuthModel;
import com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.presenter.GoogleAuthPresenter;
//import com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.presenter.GoogleAuthPresenterInterface;

public class AuthenticationFragment extends Fragment implements AuthNavigatorInterface {

    private static final int RC_SIGN_IN = 123;
   // private GoogleAuthPresenterInterface presenter;
    private GoogleAuthPresenter presenter;
    private GoogleSignInClient googleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", "onCreate: Created");
        presenter = new GoogleAuthPresenter(new GoogleAuthModel(), this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_authentication,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("OnCreate", "onViewCreate: Created");

        view.findViewById(R.id.btn_google_sign_in).setOnClickListener(v -> signInWithGoogle());
        view.findViewById(R.id.btn_username_login).setOnClickListener(v -> presenter.navigateToEmailLogin());
        view.findViewById(R.id.btn_continue_guest).setOnClickListener(v -> presenter.navigateToHome());
        view.findViewById(R.id.btn_sign_up).setOnClickListener(v -> presenter.navigateToSignUp());
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String idToken = account.getIdToken();
                presenter.handleGoogleSignIn(idToken);
            } catch (ApiException e) {
                Log.e("GoogleSignIn", "Sign-in failed", e);
            }
        }
    }

    @Override
    public void goToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_authenticationFragment4_to_homeFragment);
    }

    @Override
    public void goToEmailLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_authenticationFragment4_to_emailLoginFragment2);
    }

    @Override
    public void goToSignUp() {
        Navigation.findNavController(requireView()).navigate(R.id.action_authenticationFragment4_to_emailSignupFragment);
    }
}
