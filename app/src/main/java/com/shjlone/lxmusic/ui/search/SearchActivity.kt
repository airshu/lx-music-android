package com.shjlone.lxmusic.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shjlone.lxmusic.R
import com.shjlone.lxmusic.databinding.ActivitySearchBinding
import com.shjlone.lxmusic.ui.base.BaseActivity

class SearchActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private var adapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ArrayAdapter.createFromResource(
            this,
            R.array.platforms_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }

        binding.spinner.onItemSelectedListener = this

        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        searchViewModel.songList.observe(this, Observer { it ->
            Log.d("####", "=-----$it -=-----")
            adapter = SearchAdapter(it)
            binding.list.adapter = adapter
        })
//        binding.searchView.isSubmitButtonEnabled = true
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧有叉叉 可以关闭搜索框
//        binding.searchView.isIconified = false
//设置搜索框直接展开显示。左侧有放大镜(在搜索框外) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
//        binding.searchView.setIconifiedByDefault(false)
//设置搜索框直接展开显示。左侧有无放大镜(在搜索框中) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
        binding.searchView.onActionViewExpanded()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "---> $query")
                searchViewModel.search(query ?: "", binding.spinner.selectedItemId.toInt())



                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        // 选中某个平台
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}