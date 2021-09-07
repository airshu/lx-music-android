package com.shjlone.lxmusic.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * 基类Framgment
 *
 *
 * @author: a564
 * @Date 2021/5/13
 *
 */
open class BaseFragment : Fragment() {
    protected val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
        initData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()

        removeListener()
    }

    /**
     * 数据初始化
     */
    open fun initData() {}

    /**
     * 视图初始化
     */
    open fun initView() {}

    /**
     * 监听器初始化
     */
    open fun initListener() {}

    /**
     * 监听器移除
     */
    open fun removeListener() {}
}