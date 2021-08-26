package com.taurusx.ads.demo.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class InputStreamUtil {

    private final static String TAG = "InputStreamUtil";

    public static String readString(InputStream from, Charset charset) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(from, charset);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(reader);
            String read = br.readLine();
            while (read != null) {
                sb.append(read);
                read = br.readLine();
            }
            return sb.toString();
        } catch (Exception | Error e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // remove: "cat": ["IAB1"],
    public static String removeCat(String response) {
        return response.replace("cat", "cat_remove");
    }
}
