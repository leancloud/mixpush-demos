package cn.leancloud.demo.oppopush;

import com.avos.avoscloud.AVOPPOPushAdapter;
import com.coloros.mcssdk.mode.SubscribeResult;

import java.util.List;

public class MyPushAdapter extends AVOPPOPushAdapter {
  @Override
  public void onGetAliases(int var1, List<SubscribeResult> var2) {
  }

  @Override
  public void onSetAliases(int var1, List<SubscribeResult> var2) {
  }

  @Override
  public void onUnsetAliases(int var1, List<SubscribeResult> var2) {
  }

  @Override
  public void onSetUserAccounts(int var1, List<SubscribeResult> var2) {
  }

  @Override
  public void onUnsetUserAccounts(int var1, List<SubscribeResult> var2) {
  }

  @Override
  public void onGetUserAccounts(int var1, List<SubscribeResult> var2) {
  }

  @Override
  public void onSetTags(int var1, List<SubscribeResult> var2) {
  }

  @Override
  public void onUnsetTags(int var1, List<SubscribeResult> var2) {
  }

  @Override
  public void onGetTags(int var1, List<SubscribeResult> var2) {
  }

  @Override
  public void onGetPushStatus(int var1, int var2) {
  }

  @Override
  public void onGetNotificationStatus(int var1, int var2) {
  }

  @Override
  public void onSetPushTime(int var1, String var2) {
  }
}
