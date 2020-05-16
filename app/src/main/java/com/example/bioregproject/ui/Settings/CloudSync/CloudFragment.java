package com.example.bioregproject.ui.Settings.CloudSync;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.DatabaseExporter;
import com.example.bioregproject.Utils.DriveServiceHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.net.URI;
import java.util.Collections;


public class CloudFragment extends Fragment {

    private CloudViewModel mViewModel;
    private FloatingActionButton sync;
    //private DriveServiceHelper driveServiceHelper;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 6;
    private static  final int REQUEST_CODE_UPLOAD = 7;
    private static final int SYNC_EVERYTHING   =200;
    private static  int SYNC_CODE = 0;

    private DriveServiceHelper mDriveServiceHelper;
    private String mOpenFileId,email,nameS;
    private Button signOff;
    private ConstraintLayout loadingLayout;
    private TextView name,mail,profileT;
    private Switch history,account,products,tasks;
    private ImageView profile,coud;
    private ProgressBar progressBar;


    public static CloudFragment newInstance() {
        return new CloudFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cloud_fragment, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CloudViewModel.class);
        signOff = view.findViewById(R.id.googleSign);
        profileT = view.findViewById(R.id.portraitT);
        profileT.setBackground(null);
        sync = view.findViewById(R.id.SyncNow);
        name = view.findViewById(R.id.name);
        mail = view.findViewById(R.id.sync);
        history = view.findViewById(R.id.historysw);
        account= view.findViewById(R.id.accountSw);
        products = view.findViewById(R.id.traceabilitySw);
        tasks= view.findViewById(R.id.taskSw);
        profile= view.findViewById(R.id.portrait);
        coud = view.findViewById(R.id.Coud);
        Glide.with(getActivity()).load(R.drawable.google_drive_logo).into(coud);
        loadingLayout = view.findViewById(R.id.leoadding);
        progressBar = view.findViewById(R.id.progressBar);

        loadingLayout.setVisibility(View.VISIBLE);
        //progressBar.getProgressDrawable().setColorFilter(getActivity().getResources().getColor(R.color.officialBlue),android.graphics.PorterDuff.Mode.MULTIPLY);
        //progressBar.setProgressTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.officialBlue)));

        requestSignIn();
        profileT.setVisibility(View.INVISIBLE);
        profile.setVisibility(View.INVISIBLE);



