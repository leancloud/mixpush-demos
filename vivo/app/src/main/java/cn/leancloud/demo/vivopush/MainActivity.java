package cn.leancloud.demo.vivopush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;

import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.vivo.push.ups.CodeResult;
import com.vivo.push.ups.TokenResult;
import com.vivo.push.ups.UPSRegisterCallback;
import com.vivo.push.ups.UPSTurnCallback;
import com.vivo.push.ups.VUpsManager;
import com.vivo.push.util.VivoPushException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.leancloud.LCException;
import cn.leancloud.LCInstallation;
import cn.leancloud.LCPush;
import cn.leancloud.LCQuery;
import cn.leancloud.callback.LCCallback;
import cn.leancloud.demo.vivopush.widget.LogView;
import cn.leancloud.json.JSONObject;
import cn.leancloud.vivo.LCMixPushManager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "LeanCloud-VVPush";
  private LogView mLogView;
  private ScrollView mScrollView;
  private static final String APPID = "10000";
  private static final String API_KEY = "9c29fe5f-ea67-46d3-951f-81da78d2c029";
  private static final String APP_SECRET = "7265f2a4-ebbb-44bf-88b9-b03e67dfdc5b";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mScrollView = findViewById(R.id.scrollView);
    mLogView = findViewById(R.id.log_content);
    View root = findViewById(R.id.root);
    View logFrame = findViewById(R.id.log_frame);
    mLogView.setViews(root, logFrame, mScrollView);
    mLogView.invalidate();
    CheckBox logFilter = findViewById(R.id.log_filter);
    logFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        mLogView.filter(isChecked);
      }
    });
    try {
      PushClient.getInstance(MainActivity.this).initialize();
    } catch (VivoPushException e) {
      e.printStackTrace();
    }
    String regId = PushClient.getInstance(MainActivity.this).getRegId();
    Log.d(TAG, " regId= " + regId);
    Button bind = findViewById(R.id.bind);
    Button unbind = findViewById(R.id.unbind);
    Button getRegID = findViewById(R.id.getRegID);
    bind.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // 打开push开关, 关闭为turnOffPush，详见api接入文档
        LCMixPushManager.turnOnVIVOPush(new LCCallback<Boolean>() {
          @Override
          protected void internalDone0(Boolean aBoolean, LCException e) {
            String log1;
            if (null != e) {
              log1 = "turnOnPush failed. cause: " + e.getMessage();
            } else {
              log1 = "turnOnPush succeed. regId=" + LCMixPushManager.getRegistrationId(MainActivity.this);
            }
            Log.d(TAG, log1);
            updateDisplay(log1);
          }
        });
      }
    });
    unbind.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // 关闭push开关, 关闭为turnOffPush，详见api接入文档
        LCMixPushManager.turnOnVIVOPush(new LCCallback<Boolean>() {
          @Override
          protected void internalDone0(Boolean aBoolean, LCException e) {
            String log1;
            if (null != e) {
              log1 = "turnOffPush failed. cause: " + e.getMessage();
            } else {
              log1 = "turnOffPush succeed. regId=" + LCMixPushManager.getRegistrationId(MainActivity.this);
            }
            Log.d(TAG, log1);
            updateDisplay(log1);
          }
        });
      }
    });
    getRegID.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String regId = LCMixPushManager.getRegistrationId(MainActivity.this);
        Log.d(TAG, " regId= " + regId);
        String log = " regId= " + regId;
        updateDisplay(log);
      }
    });
    Button intent = findViewById(R.id.intent);
    intent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getIntentParma();
      }
    });
    Button checkManifest = findViewById(R.id.checkManifest);
    checkManifest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          PushClient.getInstance(MainActivity.this).checkManifest();
        } catch (VivoPushException e) {
          e.printStackTrace();
        }
      }
    });

    Button bindAlias = findViewById(R.id.bindAlias);
    bindAlias.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LCMixPushManager.bindVIVOAlias(MainActivity.this, TAG, new LCCallback<Boolean>() {
          @Override
          protected void internalDone0(Boolean aBoolean, LCException e) {
            String log1;
            if (null != e) {
              log1 = "bindAlias failed. cause: " + e.getMessage();
            } else {
              log1 = "bindAlias succeed. alias=" + TAG;
            }
            Log.d(TAG, log1);
            updateDisplay(log1);
          }
        });
      }
    });

    Button unBindAlias = findViewById(R.id.unBindAlias);
    unBindAlias.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LCMixPushManager.unbindVIVOAlias(MainActivity.this, TAG, new LCCallback<Boolean>() {
          @Override
          protected void internalDone0(Boolean aBoolean, LCException e) {
            String log1;
            if (null != e) {
              log1 = "unbindAlias failed. cause: " + e.getMessage();
            } else {
              log1 = "unbindAlias succeed. alias=" + TAG;
            }
            Log.d(TAG, log1);
            updateDisplay(log1);
          }
        });
      }
    });

    Button getAlias = findViewById(R.id.getAlias);
    getAlias.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String alias = LCMixPushManager.getVIVOAlias(MainActivity.this);
        Log.d(TAG, " getAlias= " + alias);
        String log = " getAlias= " + alias;
        updateDisplay(log);
      }
    });

    Button setTopic = findViewById(R.id.setTopic);
    setTopic.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LCMixPushManager.setVIVOTopic(MainActivity.this, TAG, new LCCallback<Boolean>() {
          @Override
          protected void internalDone0(Boolean aBoolean, LCException e) {
            String log1;
            if (null != e) {
              log1 = "setTopic failed. cause: " + e.getMessage();
            } else {
              log1 = "setTopic succeed. topic=" + TAG;
            }
            Log.d(TAG, log1);
            updateDisplay(log1);
          }
        });
      }
    });

    Button delTopic = findViewById(R.id.delTopic);
    delTopic.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LCMixPushManager.delVIVOTopic(MainActivity.this, TAG, new LCCallback<Boolean>() {
          @Override
          protected void internalDone0(Boolean aBoolean, LCException e) {
            String log1;
            if (null != e) {
              log1 = "delTopic failed. cause: " + e.getMessage();
            } else {
              log1 = "delTopic succeed. topic=" + TAG;
            }
            Log.d(TAG, log1);
            updateDisplay(log1);
          }
        });
      }
    });

    Button getTopics = findViewById(R.id.getTopics);
    getTopics.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        List<String> alias = LCMixPushManager.getVIVOTopics(MainActivity.this);
        Log.d(TAG, " getTopics size = " + alias.size() + " getTopics " + alias.toString());
        String log = " getTopics size = " + alias.size() + " getTopics " + alias.toString();
        updateDisplay(log);
      }
    });

    Button isSupport = findViewById(R.id.isSupport);
    isSupport.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        boolean issupport = LCMixPushManager.isSupportVIVOPush(MainActivity.this);
        Log.d(TAG, " issupport  = " + issupport);
        String log = " issupport  = " + issupport;
        updateDisplay(log);
      }
    });

    Button sendNotificationPush = findViewById(R.id.sendNotificationMsg);
    sendNotificationPush.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LCQuery query = LCInstallation.getQuery();
        query.whereEqualTo("installationId", LCInstallation.getCurrentInstallation().getInstallationId());
        LCPush push = new LCPush();
        push.setPushToAndroid(true);
        push.setQuery(query);
        push.setMessage("this is a test from LeanCloud.");
        push.setChannel("leancloud");
        Map<String, Object> params = new HashMap<>();
        params.put("occ", System.currentTimeMillis());
        params.put("other", "test mixpush");
        push.setData(params);
        push.sendInBackground().subscribe(new Observer<JSONObject>() {
          @Override
          public void onSubscribe(@NonNull Disposable d) {

          }

          @Override
          public void onNext(@NonNull JSONObject jsonObject) {
            String log = "succeed to send push request. result: " + jsonObject;
            updateDisplay(log);
          }

          @Override
          public void onError(@NonNull Throwable e) {
            String log = "failed to send push request. cause: " + e.getMessage();
            updateDisplay(log);
          }

          @Override
          public void onComplete() {

          }
        });
      }
    });

    Button sendTransmissionPush = findViewById(R.id.sendTransmissionMsg);
    sendTransmissionPush.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LCQuery query = LCInstallation.getQuery();
        query.whereEqualTo("installationId", LCInstallation.getCurrentInstallation().getInstallationId());
        LCPush push = new LCPush();
        push.setPushToAndroid(true);
        push.setQuery(query);
        push.setMessage("this is a test from LeanCloud.");
        push.setChannel("leancloud");
        Map<String, Object> params = new HashMap<>();
        params.put("occ", System.currentTimeMillis());
        params.put("other", "test mixpush");
        push.setData(params);
        push.sendInBackground().subscribe(new Observer<JSONObject>() {
          @Override
          public void onSubscribe(@NonNull Disposable d) {

          }

          @Override
          public void onNext(@NonNull JSONObject jsonObject) {
            String log = "succeed to send push request. result: " + jsonObject;
            updateDisplay(log);
          }

          @Override
          public void onError(@NonNull Throwable e) {
            String log = "failed to send push request. cause: " + e.getMessage();
            updateDisplay(log);
          }

          @Override
          public void onComplete() {

          }
        });
      }
    });

    Button sendSpecificTargetPush = findViewById(R.id.sendSpecificTargetMsg);
    sendSpecificTargetPush.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LCQuery query = LCInstallation.getQuery();
        query.whereEqualTo("installationId", LCInstallation.getCurrentInstallation().getInstallationId());
        LCPush push = new LCPush();
        push.setPushToAndroid(true);
        push.setQuery(query);
        push.setMessage("this is a test from LeanCloud.");
        push.setChannel("leancloud");
        Map<String, Object> params = new HashMap<>();
        params.put("occ", System.currentTimeMillis());
        params.put("other", "test mixpush");
        push.setData(params);
        push.sendInBackground().subscribe(new Observer<JSONObject>() {
          @Override
          public void onSubscribe(@NonNull Disposable d) {

          }

          @Override
          public void onNext(@NonNull JSONObject jsonObject) {
            String log = "succeed to send push request. result: " + jsonObject;
            updateDisplay(log);
          }

          @Override
          public void onError(@NonNull Throwable e) {
            String log = "failed to send push request. cause: " + e.getMessage();
            updateDisplay(log);
          }

          @Override
          public void onComplete() {

          }
        });
      }
    });
  }

  private void getIntentParma() {
    //        在Android 开发工具中，参考如下代码生成 Intent
    android.content.Intent intent = new Intent(this, CustomActivity.class);
    //Scheme协议（vpushscheme://com.vivo.push.notifysdk/detail?）开发者可以自定义
    intent.setData(Uri.parse("vpushscheme://com.vivo.pushtest/detail?"));
    //intent 中添加自定义键值对，value 为 String 型
    intent.putExtra("key1", "xxx");
    //intent 中添加自定义键值对，value 为 Integer 型
    intent.putExtra("key2", 2);
    //得到intent url 值
    String intentUri = intent.toUri(Intent.URI_INTENT_SCHEME);


    String log = " intentUri= " + intentUri;
    Log.d(TAG, " intentUri= " + intentUri);
    updateDisplay(log);
  }

  public void onStateChanged(int i) {
    Log.d(TAG, " onStateChanged= " + i);
    String regId = PushClient.getInstance(MainActivity.this).getRegId();
    Log.d(TAG, " regId= " + regId);
  }

  public void clearLog(View v) {

    if (mLogView != null) {
      mLogView.clear();
    }
  }

  public void seperatLog(View v) {

    if (mLogView != null) {
      mLogView.seperate();
    }
  }

  public static final int LEVEL_INFO = 0;
  public static final int LEVEL_WARN = LEVEL_INFO + 1;

  public void updateDisplay(final String logMsg) {
    updateDisplay(logMsg, LEVEL_WARN, false);
  }

  // 更新界面显示内容
  public void updateDisplay(final String logMsg, final int logLeve, final boolean serverLog) {

    if (mLogView != null) {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          mLogView.setText(logMsg, logLeve, serverLog);
          Log.d(TAG, "" + logMsg);
        }
      });
    }
  }
}
