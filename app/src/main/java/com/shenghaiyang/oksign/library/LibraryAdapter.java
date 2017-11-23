package com.shenghaiyang.oksign.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.shenghaiyang.oksign.R;
import java.util.List;

final class LibraryAdapter extends RecyclerView.Adapter<LibraryViewHolder> {

  private final LayoutInflater inflater;
  private final List<Library> libraries;

  LibraryAdapter(Context context, List<Library> libraries) {
    this.inflater = LayoutInflater.from(context);
    this.libraries = libraries;
  }

  @Override public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new LibraryViewHolder(inflater.inflate(R.layout.library_item, parent, false));
  }

  @Override public void onBindViewHolder(LibraryViewHolder holder, int position) {
    Library library = libraries.get(position);
    holder.nameView.setText(library.name);
    holder.licenseNameView.setText(library.licenseName);
    holder.licenseContentView.setText(library.licenseContent);
  }

  @Override public int getItemCount() {
    return libraries.size();
  }
}
