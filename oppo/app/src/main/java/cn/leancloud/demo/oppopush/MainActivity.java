package cn.leancloud.demo.oppopush;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.IGetAppNotificationCallBackService;
import com.heytap.msp.push.callback.ISetAppNotificationCallBackService;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.leancloud.LCInstallation;
import cn.leancloud.LCPush;
import cn.leancloud.LCQuery;
import cn.leancloud.json.JSONObject;
import cn.leancloud.oppo.LCMixPushManager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity
        implements PushConfigFragment.OnFragmentInteractionListener,
        PushInfoFragment.OnFragmentInteractionListener,
        UserBandingFragment.OnFragmentInteractionListener,
        TestModeUtil.UpdateTestMode{

  /**
   * 显示的log格式
   */
  private static final String LOG_FORMAT = "time ->[tag] msg";
  private static final String LOG_FORMAT_NO_TAG = "time -> msg";
  private static final int MAX_LOG_SIZE = 3000;
  /**
   * log中的时间格式
   */
  private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS", Locale.CHINA);
  /**
   * log信息显示到界面上，方便调试
   */
  private TextView logInfoTextView;
  private ScrollView mScrollView;
  /**
   * 用户操作和反馈信息，显示到界面上方便调试
   */
  private List<String> logs = new CopyOnWriteArrayList<>();

  private Fragment baseInfoFragment;
  private Fragment pushConfigFragment;
  private Fragment userBandingFragment;
  private Fragment[] fragments;
  private int lastShowFragment = 0;

  private IGetAppNotificationCallBackService getAppNotificationCallBackService = new IGetAppNotificationCallBackService() {
    @Override
    public void onGetAppNotificationSwitch(int i, int i1) {
      showResult("获取应用开关状态", "code=" + i + ",status=" + i1);
    }
  };
  private ISetAppNotificationCallBackService setAppNotificationCallBackService = new ISetAppNotificationCallBackService() {
    @Override
    public void onSetAppNotificationSwitch(int i) {
      showResult("设置应用开关", "code=" + i);
    }
  };
  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      int prevIndex = lastShowFragment;
      lastShowFragment = item.getOrder();
      switch (item.getItemId()) {
        case R.id.navigation_home:
          lastShowFragment = 0;
          break;
        case R.id.navigation_dashboard:
          lastShowFragment = 2;
          break;
        default:
          lastShowFragment = 1;
          break;
      }
      System.out.println("itemOrder:" + item.getOrder() + ", itemId:" + item.getItemId());
      switchFragment(prevIndex, lastShowFragment);
      return true;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //顶部的appid等应用信息
    TextView appInfo = (TextView) findViewById(R.id.tv_app_info);
    String info = "AppKey :\t" + MyApplication.OPPO_APPKEY;
    appInfo.setText(info);
    logInfoTextView = (TextView) findViewById(R.id.tv_log_msg);
    mScrollView = (ScrollView) findViewById(R.id.scrollView);

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    baseInfoFragment = new PushInfoFragment();
    pushConfigFragment = new PushConfigFragment();
    userBandingFragment = new UserBandingFragment();
    fragments = new Fragment[]{baseInfoFragment, pushConfigFragment, userBandingFragment};
    getSupportFragmentManager().beginTransaction().add(R.id.content, baseInfoFragment).show(baseInfoFragment).commit();

    initData();
    createChannelWithNoRedBadge();
    createChannelWithRedBadge();
  }

  private void initData() {
    Handler handler = new Handler(Looper.getMainLooper());
    //初始化push，调用注册接口
    addLog("appKey", MyApplication.OPPO_APPKEY);
    addLog("appSecret", MyApplication.OPPO_APPSECRET);
    addLog("pkgName", getPackageName());
    addLog("", "");
    TestModeUtil.setUpdateTestMode(this);
    onLogUpdate(TestModeUtil.TYPE_LOG);
    onLogUpdate(TestModeUtil.TYPE_STATUS);
    addLog("初始化注册", "调用register接口");
    HeytapPushManager.requestNotificationPermission();
  }

  @Override
  public void onLogUpdate(int type) {
    if (type == TestModeUtil.TYPE_LOG) {
      logInfoTextView.post(new Runnable() {
        @Override
        public void run() {
          String info = TestModeUtil.getLastLog();
          if (info == null) return;
          while (info != null) {
            logInfoTextView.append(info + "\n");
            info = TestModeUtil.getLastLog();
          }
          //滚动log到底部
          mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
      });
    } else if (type == TestModeUtil.TYPE_STATUS) {
//      LogUtil.w("LOG TYPE_STATUS");
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    onLogUpdate(TestModeUtil.TYPE_LOG);
    onLogUpdate(TestModeUtil.TYPE_STATUS);
//    if (NotificationUtil.hasRedMessage(this)){
//      baseInfoFragment.setNumber(1);
//    } else {
//      baseInfoFragment.setNumber(0);
//    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_about:
        startActivity(new Intent(this, AboutActivity.class));
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  protected void addLog(String tag, String info) {
    TestModeUtil.addLogString(tag, info);
  }

  private void createChannelWithNoRedBadge() {
    NotificationManager notificationManager = NotificationUtil.getNotificationManager(this);
    if (notificationManager != null) {

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel("no_red_badge", "运营推广",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setShowBadge(false);
        notificationManager.createNotificationChannel(channel);

      }
    }
  }

  private void createChannelWithRedBadge() {
    NotificationManager notificationManager = NotificationUtil.getNotificationManager(this);
    if (notificationManager != null) {

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel("red_badge", "私信",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setShowBadge(true);
        notificationManager.createNotificationChannel(channel);
      }
    }
  }

  public void switchFragment(int lastIndex, int index) {
    System.out.println("lastIndex=" + lastIndex + ", newIndex=" + index);
    if (lastIndex == index) return;
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.hide(fragments[lastIndex]);
    if (!fragments[index].isAdded()) {
      transaction.add(R.id.content, fragments[index]);
    }
    transaction.show(fragments[index]).commitAllowingStateLoss();
  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }

  public void onButtonClick(View view) {
    if (view instanceof Button) {
      addLog("点击Button", ((Button) view).getText().toString());
    } else if (view instanceof TextView) {
      addLog("点击TextView", ((TextView) view).getText().toString());
    } else {
      addLog("点击控件", view.getClass().getSimpleName());
    }
    try {
      LCInstallation currentInstall = LCInstallation.getCurrentInstallation();
      LCQuery pushQuery = LCInstallation.getQuery()
              .whereEqualTo("installationId", currentInstall.getInstallationId());
      LCPush push = new LCPush();
      push.setQuery(pushQuery);
      push.setPushToAndroid(true);
      Map<String, Object> pushParams = new HashMap<>();

      switch (view.getId()) {
        // 常用操作 fragment
        case R.id.btn_switch_enable:
          LCMixPushManager.enableAppNotificationSwitch(setAppNotificationCallBackService);
          break;
        case R.id.btn_switch_disable:
          LCMixPushManager.disableAppNotificationSwitch(setAppNotificationCallBackService);
          break;
        case R.id.btn_switch_get:
          LCMixPushManager.getAppNotificationSwitch(getAppNotificationCallBackService);
          break;
        case R.id.btn_check_opush_support:
          boolean support = LCMixPushManager.isSupportOppoPush(this);
          addLog("support Oppo Push", String.valueOf(support));
          break;
        case R.id.btn_get_regid:
          String regId = LCMixPushManager.getRegisterID();
          addLog("Oppo regId", regId);
          break;
        case R.id.btn_open_notification_setting:
          HeytapPushManager.openNotificationSettings();
          break;
        case R.id.btn_get_push_service_status:
          LCMixPushManager.getOppoPushStatus();
          break;
        case R.id.btn_get_statusbar_status:
          LCMixPushManager.getOppoNotificationStatus();
          break;
        case R.id.btn_get_version_info:
          StringBuilder sb = new StringBuilder();
          sb.append("McsVerCode:").append(HeytapPushManager.getPushVersionCode());
          sb.append(",McsVerName:").append(HeytapPushManager.getPushVersionName());
          sb.append(",SdkVer:").append(HeytapPushManager.getSDKVersionCode());
          addLog("Version", sb.toString());
          break;

        case R.id.btn_sta1:
          push.setMessage("Test From LeanCloud");
          push.sendInBackground().subscribe(new Observer<JSONObject>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull JSONObject jsonObject) {
              addLog("SendPush", "Succeed");
            }

            @Override
            public void onError(@NonNull Throwable e) {
              e.printStackTrace();
              addLog("SendPush", "Failed");
            }

            @Override
            public void onComplete() {

            }
          });
          break;
        case R.id.btn_sta2:
          pushParams.put("key1", "value1");
          push.setData(pushParams);
          push.sendInBackground().subscribe(new Observer<JSONObject>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull JSONObject jsonObject) {
              addLog("SendPushWithTarget1", "Succeed");
            }

            @Override
            public void onError(@NonNull Throwable e) {
              e.printStackTrace();
              addLog("SendPushWithTarget1", "Failed");
            }

            @Override
            public void onComplete() {

            }
          });
          break;
        case R.id.btn_sta3:
          pushParams.put("key1", "value1");
          push.setData(pushParams);
          push.sendInBackground().subscribe(new Observer<JSONObject>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull JSONObject jsonObject) {
              addLog("SendPushWithTarget2", "Succeed");
            }

            @Override
            public void onError(@NonNull Throwable e) {
              e.printStackTrace();
              addLog("SendPushWithTarget2", "Failed");
            }

            @Override
            public void onComplete() {

            }
          });
          break;
        case R.id.btn_sta4:
          pushParams.put("key1", "value1");
          push.setData(pushParams);
          push.sendInBackground().subscribe(new Observer<JSONObject>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull JSONObject jsonObject) {
              addLog("SendPushWithURL", "Succeed");
            }

            @Override
            public void onError(@NonNull Throwable e) {
              e.printStackTrace();
              addLog("SendPushWithURL", "Failed");
            }

            @Override
            public void onComplete() {

            }
          });
          break;
        case R.id.red_badge:
          break;
        case R.id.no_red_badge:
          break;
        case R.id.bt_clear_notification:
          LCMixPushManager.clearNotifications();
          break;

        // 推送设置 fragment
        case R.id.btn_pause_push:
          LCMixPushManager.pauseOppoPush();
          break;
        case R.id.btn_resume_push:
          LCMixPushManager.resumeOppoPush();
          break;

        // 用户绑定 fragment
        case R.id.btn_set_alias:
          break;
        case R.id.btn_unset_alias:
          break;
        case R.id.btn_get_alias_list:
          break;
        case R.id.btn_set_topics:
          break;
        case R.id.btn_unset_topics:
          break;
        case R.id.btn_get_topic_list:
          break;
      }
    } catch (Exception ex) {
      showResult("Exception", ex.getLocalizedMessage());
      TestModeUtil.addLogString(ex.getLocalizedMessage());
    }
  }

  /**
   * 修改每周推送时间或者修改每天推送时间之后会触发此事件
   */
  public void onPushTimeConfigChange(@Nullable View view) {
    List<Integer> times = getPushTimesSet();//时间段要符合规则
    List<Integer> days = getPushWeekdays();//用1-7表示一周的7天
    if (days.isEmpty()) {
      showResult("Error", "推送时间设置不能为空！");
      return;
    }
    try {
      LCMixPushManager.setOppoPushTime(days, times.get(0), times.get(1), times.get(2), times.get(3));
//      ((PushConfigFragment) pushConfigFragment).onPushDaysChange();
    } catch (Exception e) {
      showResult("Error", e.getLocalizedMessage());
      TestModeUtil.addLogString(e.getLocalizedMessage());
    }
  }

  private void showResult(@Nullable String tag, @NonNull String msg) {
    addLog(tag, msg);
//    LogUtil.d(tag + ":" + msg);
  }

  /**
   * 编辑框中输入的别名或者标签列表设置，使用分号或者空格，逗号等隔开,从fragment中获取
   *
   * @return 至少包含一个参数，否则会报错
   */
  private List<String> getAliasOrTopicsList() {
    String content = ((UserBandingFragment) userBandingFragment).getInputText();
    List<String> list = Arrays.asList(content.split("[,;`~!?\\s.，。；？！·]"));
//    LogUtil.d("list:" + Arrays.toString(list.toArray()));
    return list;
  }

  /**
   * 每周可加收推送消息的时间,从fragment中获取
   */
  private List<Integer> getPushWeekdays() {
    return ((PushConfigFragment) pushConfigFragment).getPushWeekdays();
  }

  /**
   * 每天接受推送消息的时间,从fragment中获取
   */
  private List<Integer> getPushTimesSet() {
    return ((PushConfigFragment) pushConfigFragment).getPushTime();
  }
}
