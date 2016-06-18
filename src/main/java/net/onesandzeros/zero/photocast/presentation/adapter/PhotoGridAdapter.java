package net.onesandzeros.zero.photocast.presentation.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;

import net.onesandzeros.zero.photocast.R;
import net.onesandzeros.zero.photocast.common.ApiConstants;
import net.onesandzeros.zero.photocast.domain.dto.PhotoItem;
import net.onesandzeros.zero.photocast.utils.LogUtils;
import net.onesandzeros.zero.photocast.utils.VolleyLib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PhotoGridAdapter extends BaseAdapter {
    private static String LOG_TAG = "PHOTOGRID";
    private final LayoutInflater mLayoutInflater;

    private List<PhotoItem>      mGridPhotos;


    private OnItemClickListener  mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(View anchor, PhotoItem photoItem);
    }

    public PhotoGridAdapter(Context context, Collection<PhotoItem> collection) {
        validateCollection(collection);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGridPhotos = new ArrayList<>(collection);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void validateCollection(Collection<PhotoItem> photoItems) {
        if (photoItems == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setCollection(Collection<PhotoItem> photoItems) {
        validateUsersCollection(photoItems);
        mGridPhotos = new ArrayList<>(photoItems);
        notifyDataSetChanged();
    }

    private void validateUsersCollection(Collection<PhotoItem> photoItems) {
        if (photoItems == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    @Override
    public int getCount() {
        if (mGridPhotos == null)
            return 0;
        return mGridPhotos.size();

    }

    @Override
    public Object getItem(int position) {
        if (mGridPhotos == null)
            return null;
        return mGridPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mGridPhotos == null)
            return 0;
        return Long.parseLong(mGridPhotos.get(position).mId);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoItem result = (PhotoItem) getItem(position);

        ViewHolder resultViewHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.photo_grid_item, parent, false);
        }

        if (convertView.getTag() == null) {
            resultViewHolder = new ViewHolder();
            resultViewHolder.instantiate(convertView);
            convertView.setTag(resultViewHolder);
        } else {
            resultViewHolder = (ViewHolder) convertView.getTag();
        }

        final PhotoItem photoItem = mGridPhotos.get(position);
        convertView.findViewById(R.id.gv_grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClicked(v, photoItem);

                LogUtils.errorLog(LOG_TAG, "GRIDDY GRID: ");
            }
        });

        resultViewHolder.bindViews(result);
        return convertView;
    }

    public static class ViewHolder {

        NetworkImageView mGridPhotos;
//        TextView mGridPhotosText;

        public void instantiate(View view) {
            mGridPhotos = (NetworkImageView) view.findViewById(R.id.iv_image);
          //  mGridPhotosText = (TextView) view.findViewById(R.id. );
        }

        public void bindViews(PhotoItem item) {
            if (item.mCoverPhoto != null && !item.mCoverPhoto.equals("")) {
                mGridPhotos.setImageUrl(ApiConstants.getCoverPhotoUrl(item.mId), VolleyLib.getImageLoader());
                LogUtils.debugLog(LOG_TAG, "COVER PHOTOS--> " + ApiConstants.getCoverPhotoUrl(item.mId));

            } else if (item.mVideoPreviewPic != null && !item.mVideoPreviewPic.equals("")) {
                mGridPhotos.setImageUrl(item.mVideoPreviewPic, VolleyLib.getImageLoader());
                LogUtils.debugLog(LOG_TAG, "PREEVIEW PIC PHOTOS--> " + item.mVideoPreviewPic );

            } else {
                mGridPhotos.setImageUrl(item.mImages.get(0).source, VolleyLib.getImageLoader());

                LogUtils.debugLog(LOG_TAG, "GET SOURCE 0--> " + item.mImages.get(0).source );


            }
        }
    }

}
