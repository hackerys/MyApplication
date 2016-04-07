package com.jansen.myapplication.config;

/**
 * Created Jansen on 2016/3/30.
 */
public class Urls {
    public static final String HOST2="https://10.0.2.2:8443";
    public static final String HOST="https://192.168.1.54:8443";

    public static final int UPLOAD_FILE_FLAG=1;
    public static final String UPLOAD_FILE_ACTION="/TestSSl/servlet/FileUpLoad";

    public static final int SEND_JSON_FLAG=2;
    public static final String SEND_JSON_ACTION="/TestSSl/servlet/JsonParams";

    public static final int DOWN_LOAD_FLAG=3;
    public static final String DOWN_LOAD_ACTION="/TestSSl/servlet/FileDownLoadServlet";

    public static final String SERVICE_URLADSS_HOTUPDATE = "http://lt-appupgrade-service.wn518.com/";
    public static final String HOTPATCH_UPDRADE = "app_upgrade.wn";
    public static  final String HOTPATCH_BLACKLIST = "blacklist.wn";
}
