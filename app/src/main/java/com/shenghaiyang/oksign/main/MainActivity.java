package com.shenghaiyang.oksign.main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.shenghaiyang.oksign.R;
import com.shenghaiyang.oksign.about.AboutActivity;
import com.shenghaiyang.oksign.library.LibraryActivity;
import java.util.ArrayList;
import java.util.List;
import okio.ByteString;

public final class MainActivity extends AppCompatActivity {

  private static final String TAG_PACKAGE = "TAG_PACKAGE";
  private static final String TAG_SIGNATURES = "TAG_SIGNATURES";

  @BindView(R.id.main_package) EditText packageView;
  @BindView(R.id.main_signatures) RecyclerView signaturesView;

  private final ArrayList<String> signatures = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    ButterKnife.bind(this);
    if (savedInstanceState != null) {
      String packageName = savedInstanceState.getString(TAG_PACKAGE);
      if (packageName != null) {
        packageView.setText(packageName);
      }
      List<String> signs = savedInstanceState.getStringArrayList(TAG_SIGNATURES);
      if (signs != null) {
        signatures.clear();
        signatures.addAll(signs);
      }
    }
    signaturesView.setLayoutManager(new LinearLayoutManager(this));
    signaturesView.setAdapter(new SignatureAdapter(getLayoutInflater(), signatures));
  }

  @OnClick(R.id.main_find) void getSignature() {
    String packageName = packageView.getText().toString();
    if (packageName.trim().isEmpty()) {
      packageView.setError("Package cannot be empty.");
      return;
    }
    List<PackageInfo> list =
        getPackageManager().getInstalledPackages(PackageManager.GET_SIGNATURES);
    PackageInfo packageInfo = null;
    for (PackageInfo info : list) {
      if (info.packageName.equals(packageName)) {
        packageInfo = info;
        break;
      }
    }
    if (packageInfo == null) {
      packageView.setError("Cannot find package.");
      return;
    }
    Signature[] signs = packageInfo.signatures;
    signatures.clear();
    for (Signature sign : signs) {
      ByteString byteString = ByteString.of(sign.toByteArray());
      signatures.add(byteString.md5().hex());
    }
    signaturesView.getAdapter().notifyDataSetChanged();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_activity, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.main_about:
        startActivity(new Intent(this, AboutActivity.class));
        return true;
      case R.id.main_library:
        startActivity(new Intent(this, LibraryActivity.class));
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    String packageName = packageView.getText().toString().trim();
    if (!packageName.isEmpty()) {
      outState.putString(TAG_PACKAGE, packageName);
    }
    if (!signatures.isEmpty()) {
      outState.putStringArrayList(TAG_SIGNATURES, signatures);
    }
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    String packageName = savedInstanceState.getString(TAG_PACKAGE);
    if (packageName != null && !packageName.isEmpty()) {
      packageView.setText(packageName);
    }
  }
}
