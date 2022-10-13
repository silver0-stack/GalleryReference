package com.android.handstudio.gallery.adapter

import com.android.handstudio.gallery.MainActivity
import com.android.handstudio.gallery.vo.PhotoVO
import androidx.recyclerview.widget.RecyclerView
import com.android.handstudio.gallery.adapter.GalleryAdapter.PhotoViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.widget.RelativeLayout
import com.android.handstudio.gallery.R
import com.android.handstudio.gallery.listener.OnItemClickListener
import java.util.ArrayList

/**
 * Created by woong on 2015. 10. 20..
 */
class GalleryAdapter
/**
 * 생성자
 * @param activity
 * @param photoList
 * @param itemLayout
 */(
    private val mActivity: MainActivity,
    private val mPhotoList: List<PhotoVO>,
    private val itemLayout: Int
) : RecyclerView.Adapter<PhotoViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    /**
     * PhotoList 반환
     * @return
     */
    fun getmPhotoList(): List<PhotoVO> {
        return mPhotoList
    }

    /**
     * 선택된 PhotoList 반환
     * @return
     */
    val selectedPhotoList: List<PhotoVO>
        get() {
            val mSelectPhotoList: MutableList<PhotoVO> = ArrayList()
            for (i in mPhotoList.indices) {
                val photoVO = mPhotoList[i]
                if (photoVO.isSelected) {
                    mSelectPhotoList.add(photoVO)
                }
            }
            return mSelectPhotoList
        }

    /**
     * 아이템 선택시 호출되는 리스너
     * @param onItemClickListener
     */
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    /**
     * 레이아웃을 만들어서 Holer에 저장
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            itemLayout, viewGroup, false
        )
        return PhotoViewHolder(view)
    }

    /**
     * listView getView 를 대체
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     * @param viewHolder
     * @param position
     */
    override fun onBindViewHolder(
        viewHolder: PhotoViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val photoVO = mPhotoList[position]
        Glide.with(mActivity)
            .load(photoVO.imgPath)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(viewHolder.imgPhoto)

        //선택
        if (photoVO.isSelected) {
            viewHolder.layoutSelect.visibility = View.VISIBLE
        } else {
            viewHolder.layoutSelect.visibility = View.INVISIBLE
        }
        viewHolder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.OnItemClick(viewHolder, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return mPhotoList.size
    }

    /**
     * 뷰 재활용을 위한 viewHolder
     */
    class PhotoViewHolder(itemView: View) : ViewHolder(itemView) {
        var imgPhoto: ImageView
        var layoutSelect: RelativeLayout

        init {
            imgPhoto = itemView.findViewById<View>(R.id.imgPhoto) as ImageView
            layoutSelect = itemView.findViewById<View>(R.id.layoutSelect) as RelativeLayout
        }
    }
}