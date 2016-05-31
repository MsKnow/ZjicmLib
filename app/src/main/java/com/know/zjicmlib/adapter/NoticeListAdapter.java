package com.know.zjicmlib.adapter;

import com.know.zjicmlib.R;
import com.know.zjicmlib.adapter.base.ListAdapter;
import com.know.zjicmlib.adapter.base.ViewHolderr;
import com.know.zjicmlib.modle.bean.Notice;
import com.know.zjicmlib.util.DateUtil;

import java.util.List;

/**
 * Created by know on 2016/5/26.
 */
public class NoticeListAdapter extends ListAdapter<Notice> {

    public NoticeListAdapter(List<Notice> data, int layoutId) {
        super(data, layoutId);
    }

    @Override
    public void bind(ViewHolderr holder, Notice notice) {
        holder.setText(R.id.tv_notice_title,notice.getTitle())
                .setText(R.id.tv_notice_date, notice.getDate());
    }
}
