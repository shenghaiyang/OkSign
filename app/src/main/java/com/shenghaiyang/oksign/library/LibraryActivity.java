package com.shenghaiyang.oksign.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.shenghaiyang.oksign.R;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import okio.Okio;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class LibraryActivity extends AppCompatActivity {

  @BindView(R.id.libraries) RecyclerView librariesView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.library_activity);
    ButterKnife.bind(this);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
    librariesView.setLayoutManager(new LinearLayoutManager(this));
    List<Library> libraries = new ArrayList<>();
    try {
      InputStream inputStream = getAssets().open("libraries.json");
      String json = Okio.buffer(Okio.source(inputStream)).readUtf8();
      JSONArray jsonArray = new JSONArray(json);
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        String name = jsonObject.getString("name");
        String url = jsonObject.getString("url");
        String licenseName = jsonObject.getString("licenseName");
        String licenseContent = jsonObject.getString("licenseContent");
        Library library = new Library(name, url, licenseName, licenseContent);
        libraries.add(library);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    librariesView.setAdapter(new LibraryAdapter(this, libraries));
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
