package flashbar.com.colorfullcards;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private final static int DEFAULT_IMAGE=R.mipmap.ic_launcher;
    private final static int ANIMATION_DURATION=600;

    private int[] values;
    private Context context;

    public GridAdapter(Context context, int[] values) {
        this.values=values;
        this.context=context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView heading;
        TextView info;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            heading = (TextView) itemView.findViewById(R.id.heading);
            info = (TextView) itemView.findViewById(R.id.info);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View convertView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_staggered, viewGroup, false);

        return new ViewHolder(convertView);
    }

    private ColorHolder extractColor(Palette.Swatch swatch){
        ColorHolder color = new ColorHolder();
        color.backgroundColor = swatch.getRgb();
        color.headingColor = swatch.getBodyTextColor();
        color.infoColor = swatch.getTitleTextColor();
        return color;
    }

    private void changeColor(GridAdapter.ViewHolder holder,ColorHolder previousColor,ColorHolder nextColor){
        //no point in animating the cardview color if previous color and current color are the same
        if (previousColor.backgroundColor != nextColor.backgroundColor) {
            animateCard(holder, previousColor, nextColor);
            holder.cardView.setTag(nextColor);
        }
    }

    private void animateCard(GridAdapter.ViewHolder holder,ColorHolder previousColor,ColorHolder nextColor){
        ObjectAnimator animator = ObjectAnimator.ofInt(holder.cardView, "cardBackgroundColor", previousColor.backgroundColor, nextColor.backgroundColor).setDuration(ANIMATION_DURATION);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();

        holder.heading.setTextColor(nextColor.headingColor);
        holder.info.setTextColor(nextColor.infoColor);
    }


    private ColorHolder getPreviousColor(GridAdapter.ViewHolder holder){
        ColorHolder colorHolder= (ColorHolder) holder.cardView.getTag();

        if (colorHolder == null)
            colorHolder = ColorHolder.getDefaultColorHolder();

        return colorHolder;
    }

    @Override
    public void onBindViewHolder(final GridAdapter.ViewHolder holder,final int position) {

        holder.heading.setText("Heading " + (position + 1));
        holder.info.setText("Some info " + (position + 1));

        Picasso.with(context)
                .load(values[position])
                .placeholder(DEFAULT_IMAGE)
                .error(DEFAULT_IMAGE).fit().centerCrop()
                .transform(PaletteTransformation.instance())
                .into(holder.imageView, new Callback() {

                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap();

                        if (bitmap != null) {
                            Palette.Swatch swatch = PaletteTransformation.getPalette(bitmap).getVibrantSwatch();
                            //get previous color which was set on the cardview as tags
                            ColorHolder previousColorValue = getPreviousColor(holder);

                            if (swatch != null)
                                //change color of cardview from the previously set color to the new generated color
                                changeColor(holder, previousColorValue, extractColor(swatch));
                            else
                                //changing color to default
                                changeColor(holder, previousColorValue,ColorHolder.getDefaultColorHolder());

                        } else
                            changeColor(holder, getPreviousColor(holder), ColorHolder.getDefaultColorHolder());
                    }

                    @Override
                    public void onError() {
                        //change to default color if any error occurs
                        changeColor(holder, getPreviousColor(holder), ColorHolder.getDefaultColorHolder());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return values.length;
    }
}