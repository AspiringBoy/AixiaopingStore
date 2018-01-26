package com.axp.axpseller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.axp.axpseller.managers.DownloadManager;
import com.axp.axpseller.managers.DownloadTask;
import com.axp.axpseller.managers.DownloadTaskListener;
import com.axp.axpseller.utils.NetworkUtils;

import java.io.File;

/**
 * Created by Dong on 2016/7/29.
 */
public class UpdateService extends Service implements DownloadTaskListener {
    private static String AIXIAOPING = "aixiaoping";
    DownloadManager downloadManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null|| intent.getExtras() == null){
            return super.onStartCommand(intent, flags, startId);
        }
        String url = intent.getStringExtra(AIXIAOPING);

        //下载
        downloadApk(url);
        return super.onStartCommand(intent, flags, startId);
    }

    private void downloadApk(String url){
        downloadManager = DownloadManager.getInstance();
        DownloadTask task = new DownloadTask.Builder().setId(AIXIAOPING).setUrl(url).setListener(UpdateService.this).build();
        downloadManager.addDownloadTask(task);
       if(!NetworkUtils.isConnected(UpdateService.this)){
           downloadManager.pause(AIXIAOPING);
       }else{
           downloadManager.resume(AIXIAOPING);
       }
    }

    @Override
    public void onDownloading(DownloadTask downloadTask, long completedSize, long totalSize, String percent) {
      //正在下载
    }

    @Override
    public void onPause(DownloadTask downloadTask, long completedSize, long totalSize, String percent) {
      //暂停
    }

    @Override
    public void onCancel(DownloadTask downloadTask) {
      //关闭

    }

    @Override
    public void onDownloadSuccess(DownloadTask downloadTask, File file) {

    }

    @Override
    public void onError(DownloadTask downloadTask, int errorCode) {

    }
}
