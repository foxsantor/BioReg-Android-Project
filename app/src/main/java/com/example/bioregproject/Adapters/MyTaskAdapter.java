package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.PersoTask;
import com.example.bioregproject.entities.PersoTask;
import com.example.bioregproject.ui.Planification.taskPlan;
import com.google.android.material.textfield.TextInputLayout;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyTaskAdapter extends ListAdapter<PersoTask,MyTaskAdapter.PersoTaskHolder> {


    private OnItemClickLisnter listener;
    private Context mContext;
    private com.example.bioregproject.ui.Planification.taskPlan TaskPlan;
    private Activity activity;
    private taskPlan fragmentActivity;
    private int mode ;
    private long timeLeft;
    private static Boolean check = false;
    private static String statusS="";


    public MyTaskAdapter(Context context,Activity activity,int mode) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;
        this.mode=mode;
    }

    public MyTaskAdapter(Context context,Activity activity,int mode,taskPlan fragmentActivity) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;
        this.mode=mode;
        this.fragmentActivity = fragmentActivity;
    }
    private static final DiffUtil.ItemCallback<PersoTask> DIFF_CALLBACK = new DiffUtil.ItemCallback<PersoTask>() {
        @Override
        public boolean areItemsTheSame(@NonNull PersoTask oldItem, @NonNull PersoTask newItem) {
            return oldItem.getCreation().equals(newItem.getCreation()) ;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PersoTask oldItem, @NonNull PersoTask newItem) {
            return  oldItem.getCreation().equals(newItem.getCreation())&&
                    oldItem.getState().equals(newItem.getState())&&
                    oldItem.getDue().equals(newItem.getDue())&&
                    oldItem.getOwnerName().equals(newItem.getOwnerName())&&
                    oldItem.getPiority().equals(newItem.getPiority())&&
                    oldItem.getAssginedId()==oldItem.getAssginedId()&&
                    oldItem.getAssignedName().equals(oldItem.getAssignedName());
        }
    };

    @NonNull
    @Override
    public PersoTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mytask_item,parent, false);
        return new PersoTaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersoTaskHolder holder, int position) {

        final PersoTask currentItem = getItem(position);
        PrettyTime p = new PrettyTime();
        Date creation = currentItem.getCreation();
        String  assgine = currentItem.getAssignedName();
        String action = currentItem.getDescription();
        String title= currentItem.getName();
        String owner = currentItem.getOwnerName();
        String stats = currentItem.getState();
        Date due = currentItem.getDue();
        String formater,dueString2;
        String priority = currentItem.getPiority();
        holder.validation.setVisibility(View.GONE);
        Date now = new Date();
        final String dueString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(due);
        if (currentItem.getDue().compareTo(now) <= 0)
        {
            holder.body.setCardBackgroundColor(mContext.getResources().getColor(R.color.disabled));
            dueString2 ="<font color='#fb4444'><u>"+dueString+"</u></font>";
            if(mode != 2)
            {
                holder.checkBox.setClickable(false);
                holder.checkBox.setEnabled(false);
            }
        }else
        {
            dueString2="<u>"+dueString+"</u>";
        }

        if(stats.equals("Done"))
        {
            final String validationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getValidationDate());
            holder.validation.setVisibility(View.VISIBLE);
            formater =  "<font color='#00A86B'><strong>Done on: "+validationDate+"</strong></font>";
            holder.validation.setText(Html.fromHtml(formater));
            if(mode !=2)
            {
                holder.checkBox.setClickable(false);
                holder.checkBox.setEnabled(false);
            }
        }
        if(mode == 2)
        {
            formater = "<strong>Task N"+currentItem.getId()+"</strong>"+" "+"<font color='#1877F2'><strong>"+title+"</strong></font>"+" due "+dueString2+" assigned to "+"<strong>"+currentItem.getAssignedName()+"</strong>"+" Created:"+p.format(currentItem.getCreation()) ;
        }else
        {
            formater =  "<strong>Task N"+currentItem.getId()+"</strong>"+" "+" <font color='#1877F2'><strong>"+title+"</strong></font>"+" due "+dueString2+" Created: "+p.format(currentItem.getCreation()) ;
        }

        holder.itemText.setText(Html.fromHtml(formater));
        if(stats.equals("Open"))
        {
            holder.color.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellowWarning));
        }else if (stats.equals("Invalid")){

            holder.color.setCardBackgroundColor(mContext.getResources().getColor(R.color.redErrorDeep));
        }
        else
        {
            holder.color.setCardBackgroundColor(mContext.getResources().getColor(R.color.greenJade));
        }
        if(priority.equals("Critic"))
        {
            holder.priority.setVisibility(View.VISIBLE);
            holder.prorityImage.setVisibility(View.VISIBLE);
            holder.priority.setText("Critic");
            holder.priority.setTextColor(mContext.getResources().getColor(R.color.redErrorDeep));
            Glide.with(mContext).load(R.drawable.ic_error_outline_red_24dp).into(holder.prorityImage);

        }else if(priority.equals("High"))
        { holder.priority.setVisibility(View.VISIBLE);
            holder.prorityImage.setVisibility(View.VISIBLE);
            holder.priority.setText("High");
            holder.priority.setTextColor(mContext.getResources().getColor(R.color.yellowWarning));
            Glide.with(mContext).load(R.drawable.ic_error_outline_warning_24dp).into(holder.prorityImage);
        }else
        {
            holder.priority.setVisibility(View.INVISIBLE);
            holder.prorityImage.setVisibility(View.INVISIBLE);
        }

        if(currentItem.getImageBase64() == null)
        {
            holder.joined.setVisibility(View.GONE);
        }else
        {
            holder.joined.setVisibility(View.VISIBLE);
        }


        if(currentItem.getComment() == null && holder.joined.getVisibility()!=View.VISIBLE)
        {
            holder.joined.setVisibility(View.GONE);
        }else
        {
            holder.joined.setVisibility(View.VISIBLE);
        }



        holder.mark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(mContext, holder.mark);
                //inflating menu from xml resource
                if(mode != 2){
                popup.inflate(R.menu.taskmenu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.validate:
                                    if(currentItem.getDue().compareTo(now) > 0)
                                    {
                                    if(currentItem.getState().equals("Done"))
                                    {
                                        final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                        LayoutInflater layoutInflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View dialogueView =layoutInflater.inflate(R.layout.revert_dialogue,null);
                                        alert.setView(dialogueView);
                                        Button cancel,revert;
                                        CheckBox invalid,open;
                                        TextView task;
                                        cancel= dialogueView.findViewById(R.id.cancel);
                                        invalid= dialogueView.findViewById(R.id.invalid);
                                        open= dialogueView.findViewById(R.id.open);
                                        revert= dialogueView.findViewById(R.id.revert);
                                        task= dialogueView.findViewById(R.id.task);
                                        alert.setTitle("Revert task status");
                                        task.setText("The task N° "+currentItem.getId()+" is already validated, would you like to revert it's status ?");
                                        final AlertDialog alerti =alert.show();
                                        invalid.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(invalid.isChecked()) {
                                                    check = true;
                                                    statusS = "Invalid";
                                                    open.setChecked(false);
                                                } else{
                                                check = false;
                                                statusS ="";}
                                            }
                                        });

                                        open.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(open.isChecked()) {
                                                    check = true;
                                                    statusS = "Open";
                                                    invalid.setChecked(false);
                                                } else{
                                                    check = false;
                                                    statusS ="";}
                                            }
                                        });

                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                            alerti.dismiss();
                                            }
                                        });
                                        revert.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(check && !statusS.equals("")){
                                                    TaskPlan.UpdateTaskOne(currentItem.getId(),statusS,"Reverted");
                                                    Toast.makeText(mContext, "Task has been Reverted to "+statusS, Toast.LENGTH_SHORT).show();
                                                    Navigation.findNavController(activity,R.id.nav_host_fragment).navigate(R.id.taskPlan);
                                                    alerti.dismiss();
                                                }else
                                                {
                                                    Toast.makeText(mContext, "Please choose a status", Toast.LENGTH_SHORT).show();

                                                    return;
                                                }

                                            }
                                        });
                                    }else
                                    {
                                        TaskPlan.UpdateTaskOne(currentItem.getId(),"Done","Completed");
                                        Toast.makeText(mContext, "Task has been Validated", Toast.LENGTH_SHORT).show();
                                        Navigation.findNavController(v).navigate(R.id.taskPlan);
                                    }}else
                                    {
                                        Toast.makeText(mContext, "You can't Validate an expired task", Toast.LENGTH_SHORT).show();
                                    }
                                    return true;
                                case R.id.details:
                                    final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                    LayoutInflater layoutInflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View dialogueView =layoutInflater.inflate(R.layout.details_dailogue,null);
                                    alert.setView(dialogueView);
                                     ImageView prioImage,profilA,attachedimage;
                                     ConstraintLayout attach;
                                     CardView prioV;
                                     TextView name,creation,title,due,prio,timer,description,comment,state,comentindi,imageind,timerandy;
                                    prioImage= dialogueView.findViewById(R.id.prioImage);
                                    comentindi= dialogueView.findViewById(R.id.comentindi);
                                    imageind= dialogueView.findViewById(R.id.imageind);
                                    prioV = dialogueView.findViewById(R.id.stateV);
                                    attach = dialogueView.findViewById(R.id.attacht);
                                    profilA = dialogueView.findViewById(R.id.profileA);
                                    attachedimage= dialogueView.findViewById(R.id.attachedobject);
                                    name= dialogueView.findViewById(R.id.name);
                                    creation= dialogueView.findViewById(R.id.creation);
                                    title = dialogueView.findViewById(R.id.title);
                                    due= dialogueView.findViewById(R.id.due);
                                    prio= dialogueView.findViewById(R.id.prio);
                                    timer= dialogueView.findViewById(R.id.timer);
                                    description= dialogueView.findViewById(R.id.description);
                                    comment = dialogueView.findViewById(R.id.comment);
                                    state = dialogueView.findViewById(R.id.state);
                                    alert.setTitle("Details");
                                    TaskPlan.ProfileImageGetter(profilA,currentItem.getAssginedId());
                                    timerandy= dialogueView.findViewById(R.id.timerandy);
                                    if(currentItem.getState().equals("Done"))
                                    {

                                        final String validationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getValidationDate());
                                        timer.setText("Done on "+validationDate);
                                        timer.setTextColor(mContext.getResources().getColor(R.color.greenJade));
                                        timerandy.setVisibility(View.GONE);
                                    }else {
                                        timerandy.setVisibility(View.VISIBLE);
                                        timeLeft = currentItem.getDue().getTime() - new Date().getTime();
                                        if (timeLeft > 0)
                                            StartTimer(timer);
                                        else
                                            timer.setText("00:00:00");
                                    }
                                    attach.setVisibility(View.GONE);
                                    comentindi.setVisibility(View.GONE);
                                    imageind.setVisibility(View.GONE);
                                    attachedimage.setVisibility(View.GONE);

                                    title.setText("Task N°"+currentItem.getId()+" "+ currentItem.getName());
                                    name.setText(currentItem.getAssignedName());
                                    final String dueString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getCreation());
                                    creation.setText("Created on "+ dueString+" by "+currentItem.getOwnerName());
                                    description.setText(currentItem.getDescription());
                                    if(currentItem.getComment()==null)
                                    {
                                        comment.setVisibility(View.GONE);
                                        comentindi.setVisibility(View.GONE);

                                    }else {
                                        if ( stats.equals("Done")) {

                                            attach.setVisibility(View.VISIBLE);
                                            comentindi.setVisibility(View.VISIBLE);
                                            comment.setVisibility(View.VISIBLE);
                                            comment.setText(currentItem.getComment());


                                        }
                                    }
                                    if( currentItem.getImageBase64() == null){
                                        imageind.setVisibility(View.GONE);
                                        attachedimage.setVisibility(View.GONE);
                                    }else
                                    {
                                    if( stats.equals("Done"))

                                    {
                                        attach.setVisibility(View.VISIBLE);
                                        imageind.setVisibility(View.VISIBLE);
                                        attachedimage.setVisibility(View.VISIBLE);
                                        Glide.with(activity).load(StaticUse.transsformerImageBytesBase64(currentItem.getImageBase64())).into(attachedimage);
                                    }
                                  }

                                    final String creationString = new SimpleDateFormat(" EEEE, MMMMM d, yyyy").format(currentItem.getDue());
                                    final String creationString2 = new SimpleDateFormat("h:mm a").format(currentItem.getDue());
                                    due.setText("Due "+StaticUse.capitalize(creationString)+" at "+creationString2);
                                    if(stats.equals("Open"))
                                    {
                                        state.setText(stats);
                                        state.setTextColor(mContext.getResources().getColor(R.color.yellowWarning));
                                        prioV.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellowWarning));
                                    }else if (stats.equals("Invalid")){
                                        state.setText(stats);
                                        state.setTextColor(mContext.getResources().getColor(R.color.redErrorDeep));
                                        prioV.setCardBackgroundColor(mContext.getResources().getColor(R.color.redErrorDeep));
                                    }
                                    else
                                    {
                                        state.setText(stats);
                                        state.setTextColor(mContext.getResources().getColor(R.color.greenJade));
                                        prioV.setCardBackgroundColor(mContext.getResources().getColor(R.color.greenJade));
                                    }
                                    if(priority.equals("Critic"))
                                    {
                                        prio.setText("Critic");
                                        prio.setTextColor(mContext.getResources().getColor(R.color.redErrorDeep));
                                        Glide.with(mContext).load(R.drawable.ic_error_outline_red_24dp).into(prioImage);

                                    }else if(priority.equals("High"))
                                    {
                                        prio.setText("High");
                                        prio.setTextColor(mContext.getResources().getColor(R.color.yellowWarning));
                                        Glide.with(mContext).load(R.drawable.ic_error_outline_warning_24dp).into(prioImage);
                                    }else
                                    {
                                        prio.setText(priority);
                                        prio.setTextColor(mContext.getResources().getColor(R.color.greenMint));
                                        prioImage.setVisibility(View.INVISIBLE);
                                    }

                                    //TextInputLayout textInputLayout =
                                    // Set an EditText view to get user input
