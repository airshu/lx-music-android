package com.shjlone.lxmusic.ui.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.exoplayer2.ui.PlayerControlView

/**
 *
 *
 * @author: a564
 * @Date 2021/5/26
 */

class CustomPlayerControllerVIew @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : PlayerControlView(context, attrs, defStyleAttr) {


    //原视图逻辑会隐藏，改成一直显示
    override fun hide() {

    }
}