package com.shenghaiyang.oksign.library;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.shenghaiyang.oksign.R;

final class LibraryViewHolder extends ViewHolder {

  @BindView(R.id.library_name) TextView nameView;
  @BindView(R.id.library_license_name) TextView licenseNameView;
  @BindView(R.id.library_license_content) TextView licenseContentView;

  LibraryViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
