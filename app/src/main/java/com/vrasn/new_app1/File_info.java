package com.vrasn.new_app1;

/**
 * Created by ADMIN on 3/23/2016.
 */
public class File_info {
    private String name;
    private String path;
    private String user_name;

    public File_info(String n,String p,String uname)
    {
        name=n;
        path=p;
        user_name=uname;
    }

    public String getname(){
        return name;
    }
    public String getpath(){
        return path;
    }
    public String getUser_name(){
        return user_name;
    }
}
