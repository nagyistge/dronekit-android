package com.droidplanner;

import com.droidplanner.DroidPlannerApp.ConnectionStateListner;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public abstract class SuperActivity extends Activity implements
		OnNavigationListener, ConnectionStateListner {

	abstract int getNavigationItem();
	public DroidPlannerApp app;
	private MenuItem connectButton;	

	public SuperActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
	
		// Set up the action bar to show a dropdown list.
		setUpActionBar();
		app = (DroidPlannerApp) getApplication();
		app.setConectionStateListner(this);

	}

	public void setUpActionBar() {
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
				R.array.menu_dropdown,
				android.R.layout.simple_spinner_dropdown_item);
		actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
		actionBar.setSelectedNavigationItem(getNavigationItem());
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (itemPosition == getNavigationItem()) {
			return false;
		}
		Intent navigationIntent;
		switch (itemPosition) {
		default:
		case 0: // Planning
			navigationIntent = new Intent(this, PlanningActivity.class);
			break;
		case 1: // Flight Data
			navigationIntent = new Intent(this, FlightDataActivity.class);
			navigationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			break;
		case 2: // RC
			navigationIntent = new Intent(this, RCActivity.class);
			break;
		case 3: // GCP
			navigationIntent = new Intent(this, GCPActivity.class);
			break;
		case 4: // Terminal
			navigationIntent = new Intent(this, TerminalActivity.class);
			break;			
		}
		startActivity(navigationIntent);
		return false;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				return true;
			case R.id.menu_connect:
				app.MAVClient.toggleConnectionState();
				return true;
			case R.id.menu_load_from_apm:
				app.waypointMananger.getWaypoints();
				return true;					
			default:
				return super.onMenuItemSelected(featureId, item);
		}
	}
	
	

	public void notifyDisconnected() {
		if (connectButton != null) {
			connectButton.setTitle(getResources().getString(
					R.string.menu_connect));
		}
	}

	public void notifyConnected() {
		if (connectButton != null) {
			connectButton.setTitle(getResources().getString(
					R.string.menu_disconnect));		
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		connectButton = menu.findItem(R.id.menu_connect);
		app.MAVClient.queryConnectionState();
		return super.onCreateOptionsMenu(menu);
	}
}