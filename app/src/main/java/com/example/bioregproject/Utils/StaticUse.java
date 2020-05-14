package com.example.bioregproject.Utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContentResolverCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.Notification;
import com.example.bioregproject.entities.PersoTask;
import com.example.bioregproject.ui.History.DeviceHistory;
import com.example.bioregproject.ui.History.DeviceHistoryViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.opencsv.CSVReader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StaticUse extends AppCompatActivity {

    public static final String SKELETON = "http://192.168.1.8:5000/api/";
    public static final String SKELETON_PRIMITIVE = "http://192.168.1.8:5000/";
    public static final String SHARED_NAME_USER ="user";
    public static final String SHARED_NAME_ADMIN ="admin";
    public static final String SHARED_NAME_USER_LOG ="userLog";
    private static String email,password,fullName;
    private static long id;

    public static String loadEmail(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_USER, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        return email;
    }
    public static String loadAdminPassword(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_ADMIN, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("password","");
        return email;
    }

    public static boolean hasAdmin(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_ADMIN, Context.MODE_PRIVATE);
        password = sharedPreferences.getString("password","");
        if(!password.isEmpty() && !password.equals(""))
            return true;
        else
            return false;
    }

    public static void clearShared(String name,Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void  saveEmail(Context context,String email)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.apply();

    }

    public static void  saveAdmin(Context context,String password)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_NAME_ADMIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password",password);
        editor.apply();

    }


    public static boolean validateEmpty(TextInputLayout text , String type)
    {
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError(""+ type +" can't be empty");
            return false;}
        else
        {
            text.setError(null);
            return true;
        }

    }

    public static boolean validateEmail(TextInputLayout text)
    {
        if(text.getError()!= null)
        {text.setError(null);}
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError("Email can't be empty");
            return false;}
        else if(!Patterns.EMAIL_ADDRESS.matcher(textToCheck).matches()){
            text.setError("Please enter a valid email address ");
            return false;
        }
        else
        {
            text.setError(null);
            return true;
        }
    }

    public  static void removeData(String name,String key, Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();

    }
    public static void editData(String name, Context context, String key,String data)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,data);
        editor.commit();
    }

    public static String FloatFormInflater(Float number)
    {

        if (number == Math.floor(number)) {
            return String.format("%.0f", number);
        } else {
            return Float.toString(number);
        }

    }

    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static byte[] transformerImageBytes(ImageView container)
    {
        BitmapDrawable drawable = (BitmapDrawable) container.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        return imageInByte;
    }

    public static String transformerImageBase64(ImageView container)
    {
        String base64String ;
        BitmapDrawable drawable = (BitmapDrawable) container.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        base64String = Base64.encodeToString(bb,0);
        //Toast.makeText(getActivity(), ""+base64String, Toast.LENGTH_SHORT).show();
        return base64String;

    }

    public static String transformerImageBase64frombytes(byte[] bb)
    {
        String base64String ;
        base64String = Base64.encodeToString(bb,0);
        //Toast.makeText(getActivity(), ""+base64String, Toast.LENGTH_SHORT).show();
        return base64String;

    }

    public static byte[] transsformerImageBytesBase64(String base64)
    {
        return Base64.decode(base64, Base64.DEFAULT);
    }

    public static void loginInternal(final Context context,final int destination,final View views)
    {

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogueView =layoutInflater.inflate(R.layout.password_confirm_dialogue,null);
        alert.setView(dialogueView);
        final TextInputLayout textInputLayout = dialogueView.findViewById(R.id.argax);
        final TextView fullNameView = dialogueView.findViewById(R.id.fullName);
        final Button logIn = dialogueView.findViewById(R.id.log);
        final Button back = dialogueView.findViewById(R.id.back);

        alert.setTitle("Please enter Password");
        fullNameView.setText("Administrator");
        //TextInputLayout textInputLayout =
        // Set an EditText view to get user input

        final AlertDialog alerti =alert.show();
        //Button save = alerti.getButton(AlertDialog.BUTTON_POSITIVE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerti.dismiss();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textInputLayout.getEditText().getText().toString().isEmpty() && !textInputLayout.getEditText().getText().toString().equals("") && textInputLayout.getEditText().getText().toString()!=null)
                {
                    if(textInputLayout.getEditText().getText().toString().equals(loadAdminPassword(context))){
                        textInputLayout.setError(null);
                        Navigation.findNavController(views).navigate(destination);
                        alerti.dismiss();
                    }else
                    {
                        textInputLayout.setError("Password is wrong ");

                    }

                }
            }
        });


    }


    public static boolean loggedInInternal(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_USER_LOG, Context.MODE_PRIVATE);
        fullName = sharedPreferences.getString("name","");
        if(!fullName.isEmpty() && !fullName.equals(""))
            return true;
        else
            return false;
    }
    public static boolean loggedInInternalAdmin(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_USER_LOG, Context.MODE_PRIVATE);
        fullName = sharedPreferences.getString("name","");
        password = sharedPreferences.getString("password","");
        if(!fullName.isEmpty() && !fullName.equals("") && !password.isEmpty() && !password.equals("")&& fullName.equals("Administrator ")&& password.equals(loadAdminPassword(context)))
            return true;
        else
            return false;
    }



    public static void  saveSession(Context context,String password,String fullName,long id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_NAME_USER_LOG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",fullName);
        editor.putString("password",password);
        editor.putLong("id",id);
        editor.apply();
    }
    public static Account loadSession(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_USER_LOG, Context.MODE_PRIVATE);
        password = sharedPreferences.getString("password","");
        fullName =sharedPreferences.getString("name","");
        id = sharedPreferences.getLong("id",0);
        Account session = new Account();
        session.setId(id);
        session.setFirstName(fullName);
        session.setPassword(password);
        return session;
    }
    public static byte[] imageGetter(ImageView imageView)
    {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        return  imageInByte;
    }


    public static void backgroundAnimator(ViewGroup viewGroup)
    {
        AnimationDrawable animationDrawable = (AnimationDrawable)viewGroup.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

    }

    public static boolean validateSpinner(String spinner,TextInputLayout container,String Indicator) {
        if ((spinner == null || spinner.equals("") || spinner.isEmpty())) {
            container.setError("Must Choose a !"+Indicator);
            return false;
        } else {
            container.setError(null);
            return true;

        }
    }


    public static final String insertImage(ContentResolver cr,
                                           Bitmap source,
                                           String title,
                                           String description) {

        ContentValues values = new ContentValues();
        values.put(Images.Media.TITLE, title);
        values.put(Images.Media.DISPLAY_NAME, title);
        values.put(Images.Media.DESCRIPTION, description);
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
        // Add the date meta data to ensure the image is added at the front of the gallery
        values.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());

        Uri url = null;
        String stringUrl = null;    /* value to be returned */

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (source != null) {
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                } finally {
                    imageOut.close();
                }

                long id = ContentUris.parseId(url);
                // Wait until MINI_KIND thumbnail is generated.
                Bitmap miniThumb = Images.Thumbnails.getThumbnail(cr, id, Images.Thumbnails.MINI_KIND, null);
                // This is for backward compatibility.
                storeThumbnail(cr, miniThumb, id, 50F, 50F,Images.Thumbnails.MICRO_KIND);
            } else {
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }

        return stringUrl;
    }

    private static final Bitmap storeThumbnail(
            ContentResolver cr,
            Bitmap source,
            long id,
            float width,
            float height,
            int kind) {

        // create the matrix to scale it
        Matrix matrix = new Matrix();

        float scaleX = width / source.getWidth();
        float scaleY = height / source.getHeight();

        matrix.setScale(scaleX, scaleY);

        Bitmap thumb = Bitmap.createBitmap(source, 0, 0,
                source.getWidth(),
                source.getHeight(), matrix,
                true
        );

        ContentValues values = new ContentValues(4);
        values.put(Images.Thumbnails.KIND,kind);
        values.put(Images.Thumbnails.IMAGE_ID,(int)id);
        values.put(Images.Thumbnails.HEIGHT,thumb.getHeight());
        values.put(Images.Thumbnails.WIDTH,thumb.getWidth());

        Uri url = cr.insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream thumbOut = cr.openOutputStream(url);
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
            thumbOut.close();
            return thumb;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }


    public static String randomizerName(String bytes) {
        byte[] array = new byte[4]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return "BioReg_"+generatedString+bytes;
    }


    public static void createNotificationChannel(Notification notification,Activity activity) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = notification.getCategoryName();
            String description = notification.getOwnerFirstName()+" "+notification.getOwnerFirstName()+" "+notification.getDescription()+" "+notification.getName();
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("123", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void displayNotification (Activity activity,int icon,Notification notification)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity,"1234");
        builder.setSmallIcon(R.drawable.admin_settings);
        builder.setContentText(notification.getOwnerFirstName()+" "+notification.getOwnerFirstName()+" "+notification.getDescription()+" "+notification.getName());
        builder.setContentTitle(notification.getCategoryName());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(activity);
        notificationManagerCompat.notify(2,builder.build());

    }

    //file exports csv for Notification Type
    public static void exportCsvFilesNotification(List<History> list,Activity activity)
    {
        StringBuilder data = new StringBuilder();
        data.append("Name,Category,Creation_Date,Owner_FirstName,Owner_LastName,OwnerId,Description,SubjectName,SubjectId,Sub_Category");
        for (History n :list
             ) {
           data.append("\n"+n.getName()+","+n.getCategoryName()+","
                   + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(n.getCreation())
                   +","+n.getOwnerFirstName()+","+n.getOwnerLastName()+","+n.getOwnerLinking()+","+n.getDescription()+","+n.getName()
                   +","+n.getSubjectLinking()+","+n.getSubCategoryName());
        }
        try {
            /*FileOutputStream out = activity.openFileOutput(StaticUse.loadEmail(activity)+"_Notification_BioReg.csv",MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();*/
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(activity.openFileOutput(StaticUse.loadEmail(activity)+"_History_BioReg.csv", Context.MODE_PRIVATE));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.close();
            File fileLocation = new File(activity.getFilesDir(),StaticUse.loadEmail(activity)+"_History_BioReg.csv");
            Uri path = FileProvider.getUriForFile(activity,"com.example.bioregproject.fileprovider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"History_DATA");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,path);
            activity.startActivity(Intent.createChooser(fileIntent,"0DATA"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void exportCsvFilesTasks(List<PersoTask> list, Activity activity)
    {
        StringBuilder data = new StringBuilder();
        data.append("Title,Assignee_Name,Creation_Date,Due_Date,Description,Priority,State,OwnerName,Assignee_ID");
        for (PersoTask n :list
        ) {
            data.append("\n"+n.getName()+","+n.getAssignedName()+","
                    + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(n.getCreation())
                    +","+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(n.getDue())+","
                    +n.getDescription()+","+n.getPiority()+","+n.getState()
                    +","+n.getOwnerName()+","+n.getAssginedId()+",");
        }
        try {
            /*FileOutputStream out = activity.openFileOutput(StaticUse.loadEmail(activity)+"_Notification_BioReg.csv",MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();*/
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(activity.openFileOutput(StaticUse.loadEmail(activity)+"_Tasks_BioReg.csv", Context.MODE_PRIVATE));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.close();
            File fileLocation = new File(activity.getFilesDir(),StaticUse.loadEmail(activity)+"_Tasks_BioReg.csv");
            Uri path = FileProvider.getUriForFile(activity,"com.example.bioregproject.fileprovider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Tasks_DATA");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,path);
            activity.startActivity(Intent.createChooser(fileIntent,"0DATA"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public  static void readCsvFileType(Activity activity,String Type)
    {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/csv");
        activity.startActivityForResult(intent, 7);


    }

    public  static  String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    public static void SaveHistory(LifecycleOwner lifecycleOwner,DeviceHistoryViewModel viewModel, Activity activity, String catName, String description, String objectName, long objectLinking, String subCategory) {
        viewModel.getAccount(StaticUse.loadSession(activity).getId()).observe(lifecycleOwner, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                final Account user = accounts.get(0);
                History history = new History();
                history.setCreation(new Date());
                history.setOwnerFirstName(user.getFirstName());
                history.setOwnerLastName(user.getLastName());
                history.setCategoryName(catName);
                history.setName(objectName);
                history.setDescription(description);
                history.setOwnerLinking(user.getId());
                history.setSubCategoryName(subCategory);
                history.setSubjectLinking(objectLinking);
                viewModel.insert(history);
                viewModel.getAccount(StaticUse.loadSession(activity).getId()).removeObservers(lifecycleOwner);
            }
        });
    }

    public static void SaveNotification(LifecycleOwner lifecycleOwner,MainActivityViewModel viewModel, Activity activity, String catName, String description, String objectName, byte[] imageHolder,ImageView container,int img) {
        viewModel.getAccount(StaticUse.loadSession(activity).getId()).observe(lifecycleOwner, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                final Account user = accounts.get(0);
                Notification notification = new Notification();
                notification.setCreation(new Date());
                notification.setOwnerFirstName(user.getFirstName().trim());
                notification.setOwnerLastName(user.getLastName().trim());
                notification.setCategoryName(catName);
                notification.setName(objectName);
                notification.setDescription(description);
                notification.setSeen(false);
                if(imageHolder != null)
                notification.setObjectImageBase64(StaticUse.transformerImageBase64frombytes(imageHolder));
                if(container!=null)
                notification.setObjectImageBase64(StaticUse.transformerImageBase64(container));
                notification.setImageBase64(StaticUse.transformerImageBase64frombytes(user.getProfileImage()));
                viewModel.insert(notification);
                StaticUse.createNotificationChannel(notification,activity);
                StaticUse.displayNotification(activity,img,notification);
                viewModel.getAccount(StaticUse.loadSession(activity).getId()).removeObservers(lifecycleOwner);
            }
        });
    }



}
