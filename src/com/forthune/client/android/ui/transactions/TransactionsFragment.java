package com.forthune.client.android.ui.transactions;

import java.util.LinkedHashMap;
import java.util.Map;

import objectforms.utils.elog;

import com.androidquery.AQuery;
import com.forthune.client.android.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class TransactionsFragment extends Fragment implements TabHost.OnTabChangeListener {
	private static final String TAB_BYCONTACT = "bycontact";
	private static final String TAB_ALL = "all";

	private TabHost tabHost;
	private Map<String, TabInfo> tabInfos = new LinkedHashMap<String, TabInfo>(); // linked, to preserve order
	private TabInfo currentTab;
	private AQuery aq;

	/**
	 * Stores tab info.
	 */
	private class TabInfo {
		private String tag;
		private Class<? extends Fragment> clazz;
		private Bundle args;
		private int labelId;
		private Fragment fragment;

		TabInfo(String tag, Class<? extends Fragment> clazz, int labelId, Bundle args) {
			this.tag = tag;
			this.clazz = clazz;
			this.labelId = labelId;
			this.args = args;
		}
	}

	/**
	 * Creates a dummy view for tabs
	 */
	private class TabFactory implements TabContentFactory {
		private final Context context;

		public TabFactory(Context context) {
			this.context = context;
		}

		public View createTabContent(String tag) {
			View v = new View(this.context);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		elog.verbose("enter");

		// create the view
		View view = inflater.inflate(R.layout.fragment_transactions, container);
		aq = new AQuery(getActivity(), view);

		// create the tabs
		this.tabHost = (TabHost) aq.id(android.R.id.tabhost).getView();
		setupTabs(savedInstanceState);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		elog.verbose("enter");

		super.onActivityCreated(savedInstanceState);

		// listen to tab changes
		this.tabHost.setOnTabChangedListener(this);

		// select the current tab
		String currentTab = savedInstanceState != null ? savedInstanceState.getString("tab") : TAB_BYCONTACT;
		this.tabHost.setCurrentTabByTag(currentTab);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		elog.verbose("enter");

		// save the current tab
		outState.putString("tab", this.tabHost.getCurrentTabTag());

		super.onSaveInstanceState(outState);
	}

	private void setupTabs(Bundle savedInstanceState) {
		this.tabHost.setup();

		// define the list of tabs
		TabInfo info = new TabInfo(TAB_BYCONTACT, ListByContactFragment.class, R.string.tab_transactions_bycontact, savedInstanceState);
		this.tabInfos.put(info.tag, info);
		info = new TabInfo(TAB_ALL, ListAllFragment.class, R.string.tab_transactions_all, savedInstanceState);
		this.tabInfos.put(info.tag, info);

		// add the tabs
		for (TabInfo i : this.tabInfos.values()) {
			addTab(i);
		}
	}

	private void addTab(TabInfo info) {
		// create the tab
		TabSpec tabSpec = this.tabHost.newTabSpec(info.tag);
		tabSpec.setIndicator(getResources().getString(info.labelId));
		tabSpec.setContent(new TabFactory(getActivity()));

		// detach a potential previous fragment associated with this tab
		FragmentManager fm = getFragmentManager();
		info.fragment = fm.findFragmentByTag(info.tag);
		if (info.fragment != null && !info.fragment.isDetached()) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.detach(info.fragment);
			ft.commit();
		}

		// add the tab to the host
		this.tabHost.addTab(tabSpec);
	}

	@Override
	public void onTabChanged(String tabId) {
		elog.debug("tabId = " + tabId);

		updateTab(tabId);
	}

	private void updateTab(String tag) {
		TabInfo newTab = this.tabInfos.get(tag);
	
		// if the new tab is different from the current one
		if (this.currentTab != newTab) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			
			// detach the current tab
			if (this.currentTab != null) {
				if (this.currentTab.fragment != null) {
					ft.detach(this.currentTab.fragment);
				}
			}
			
			if (newTab != null) { // really ?
				// create a new fragment for the tab if it doesn't exist
				if (newTab.fragment == null) {
					newTab.fragment = Fragment.instantiate(getActivity(), newTab.clazz.getName(), newTab.args);
					ft.add(android.R.id.tabcontent, newTab.fragment, newTab.tag);
					
				// or attach the existing one
				} else {
					ft.attach(newTab.fragment);
				}
			}
			
			this.currentTab = newTab;
			ft.commit();
		}
	}
}
