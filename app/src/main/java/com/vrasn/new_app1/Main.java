package com.vrasn.new_app1;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends ListActivity {


    private List<File_info> item = null;

    private String currentDir; //  Stores the path of the current directory .
    private String root = "/storage"; //  Stores the root directory  ie "/" .
    public int i=0;
    private TextView myPath;  // view variables to display directory path .
    private TextView dirname; // view variable to display directory name .
    private ListView lv;        //ListView variable to displaying file and folders .

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //============================== method on create activity =====================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //==============================  providing layout for the main class =================================
        setContentView(R.layout.main);


        //========================================list view variable ==============================================
        lv= getListView();
        //============================ on long hold click menu =============================================
        /*lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String str = Long.toString(R.string.Copy);
                String str1 = Long.toString(R.string.Cut);
                return false;
            }
        });*/


        // ======================== initialising Views of the class layout =================================================
        //========================== initialising directory name view =====================================
        dirname = (TextView) findViewById(R.id.dir_name);
        //============================ initialising path View ===============================================
        myPath = (EditText) findViewById(R.id.path);
        //============================ initialising List View variable =======================================


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });*/

        registerForContextMenu(lv);

        //======================== Add folder button click operation =========================================================
        Button Add_folder = (Button) findViewById(R.id.add_folder);
        Add_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_folder();
            }
        });

        final EditText file_to_search = (EditText) findViewById(R.id.search_file_path);

        //==================================== search button click operations =====================================================
        final Button search = (Button) findViewById(R.id.search_file_btn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(Main.this, search_result.class);
               /* searchIntent.putExtra("to_search", file_to_search.getText().toString());*/
                startActivity(searchIntent);
            }
        });
        //===================== Preferences button event handling===============================================
        Button preferences = (Button) findViewById(R.id.menu);


        //=================== calling method to fill the List view with directories in the root directory ===================
        getDir(root);


       /* setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,
                getResources().getStringArray(R.array.country)));*/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    //==================== add folder event ==================================================
    private void add_folder() {
        if (currentDir.equals(root))
        {
            Toast.makeText(Main.this, "No permissin to add here....", Toast.LENGTH_SHORT).show();
        }
        else {
            int i = 1;
            // making prompt dialog to take folder name from user
            LayoutInflater prompt_li = LayoutInflater.from(this);

            View promptView = prompt_li.inflate(R.layout.prompt_user_input, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            final EditText et = (EditText) promptView.findViewById(R.id.input_edit_text);
            et.setHint("enter file name");
            alertDialogBuilder.setView(promptView).setTitle("Enter name of new file")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String st = et.getText().toString();

                            File ff = new File(currentDir + "/" + st);

                            if (!ff.exists()) {
                                ff.mkdir();
                                getDir(currentDir);
                            } else {
                                Toast.makeText(Main.this, "folder already exist \n please try again", Toast.LENGTH_SHORT).show();
                                add_folder();

                            }
                        }

                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
            //adding folder into current directory


        }


    }

    //===================== filling the list view with directories and folders =====================================================
    private void getDir(String dirPath) {

        currentDir=dirPath;

        item = new ArrayList<File_info>();
        List<File_info> itemD = new ArrayList<File_info>();
        List<File_info> itemF = new ArrayList<File_info>();


            File f = new File(dirPath);


            myPath.setText(f.getPath());
            if(dirPath.equals(root))
               dirname.setText("Root Directory");
            else
               dirname.setText(f.getName());

            File[] files = f.listFiles();

            if (!dirPath.equals(root)) {

                itemD.add(new File_info("../", f.getParent(),"../"));


            }

            for (int i = 0; i < files.length; i++) {

                File file = files[i];


                if (file.isDirectory()) {
                    if(file.canRead())
                        itemD.add(new File_info(file.getName() + "/", file.getPath(),file.getName()));
                } else {
                    itemF.add(new File_info(file.getName(), file.getPath(),file.getName()));
                }


            }

            item.addAll(itemD);
            item.addAll(itemF);

            setListAdapter(new customAdapter(this, R.layout.file_view, item));

    }




    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.vrasn.new_app1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.vrasn.new_app1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }



    //on List item click event

    //================================== method to perform on List Item click ==============================================


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        File file = new File(item.get(position).getpath());

        if (file.isDirectory()) {
            if (file.canRead())
                getDir(item.get(position).getpath());
           else

                new AlertDialog.Builder(this).setIcon(R.drawable.directory_icon)
                        .setTitle("Need Admin Authentication....")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
        }
        else {
            new AlertDialog.Builder(this).setIcon(R.drawable.file_icon)
                    .setTitle("[" + file.getName() + "] file can't be read")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    }


    //=================================== option menu code ======================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    //========================================= on option menu item selected then do this ======================================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(Main.this, "This service in progress....", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.action_sort) {
            Toast.makeText(Main.this, "Sorting service in progress....", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

    //context menu for list items
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       /* long currentItem;
        view w ;
        super.onCreateContextMenu(menu, v, menuInfo);

        if(!currentDir.equals(root)) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
        }

        currentItem = getSelectedItemPosition();*/

       /* AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("Countries");
        String[] menuItem;
        menuItem = getResources().getStringArray(R.array.menu);
        for (int i=0;i<menuItem.length;i++){
            menu.add(Menu.NONE,i,i,menuItem[i]);
        }*/
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.copy) {
            Toast.makeText(Main.this, "This service in progress....", Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}



