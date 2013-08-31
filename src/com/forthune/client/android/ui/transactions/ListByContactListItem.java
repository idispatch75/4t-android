package com.forthune.client.android.ui.transactions;

import com.google.common.base.Optional;

public class ListByContactListItem {
	public Optional<byte[]> icon;
	public String pseudo;
	public double amount;
	
	public ListByContactListItem(Optional<byte[]> icon, String pseudo) {
		this.icon = icon;
		this.pseudo = pseudo;
		this.amount = 0;
	}
}
