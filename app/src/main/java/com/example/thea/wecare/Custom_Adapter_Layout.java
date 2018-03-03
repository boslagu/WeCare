package com.example.thea.wecare;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Custom_Adapter_Layout extends PagerAdapter {
//    private Context context;
//    private LayoutInflater layoutInflater;
//    private Integer [] images = {R.drawable.takipkuhol1, R.drawable.makabuhay1, R.drawable.kalatsutsileaves1};
//
//    public Custom_Adapter_Layout(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return images.length;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.custom_layout_slider, null);
//        ImageView image = (ImageView) view.findViewById(R.id.imageView);
//        image.setImageResource(images[position]);
//
//        ViewPager vp = (ViewPager) container;
//        vp.addView(view,0);
//
//        return view;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        ViewPager vp = (ViewPager) container;
//        View view = (View) object;
//        vp.removeView(view);
//    }
//}

    Context context;
    LayoutInflater layoutInflater;

    public Custom_Adapter_Layout(Context context){
        this.context = context;
    }

    //    Array
    public int[] slide_images = {
            R.drawable.shome,
            R.drawable.sconsult,
            R.drawable.snconsult,
            R.drawable.snnconsult,
            R.drawable.slearning,
            R.drawable.sdisease,
            R.drawable.sherbal
    };
    public String[] slide_heading={
            "Home",
            "Consult",
            "Consult",
            "Consult",
            "Learning",
            "Disease",
            "Herbal",
    };
//    public String[] slide_desc={
//            "Eal;dgjkladfkgldfkgAT",
//            "LEEPalkdfngjpadfjigiajdfg",
//            "CODEaldkfgjiadugjjiafg"
//    };
    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout_slider, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.textView3);
//        TextView slideDesc = (TextView) view.findViewById(R.id.textView4);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_heading[position]);
//        slideDesc.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);

    }
}