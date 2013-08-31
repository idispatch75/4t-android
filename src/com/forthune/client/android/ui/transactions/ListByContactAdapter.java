package com.forthune.client.android.ui.transactions;

import java.text.Format;
import java.text.NumberFormat;
import java.util.List;

import com.forthune.client.android.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public final class ListByContactAdapter extends BaseAdapter {
	private static final Format AMOUNT_FORMAT = NumberFormat.getCurrencyInstance();
	
	private final Activity activity;
	private final List<ListByContactListItem> items;

	private static class ViewHolder {
		public ImageView icon;
		public TextView pseudo;
		public TextView amount;
	}

	public ListByContactAdapter(Activity activity, List<ListByContactListItem> items) {
		this.activity = activity;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// we try to reuse the convertview if possible
		View rowView = convertView;
		
		// if the convertview is null create a new view and store its info in the holder
		if (rowView == null) {
			rowView = this.activity.getLayoutInflater().inflate(R.layout.listitem_transactionsbycontact, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
			viewHolder.pseudo = (TextView) rowView.findViewById(R.id.pseudo);
			viewHolder.amount = (TextView) rowView.findViewById(R.id.amount);
			
			rowView.setTag(viewHolder);
		}

		// affect the item data to the holder 
		ViewHolder holder = (ViewHolder) rowView.getTag();
		ListByContactListItem item = this.items.get(position);
		if (item.icon.isPresent()) {
			
		} else {
			holder.icon.setImageResource(R.drawable.ic_contact_picture);
		}
		holder.pseudo.setText(item.pseudo);
		holder.amount.setText(AMOUNT_FORMAT.format(item.amount));

		return rowView;
	}
	
	
	@Override
	public int getCount() {
		return this.items.size();
	}

	@Override
	public Object getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
