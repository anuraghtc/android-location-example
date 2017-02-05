package teamtreehouse.com.iamhere;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    protected TextView mLatitudeText;
    protected TextView mLongitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));

    }

    @Override
    protected void handleNewLocation(Location location) {
        super.handleNewLocation(location);

        mLatitudeText.setText(Double.toString(location.getLatitude()));
        mLongitudeText.setText(Double.toString(location.getLatitude()));
    }
}
