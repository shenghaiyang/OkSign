package com.shenghaiyang.oksign.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.shenghaiyang.oksign.R;

final class SignatureViewHolder extends ViewHolder {

  @BindView(R.id.signature_text) TextView signatureView;

  SignatureViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @OnClick(R.id.signature_share) void share() {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);
    intent.putExtra(Intent.EXTRA_TEXT, signatureView.getText());
    intent.setType("text/plain");

    signatureView.getContext().startActivity(Intent.createChooser(intent, "Share Signature"));
  }
}
