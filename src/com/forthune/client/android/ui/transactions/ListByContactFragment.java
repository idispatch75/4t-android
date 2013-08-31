package com.forthune.client.android.ui.transactions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forthune.client.android.Backend;
import com.forthune.client.android.BackendService;
import com.forthune.client.android.BackendService.BackendBinder;
import com.forthune.client.data.Transaction;
import com.forthune.client.data.User;
import com.google.common.base.Optional;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListByContactFragment extends ListFragment {
	private Optional<Backend> backend = Optional.absent();

	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			BackendBinder binder = (BackendBinder) service;
			ListByContactFragment.this.backend = Optional.of(binder.getService());
			createListItems();
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get the backend
		Intent intent = new Intent(getActivity(), BackendService.class);
		getActivity().bindService(intent, this.serviceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	private void createListItems() {
		if (this.backend.isPresent() && this.backend.get().getCurrentUser().isPresent()) {
			// merge transactions into contacts
			User user = this.backend.get().getCurrentUser().get();
			Map<String, ListByContactListItem> items = new HashMap<String, ListByContactListItem>();
			for (Transaction tx : user.getTransactions().values()) {
				ListByContactListItem item;
				String contactId = tx.getContact();
				
				// create the contact item if it doesn't exist
				if (!items.containsKey(contactId)) {
					Optional<byte[]> icon;
					if (user.getContacts().containsKey(contactId)) {
						icon = user.getContacts().get(contactId).getIcon();
					} else {
						icon = Optional.absent();
					}
					item = new ListByContactListItem(icon, contactId);
					items.put(contactId, item);
				} else {
					item = items.get(contactId);
				}
				
				// update the amount
				item.amount += tx.getAmount();
			}
			
			// sort the list by pseudo
			List<ListByContactListItem> sorted = new ArrayList<ListByContactListItem>(items.values());
			Collections.sort(sorted, new Comparator<ListByContactListItem>() {
				public int compare(ListByContactListItem lhs, ListByContactListItem rhs) {
					return lhs.pseudo.compareToIgnoreCase(rhs.pseudo);
				}
			});

			// set the list adapter
			ListByContactAdapter adapter = new ListByContactAdapter(getActivity(), sorted);
			setListAdapter(adapter);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(getActivity(), "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		getActivity().unbindService(this.serviceConnection);
	}
}
