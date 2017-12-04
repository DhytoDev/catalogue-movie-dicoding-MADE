package com.dhytodev.mybasemvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhytodev.mybasemvp.listener.RecyclerViewItemClickListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by izadalab on 03/12/17.
 */

public abstract class BaseRvAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private int layout;
    private Context context;
    private Class<VH> viewHolder;
    private List<T> data;
    private RecyclerViewItemClickListener recyclerViewItemClickListener ;

    public BaseRvAdapter(int layout, Class<VH> viewHolder, List<T> data) {
        this.layout = layout;
        this.viewHolder = viewHolder;
        this.data = data;
    }

    public BaseRvAdapter(int layout, Class<VH> viewHolder, List<T> data, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.layout = layout;
        this.viewHolder = viewHolder;
        this.data = data;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(layout, parent, false);

        try {
            Constructor<VH> constructor = viewHolder.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        T model = data.get(position);
        bindView(holder, model, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getAdapterPosition() ;
                recyclerViewItemClickListener.onItemClick(itemPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    abstract protected void bindView(VH holder, T model, int position);
}