        account.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                   SYNC_CODE = 2;
                }else
                {
                    SYNC_CODE = 0;
                }
            }
        });
        products.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SYNC_CODE = 2;
                }else
                {
                    SYNC_CODE = 0;
                }
            }
        });
        tasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SYNC_CODE = 2;
                }else
                {
                    SYNC_CODE = 0;
                }
            }
        });
        history.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SYNC_CODE = 2;
                }else
                {
                    SYNC_CODE = 0;
                }
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SYNC_CODE != 0)
                {
                    requestSignIn();
                    history.setChecked(false);
                    tasks.setChecked(false);
                    products.setChecked(false);
                    account.setChecked(false);

                }else {
                    Toast.makeText(getActivity(), "Nothing to Sync please check at least one table ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSignOff();

            }
        });

        // Authenticate the user. For most apps, this should be done when the user performs an
        // action that requires Drive access rather than in onCreate.


       // DatabaseExporter.ExporterCSV("history_table",getActivity());



    }


    private void uploadFile()
    {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading to Drive");
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        String filepath = "/Internal Storage/Download/1.pdf";
        mDriveServiceHelper.createFile().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
               progressDialog.dismiss();
                Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Check your google drive api", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void RequestAuth()
    {
        GoogleSignInOptions googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(getActivity(),googleSignInOptions);
        startActivityForResult(client.getSignInIntent(),420);

    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == getActivity().RESULT_OK && data != null&& data.getData() != null){
//        if(requestCode == 420)
//        {
//            handleSignIn(data);
//        }}
//        else
//        {
//            return;
//        }
//    }


    private void handleSignIn(Intent data)
    {
        GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        GoogleAccountCredential googleAccountCredential = GoogleAccountCredential
                                .usingOAuth2(getActivity(), Collections.singleton(DriveScopes.DRIVE_FILE));

                        googleAccountCredential.setSelectedAccount(googleSignInAccount.getAccount());
                        Drive service = new Drive.Builder(AndroidHttp.newCompatibleTransport(),
                                new GsonFactory(),googleAccountCredential)
                                .setApplicationName("BioReg").build();
                        mDriveServiceHelper = new DriveServiceHelper(service);
                        //Toast.makeText(getActivity(), "i was here", Toast.LENGTH_SHORT).show();
                        //driveServiceHelper.createFilePickerIntent();


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);
                }
                break;
            case REQUEST_CODE_OPEN_DOCUMENT:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    Uri uri = resultData.getData();
                    if (uri != null) {
                        openFileFromFilePicker(uri);
                    }
                }
                break;
        }


    }

    /**
     * Starts a sign-in activity using {@link #REQUEST_CODE_SIGN_IN}.
     */
    private void requestSignIn() {


        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .build();
        GoogleSignInClient client = GoogleSignIn.getClient(getActivity(), signInOptions);
        //loadingLayout.setVisibility(View.GONE);
        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    private void requestSignOff() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .build();
        GoogleSignInClient client = GoogleSignIn.getClient(getActivity(), signInOptions);
        client.signOut();
        //loadingLayout.setVisibility(View.GONE);
        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    /**
     * Handles the {@code result} of a completed sign-in activity initiated from {@link
     * #requestSignIn()}.
     */
    private void handleSignInResult(Intent result) {

        loadingLayout.setVisibility(View.VISIBLE);
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {


                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    getActivity(), Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    name.setText(googleAccount.getDisplayName());
                    mail.setText("Syncing to "+googleAccount.getEmail());
                    if(googleAccount.getPhotoUrl()==null)
                    {
                      profile.setVisibility(View.GONE);
                      profileT.setVisibility(View.VISIBLE);
                      profileT.setText(""+googleAccount.getDisplayName().charAt(0));
                    }
                    else {
                        profileT.setVisibility(View.GONE);
                        profile.setVisibility(View.VISIBLE);
                        Glide.with(getActivity()).load(googleAccount.getPhotoUrl()).into(profile);
                    }
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();

                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions.
                    mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                    loadingLayout.setVisibility(View.GONE);
                    if(account.isChecked()){
                        mDriveServiceHelper.saveFileDatabase("history_table","history_table","history_table",getActivity());
                    }
                    if(history.isChecked())
                    {
                        mDriveServiceHelper.saveFileDatabase("account_table","account_table","account_table",getActivity());
                    }
                    if(products.isChecked())
                    {
                        mDriveServiceHelper.saveFileDatabase("product_table","product_table","product_table",getActivity());
                    }
                    if(tasks.isChecked())
                    {
                        mDriveServiceHelper.saveFileDatabase("parsotask_table","parsotask_table","parsotask_table",getActivity());
                    }



                })
                .addOnFailureListener(exception -> Log.e("error", "Unable to sign in.", exception));}



    /**
     * Opens the Storage Access Framework file picker using {@link #REQUEST_CODE_OPEN_DOCUMENT}.
     */
    private void openFilePicker() {
        if (mDriveServiceHelper != null) {
            Log.d("error", "Opening file picker.");

            Intent pickerIntent = mDriveServiceHelper.createFilePickerIntent();

            // The result of the SAF Intent is handled in onActivityResult.
            startActivityForResult(pickerIntent, REQUEST_CODE_OPEN_DOCUMENT);
        }
    }

    /**
     * Opens a file from its {@code uri} returned from the Storage Access Framework file picker
     * initiated by {@link #openFilePicker()}.
     */
    private void openFileFromFilePicker(Uri uri) {
        if (mDriveServiceHelper != null) {
            Log.d("error", "Opening " + uri.getPath());

            mDriveServiceHelper.openFileUsingStorageAccessFramework(getActivity().getContentResolver(), uri)
                    .addOnSuccessListener(nameAndContent -> {
                        String name = nameAndContent.first;
                        String content = nameAndContent.second;


                        // Files opened through SAF cannot be modified.
                        setReadOnlyMode();
                    })
                    .addOnFailureListener(exception ->
                            Log.e("error", "Unable to open file from picker.", exception));
        }
    }

    /**
     * Creates a new file via the Drive REST API.
     */
    private void createFile() {
        if (mDriveServiceHelper != null) {
            Log.d("error", "Creating a file.");

            mDriveServiceHelper.createFile()
                    .addOnSuccessListener(fileId -> readFile(fileId))
                    .addOnFailureListener(exception ->
                            Log.e("error", "Couldn't create file.", exception));
        }
    }

    /**
     * Retrieves the title and content of a file identified by {@code fileId} and populates the UI.
     */
    private void readFile(String fileId) {
        if (mDriveServiceHelper != null) {
            Log.d("error", "Reading file " + fileId);

            mDriveServiceHelper.readFile(fileId)
                    .addOnSuccessListener(nameAndContent -> {
                        String name = nameAndContent.first;
                        String content = nameAndContent.second;



                        setReadWriteMode(fileId);
                    })
                    .addOnFailureListener(exception ->
                            Log.e("error", "Couldn't read file.", exception));
        }
    }

    /**
     * Saves the currently opened file created via {@link #createFile()} if one exists.
     */
    private void saveFile() {
        if (mDriveServiceHelper != null && mOpenFileId != null) {
            Log.d("error", "Saving " + mOpenFileId);

            String fileName = "";
            String fileContent = "";

            mDriveServiceHelper.saveFile(mOpenFileId, fileName, fileContent)
                    .addOnFailureListener(exception ->
                            Log.e("error", "Unable to save file via REST.", exception));
        }
    }

    /**
     * Queries the Drive REST API for files visible to this app and lists them in the content view.
     */
    private void query() {
        if (mDriveServiceHelper != null) {
            Log.d("error", "Querying for files.");

            mDriveServiceHelper.queryFiles()
                    .addOnSuccessListener(fileList -> {
                        StringBuilder builder = new StringBuilder();
                        for (File file : fileList.getFiles()) {
                            builder.append(file.getName()).append("\n");
                        }
                        String fileNames = builder.toString();

//                        mFileTitleEditText.setText("File List");
//                        mDocContentEditText.setText(fileNames);

                        setReadOnlyMode();
                    })
                    .addOnFailureListener(exception -> Log.e("error", "Unable to query files.", exception));
        }
    }

    /**
     * Updates the UI to read-only mode.
     */
    private void setReadOnlyMode() {
//        mFileTitleEditText.setEnabled(false);
//        mDocContentEditText.setEnabled(false);
        mOpenFileId = null;
    }

    /**
     * Updates the UI to read/write mode on the document identified by {@code fileId}.
     */
    private void setReadWriteMode(String fileId) {
//        mFileTitleEditText.setEnabled(true);
//        mDocContentEditText.setEnabled(true);
        mOpenFileId = fileId;
    }
}
