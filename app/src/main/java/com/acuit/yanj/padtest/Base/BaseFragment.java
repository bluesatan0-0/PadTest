package com.acuit.yanj.padtest.Base;

import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

/**
 * 类名: BaseFragment <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/13 11:38 <p>
 * 描述: Fragment的基类，用于添加Fragment中的通用方法
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class BaseFragment extends Fragment {

    // TODO: 2017/7/12 BaseFragment待补充

    @Override
    public void onDestroy() {
        super.onDestroy();

//        LeakCanary内存泄漏监控
        RefWatcher refWatcher = BaseApplication.getRefWatcher();
        refWatcher.watch(this);

    }
}
