package com.ringlayer.seanergy;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.lang.String;


public class FileSystemOp {
    public void _do_log_debug(Throwable ex, String function_source) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            String debug_msg = function_source + "->" + ex.toString();
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/seanergy/debug.log");
            if (file.exists()) {
                append_file(Environment.getExternalStorageDirectory().getAbsolutePath() + "/seanergy/debug.log", debug_msg);
                //String stackTrace = Log.getStackTraceString(ex);
                //append_file(Environment.getExternalStorageDirectory().getAbsolutePath() + "/seanergy/debug.log", stackTrace);
            }
            else {
                Log.e("_do_log_debug", ex.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("_do_log_debug exception", e.toString());
        }
    }
    /* self made function */
    public List<String> EnumSD() {
        String tmp_storage;
        List<String> storages_string = new ArrayList<String>();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ListIterator<String> data;

            List<String> DirAndFile = listFolders("/mnt/");
            data = DirAndFile.listIterator();
            while (data.hasNext()) {
                tmp_storage = data.next();
                if (tmp_storage.toLowerCase().contains("sd")) {
                    storages_string.add(tmp_storage);
                }
            }
        } catch (Exception e) {
            _do_log_debug(e, "EnumSD");
        }
        return storages_string;
    }

    public void append_file(String path, String content) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(content);
            pw.close();
        } catch (Exception e) {
            _do_log_debug(e, "append_file");
            e.printStackTrace();
        }
    }

    public int prepare_log_dir() {
        int retme = 0;
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            Log.e("Prepare Log Dir", "[+] preparing log dir called");
            boolean exists = false;
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/seanergy";
            Log.e("Prepare Log Dir", "[+] path : " + path);

            exists = CheckIfDirectoryExists(path);
            if (exists == false) {
                Log.e("Prepare Log Dir", "[+] mkdir");
                new File(path).mkdir();
            }
            exists = CheckIfDirectoryExists(path);
            if (exists == false) {
                Log.e("Prepare Log Dir", "[+] mkdir using cmd");
                String cmd = "mkdir " + path;
                JustExec(cmd);
                retme = 1;
            }
            exists = CheckIfDirectoryExists(path);
            if (exists == false) {
                Log.e("Prepare Log Dir", "[-] failed to prepare app directory");
            }

        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.e("Prepare Log Dir", stackTrace);
        }

        return retme;
    }

    /*
    * maptostringarray from http://stackoverflow.com/questions/2741175/set-windows-path-environment-variable-at-runtime-in-java
    * */
    static String[] mapToStringArray(Map<String, String> map) {
        final String[] strings = new String[map.size()];
        int i = 0;
        for (Map.Entry<String, String> e : map.entrySet()) {
            strings[i] = e.getKey() + '=' + e.getValue();
            i++;
        }
        return strings;
    }

    /* taken from https://androidcookbook.com/Recipe.seam?recipeId=2457 */
    public String ExecCmd(String path, String cmd) {
        StringBuffer commandOutput;
        commandOutput = new StringBuffer();
        commandOutput.append(" ", 0, 1);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            String log_result = "";
            final Map<String, String> env = new HashMap<String, String>(System.getenv());
            String log_cmd = path + "/" + cmd;
            env.put("PATH", env.get("PATH"));
            env.put("LANG", "en_US.UTF-8");
            env.put("ANDROID_DATA", env.get("ANDROID_DATA"));
            env.put("PWD", "/sdcard/");
            final String[] env_strings = mapToStringArray(env);

            _do_log_cmd(log_cmd);
            Process process = Runtime.getRuntime().exec(log_cmd, env_strings);
            InputStreamReader reader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);
            int numRead;
            char[] buffer = new char[5000];
            commandOutput = new StringBuffer();
            while ((numRead = bufferedReader.read(buffer)) > 0) {
                commandOutput.append(buffer, 0, numRead);
            }
            bufferedReader.close();
            process.waitFor();
            log_result = commandOutput.toString();
            _do_log_cmd(log_result);

        } catch (Exception e) {
            _do_log_debug(e, "ExecCmd");
        }

        return commandOutput.toString();
    }


    /* modified from
    * https://dzone.com/articles/java-example-list-all-files
    */

    public List<String> listFolders(String directoryName) {
        List<String> DirAndFile = new ArrayList<String>();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            File directory = new File(directoryName);
            File[] fList = directory.listFiles();
            for (File file : fList) {
                if (file.isDirectory()) {
                    DirAndFile.add(file.getName());
                }
            }
        } catch (Exception e) {
            _do_log_debug(e, "listFolders");
        }
        return DirAndFile;
    }


    /*
    taken from https://www.learn2crack.com/2014/04/android-read-write-file.html
    */
    public Boolean write(String fname, String fcontent) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            String fpath = fname;

            File file = new File(fpath);

            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists()) {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(fcontent);
                bw.close();
            }
            return true;

        } catch (IOException e) {
            _do_log_debug(e, "write");
            //e.printStackTrace();
            return false;
        }

    }

    public String read(String fname) {

        BufferedReader br = null;
        String line = null;

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            br = new BufferedReader(new FileReader(fname));
            line = br.readLine();
            if (line == null) {
                line = "";
            }
        } catch (IOException e) {
            _do_log_debug(e, "read");
            return null;
        }
        return line;
    }




    public void _do_log_cmd(String cmd) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            append_file(Environment.getExternalStorageDirectory().getAbsolutePath() + "/seanergy/command.log", cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    taken from
    * http://www.java2s.com/Code/Android/File/CopyFileandDirectory.htm
    *
    * */

    public void copyDirectory(File sourceLocation, File targetLocation) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            if (sourceLocation.isDirectory()) {
                if (!targetLocation.exists()) {
                    targetLocation.mkdirs();
                }

                String[] children = sourceLocation.list();
                for (int i = 0; i < children.length; i++) {
                    copyDirectory(new File(sourceLocation, children[i]), new File(
                            targetLocation, children[i]));
                }
            } else {

                copyFile(sourceLocation, targetLocation);
            }

        } catch (Exception e) {
            _do_log_debug(e, "copyDirectory");
        }

    }

    public void copyFile(File sourceLocation, File targetLocation) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

        } catch (Exception e) {
            _do_log_debug(e, "copyFile");
        }

    }

    public boolean deleteDirectory(File path) {
        boolean delres = true;
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            if (path.exists()) {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
            delres = path.delete();
        } catch (Exception e) {
            _do_log_debug(e, "deleteDirectory");
        }
        return delres;
    }

    /* modified from https://examples.javacodegeeks.com/core-java/io/file/check-if-directory-exists/ */
    public boolean CheckIfDirectoryExists(String path) {
        boolean exists = false;
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            File dir = new File(path);
            exists = dir.exists();
            return exists;
        } catch (Exception e) {
            _do_log_debug(e, "CheckIfDirectoryExists");
            return false;
        }
    }

    public int check_file_exits(String full_path) {
        int retme = 0;
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
            File file = new File(full_path);
            if (file.exists()) {
                retme = 1;
            }
        } catch (Exception e) {
            _do_log_debug(e, "check_file_exits");
        }

        return retme;
    }

    public void createFileInSDCard(String path) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            int exists = check_file_exits(path);

            if (exists == 0) {
                File file = new File(path);
                file.createNewFile();
            }
        } catch (Exception e) {
            _do_log_debug(e, "createFileInSDCard");
        }
    }

    public void JustExec(String cmd) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            String do_cmd = "/system/bin/" + cmd;
            Process process = Runtime.getRuntime().exec(do_cmd);
        } catch (Exception e) {
            _do_log_debug(e, "JustExec");
        }
    }



    /*
    * modified from http://www.java2s.com/Code/Java/File-Input-Output/Makingazipfileofdirectoryincludingitssubdirectoriesrecursively.htm
    * for fun and profits
    *
    * */
    public void zipDir(String zipFileName, String dir) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            String log_cmd = "";
            File dirObj = new File(dir);
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
            log_cmd = "Creating : " + zipFileName;
            append_file(Environment.getExternalStorageDirectory().getAbsolutePath() + "/seanergy/debug.log", log_cmd);
            addDir(dirObj, out);
            out.close();
        } catch (Exception e) {
            _do_log_debug(e, "zipDir");
        }

    }

    public byte[] convertToByteArray(InputStream inputStream) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int next = inputStream.read();

        while (next > -1) {
            bos.write(next);
            next = inputStream.read();
        }

        bos.flush();

        return bos.toByteArray();
    }

    private void addDir(File dirObj, ZipOutputStream out) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            String log_cmd = "";
            File[] files = dirObj.listFiles();
            byte[] tmpBuf = new byte[1024];

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    addDir(files[i], out);
                    continue;
                }
                FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
                log_cmd = " Adding: " + files[i].getAbsolutePath();
                String fname = Environment.getExternalStorageDirectory().getAbsolutePath() + "/seanergy/debug.log";
                append_file(fname, log_cmd);
                out.putNextEntry(new ZipEntry(files[i].getAbsolutePath()));
                int len;
                while ((len = in.read(tmpBuf)) > 0) {
                    out.write(tmpBuf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            _do_log_debug(e, "addDir");
        }
    }

    /* taken from stackoverflow */


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        boolean retval = false;

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            String state = Environment.getExternalStorageState();
            if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
                retval = true;
            }
        }
        catch (Exception e) {

        }


        return retval;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            return true;
        }
        return false;
    }

    public boolean DeleteFile(String full_path) {
        Boolean retme = false;
        try {
            File file = new File(full_path);
            if (file.exists()) {
                Log.e("[+] DeleteFile", "File found :  " + full_path);
                if(file.delete()) {
                    Log.e("[+] DeleteFile", "File deleted :  " + full_path);
                    retme = true;
                }
            }
        }
        catch (Exception e) {
            Log.e("[-] DeleteFile", "Failed to Delete " + full_path);
        }

        return retme;
    }
}
