package com.example.bioregproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bioregproject.Adapters.AccountPopUp;
import com.example.bioregproject.Adapters.NotificationAdapater;
import com.example.bioregproject.Services.TaskManger;
import com.example.bioregproject.Utils.AspectRatioFragment;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bioregproject.entities.Notification;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.ui.Traceability.ImageFlow.ManageDataViewModel;
import com.google.android.cameraview.AspectRatio;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private static MainActivityViewModel mViewModel;
    private static ManageDataViewModel mViewModelPro;
    private static  Context conx;
    private static RequestQueue requestQueue;
    private static boolean hasConnection = false;
    private ImageView image;
    private ImageButton imageButton,notification,seetingsNot;
    private TextView name,num;
    private ConstraintLayout layout;
    private AccountPopUp adapter;
    private RecyclerView notifications;
    private ConstraintLayout layoutNotif,menu;
    public static AlertDialog alerti;
    private LinearLayoutManager layoutManager;
    private static  int numberOfNotificationUnseen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent stickyService = new Intent(this, TaskManger.class);
        startService(stickyService);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModelPro = ViewModelProviders.of(this).get(ManageDataViewModel.class);
        //mViewModel.deleteAllNotif();
        setContentView(R.layout.activity_main);
        conx = this;
        layout = findViewById(R.id.connetcd);
        notifications = findViewById(R.id.notifica);
        seetingsNot = findViewById(R.id.csvExport);
        menu =findViewById(R.id.menu);
        notification = findViewById(R.id.notification);
        num = findViewById(R.id.num);
        layoutNotif = findViewById(R.id.layoutNotifica);
        name = findViewById(R.id.namePopup);
        image = findViewById(R.id.image);
        imageButton = findViewById(R.id.imageButton);
        final NotificationAdapater adapter = new NotificationAdapater(conx,this);
        notifications.setLayoutManager(new LinearLayoutManager(conx));
        notifications.setAdapter(adapter);
        mViewModel.getAllNotifications().observe(this, new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                for (Notification n:notifications) {
                    numberOfNotificationUnseen = 0;

                   if(n.isSeen() == false){
                    numberOfNotificationUnseen++;}
                    }
                num.setVisibility(View.VISIBLE);
                num.setText(""+numberOfNotificationUnseen);
                if(numberOfNotificationUnseen == 0)
                {
                    num.setVisibility(View.GONE);
                }
                adapter.submitList(notifications);

            }
        });



        adapter.setOnIteemClickListener(new NotificationAdapater.OnItemClickLisnter() {
            @Override
            public void OnItemClick(Notification Notification) {
                if(Notification.isSeen() == false){
                    Notification.setSeen(true);
                mViewModel.update(Notification);
                adapter.notifyDataSetChanged();
                numberOfNotificationUnseen--;
                if(numberOfNotificationUnseen == 0)
                {
                    num.setVisibility(View.GONE);
                }
                num.setText(""+numberOfNotificationUnseen);
                }
                else
                    return;
            }
        });
        final AlertDialog.Builder alert = new AlertDialog.Builder(conx);
        LayoutInflater layoutInflater =  (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogueView =layoutInflater.inflate(R.layout.pop_up_user,null,false);
        num.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);


        seetingsNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getAllNotifications().observe(MainActivity.this, new Observer<List<Notification>>() {
                    @Override
                    public void onChanged(List<Notification> list) {
                     StaticUse.exportCsvFilesNotification(list,MainActivity.this);
                    }
                });
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menu.getVisibility()== View.VISIBLE)
                {
                   menu.setVisibility(View.GONE);
                }else
                {
                    menu.setVisibility(View.VISIBLE);
                }
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Hiiiiiiiiiiii !", Toast.LENGTH_SHORT).show();
                if(dialogueView.getParent() != null) {
                    ((ViewGroup)dialogueView.getParent()).removeView(dialogueView); // <- fix
                }
                alert.setView(dialogueView);

                final ImageView profilePop = dialogueView.findViewById(R.id.profilPop);
                final TextView fullNameView = dialogueView.findViewById(R.id.namePop);
                final Button logout = dialogueView.findViewById(R.id.logout);


                Account account = StaticUse.loadSession(conx);
                mViewModel.getAccount(account.getId()).observe(MainActivity.this, new Observer<List<Account>>() {
                    @Override
                    public void onChanged(List<Account> accounts) {
                        //Toast.makeText(MainActivity.this, ""+accounts.get(0).getId(), Toast.LENGTH_SHORT).show();
                        fullNameView.setText("Logged in as "+accounts.get(0).getFirstName());
                        Glide.with(conx).asBitmap().load(accounts.get(0).getProfileImage()).into(profilePop);
                    }
                });

                //TextInputLayout textInputLayout =
                // Set an EditText view to get user input
                alerti =alert.show();
                alerti.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                //Button save = alerti.getButton(AlertDialog.BUTTON_POSITIVE);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View vi) {
                        StaticUse.clearShared(StaticUse.SHARED_NAME_USER_LOG,conx);
                        layout.setVisibility(View.GONE);
                        Navigation.findNavController((Activity)conx,R.id.nav_host_fragment).navigate(R.id.accountBindiner);
                        alerti.dismiss();
                    }
                });
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        layout.setVisibility(View.GONE);

        if(StaticUse.loggedInInternal(conx))
        {
            layout.setVisibility(View.VISIBLE);
            Account currentAccount = StaticUse.loadSession(conx);
            name.setText(currentAccount.getFirstName());
            Glide.with(conx).asBitmap().load(currentAccount.getProfileImage()).into(image);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        if (item.getTitle().equals("Home")) {
            if(StaticUse.loggedInInternal(conx))
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.mainMenu);
            else
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.accountBindiner);
        }
        if (item.getTitle().equals("Settings")) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.settingsFragment);
        }
        if (item.getTitle().equals("Logout")) {
            StaticUse.removeData(StaticUse.SHARED_NAME_USER, "email", this);
            StaticUse.clearShared(StaticUse.SHARED_NAME_USER_LOG,this);
            layout.setVisibility(View.GONE);
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.signIn);
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean getConnection()
    {

        requestQueue = Volley.newRequestQueue(conx);
        requestQueue.start();
        JsonObjectRequest request = new JsonObjectRequest (Request.Method.GET,StaticUse.SKELETON_PRIMITIVE, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hasConnection =true;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                hasConnection = false;
                return;

            }
        });
        requestQueue.add(request);
        return hasConnection;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        conx = this;
        layout = findViewById(R.id.connetcd);
        name = findViewById(R.id.namePopup);
        image = findViewById(R.id.image);
        if(StaticUse.loggedInInternal(conx))
        {

            layout.setVisibility(View.VISIBLE);
            Account currentAccount = StaticUse.loadSession(conx);
            mViewModel.getAccount(currentAccount.getId()).observe(MainActivity.this, new Observer<List<Account>>() {
                @Override
                public void onChanged(List<Account> accounts) {
                    name.setText(accounts.get(0).getFirstName());
                    Glide.with(conx).asBitmap().load(accounts.get(0).getProfileImage()).into(image);
                }
            });

        }
    }


    public static void dismissMessage() {
        alerti.dismiss();
    }

    public static AlertDialog AskOption(final Context context, final Account account)
    {


        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_delete_red)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mViewModel.delete(account);
                        Toast.makeText(context, "The account of "+account.getFirstName()+" "+account.getLastLoggedIn()+" has been deleted", Toast.LENGTH_SHORT).show();

                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    public static void insertNotification(final Notification notification)
    {
        mViewModel.insert(notification);
    }

    public static void updateNotification(final Notification notification)
    {
        mViewModel.update(notification);
    }

    public static AlertDialog AskOptionPro(final Context context, final Products account, final LifecycleOwner activity, final String type)
    {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_delete_red)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mViewModelPro.delete(account);
                        mViewModel.getAccount(StaticUse.loadSession(context).getId()).observe(activity, new Observer<List<Account>>() {
                            @Override
                            public void onChanged(List<Account> accounts) {
                                final Account user = accounts.get(0);
                                Notification notification = new Notification();
                                notification.setCreation(new Date());
                                notification.setOwner(user.getFirstName());
                                notification.setCategoryName("Traceability Module");
                                notification.setSeen(false);
                                notification.setName(type);
                                notification.setDescription("has deleted a Traced Product by the ID of "+account.getId()+" from ");
                                notification.setObjectImageBase64(StaticUse.transformerImageBase64frombytes(account.getImage()));
                                notification.setImageBase64(StaticUse.transformerImageBase64frombytes(user.getProfileImage()));
                                MainActivity.insertNotification(notification);
                                StaticUse.createNotificationChannel(notification,(Activity)context);
                                StaticUse.displayNotification((Activity)context,R.drawable.ic_delete_blue_24dp,notification);
                            }
                        });
                        dialog.dismiss();
                        Toast.makeText(context, "The Trace of "+account.getId()+" has been deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri PathHolder = data.getData();
        FileInputStream fileInputStream = null;
        StringBuilder text = new StringBuilder();
        try {
            InputStream inputStream = getContentResolver().openInputStream(PathHolder);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String mLine;
            while ((mLine = r.readLine()) != null) {
                text.append(mLine);
                text.append('\n');
            }
            Toast.makeText(conx, ""+text.toString(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
