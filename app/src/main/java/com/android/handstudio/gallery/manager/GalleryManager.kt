package com.android.handstudio.gallery.manager

import android.content.Context
import com.android.handstudio.gallery.vo.PhotoVO
import android.provider.MediaStore
import java.util.*

/**
 * Created by woong on 2015. 10. 20..
 */
class GalleryManager(private val mContext: Context) {
    /**
     * 갤러리 이미지 반환
     *
     * @return
     */
    val allPhotoPathList: List<PhotoVO>
        get() {
            val photoList = ArrayList<PhotoVO>()
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.DATE_ADDED
            )
            val cursor = mContext.contentResolver.query(uri, projection, null, null, null)
            val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            while (cursor.moveToNext()) {
                val photoVO = PhotoVO(cursor.getString(columnIndexData), false)
                photoList.add(photoVO)
            }
            cursor.close()
            return photoList
        }

    /**
     * 날짜별 갤러리 이미지 반환
     *
     * @return
     */
    fun getDatePhotoPathList(year: Int, month: Int, day: Int): List<PhotoVO> {
        val startCalendar = Calendar.getInstance()
        startCalendar[year, month, day, 0] = 0
        val endCalendar = Calendar.getInstance()
        endCalendar[year, month, day, 24] = 0
        val startTitme = startCalendar.timeInMillis.toString().substring(0, 10)
        val endTitme = endCalendar.timeInMillis.toString().substring(0, 10)
        val photoList = ArrayList<PhotoVO>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
        )
        val selection = (MediaStore.Images.Media.DATE_ADDED + " >= " + startTitme + " AND "
                + MediaStore.Images.Media.DATE_ADDED + " <= " + endTitme)
        val cursor = mContext.contentResolver.query(uri, projection, selection, null, null)
        val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor.moveToNext()) {
            val photoVO = PhotoVO(cursor.getString(columnIndexData), false)
            photoList.add(photoVO)
        }
        cursor.close()
        return photoList
    }
}