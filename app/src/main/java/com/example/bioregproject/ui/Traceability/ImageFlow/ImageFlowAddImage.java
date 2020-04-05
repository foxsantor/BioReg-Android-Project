package com.example.bioregproject.ui.Traceability.ImageFlow;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.AspectRatioFragment;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Notification;
import com.example.bioregproject.entities.Products;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ImageFlowAddImage extends Fragment implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener {

    private ImageFlowAddImageViewModel mViewModel;
    private static final String TAG = "Hello";

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private static final String FRAGMENT_DIALOG = "dialog";

    private static final int[] FLASH_OPTIONS = {
            CameraView.FLASH_AUTO,
            CameraView.FLASH_OFF,
            CameraView.FLASH_ON,
    };

    private static final int[] FLASH_ICONS = {
            R.drawable.ic_flash_auto,
            R.drawable.ic_flash_off,
            R.drawable.ic_flash_on,
    };

    private static final int[] FLASH_TITLES = {
            R.string.flash_auto,
            R.string.flash_off,
            R.string.flash_on,
    };

    private int mCurrentFlash;

    private CameraView mCameraView;
    private MainActivityViewModel mainActivityViewModel;
    private byte[] imageHolder;
    private Handler mBackgroundHandler;
    private CheckBox local;
    private Button done,cancel;
    private FloatingActionButton fab;
    private ImageView preview;
    private Toolbar toolbar;
    private  Bundle bundle;
    private static int  CODE=0;
    private static int UPDATE_CODE =1;
    private static int ADD_CODE=2;
    private ConstraintLayout constraintLayout;
    private boolean isSaveChecked = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         View root=inflater.inflate(R.layout.image_flow_add_image_fragment, container, false);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       bundle = getArguments();
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mViewModel = ViewModelProviders.of(this).get(ImageFlowAddImageViewModel.class);
        mainActivityViewModel  = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mCameraView = view.findViewById(R.id.camera);
        cancel =view.findViewById(R.id.cancel);
        local = view.findViewById(R.id.local);
        preview = view.findViewById(R.id.Preview);
        done = view.findViewById(R.id.done);
        toolbar =view.findViewById(R.id.toolbar);
        constraintLayout = view.findViewById(R.id.mother);
        StaticUse.backgroundAnimator(constraintLayout);


        if(bundle != null){
            CODE = UPDATE_CODE;
            Glide.with(getActivity()).clear(preview);
            Glide.with(getActivity()).asBitmap().load(bundle.getByteArray("image")).into(preview);

        }else
        {
            CODE = ADD_CODE;
        }

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitle("");
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(bundle== null)
                Navigation.findNavController(view).navigate(R.id.action_imageFlowAddImage_to_imageFlowHome);
                else
                    Navigation.findNavController(view).navigate(R.id.action_imageFlowAddImage_to_manageData);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        local.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isSaveChecked = true;
                else
                    isSaveChecked =false;
            }
        });
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }
        fab = view.findViewById(R.id.take_picture);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.take_picture:
                        if (mCameraView != null) {
                            mCameraView.takePicture();
                        }
                        break;
                }
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Products products = new Products();
                products.setCreationDate(new Date());
                products.setExpirationDate(new Date());
                products.setFabricationDate(new Date());
                if(CODE != 2) {
                  products.setId(bundle.getLong("id"));
                  //products.setImage(bundle.getByteArray("image"));
                }
                if(imageHolder != null){
                if(isSaveChecked)
                {
                    String name =StaticUse.randomizerName(imageHolder.toString());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageHolder, 0, imageHolder.length);
                    MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, name, "BioReg Product");
                    products.setImage_name(name);
                }
                products.setImage(imageHolder);
                products.setType("ImageT");
                if(CODE==2) {
                    mViewModel.insert(products);
                    Toast.makeText(getActivity(), "added Successfully", Toast.LENGTH_SHORT).show();

                    mainActivityViewModel.getAccount(StaticUse.loadSession(getContext()).getId()).observe(getActivity(), new Observer<List<Account>>() {
                        @Override
                        public void onChanged(List<Account> accounts) {
                            final Account user = accounts.get(0);
                            Notification notification = new Notification();
                            notification.setCreation(new Date());
                            notification.setOwner(user.getFirstName());
                            notification.setCategoryName("Traceability Module");
                            notification.setSeen(false);
                            notification.setName("Visual Traceability");
                            notification.setDescription("has added a Traced Product"+" from ");
                            notification.setObjectImageBase64(StaticUse.transformerImageBase64frombytes(imageHolder));
                            notification.setImageBase64(StaticUse.transformerImageBase64frombytes(user.getProfileImage()));
                            MainActivity.insertNotification(notification);
                            StaticUse.createNotificationChannel(notification,getActivity());
                        }
                    });

                    Glide.with(getActivity()).clear(preview);
                    local.setChecked(false);
                }else {
                    mViewModel.update(products);
                    Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                    mainActivityViewModel.getAccount(StaticUse.loadSession(getContext()).getId()).observe(getActivity(), new Observer<List<Account>>() {
                        @Override
                        public void onChanged(List<Account> accounts) {
                            final Account user = accounts.get(0);
                            Notification notification = new Notification();
                            notification.setCreation(new Date());
                            notification.setOwner(user.getFirstName());
                            notification.setCategoryName("Traceability Module");
                            notification.setSeen(false);
                            notification.setName("Visual Traceability");
                            notification.setDescription("has updated a Traced Product"+" from ");
                            notification.setObjectImageBase64(StaticUse.transformerImageBase64frombytes(imageHolder));
                            notification.setImageBase64(StaticUse.transformerImageBase64frombytes(user.getProfileImage()));
                            MainActivity.insertNotification(notification);
                            StaticUse.createNotificationChannel(notification,getActivity());
                        }
                    });

                    Navigation.findNavController(view).navigate(R.id.action_imageFlowAddImage_to_manageData);
                    imageHolder = null;

                }
                }else {
                    Toast.makeText(getActivity(), "You did not Chose an image", Toast.LENGTH_SHORT).show();
                    return;}
            }
        });



        mViewModel.getAllProducts().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {


            }
        });

    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.camera, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aspect_ratio:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (mCameraView != null
                        && fragmentManager.findFragmentByTag(FRAGMENT_DIALOG) == null) {
                    final Set<AspectRatio> ratios = mCameraView.getSupportedAspectRatios();
                    final AspectRatio currentRatio = mCameraView.getAspectRatio();
                    AspectRatioFragment.newInstance(ratios, currentRatio,this)
                            .show(fragmentManager, FRAGMENT_DIALOG);
                }
                return true;
            case R.id.switch_flash:
                if (mCameraView != null) {
                    mCurrentFlash = (mCurrentFlash + 1) % FLASH_OPTIONS.length;
                    item.setTitle(FLASH_TITLES[mCurrentFlash]);
                    item.setIcon(FLASH_ICONS[mCurrentFlash]);
                    mCameraView.setFlash(FLASH_OPTIONS[mCurrentFlash]);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            if (mCameraView != null ) {
                mCameraView.start();
            }
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            ConfirmationDialogFragment
                    .newInstance(R.string.camera_permission_confirmation,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION,
                            R.string.camera_permission_not_granted)
                    .show(getActivity().getSupportFragmentManager(), FRAGMENT_DIALOG);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onPause() {
        if (mCameraView != null )
            mCameraView.stop();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBackgroundHandler != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mBackgroundHandler.getLooper().quitSafely();
            } else {
                mBackgroundHandler.getLooper().quit();
            }
            mBackgroundHandler = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (permissions.length != 1 || grantResults.length != 1) {
                    throw new RuntimeException("Error on requesting camera permission.");
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), R.string.camera_permission_not_granted,
                            Toast.LENGTH_SHORT).show();
                }
                // No need to start camera here; it is handled by onResume
                break;
        }
    }

    @Override
    public void onAspectRatioSelected(@NonNull AspectRatio ratio) {
        if (mCameraView != null) {
            Toast.makeText(getActivity(), ratio.toString(), Toast.LENGTH_SHORT).show();
            mCameraView.setAspectRatio(ratio);
        }
    }

    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }

    private CameraView.Callback mCallback
            = new CameraView.Callback() {
    @Override
    public void onCameraOpened(CameraView cameraView) {
        Log.d(TAG, "onCameraOpened");
    }

    @Override
    public void onCameraClosed(CameraView cameraView) {
        Log.d(TAG, "onCameraClosed");
    }

    @Override
    public void onPictureTaken(CameraView cameraView, final byte[] data) {
        Log.d(TAG, "onPictureTaken " + data.length);
        Glide.with(getActivity()).asBitmap().load(data).into(preview);
        imageHolder = data;

    }

};

    public static class ConfirmationDialogFragment extends DialogFragment {

        private static final String ARG_MESSAGE = "message";
        private static final String ARG_PERMISSIONS = "permissions";
        private static final String ARG_REQUEST_CODE = "request_code";
        private static final String ARG_NOT_GRANTED_MESSAGE = "not_granted_message";

        public static ConfirmationDialogFragment newInstance(@StringRes int message,
                                                             String[] permissions, int requestCode, @StringRes int notGrantedMessage) {
            ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_MESSAGE, message);
            args.putStringArray(ARG_PERMISSIONS, permissions);
            args.putInt(ARG_REQUEST_CODE, requestCode);

            args.putInt(ARG_NOT_GRANTED_MESSAGE, notGrantedMessage);
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Bundle args = getArguments();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(args.getInt(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String[] permissions = args.getStringArray(ARG_PERMISSIONS);
                                    if (permissions == null) {
                                        throw new IllegalArgumentException();
                                    }
                                    ActivityCompat.requestPermissions(getActivity(),
                                            permissions, args.getInt(ARG_REQUEST_CODE));
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(),
                                            args.getInt(ARG_NOT_GRANTED_MESSAGE),
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                    .create();
        }

    }

}
