package com.starprose.haunted;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.admob.android.ads.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

public class HauntedHouses extends MapActivity implements OnTabChangeListener, AdListener {
	// release key: 0G1c9bNCm8v-D2592zpesiEP-vL8utTH6sFslvQ
	// debug key: 0G1c9bNCm8v_SmOQmGa7v3pEDBdeXY6wlmHqq2Q
    private static final String LIST_TAB_TAG = "List";    
    private static final String MAP_TAB_TAG = "Map";
	private MapView mapView;
	private TabHost tabHost;
	private ListView listView;
	private AdView ad;
	Attractions hauntedsites = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		try {

			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			HauntedXMLHandler myXMLHandler = new HauntedXMLHandler();
			xr.setContentHandler(myXMLHandler);
			
			xr.parse(new InputSource(getResources().openRawResource(R.raw.fear)));

		} catch (Exception e) {
			System.out.println("XML Parsing Exception = " + e);
		}
		
		/** Get result from MyXMLHandler SitlesList Object */
		hauntedsites = HauntedXMLHandler.Attractions;
        
		// setup must be called if you are not inflating the tabhost from XML
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.setOnTabChangedListener(this);
		
		// setup ad
        ad = (AdView) findViewById(R.id.ad);
        ad.setAdListener(this);
        ad.setVisibility(AdView.VISIBLE);
        
		// setup list view
		listView = (ListView) findViewById(R.id.list);
        // fill in the grid_item layout
		listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , hauntedsites.getName()));
		listView.setEmptyView((TextView) findViewById(R.id.empty));
		
		// add an onclicklistener to see point on the map
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			float longitude = hauntedsites.getLongitude().get(position);
			float latitude = hauntedsites.getLatitude().get(position);

			GeoPoint point = new GeoPoint((int)(latitude * 1E6), (int)(longitude * 1E6));
				if(point != null) {
					// have map view moved to this point
					setMapZoomPoint(point, 12);
					tabHost.setCurrentTab(1);
				}
			}
		});

		
		// setup map
		mapView = (MapView)findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.pushpin);

        HauntedItemizedOverlay itemizedoverlay = new HauntedItemizedOverlay(drawable,this);
        for (int i=0;i< hauntedsites.getName().size();i++){
        	float longitude = hauntedsites.getLongitude().get(i);
			float latitude = hauntedsites.getLatitude().get(i);
			String name = hauntedsites.getName().get(i);
			String address = hauntedsites.getAddress().get(i);
			String city  = hauntedsites.getCity().get(i);
			String state = hauntedsites.getState().get(i);
			GeoPoint point = new GeoPoint((int)(latitude * 1E6), (int)(longitude * 1E6));
            OverlayItem overlayitem = new OverlayItem(point, name, address + "\n"+ city + " , " + state);
            itemizedoverlay.addOverlay(overlayitem);	
        }
        mapOverlays.add(itemizedoverlay);
        
		// add views to tab host
		tabHost.addTab(tabHost.newTabSpec(LIST_TAB_TAG).setIndicator("List").setContent(new TabContentFactory() {
			public View createTabContent(String arg0) {
				return listView;
			}
		}));
		tabHost.addTab(tabHost.newTabSpec(MAP_TAB_TAG).setIndicator("Map").setContent(new TabContentFactory() {
			public View createTabContent(String arg0) {
				return mapView;
			}
		}));

		tabHost.setCurrentTab(1);
		tabHost.setCurrentTab(0);
    }
    
    /**
     * Instructs the map view to navigate to the point and zoom level specified.
     * @param geoPoint
     * @param zoomLevel
     */
    private void setMapZoomPoint(GeoPoint geoPoint, int zoomLevel) {
		mapView.getController().setCenter(geoPoint);
		mapView.getController().setZoom(zoomLevel);
		mapView.postInvalidate();
	}
    
    /* (non-Javadoc)
	 * @see com.admob.android.ads.AdView.AdListener#onFailedToReceiveAd(com.admob.android.ads.AdView)
	 */
	public void onFailedToReceiveAd(AdView adView)
	{
		Log.d("Lunar", "onFailedToReceiveAd");
	}

	/* (non-Javadoc)
	 * @see com.admob.android.ads.AdView.AdListener#onFailedToReceiveRefreshedAd(com.admob.android.ads.AdView)
	 */
	public void onFailedToReceiveRefreshedAd(AdView adView)
	{
		Log.d("Lunar", "onFailedToReceiveRefreshedAd");
	}

	/* (non-Javadoc)
	 * @see com.admob.android.ads.AdView.AdListener#onReceiveAd(com.admob.android.ads.AdView)
	 */
	public void onReceiveAd(AdView adView)
	{
		Log.d("Lunar", "onReceiveAd");
	}

	/* (non-Javadoc)
	 * @see com.admob.android.ads.AdView.AdListener#onReceiveRefreshedAd(com.admob.android.ads.AdView)
	 */
	public void onReceiveRefreshedAd(AdView adView)
	{
		Log.d("Lunar", "onReceiveRefreshedAd");
	}
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
	/**
	 * Implement logic here when a tab is selected
	 */
	public void onTabChanged(String tabName) {
		if(tabName.equals(MAP_TAB_TAG)) {
			//do something on the map
		}
		else if(tabName.equals(LIST_TAB_TAG)) {
			//do something on the list
		}
	}

    public class HauntedItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    	private Context mContext;
    	
    	public HauntedItemizedOverlay(Drawable defaultMarker, Context context) {
    		super(boundCenterBottom(defaultMarker));
    		mContext = context;
    		// TODO Auto-generated constructor stub
    	}

    	 public void addOverlay(OverlayItem overlay)
    	 {
    	 mOverlays.add(overlay);
    	 populate();
    	 }
    	
    	@Override
    	protected OverlayItem createItem(int arg0) {
    		// TODO Auto-generated method stub
    		return mOverlays.get(arg0);
    	}

    	@Override
    	public int size() {
    		// TODO Auto-generated method stub
    		 return mOverlays.size();
    	}
    	
    	 @Override
    	 protected boolean onTap(int index)
    	 {
    	 OverlayItem item = mOverlays.get(index);
    	 AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
    	 final String query = item.getSnippet();
    	 dialog.setTitle(item.getTitle());
    	 dialog.setMessage(item.getSnippet());
		 dialog.setPositiveButton("Drive To",
	 	 new DialogInterface.OnClickListener(){
	          public void onClick(DialogInterface dialog, int id){
	         	 Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
	         	    	 Uri.parse("google.navigation:q=" + query));
	         	    	 startActivity(intent);
	          }
	 	 });
    	 dialog.show();

    	 return true;
    	 }
    }
    
    
}