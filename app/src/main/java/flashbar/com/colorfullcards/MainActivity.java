package flashbar.com.colorfullcards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView gridView =(RecyclerView)findViewById(R.id.recycler_view);
        gridView.setClipChildren(false);
        GridLayoutManager layoutManager =new GridLayoutManager(getApplicationContext(),2);

        gridView.setLayoutManager(layoutManager);
        gridView.addItemDecoration(new GridDecoration(10));
        gridView.setHasFixedSize(true);
        gridView.setItemAnimator(new DefaultItemAnimator());

        GridAdapter adapter =new GridAdapter(getApplicationContext(),getValuesFromAssets());
        gridView.setAdapter(adapter);

        gridView.setClipToPadding(false);
        gridView.setVerticalFadingEdgeEnabled(true);
        gridView.setFadingEdgeLength(25);
        gridView.setScrollbarFadingEnabled(true);
    }


    int[] getValuesFromAssets(){
        int values[] = new int[29];

           for(int i=0;i<29;i++){
                values[i] = getResources().getIdentifier("image_"+(i+1), "raw", getPackageName());
           }

        return values;
    }
}
