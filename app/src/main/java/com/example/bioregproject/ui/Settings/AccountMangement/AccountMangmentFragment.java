package com.example.bioregproject.ui.Settings.AccountMangement;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;


public class AccountMangmentFragment extends Fragment{

    private AccountMangmentViewModel mViewModel;
    private Button createAccount, manageAccount, changePriv;
    private TextView text;
    private ImageButton close;
    private CardView errors;
    private Bundle bundle;
    private String response;


    public static AccountMangmentFragment newInstance() {
        return new AccountMangmentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_mangment_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createAccount = view.findViewById(R.id.create);
        manageAccount = view.findViewById(R.id.button2);
        changePriv =  view.findViewById(R.id.button8);
        close = view.findViewById(R.id.close);
        text = view.findViewById(R.id.error);
        errors = view.findViewById(R.id.errors);
        errors.setVisibility(View.GONE);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getActivity(), ""+StaticUse.loggedInInternal(getActivity()), Toast.LENGTH_SHORT).show();
                if(StaticUse.loggedInInternal(getActivity()))
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.mainMenu);
                else
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.accountBindiner);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        bundle = getArguments();

        if(bundle != null)
        {
            response = bundle.getString("message");
            errors.setVisibility(View.VISIBLE);
            Animation animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.fade_in);
            errors.startAnimation(animFadeIn);
            text.setText(response);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errors.setVisibility(View.GONE);
                Animation animFadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.fade_out);
                errors.startAnimation(animFadeOut);

            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = StaticUse.loadSession(getActivity());
                //Toast.makeText(getActivity(), ""+account.getFirstName()+"/"+account.getPassword(), Toast.LENGTH_SHORT).show();
                if(StaticUse.loggedInInternalAdmin(getActivity()))
                    Navigation.findNavController(v).navigate(R.id.accountCreation);
                else
                StaticUse.loginInternal(getActivity(),R.id.accountCreation,v);
            }
        });
        manageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StaticUse.loggedInInternalAdmin(getActivity()))
                    Navigation.findNavController(v).navigate(R.id.action_accountMangmentFragment_to_mangeAccount);
                else
                    StaticUse.loginInternal(getActivity(),R.id.action_accountMangmentFragment_to_mangeAccount,v);
            }
        });
        changePriv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Under Construction", Toast.LENGTH_SHORT).show();
                //Navigation.findNavController(getActivity(),R.id.fragment).navigate(R.id.accountCreation);
            }
        });
    }


}
