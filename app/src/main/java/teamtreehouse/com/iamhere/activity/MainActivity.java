package teamtreehouse.com.iamhere.activity;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import teamtreehouse.com.iamhere.R;
import teamtreehouse.com.iamhere.common.LruBitmapCache;
import teamtreehouse.com.iamhere.common.MySingleton;
import teamtreehouse.com.iamhere.model.Place;

public class MainActivity extends BaseActivity {

    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    protected RecyclerView list;

    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=2000&type=museum&key=AIzaSyDSsuLaBf-bXpABI5nPjfFKVP7uGBbMHzI";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));
        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(new RecyclerView.ItemDecoration() {
            private final Drawable dividerDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.divider);
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int dividerRight = parent.getWidth() - parent.getPaddingRight();
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    int dividerLeft = parent.getPaddingLeft();

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int dividerTop = child.getBottom() + params.bottomMargin;
                    int dividerBottom = dividerTop + dividerDrawable.getIntrinsicHeight();
                    dividerDrawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
                    dividerDrawable.draw(c);

                }
            }
        });

        mRequestQueue = MySingleton.getInstance(this).getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(this)));


    }

    @Override
    protected void handleNewLocation(final Location location) {
        super.handleNewLocation(location);

        mLatitudeText.setText(Double.toString(location.getLatitude()));
        mLongitudeText.setText(Double.toString(location.getLatitude()));


        Uri requestUri = Uri.parse(url).buildUpon().appendQueryParameter("location", location.getLatitude() + "," + location.getLongitude()).build();
        JsonObjectRequest jsObjectRequest = new JsonObjectRequest(Request.Method.GET, requestUri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Place> places = new ArrayList<>();
                        try{
                            JSONArray results = response.getJSONArray("results");
                            for(int i=0; i< results.length(); i++){
                                JSONObject obj = results.getJSONObject(i);
                                Place place = new Place();
                                place.setIconUrl(obj.getString("icon"));
                                place.setTitle(obj.getString("name"));
                                place.setAddress(obj.getString("vicinity"));
                                places.add(place);
                            }
                        }catch (JSONException jsEx){

                        }
                        Adapter adapter = new Adapter(places);
                        list.setAdapter(adapter);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(jsObjectRequest);
    }

    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<Place> places;

        public Adapter(List<Place> places){
            this.places = places;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item_layout, parent, false);
            PlaceVH vh = new PlaceVH(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            PlaceVH placeView = (PlaceVH) holder;
            Place place = places.get(position);
            placeView.setTitle(place.getTitle());
            placeView.setAddress(place.getAddress());
            placeView.setImageUrl(place.getIconUrl());
        }

        @Override
        public int getItemCount() {
            return places.size();
        }

    }

    private class PlaceVH extends RecyclerView.ViewHolder{
        TextView title;
        TextView address;
        NetworkImageView imageView;

        public PlaceVH(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.address = (TextView) itemView.findViewById(R.id.address);
            this.imageView = (NetworkImageView) itemView.findViewById(R.id.icon);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setAddress(String address) {
            this.address.setText(address);
        }

        public void setImageUrl(String url) {
            imageView.setImageUrl(url, mImageLoader);
        }
    }

}