//                                    alert.setPositiveButton("Save", null);
//                                    alert.setNegativeButton("Back", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int whichButton) {
//                                            // Canceled.
//                                        }
//                                    });
                                final AlertDialog alerti =alert.show();
//                                    Button save = alerti.getButton(AlertDialog.BUTTON_POSITIVE);
//                                    save.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View vi) {
//                                            if(!textInputLayout.getEditText().getText().toString().isEmpty() && !textInputLayout.getEditText().getText().toString().equals("") && textInputLayout.getEditText().getText().toString()!=null)
//                                            {
//                                                EditComment(currentItem.getIdComment(),textInputLayout.getEditText().getText().toString());
//                                                notifyItemChanged(position);
//                                                Navigation.findNavController(v).navigate(R.id.commentsection);
//                                                alerti.dismiss();
//
//                                            }
//
//                                        }
//                                    });
                                    return  true;
                                case R.id.Attach :
                                taskPlan.DilaogueAttach(currentItem,fragmentActivity);
                                    return  true;
                                default:
                                    return false;
                            }
                        }
                    });
                    //displaying the popup
                    popup.show();
                }
                else{
                    popup.inflate(R.menu.adminmenu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    final AlertDialog.Builder alerto = new AlertDialog.Builder(mContext);
                                    LayoutInflater layoutInflatero =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View dialogueViewo =layoutInflatero.inflate(R.layout.delete_dialogue,null);
                                    alerto.setView(dialogueViewo);
                                    Button delete,cancelo;
                                    alerto.setTitle("Delete Task N° "+currentItem.getId());
                                     delete = dialogueViewo.findViewById(R.id.delete);
                                    cancelo= dialogueViewo.findViewById(R.id.cancel);
                                    final AlertDialog alertio =alerto.show();
                                    cancelo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertio.dismiss();
                                        }
                                    });
                                    delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            taskPlan.DeleteTaskOne(currentItem.getId());
                                            alertio.dismiss();
                                        }
                                    });
                                    return true;
                                case R.id.details:
                                    final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                    LayoutInflater layoutInflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View dialogueView =layoutInflater.inflate(R.layout.details_dailogue,null);
                                    alert.setView(dialogueView);
                                    ImageView prioImage,profilA,attachedimage;
                                    ConstraintLayout attach;
                                    CardView prioV;
                                    TextView name,creation,title,due,prio,timer,description,comment,state,comentindi,imageind,timerandy;
                                    prioImage= dialogueView.findViewById(R.id.prioImage);
                                    comentindi= dialogueView.findViewById(R.id.comentindi);
                                    imageind= dialogueView.findViewById(R.id.imageind);
                                    prioV = dialogueView.findViewById(R.id.stateV);
                                    attach = dialogueView.findViewById(R.id.attacht);
                                    profilA = dialogueView.findViewById(R.id.profileA);
                                    attachedimage= dialogueView.findViewById(R.id.attachedobject);
                                    name= dialogueView.findViewById(R.id.name);
                                    creation= dialogueView.findViewById(R.id.creation);
                                    title = dialogueView.findViewById(R.id.title);
                                    due= dialogueView.findViewById(R.id.due);
                                    prio= dialogueView.findViewById(R.id.prio);
                                    timer= dialogueView.findViewById(R.id.timer);
                                    description= dialogueView.findViewById(R.id.description);
                                    comment = dialogueView.findViewById(R.id.comment);
                                    state = dialogueView.findViewById(R.id.state);
                                    alert.setTitle("Details");
                                    TaskPlan.ProfileImageGetter(profilA,currentItem.getAssginedId());
                                    timerandy= dialogueView.findViewById(R.id.timerandy);
                                    if(currentItem.getState().equals("Done"))
                                    {

                                        final String validationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getValidationDate());
                                        timer.setText("Done on "+validationDate);
                                        timer.setTextColor(mContext.getResources().getColor(R.color.greenJade));
                                        timerandy.setVisibility(View.GONE);
                                    }else {
                                        timerandy.setVisibility(View.VISIBLE);
                                        timeLeft = currentItem.getDue().getTime() - new Date().getTime();
                                        if (timeLeft > 0)
                                            StartTimer(timer);
                                        else
                                            timer.setText("00:00:00");
                                    }
                                    attach.setVisibility(View.GONE);
                                    comentindi.setVisibility(View.GONE);
                                    imageind.setVisibility(View.GONE);
                                    attachedimage.setVisibility(View.GONE);

                                    title.setText("Task N°"+currentItem.getId()+" "+ currentItem.getName());
                                    name.setText(currentItem.getAssignedName());
                                    final String dueString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getCreation());
                                    creation.setText("Created on "+ dueString+" by "+currentItem.getOwnerName());
                                    description.setText(currentItem.getDescription());
                                    if(currentItem.getComment()==null)
                                    {
                                        comment.setVisibility(View.GONE);
                                        comentindi.setVisibility(View.GONE);

                                    }else {
                                        if ( stats.equals("Done")) {

                                            attach.setVisibility(View.VISIBLE);
                                            comentindi.setVisibility(View.VISIBLE);
                                            comment.setVisibility(View.VISIBLE);
                                            comment.setText(currentItem.getComment());


                                        }
                                    }
                                    if( currentItem.getImageBase64() == null){
                                        imageind.setVisibility(View.GONE);
                                        attachedimage.setVisibility(View.GONE);
                                    }else
                                    {
                                        if( stats.equals("Done"))

                                        {
                                            attach.setVisibility(View.VISIBLE);
                                            imageind.setVisibility(View.VISIBLE);
                                            attachedimage.setVisibility(View.VISIBLE);
                                            Glide.with(activity).load(StaticUse.transsformerImageBytesBase64(currentItem.getImageBase64())).into(attachedimage);
                                        }
                                    }

                                    final String creationString = new SimpleDateFormat(" EEEE, MMMMM d, yyyy").format(currentItem.getDue());
                                    final String creationString2 = new SimpleDateFormat("h:mm a").format(currentItem.getDue());
                                    due.setText("Due "+StaticUse.capitalize(creationString)+" at "+creationString2);
                                    if(stats.equals("Open"))
                                    {
                                        state.setText(stats);
                                        state.setTextColor(mContext.getResources().getColor(R.color.yellowWarning));
                                        prioV.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellowWarning));
                                    }else if (stats.equals("Invalid")){
                                        state.setText(stats);
                                        state.setTextColor(mContext.getResources().getColor(R.color.redErrorDeep));
                                        prioV.setCardBackgroundColor(mContext.getResources().getColor(R.color.redErrorDeep));
                                    }
                                    else
                                    {
                                        state.setText(stats);
                                        state.setTextColor(mContext.getResources().getColor(R.color.greenJade));
                                        prioV.setCardBackgroundColor(mContext.getResources().getColor(R.color.greenJade));
                                    }
                                    if(priority.equals("Critic"))
                                    {
                                        prio.setText("Critic");
                                        prio.setTextColor(mContext.getResources().getColor(R.color.redErrorDeep));
                                        Glide.with(mContext).load(R.drawable.ic_error_outline_red_24dp).into(prioImage);

                                    }else if(priority.equals("High"))
                                    {
                                        prio.setText("High");
                                        prio.setTextColor(mContext.getResources().getColor(R.color.yellowWarning));
                                        Glide.with(mContext).load(R.drawable.ic_error_outline_warning_24dp).into(prioImage);
                                    }else
                                    {
                                        prio.setText(priority);
                                        prio.setTextColor(mContext.getResources().getColor(R.color.greenMint));
                                        prioImage.setVisibility(View.INVISIBLE);
                                    }

                                    //TextInputLayout textInputLayout =
                                    // Set an EditText view to get user input
