package cn.leancloud.demo.oppopush;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements PushConfigFragment.OnFragmentInteractionListener,
        PushInfoFragment.OnFragmentInteractionListener, UserBandingFragment.OnFragmentInteractionListener {

//  private TextView mTextMessage;

  private Fragment baseInfoFragment;
  private Fragment pushConfigFragment;
  private Fragment userBandingFragment;
  private Fragment[] fragments;
  private int lastShowFragment = 0;

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

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    baseInfoFragment = new PushInfoFragment();
    pushConfigFragment = new PushConfigFragment();
    userBandingFragment = new UserBandingFragment();
    fragments = new Fragment[]{baseInfoFragment, pushConfigFragment, userBandingFragment};
    getSupportFragmentManager().beginTransaction().add(R.id.content, baseInfoFragment).show(baseInfoFragment).commit();

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
}
