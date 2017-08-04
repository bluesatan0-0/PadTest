package com.zjxl.yanj.padtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zjxl.yanj.padtest.Bean.Hole;
import com.zjxl.yanj.padtest.Bean.Line;
import com.zjxl.yanj.padtest.R;

import java.util.List;

/**
 * 类名: HolesAdapter_Settings <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:16 <p>
 * 描述: 设置界面——设备列表，适配器
 * <p>
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class HolesAdapter_Settings extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private List<Hole> dataList;
    private List<Line> linesList;
    private Line line;

    public HolesAdapter_Settings(Context context, List<Hole> holes, List<Line> linesList) {
        this.context = context;
        this.dataList = holes;
        this.linesList = linesList;
//        headerView对应数据
    }

    @Override
    public ViewHolder_HolesAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_hole_settings, parent, false);
        return new ViewHolder_HolesAdapter(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Hole hole = dataList.get(position);
        int lineId = hole.getLineId();
        String lineName = null;

        if (null != line && lineId == line.getId()) {
            lineName = line.getName();
        } else {
            for (Line line : linesList) {
                if (lineId == line.getId()) {
                    this.line = line;
                    lineName = line.getName();
                }
            }
        }
//        获取lineName👆

        String holeStatu = null;
        switch (hole.getStatu()) {
            case -2:
                holeStatu = "离线";
                break;
            case 0:
                holeStatu = "离线";
                break;
            case 1:
                holeStatu = "在线";
                break;
            case -4:
                holeStatu = "未保活";
                break;
        }
//        获取holeStatu👆

        ViewHolder_HolesAdapter holder_HolesAdapter = (ViewHolder_HolesAdapter) holder;

        holder_HolesAdapter.getTvHoleName().setText(hole.getName());
        holder_HolesAdapter.getTvLineName().setText(lineName);
        holder_HolesAdapter.getTvHoleCode().setText(hole.getUuid());
        holder_HolesAdapter.getTvHoleStatu().setText(holeStatu);

        holder_HolesAdapter.getBtnDeviceEdit().setOnClickListener(this);
        holder_HolesAdapter.getBtnDeviceCheck().setOnClickListener(this);
        holder_HolesAdapter.getBtnDeviceDelete().setOnClickListener(this);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // TODO: 2017/7/27 设置点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_device_edit:

                break;
            case R.id.btn_device_check:

                break;
            case R.id.btn_device_delete:

                break;
        }
    }


    class ViewHolder_HolesAdapter extends RecyclerView.ViewHolder {

        private TextView tvHoleName;
        private TextView tvLineName;
        private TextView tvHoleCode;
        private TextView tvHoleStatu;
        private Button btnDeviceEdit;
        private Button btnDeviceCheck;
        private Button btnDeviceDelete;
        private int itemPosition;

        public ViewHolder_HolesAdapter(View itemView) {
            super(itemView);

            tvHoleName = (TextView) itemView.findViewById(R.id.tv_hole_name);
            tvLineName = (TextView) itemView.findViewById(R.id.tv_line_name);
            tvHoleCode = (TextView) itemView.findViewById(R.id.tv_hole_code);
            tvHoleStatu = (TextView) itemView.findViewById(R.id.tv_hole_statu);
            btnDeviceEdit = (Button) itemView.findViewById(R.id.btn_device_edit);
            btnDeviceCheck = (Button) itemView.findViewById(R.id.btn_device_check);
            btnDeviceDelete = (Button) itemView.findViewById(R.id.btn_device_delete);

        }

        public void setItemPosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        public int getItemPosition() {
            return itemPosition;
        }

        public TextView getTvHoleName() {
            return tvHoleName;
        }

        public TextView getTvLineName() {
            return tvLineName;
        }

        public TextView getTvHoleCode() {
            return tvHoleCode;
        }

        public TextView getTvHoleStatu() {
            return tvHoleStatu;
        }

        public Button getBtnDeviceEdit() {
            return btnDeviceEdit;
        }

        public Button getBtnDeviceCheck() {
            return btnDeviceCheck;
        }

        public Button getBtnDeviceDelete() {
            return btnDeviceDelete;
        }
    }

}
