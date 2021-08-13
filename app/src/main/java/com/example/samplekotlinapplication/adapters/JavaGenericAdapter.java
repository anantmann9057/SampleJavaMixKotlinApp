package com.example.samplekotlinapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.samplekotlinapplication.BR;
import com.example.samplekotlinapplication.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JavaGenericAdapter<T> extends RecyclerView.Adapter<JavaGenericAdapter.MyViewHolder> {

    public List<T> getmObjects() {
        return mObjects;
    }
    int layoutValue;
    private List<T> mObjects;
    private JavaGenericAdapter.OnItemClickListener<T> onItemClickListener;
    private TextView emptyTextView;
    private int emptyViewText = R.string.app_name;
    private Fragment fragment;
    private boolean isUserPhotos;

    public void setEmptyTextView(TextView emptyTextView, @StringRes int emptyViewText) {
        this.emptyTextView = emptyTextView;
        this.emptyViewText = emptyViewText;
    }


    public void setUserPhotos(boolean isUserPhotos) {
        this.isUserPhotos = isUserPhotos;
    }

    public void notiftAdapter(List<T> mObjects){
        this.mObjects = mObjects;
        notifyDataSetChanged();

    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T object);
    }

    public JavaGenericAdapter(final List<T> objects) {
        this(objects, null,0);
    }

    public JavaGenericAdapter(final List<T> objects, JavaGenericAdapter.OnItemClickListener onItemClickListener,int layoutValue) {
        mObjects = objects;
        this.onItemClickListener = onItemClickListener;
        this.layoutValue=layoutValue;
    }

    @Override
    public JavaGenericAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(JavaGenericAdapter.MyViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.model, getItem(position));
        holder.getBinding().setVariable(BR.itemClickListener, onItemClickListener);
        holder.getBinding().setVariable(BR.index, ( position + 1));
    }

    @Override
    public int getItemViewType(int position) {
        return layoutValue;
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(final T object) {
        mObjects.add(object);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(final List<T> objects) {
        int posStart = mObjects.size();
        mObjects.addAll(objects);
        notifyItemRangeInserted(posStart, getItemCount() - 1);
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        final int size = getItemCount();
        mObjects.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }


    public T getItem(final int position) {
        return mObjects.get(position);
    }

    public long getItemId(final int position) {
        return position;
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(final T item) {
        return mObjects.indexOf(item);
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(final T object, int index) {
        mObjects.add(index, object);
        notifyItemInserted(index);
    }


    public void insert(final List<T> objects, int index) {
        int posStart = mObjects.size();
        mObjects.addAll(index, objects);
        notifyItemRangeInserted(posStart, getItemCount() - 1);
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(T object) {
        final int position = getPosition(object);
        mObjects.remove(object);
        notifyItemRemoved(position);
    }


    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        Collections.sort(mObjects, comparator);
        notifyItemRangeChanged(0, getItemCount());
    }


    public void addItem(int position, T model) {
        mObjects.add(position, model);
        notifyItemInserted(position);
    }

    public void setItem(int position, T model) {
        mObjects.set(position, model);
        notifyItemChanged(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final T model = mObjects.remove(fromPosition);
        mObjects.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void update(List<T> models, boolean replaceExisting) {
        /*removal of objects creates problem with pagination logic, so this is skipped.*/
//        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models, replaceExisting);
        applyAndAnimateMovedItems(models);

        //Collections.sort(models);
    }

    private void applyAndAnimateRemovals(List<T> newModels) {
        for (T mObject : mObjects) {
            if (!newModels.contains(mObject)) {
                remove(mObject);
            }
        }
    }

    private void applyAndAnimateAdditions(List<T> newModels, boolean replaceExisting) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final T model = newModels.get(i);

            int index = mObjects.indexOf(model);
            if (!mObjects.contains(model)) {
                addItem(i, model);
            } else {
                if (replaceExisting) {
                    setItem(index, model);
                }
            }
        }
    }

    private void applyAndAnimateMovedItems(List<T> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final T model = newModels.get(toPosition);
            final int fromPosition = mObjects.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.executePendingBindings();
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }


}

