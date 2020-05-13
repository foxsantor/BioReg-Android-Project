package com.example.bioregproject.Utils;

import android.app.Activity;
import android.app.Application;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bioregproject.DataBases.BioRegDB;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

public class DatabaseExporter  extends AppCompatActivity {

    public static File ExporterCSV(String DatabaseName, Activity activity)
    {

         BioRegDB bioRegDB = BioRegDB.getInstance(activity);
        File directory = activity.getFilesDir();
        File directory2 =Environment.getExternalStorageDirectory();
        File exportDir = new File(directory2, "/BioReg/");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, DatabaseName + ".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = bioRegDB.query("SELECT * FROM " + DatabaseName, null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = new String[curCSV.getColumnCount()];
                for (int i = 0; i < curCSV.getColumnCount() - 1; i++)
                    arrStr[i] = curCSV.getString(i);
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();

            Toast.makeText(activity, "Exported", Toast.LENGTH_SHORT).show();

        } catch (Exception sqlEx) {
            sqlEx.printStackTrace();
            Log.e("ExporterCSV", sqlEx.getMessage(), sqlEx);
        }
        return file;
    }



}
