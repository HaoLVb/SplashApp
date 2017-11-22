package com.example.levanhao.splashapp.model;

public class NavigationItem {
	private String itemName;
	private int itemIcon;
	private boolean isSelected;

	public NavigationItem(int itemIcon, String itemName) {
		this.itemIcon = itemIcon;
		this.itemName = itemName;
		this.isSelected = false;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemIcon() {
		return itemIcon;
	}

	public void setItemIcon(int itemIcon) {
		this.itemIcon = itemIcon;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
}
