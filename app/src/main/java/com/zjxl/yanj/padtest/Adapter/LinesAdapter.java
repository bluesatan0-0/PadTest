package com.zjxl.yanj.padtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zjxl.yanj.padtest.Bean.Line;
import com.zjxl.yanj.padtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名: LinesAdapter <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:16 <p>
 * 描述: 设置界面——餐线列表，适配器
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class LinesAdapter extends RecyclerView.Adapter {


    private Context context;
    private ItemClickListener itemClickListener;
    private List<Line> dataList = new ArrayList<Line>();


    public LinesAdapter(Context context, List<Line> lines) {
        this.context = context;
        this.dataList.clear();
        this.dataList.addAll(lines);

    }

    @Override
    public ViewHolder_LinesAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_line, null);

        if (null==itemClickListener) {
            System.out.println("aaa itemClickListener is null");
        }

        return new ViewHolder_LinesAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Line line = dataList.get(position);
        ViewHolder_LinesAdapter viewHolder = (ViewHolder_LinesAdapter) holder;
        viewHolder.getBtn_lineName().setText(line.getName());
        viewHolder.setItemPosition(position);


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

        void onDeleteClick(String lineName);

        void onEditClick(String lineName);
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
        private ImageView iv_lineDelete;
        private ImageView iv_lineEdit;
        private int itemPosition;
        private ItemClickListener mItemClickListener;

        public ViewHolder_LinesAdapter(View itemView, ItemClickListener listener) {
            super(itemView);

            btn_lineName = (Button) itemView.findViewById(R.id.btn_lineName);
            iv_lineDelete = (ImageView) itemView.findViewById(R.id.iv_line_delete);
            iv_lineEdit = (ImageView) itemView.findViewById(R.id.iv_line_edit);

            mItemClickListener = listener;

            btn_lineName.setOnClickListener(this);
            iv_lineDelete.setOnClickListener(this);
            iv_lineEdit.setOnClickListener(this);

        }

        // TODO: 2017/7/27 设置点击事件
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_lineName:
                    Button btnName = (Button) v;
                    String linesName = btnName.getText().toString();
                    mItemClickListener.onBtnNameClick(linesName);
                    break;
                case R.id.iv_line_edit:
                    View parent = (View) v.getParent();
                    btnName = (Button) parent.findViewById(R.id.btn_lineName);
                    linesName = btnName.getText().toString();
                    mItemClickListener.onEditClick(linesName);
                    break;
                case R.id.iv_line_delete:
                    parent = (View) v.getParent();
                    btnName = (Button) parent.findViewById(R.id.btn_lineName);
                    linesName = btnName.getText().toString();
                    mItemClickListener.onDeleteClick(linesName);
                    break;
            }
        }

        public void setItemPosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        public int getItemPosition() {
            return itemPosition;
        }

        public Button getBtn_lineName() {
            return btn_lineName;
        }

        public ImageView getIv_lineEdit() {
            return iv_lineEdit;
        }

        public ImageView getIv_lineDelete() {
            return iv_lineDelete;
        }
    }


}
