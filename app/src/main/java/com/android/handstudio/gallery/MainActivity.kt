package com.android.handstudio.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.handstudio.gallery.adapter.GalleryAdapter
import com.android.handstudio.gallery.divider.GridDividerDecoration
import com.android.handstudio.gallery.listener.OnItemClickListener
import com.android.handstudio.gallery.manager.GalleryManager
import com.android.handstudio.gallery.vo.PhotoVO

class MainActivity : AppCompatActivity() {
    private var mGalleryManager: GalleryManager? = null
    private var recyclerGallery: RecyclerView? = null
    private var galleryAdapter: GalleryAdapter? = null
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done -> selectDone()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 레이아웃 초기화
     */
    private fun initLayout() {
        recyclerGallery = findViewById(R.id.recyclerGallery)
    }

    /**
     * 데이터 초기화
     */
    private fun init() {

        //갤러리 리사이클러뷰 초기화
        initRecyclerGallery()
    }

    /**
     * 갤러리 아미지 데이터 초기화
     */
    private fun initGalleryPathList(): List<PhotoVO> {
        mGalleryManager = GalleryManager(applicationContext)
        //return mGalleryManager.getDatePhotoPathList(2015, 9, 19);
        return mGalleryManager!!.allPhotoPathList
    }

    /**
     * 확인 버튼 선택 시
     */
    private fun selectDone() {
        val selectedPhotoList: List<PhotoVO> = galleryAdapter!!.selectedPhotoList
        for (i in selectedPhotoList.indices) {
            Log.i("", ">>> selectedPhotoList   :  " + selectedPhotoList[i].imgPath)
        }
    }

    /**
     * 갤러리 리사이클러뷰 초기화
     */
    private fun initRecyclerGallery() {
        galleryAdapter =
            GalleryAdapter(this@MainActivity, initGalleryPathList(), R.layout.item_photo)
        galleryAdapter!!.setOnItemClickListener(mOnItemClickListener)
        recyclerGallery?.adapter = galleryAdapter
        recyclerGallery?.layoutManager = GridLayoutManager(this, 4)
        recyclerGallery?.itemAnimator = DefaultItemAnimator()
        recyclerGallery?.addItemDecoration(
            GridDividerDecoration(
                resources,
                R.drawable.divider_recycler_gallery
            )
        )
    }

    /**
     * 리사이클러뷰 아이템 선택시 호출 되는 리스너
     */
    private val mOnItemClickListener: OnItemClickListener = object : OnItemClickListener {
        @SuppressLint("NotifyDataSetChanged")
        override fun OnItemClick(photoViewHolder: GalleryAdapter.PhotoViewHolder?, position: Int) {
            val photoVO: PhotoVO = galleryAdapter?.getmPhotoList()!![position]
            photoVO.isSelected = !photoVO.isSelected
//            galleryAdapter!!.getmPhotoList().set(position, photoVO)
            galleryAdapter!!.notifyDataSetChanged()
        }
    }
}

//private fun Any.set(position: Int, photoVO: PhotoVO) {
//
//}
