package com.abc.client1.data.models;

public class MenuModel {

    public String menuName;
    public boolean hasChildren, isGroup;
    public int navigateCode;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, int navigateCode) {

        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.navigateCode = navigateCode;
    }

}
