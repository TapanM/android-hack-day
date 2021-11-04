package com.example.androidhackday.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Arrays;

public class AppUtils {

    public static void writeToFile(Context context, String data) {
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput("log.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data);
        }
        catch (IOException exception) {
            Log.e("Exception", "File write failed: " + exception.toString());
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (IOException exception) {
                Log.e("Exception", "writer failed to close: " + exception.toString());
            }
        }
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }

    public static String readFromFile(Context context) {

        String ret = "";
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput("log.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException exception) {
            Log.e("FileNotFoundException", "File not found: " + exception.toString());
        } catch (IOException exception) {
            Log.e("IOException", "Can not read file: " + exception.toString());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException exception) {
                Log.e("Exception", "input stream failed to close: " + exception.toString());
            }
        }

        return ret;
    }

    public static String getFormattedPrice(BigDecimal price) {
        return price.stripTrailingZeros().toPlainString();
    }
}
