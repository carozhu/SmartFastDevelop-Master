/*
 * Copyright (C) 2017 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of rebase-android
 *
 * rebase-android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * rebase-android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with rebase-android. If not, see <http://www.gnu.org/licenses/>.
 */

package com.carozhu.fastdev.widget.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: carozhu
 * Date  : On 2018/11/13
 * Desc  :
 */
public class LoadMoreDelegate {
    private final LoadMoreSubject loadMoreSubject;


    public LoadMoreDelegate(LoadMoreSubject loadMoreSubject) {
        this.loadMoreSubject = loadMoreSubject;
    }


    /**
     * Should be called after recyclerView setup with its layoutManager and adapter
     *
     * @param recyclerView the RecyclerView
     */
    public <RV extends RecyclerView >void attach(RV recyclerView) {
        //final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager, loadMoreSubject));
        recyclerView.addOnScrollListener(new OnLoadMoreScrollListener(loadMoreSubject));
    }


    /*
    * Note : only surport LinearLayoutManager
    private static class EndlessScrollListener extends RecyclerView.OnScrollListener {
        private static final int VISIBLE_THRESHOLD = 6;
        private final LinearLayoutManager layoutManager;
        private final LoadMoreSubject loadMoreSubject;


        private EndlessScrollListener(LinearLayoutManager layoutManager, LoadMoreSubject loadMoreSubject) {
            this.layoutManager = layoutManager;
            this.loadMoreSubject = loadMoreSubject;
        }


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy < 0 || loadMoreSubject.isLoading()) return;

            final int itemCount = layoutManager.getItemCount();
            final int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
            final boolean isBottom = (lastVisiblePosition >= itemCount - VISIBLE_THRESHOLD);
            if (isBottom) {
                loadMoreSubject.onLoadMore();
            }
        }
    }*/


    public interface LoadMoreSubject {
        boolean isLoading();

        void onLoadMore();
    }


    /**
     * 测试支持LinearLayoutManager 和 GridLayoutManager
     */
    public static class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {
        private static final int VISIBLE_THRESHOLD = 10;
        private LoadMoreSubject loadMoreSubject;

        public OnLoadMoreScrollListener(LoadMoreSubject loadMoreSubject) {
            this.loadMoreSubject = loadMoreSubject;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //add by carozhu
            if (dy < 0 || loadMoreSubject.isLoading()) return;

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();

            boolean triggerCondition = visibleItemCount > 0 && canTriggerLoadMore(recyclerView);

            if (triggerCondition) {
                loadMoreSubject.onLoadMore();
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //add by carozhu
            /*
            if (loadMoreSubject.isLoading()){
                return;
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();

            boolean triggerCondition = visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && canTriggerLoadMore(recyclerView);

            if (triggerCondition) {
                loadMoreSubject.onLoadMore();
            }*/
        }

        public boolean canTriggerLoadMore(RecyclerView recyclerView) {
            View lastChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            int position = recyclerView.getChildLayoutPosition(lastChild);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int totalItemCount = layoutManager.getItemCount();
            //add by carozhu
            if (totalItemCount < VISIBLE_THRESHOLD) {
                return false;
            }
            return totalItemCount - 1 == position;
        }
    }
}
