package com.shenghaiyang.oksign.main;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.shenghaiyang.oksign.R;
import java.util.List;

final class SignatureAdapter extends Adapter<SignatureViewHolder> {

  private final List<String> signatures;
  private final LayoutInflater inflater;

  SignatureAdapter(LayoutInflater inflater, List<String> signatures) {
    this.inflater = inflater;
    this.signatures = signatures;
  }

  @Override public SignatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new SignatureViewHolder(inflater.inflate(R.layout.signature_item, parent, false));
  }

  @Override public void onBindViewHolder(SignatureViewHolder holder, int position) {
    holder.signatureView.setText(signatures.get(position));
  }

  @Override public int getItemCount() {
    return signatures.size();
  }
}
