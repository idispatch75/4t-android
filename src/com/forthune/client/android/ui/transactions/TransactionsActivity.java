package com.forthune.client.android.ui.transactions;

import com.forthune.client.android.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class TransactionsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transactions);
 
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.main_actions_list,
				R.layout.support_simple_spinner_dropdown_item);
		bar.setListNavigationCallbacks(spinnerAdapter, new ActionBar.OnNavigationListener() {
			public boolean onNavigationItemSelected(int position, long itemId) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(TransactionsActivity.this);
				builder.setMessage(getResources().getStringArray(R.array.main_actions_list)[position])
						.setTitle(android.R.string.dialog_alert_title)
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();

				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transactions_list_actions, menu);

		return super.onCreateOptionsMenu(menu);
	}

}
