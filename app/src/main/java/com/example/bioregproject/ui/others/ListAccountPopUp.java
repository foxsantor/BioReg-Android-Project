package com.example.bioregproject.ui.others;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bioregproject.Adapters.AccountPopUp;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;

import java.util.List;

public class ListAccountPopUp extends Fragment {

    private ListAccountPopUpViewModel mViewModel;
    private RecyclerView recyclerView;
    private LifecycleOwner lifecycleOwner;


    private AccountPopUp adapter;
    private Bundle bundle;

    public static ListAccountPopUp newInstance() {
        return new ListAccountPopUp();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.list_account_pop_up_fragment, container, false);
            return view;

    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListAccountPopUpViewModel.class);
        recyclerView = view.findViewById(R.id.accountsPop);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new AccountPopUp(getActivity(),getActivity());
        recyclerView.setAdapter(adapter);
        lifecycleOwner = this;
        mViewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                Account remover = StaticUse.loadSession(getActivity());
                for(int i = 0; i<accounts.size();i++)
                {
                if(accounts.get(i).getId() == remover.getId())
                    accounts.remove(i);
                    adapter.submitList(accounts);
                }
                
            }
        });

        adapter.setOnIteemClickListener(new AccountPopUp.OnItemClickLisnter() {
            @Override
            public void OnItemClick(Account account) {

                bundle = new Bundle();
                bundle.putByteArray("image",account.getProfileImage());
                bundle.putString("FirstName",account.getFirstName());
                bundle.putString("LastName",account.getLastName());
                bundle.putString("password",account.getPassword());
                bundle.putLong("id",account.getId());
                Navigation.findNavController(view).navigate(R.id.action_listAccountPopUp_to_formPopUp,bundle);
            }
        });
    }


}
