package com.forthune.client.persist.protostuff;

import com.dyuproject.protostuff.Tag;
import com.forthune.client.data.Contact;

/**
 * @author Guillaume Durand
 *
 */
final class ContactRaw extends ItemRaw {
	@Tag(20)
	String pseudo;
	
	@Tag(21)
	byte[] icon;

	public ContactRaw(Contact contact) {
		super(contact);
		this.pseudo = contact.getPseudo();
		if (contact.getIcon().isPresent()) {
			this.icon = contact.getIcon().get();
		}
	}
	
	public Contact getFinal() {
		Contact contact = new Contact(this.id, this.revision, this.pseudo);
		contact.setIcon(this.icon);
		return contact;
	}
}