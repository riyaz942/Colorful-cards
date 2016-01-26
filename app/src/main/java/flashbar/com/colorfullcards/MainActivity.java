package flashbar.com.colorfullcards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {
    RelativeLayout parentView;
    AutoFitRecyclerView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        parentView = (RelativeLayout)findViewById(R.id.parent_view);
        gridView =new AutoFitRecyclerView(getApplicationContext());
        gridView.setGridLayoutManager(AutoFitRecyclerView.VERTICAL, R.layout.list_item_staggered, 2);

        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params);
        gridView.setClipChildren(false);

        GridLayoutManager layoutManager =new GridLayoutManager(getApplicationContext(),2);
        gridView.setLayoutManager(layoutManager);
        gridView.addItemDecoration(new GridDecoration(10));
        gridView.setHasFixedSize(true);
        gridView.setItemAnimator(new DefaultItemAnimator());
        gridView.setClipToPadding(false);
        gridView.setVerticalFadingEdgeEnabled(true);
        gridView.setFadingEdgeLength(25);
        gridView.setScrollbarFadingEnabled(true);

        GridAdapter adapter =new GridAdapter(getApplicationContext(),getValuesFromAssets());
        gridView.setAdapter(adapter);

        parentView.addView(gridView);
    }


    int[] getValuesFromAssets(){
        int values[] = new int[29];

           for(int i=0;i<29;i++){
                values[i] = getResources().getIdentifier("image_"+(i+1), "raw", getPackageName());
           }

        return values;
    }
}
