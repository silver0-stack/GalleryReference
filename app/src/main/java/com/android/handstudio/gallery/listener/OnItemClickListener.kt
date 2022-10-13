package com.android.handstudio.gallery.listener

import com.android.handstudio.gallery.adapter.GalleryAdapter.PhotoViewHolder

/**
 * Created by woong on 2015. 10. 20..
 */
interface OnItemClickListener {
    fun OnItemClick(photoViewHolder: PhotoViewHolder?, position: Int)
}