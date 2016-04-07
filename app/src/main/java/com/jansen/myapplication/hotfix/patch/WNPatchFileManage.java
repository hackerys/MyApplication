package com.jansen.myapplication.hotfix.patch;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jansen.myapplication.config.Urls;
import com.jansen.myapplication.hotfix.WSApplication;
import com.jansen.myapplication.hotfix.patch.bean.HotPatchBean;
import com.jansen.myapplication.hotfix.patch.bean.HotPatchBlackListRequest;
import com.jansen.myapplication.hotfix.patch.bean.HotPatchRequest;
import com.jansen.myapplication.hotfix.patch.bean.HotPatchResponse;
import com.jansen.myapplication.utils.AESEncryptSafe;
import com.jansen.myapplication.utils.AppInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 此类用于热更新接口，包括:热更新、React Native文件更新、config url更新等
 * <p/>
 * Coolspan on 2016/3/14 12:27
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class WNPatchFileManage {

    private String TAG = "WNPatchFileManage";

    //是否显示Toast
    private boolean isShowLog = true;
    //是否显示Log
    private boolean isShowToast = false;

    //上下文对象是Application上下文，在Application中初始化
    private Context context;
    //Handler
    private Handler handler;

    //Java服务器接口返回的数据Bean
    private HotPatchResponse hotPatchResponse;

    //存储单个文件下载的失败次数，最多可以请求下载3次
    private HashMap<Integer, Integer> faildRequestTime;

    //此类使用的单例模式
    private static WNPatchFileManage instance;

    //防止多线程同时访问此方法获取实例对象，所以加上了同步锁
    public static WNPatchFileManage getInstance() {
        if (instance == null) {
            synchronized (WNPatchFileManage.class) {
                if (instance == null) {//懒加载方式
                    instance = new WNPatchFileManage();
                }
            }
        }
        return instance;
    }

    private AppInfo mAppInfo;

    /**
     * 初始化数据，并请求新数据及本地操作
     *
     * @param context 上下文
     * @param version 补丁版本号(可以读取apk versionCode)
     */
    public void init(Context context, int version) {
        this.context = context;
        this.handler = new Handler();
        mAppInfo = new AppInfo(context);
        int oldVersion = this.getKeyValueInt("version");
        if (oldVersion == -1 || oldVersion != version) {
            //删除文件
            //请求接口，获取是否有新的补丁文件下载
            WSApplication.getFixBugManage().removeAllPatch();//清除已添加的补丁文件
            this.removeAllPatchFile();//删除下载的补丁文件
            this.clearKeyValue();//清除保存的数据
            this.saveKeyValueInt("version", version);//保存当前的版本号
        } else {
            //如果本地有补丁文件，判断补丁文件是否完整，使用md5判断
            //如果文件不完整，重新下载，并再次判断文件是否完整
            // 签名不同，接口会自动通知重新下载 请求接口在下方
        }

        this.faildRequestTime = new HashMap<Integer, Integer>();

        //初始化结束，就开始校验本地文件和请求网络获取新的补丁数据
        this.checkHotPatch();
    }

    //清除所有的补丁文件
    public void removeAllPatchFile() {
        File rootFile = this.getDir();
        if (rootFile.exists()) {
            File[] childrenFiles = rootFile.listFiles();
            for (int i = 0; i < childrenFiles.length; i++) {
                childrenFiles[i].delete();
            }
        }
    }

    /**
     * 获取补丁文件目录
     *
     * @return
     */
    private File getDir() {
        File root = new File(Environment.getExternalStorageDirectory(), "/Android/data/" + context.getPackageName() + File.separator + "PatchFileMange");
        if (!root.exists()) {
            root.mkdirs();
        }
        return root;
    }

    /**
     * 判断是否有新的补丁文件需要下载
     */
    private void checkHotPatch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String hMD5 = null, rMD5 = null, cMD5 = null;
                    String AndroidHotFix_HotPatch = getKeyValueString("AndroidHotFix_HotPatch");
                    if (AndroidHotFix_HotPatch != null) {
                        log(AndroidHotFix_HotPatch);
                        HotPatchBean hotPatchBean = JSON.parseObject(AndroidHotFix_HotPatch, HotPatchBean.class);
                        if (hotPatchBean != null) {
                            hMD5 = checkHotPatchFileMd5(hotPatchBean);
                        }
                    }

                    URL url = new URL(Urls.SERVICE_URLADSS_HOTUPDATE + Urls.HOTPATCH_UPDRADE);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setUseCaches(false);//禁止使用用户缓存
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //                    httpURLConnection.setDoInput(true);
                    HotPatchRequest hotPatchRequest = new HotPatchRequest();
                    hotPatchRequest.setHotpatch_md5(hMD5);//hot fix文件的MD5
                    hotPatchRequest.setConfig_md5(cMD5);//config文件的MD5
                    hotPatchRequest.setReact_md5(rMD5);//react native文件的MD5
                    hotPatchRequest.setOs_type(1);//1:Android   2:IOS
                    hotPatchRequest.setFamily_id(31);//1:yunshang
                    hotPatchRequest.setVer_num("1");//WNUtils.getVersionName(context)//app版本号
                    hotPatchRequest.setDevice_name(android.os.Build.MANUFACTURER + ":" + android.os.Build.MODEL);//设备名称+设备型号
                    //发送Post Body数据
                    String requestData = JSON.toJSONString(hotPatchRequest);
                    requestData = "param=" + requestData;
                    log(requestData);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(requestData.getBytes(), 0, requestData.getBytes().length);
                    outputStream.flush();
                    outputStream.close();

                    int code = httpURLConnection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        byte[] data = readFile(inputStream);
                        httpURLConnection.disconnect();
                        if (data != null) {
                            log(new String(data));
                            //                            String result = new String(data);
                            hotPatchResponse = JSON.parseObject(data, HotPatchResponse.class);
                            if (hotPatchResponse != null && hotPatchResponse.getCode() == 1 && hotPatchResponse.getBody() != null) {
                                if (hotPatchResponse.getBody().getHotpatch() != null && hotPatchResponse.getBody().getHotpatch().getStatus() == 1) {
                                    //                                    deleteKeyValue("hotpatch_crash");
                                    saveKeyValueString("AndroidHotFix_HotPatch", JSON.toJSONString(hotPatchResponse.getBody().getHotpatch()));
                                    log(JSON.toJSONString(hotPatchResponse.getBody().getHotpatch()));
                                    //下载补丁文件
                                    File hotFixFile = new File(getDir(), getLocalPatchFileName(hotPatchResponse.getBody().getHotpatch().getUrl()));
                                    downloadPatchFile(hotPatchResponse.getBody().getHotpatch().getUrl(), hotFixFile.getAbsolutePath(), 100);
                                }
                                if (hotPatchResponse.getBody().getConfig() != null && hotPatchResponse.getBody().getConfig().getStatus() == 1) {
                                    //TODO 下载React Native文件
                                }
                                if (hotPatchResponse.getBody().getReact() != null && hotPatchResponse.getBody().getReact().getStatus() == 1) {
                                    //TODO 下载Config文件
                                }
                            } else {
                                //没有任何更新
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * log输出
     *
     * @param msg
     */
    private void log(String msg) {
        if (isShowLog == true)
            Log.e("qxs", msg);
    }

    /**
     * 下载热更新补丁文件
     *
     * @param url      下载的url
     * @param filePath 下载文件的目录
     * @param taskFlag 请求网络标识
     * @throws Exception
     */
    private void downloadPatchFile(final String url, final String filePath, final int taskFlag) throws Exception {
        URL URI = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) URI.openConnection();
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("GET");
        //        httpURLConnection.setDoOutput(true);
        //                    httpURLConnection.setDoInput(true);

        int code = httpURLConnection.getResponseCode();
        if (code == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] data = readFile(inputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(data, 0, data.length);
            fileOutputStream.flush();
            fileOutputStream.close();

            if (taskFlag == 100) {//热更新补丁文件的处理
                checkHotPatchFileMd5(taskFlag, hotPatchResponse.getBody().getHotpatch());
            } else {
                //TODO 处理其他类型补丁文件
            }
            data = null;
        } else {
            if (taskFlag == 100) {
                int cTime = 0;
                if (faildRequestTime.get(taskFlag) != null) {
                    cTime = faildRequestTime.get(taskFlag);
                }
                cTime++;
                faildRequestTime.put(taskFlag, cTime);
                if (cTime <= 3) {
                    String fileName = getLocalPatchFileName(hotPatchResponse.getBody().getHotpatch().getUrl());//根据url获取资源的文件名称
                    File hotFixFile = new File(getDir(), fileName);//获取补丁文件
                    downloadPatchFile(hotPatchResponse.getBody().getHotpatch().getUrl(), hotFixFile.getAbsolutePath(), taskFlag);
                } else {
                    //下载失败超过3次，不再重试下载
                }
            } else {
                //TODO 校验其他类型补丁文件
            }
        }
    }

    /**
     * 判断本地已下载的补丁文件是否被修改
     *
     * @param hotPatchBean 本地保存的热更新补丁文件的数据对象
     * @return 补丁文件的MD5
     * @throws Exception
     */
    private String checkHotPatchFileMd5(HotPatchBean hotPatchBean) throws Exception {
        String fileName = this.getLocalPatchFileName(hotPatchBean.getUrl());//根据url获取资源的文件名称
        File hotFixFile = new File(getDir(), fileName);//获取补丁文件
        if (hotFixFile.exists()) {//补丁文件存在
            //            String md5 = getLocalFileMd5(hotFixFile.getAbsolutePath());
            byte[] data = this.getLocalFileContent(hotFixFile.getAbsolutePath());
            String md5 = this.getContentMD5(data, -1);
            log(JSON.toJSONString(hotPatchBean));
            if (hotPatchBean.getMd5().equals(md5)) {//本地文件md5与服务器文件的MD5一致
                File outFile = null;
                int isChanged = 0;
                if (hotPatchBean.getMd6() != null) {
                    boolean isExists = WSApplication.getFixBugManage().checkPathFile(hotPatchBean.getMd6());
                    if (!isExists) {
                        outFile = new File(getDir(), fileName + "_source");
                        if (outFile.exists()) {
                            String ___md5 = this.getLocalFileMd5(outFile.getAbsolutePath(), -1);
                            if (___md5 != null && ___md5.equals(hotPatchBean.getMd6())) {
                                //                                outFile.delete();
                                isChanged = 1;
                            } else {
                                outFile.delete();
                                isChanged = 0;
                            }
                        } else {
                            isChanged = 0;
                        }
                    } else {
                        isChanged = 2;
                    }
                } else {
                    isChanged = 0;
                }
                log("isChanged:" + isChanged);
                //                isChanged = 0;
                if (isChanged == 0) {//如果解密后的文件被修改重新解密加密文件
                    if (outFile == null) {
                        outFile = new File(getDir(), fileName + "_source");
                    }
                    String _md5 = this.decrtyPatchFile(data, outFile, hotPatchBean.getFile_size());
                    hotPatchBean.setMd6(_md5);//本地判断文件是否被篡改的数据
                    saveKeyValueString("AndroidHotFix_HotPatch", JSON.toJSONString(hotPatchBean));
                    WSApplication.getFixBugManage().addPatch(outFile.getAbsolutePath());//添加补丁文件
                    if (isShowToast == true)
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WSApplication.getFixBugManage().context, "补丁已加载0", Toast.LENGTH_LONG).show();
                            }
                        });
                } else if (isChanged == 1) {//如果解密后的文件被修改重新解密加密文件
                    if (outFile == null) {
                        outFile = new File(getDir(), fileName + "_source");
                    }
                    WSApplication.getFixBugManage().addPatch(outFile.getAbsolutePath());//添加补丁文件
                    if (isShowToast == true)
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WSApplication.getFixBugManage().context, "补丁已加载1", Toast.LENGTH_LONG).show();
                            }
                        });
                } else if (isChanged == 2) {
                    if (isShowToast == true)
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WSApplication.getFixBugManage().context, "补丁已存在2.....", Toast.LENGTH_LONG).show();
                            }
                        });
                }
                //                WSApplication.getFixBugManage().removeAllPatch();//清除所有的补丁文件
            } else {
                hotFixFile.delete();//MD5不同，删除该文件
            }
            data = null;
            return md5;
        } else {
            return null;
        }
    }

    /**
     * 判断刚下载的补丁文件是否完整
     *
     * @param taskFlag     请求网络的标识
     * @param hotPatchBean 热更新文件数据对象
     * @throws Exception
     */
    private void checkHotPatchFileMd5(int taskFlag, HotPatchBean hotPatchBean) throws Exception {
        String url = hotPatchBean.getUrl();
        String MD5 = hotPatchBean.getMd5();
        int file_size = hotPatchBean.getFile_size();

        String fileName = this.getLocalPatchFileName(url);//根据url获取资源的文件名称
        File hotFixFile = new File(getDir(), fileName);//获取补丁文件
        if (hotFixFile.exists()) {//补丁文件存在
            //            String md5 = getLocalFileMd5(hotFixFile.getAbsolutePath());
            byte[] data = this.getLocalFileContent(hotFixFile.getAbsolutePath());
            String md5 = this.getContentMD5(data, -1);
            if (MD5.equals(md5)) {//本地文件md5与服务器文件的MD5一致
                File outFile = new File(getDir(), fileName + "_source");
                if (outFile.exists()) {
                    outFile.delete();//删除补丁文件      TODO 如果文件MD5正确可以直接使用
                }
                String _md5 = this.decrtyPatchFile(data, outFile, file_size);//解密文件

                hotPatchBean.setMd6(_md5);//本地判断文件是否被篡改的数据
                saveKeyValueString("AndroidHotFix_HotPatch", JSON.toJSONString(hotPatchBean));
                WSApplication.getFixBugManage().addPatch(outFile.getAbsolutePath());//添加补丁文件
                if (isShowToast == true)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WSApplication.getFixBugManage().context, "补丁已加载", Toast.LENGTH_LONG).show();
                        }
                    });
            } else {
                //TODO 重新下载，最多重试二次
                this.downloadPatchFile(url, hotFixFile.getAbsolutePath(), taskFlag);
            }
            data = null;
        } else {
            //TODO 重新下载，最多重试二次
            this.downloadPatchFile(url, hotFixFile.getAbsolutePath(), taskFlag);
        }
    }

    /**
     * 添加此设备到服务器黑名单
     */
    public void addMobileToServerBlackList() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(Urls.SERVICE_URLADSS_HOTUPDATE + Urls.HOTPATCH_BLACKLIST);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setUseCaches(false);//禁止使用用户缓存
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //                    httpURLConnection.setDoInput(true);
                    HotPatchBlackListRequest hotPatchBlackListRequest = new HotPatchBlackListRequest();
                    hotPatchBlackListRequest.setOs_type(1);
                    hotPatchBlackListRequest.setFamily_id(31);
                    hotPatchBlackListRequest.setVer_num(mAppInfo.getVersionName());//WNUtils.getVersionName(context)//app版本号
                    hotPatchBlackListRequest.setType(3);
                    hotPatchBlackListRequest.setDevice_name(android.os.Build.MANUFACTURER + ":" + android.os.Build.MODEL);//设备名称+设备型号
                    //发送Post Body数据
                    String requestData = JSON.toJSONString(hotPatchBlackListRequest);
                    requestData = "param=" + requestData;
                    log(requestData);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(requestData.getBytes(), 0, requestData.getBytes().length);
                    outputStream.flush();
                    outputStream.close();

                    int code = httpURLConnection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        byte[] data = readFile(inputStream);
                        httpURLConnection.disconnect();
                        if (data != null) {
                            log(new String(data));
                            //                            String result = new String(data);
                            hotPatchResponse = JSON.parseObject(data, HotPatchResponse.class);
                            if (hotPatchResponse != null && hotPatchResponse.getCode() == 1 && hotPatchResponse.getBody() != null) {
                                deleteKeyValue("hotpatch_crash");
                                if (isShowToast == true)
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(WSApplication.getFixBugManage().context, hotPatchResponse.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                            } else {
                                //不做任何处理
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 删除热修复文件
     */
    public void deleteHotFixPatch() {
        try {
            String AndroidHotFix_HotPatch = getKeyValueString("AndroidHotFix_HotPatch");
            if (AndroidHotFix_HotPatch != null) {
                HotPatchBean hotPatchBean = JSON.parseObject(AndroidHotFix_HotPatch, HotPatchBean.class);
                if (hotPatchBean != null) {
                    String fileName = getLocalPatchFileName(hotPatchBean.getUrl());//根据url获取资源的文件名称
                    File hotFixFile = new File(getDir(), fileName);//获取补丁文件
                    if (hotFixFile.exists()) {
                        hotFixFile.delete();
                    }
                    File outFile = new File(getDir(), fileName + "_source");
                    if (outFile.exists()) {
                        outFile.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密数据到指定的目录文件中
     *
     * @param data      数据
     * @param out       输出文件
     * @param file_size 数据内容长度
     * @return 解密后文件的MD5
     */
    private String decrtyPatchFile(byte[] data, File out, int file_size) {
        try {
            byte[] content = this.decrty(data);
            this.writePatchFile(content, out, file_size);
            String md5 = this.getContentMD5(content, file_size);
            return md5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密数据
     *
     * @param data 数据
     * @return byte数据
     */
    private byte[] decrty(byte[] data) {
        byte[] content = new AESEncryptSafe("weizhang").decrypt2(new String(data));
        return content;
    }

    /**
     * 写数据到指定文件和指定长度
     *
     * @param content   数据
     * @param out       输出文件
     * @param file_size 数据长度
     */
    private void writePatchFile(byte[] content, File out, int file_size) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(out);
            fileOutputStream.write(content, 0, file_size);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url获取文件名称
     *
     * @param url url内容
     * @return 文件名称
     */
    private String getLocalPatchFileName(String url) {
        url = url.substring(url.lastIndexOf("/") + 1);
        return url;
    }

    /**
     * 根据输入流读取流内容
     *
     * @param inputStream 输入流
     * @return byte 数据
     */
    private byte[] readFile(InputStream inputStream) {
        try {
            byte[] buffer = new byte[1024];
            int len;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            while ((len = inputStream.read(buffer, 0, 1024)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            inputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据路径获取文件的内容
     *
     * @param filePath 文件路径
     * @return byte 数据
     */
    private byte[] getLocalFileContent(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            return this.readFile(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据内容计算出MD5
     *
     * @param data   数据
     * @param length 内容长度
     * @return MD5
     */
    private String getContentMD5(byte[] data, int length) {
        try {
            if (data == null) {
                return null;
            }
            if (length == -1) {
                length = data.length;
            }
            MessageDigest digests = MessageDigest.getInstance("MD5");
            digests.update(data, 0, length);
            BigInteger bi = new BigInteger(1, digests.digest());
            String result = bi.toString(16);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件的MD5值
     *
     * @param filePath  文件路径
     * @param file_size 内容长度
     * @return MD5
     */
    private String getLocalFileMd5(String filePath, int file_size) {
        byte[] data = this.getLocalFileContent(filePath);

        if (data == null) {
            return null;
        }
        return this.getContentMD5(data, file_size);
    }

    /**
     * 保存PatchFile相关联的本地数据
     *
     * @param key
     * @param value
     */
    public void saveKeyValueString(String key, String value) {
        this.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    /**
     * 保存PatchFile相关联的本地数据
     *
     * @param key
     * @param value
     */
    public void saveKeyValueInt(String key, int value) {
        this.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    public String getKeyValueString(String key) {
        return this.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getString(key, null);
    }

    public int getKeyValueInt(String key) {
        return this.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getInt(key, -1);
    }

    public void deleteKeyValue(String key) {
        this.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().remove(key).apply();
    }

    public void clearKeyValue() {
        this.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().clear().apply();
    }
}