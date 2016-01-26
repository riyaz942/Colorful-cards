package flashbar.com.colorfullcards;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

public class AutoFitRecyclerView extends RecyclerView{
    private int     m_gridMinSpans;
    private int     m_gridItemLayoutId;
    private LayoutRequester m_layoutRequester = new LayoutRequester();

    public AutoFitRecyclerView(Context context) {
        super(context);
    }

    public void setGridLayoutManager( int orientation, int itemLayoutId, int minSpans ) {
        GridLayoutManager layoutManager = new GridLayoutManager( getContext(), 2, orientation, false );
        m_gridItemLayoutId = itemLayoutId;
        m_gridMinSpans = minSpans;

        setLayoutManager( layoutManager );
    }

    @Override
    protected void onLayout( boolean changed, int left, int top, int right, int bottom ) {
        super.onLayout(changed, left, top, right, bottom);

        if( changed ) {
                LayoutManager layoutManager = getLayoutManager();

                LayoutInflater inflater = LayoutInflater.from(getContext());
                View item = inflater.inflate( m_gridItemLayoutId, this, false );
                int measureSpec = MeasureSpec.makeMeasureSpec( 0, MeasureSpec.UNSPECIFIED );
                item.measure( measureSpec, measureSpec );
                int itemWidth = item.getMeasuredWidth();
                int recyclerViewWidth = getMeasuredWidth();
                int spanCount = Math.max( m_gridMinSpans, recyclerViewWidth / itemWidth );

                if(layoutManager instanceof StaggeredGridLayoutManager) {
                    ((StaggeredGridLayoutManager)layoutManager).setSpanCount(spanCount);
                }
            else if(layoutManager instanceof GridLayoutManager){
                    ((GridLayoutManager)layoutManager).setSpanCount(spanCount);
                }
                // if you call requestLayout() right here, you'll get ArrayIndexOutOfBoundsException when scrolling
                post(m_layoutRequester);
        }
    }

    private class LayoutRequester implements Runnable {
        @Override
        public void run() {
            requestLayout();
        }
    }
}
