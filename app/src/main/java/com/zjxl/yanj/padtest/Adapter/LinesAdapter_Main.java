package com.zjxl.yanj.padtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zjxl.yanj.padtest.Bean.Line;
import com.zjxl.yanj.padtest.R;

import java.util.List;

/**
 * 类名: LinesAdapter_Main <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:16 <p>
 * 描述: 设置界面——餐线列表，适配器
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class LinesAdapter_Main extends RecyclerView.Adapter {


    private Context context;
    private ItemClickListener itemClickListener;
    private List<Line> dataList;


    public LinesAdapter_Main(Context context, List<Line> lines) {
        this.context = context;
        this.dataList=lines;

    }

    @Override
    public ViewHolder_LinesAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_line_main, parent,false);

        if (null == itemClickListener) {
            System.out.println("aaa itemClickListener is null");
        }

        return new ViewHolder_LinesAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Line line = dataList.get(position);
        ViewHolder_LinesAdapter viewHolder = (ViewHolder_LinesAdapter) holder;

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    /**
     * item点击回调 接口
     */
    public interface ItemClickListener {
        void onBtnNameClick(String lineName);

    }

    /**
     * 外部设置 回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }


    class ViewHolder_LinesAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {


        private Button btn_lineName;
        private int itemPosition;
        private ItemClickListener mItemClickListener;

        public ViewHolder_LinesAdapter(View itemView, ItemClickListener listener) {
            super(itemView);

            btn_lineName = (Button) itemView.findViewById(R.id.btn_lineName);

            mItemClickListener = listener;

            btn_lineName.setOnClickListener(this);

        }

        // TODO: 2017/7/27 设置点击事件
        @Override
        public void onClick(View v) {

            View parent = (View) v.getParent();
            Button btnName = (Button) parent.findViewById(R.id.btn_lineName);
            String linesName = btnName.getText().toString();

            mItemClickListener.onBtnNameClick(linesName);
        }
    }


}