//                                    alert.setPositiveButton("Save", null);
//                                    alert.setNegativeButton("Back", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int whichButton) {
//                                            // Canceled.
//                                        }
//                                    });
                                    final AlertDialog alerti =alert.show();
//                                    Button save = alerti.getButton(AlertDialog.BUTTON_POSITIVE);
//                                    save.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View vi) {
//                                            if(!textInputLayout.getEditText().getText().toString().isEmpty() && !textInputLayout.getEditText().getText().toString().equals("") && textInputLayout.getEditText().getText().toString()!=null)
//                                            {
//                                                EditComment(currentItem.getIdComment(),textInputLayout.getEditText().getText().toString());
//                                                notifyItemChanged(position);
//                                                Navigation.findNavController(v).navigate(R.id.commentsection);
//                                                alerti.dismiss();
//
//                                            }
//
//                                        }
//                                    });

                                    return  true;
                                case R.id.edit :
                                    Bundle bundle = new Bundle();
                                    bundle.putString("title",currentItem.getName());
                                    bundle.putString("description",currentItem.getDescription());
                                    bundle.putString("AssgineName",currentItem.getAssignedName());
                                    bundle.putLong("assgineId",currentItem.getAssginedId());
                                    bundle.putLong("id",currentItem.getId());
                                    bundle.putString("priority",currentItem.getPiority());
                                    bundle.putString("status",currentItem.getState());
                                    final String validationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getDue());
                                    bundle.putString("due",validationDate);
                                    Navigation.findNavController(activity,R.id.nav_host_fragment).navigate(R.id.action_taskPlan_to_taskPlanManage,bundle);


                                    return  true;

                                default:
                                    return false;
                            }
                        }
                    });
                    //displaying the popup
                    popup.show();



                }
                //adding click listener

            }
        });


    }



    public PersoTask getPersoTaskAt(int postion) {
        return getItem(postion);
    }

    class PersoTaskHolder extends RecyclerView.ViewHolder {

        public CardView color,body;
        public TextView itemText,priority,validation;
        public CheckBox checkBox;
        public ImageButton mark;
        public ImageView prorityImage,joined;



        public PersoTaskHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color);
            body = itemView.findViewById(R.id.body);
            itemText = itemView.findViewById(R.id.priority);
            checkBox = itemView.findViewById(R.id.check);
            priority = itemView.findViewById(R.id.itemText);
            mark = itemView.findViewById(R.id.mark);
            joined = itemView.findViewById(R.id.joined);
            prorityImage = itemView.findViewById(R.id.imageView13);
            validation =itemView.findViewById(R.id.validation);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked())
                        //Toast.makeText(activity, ""+getPersoTaskAt(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                        listener.Select(v,getItem(getAdapterPosition()).getId(),getItem(getAdapterPosition()).getId());
                    else
                        listener.Select(v,0,getItem(getAdapterPosition()).getId());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.OnItemClick(getItem(position));
                }
            });
        }







    }

    public interface OnItemClickLisnter {
        void OnItemClick(PersoTask PersoTask);
        void Select(View v, long position,long id);

    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
    public void StartTimer(TextView textView)
    {
        CountDownTimer countDownTimer =new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft=millisUntilFinished;
                UpateTimer(textView);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void UpateTimer(TextView textView)
    {
        int hours = (int) (timeLeft / 1000) / 3600;
        int minutes = (int) ((timeLeft / 1000) % 3600) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        textView.setText(timeLeftFormatted);
    }

}
