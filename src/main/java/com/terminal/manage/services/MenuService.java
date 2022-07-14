package com.terminal.manage.services;

import com.terminal.manage.model.Menu;

import java.util.List;
import java.util.Optional;

/**
 * @author Jyt
 * @date 2021/9/29
 */
public interface MenuService {

    /**
     * Get Menu List based on menu-conditions
     * @param pageStart current-page
     * @param pageSize show each page size
     * @param menu menu-conditions
     * @return Menu List
     */
    Optional<List<Menu>> pageMenu(Integer pageStart, Integer pageSize, Menu menu);

    /**
     * Get Menu List Based on menu-id
     * @param menuId menu-id
    * @return Menu info
     */
    Optional<Menu> getMenuById(Long menuId);

    /**
     * Get menu list based on pid, if pid is null else return new arrayList()
     * @param pid parent-id
     * @return Menu List
     */
    Optional<List<Menu>> getMenuListByPid(Long pid);

    /**
     * Add menu
     * @param menu menu info
     * @return if add successfully return true else false;
     */
    Optional<Boolean> addMenu(Menu menu);

    /**
     * Update menu by menu-id, but menu-id can't be null
     * @param menu menu info
     * @return if add successfully return true else false;
     */
    Optional<Boolean> updateMenu(Menu menu);

    /**
     * Delete menu by menu-id, but menu-id can't be null
     * @param menuId menu-id
     * @return if add successfully return true else false;
     */
    Optional<Boolean> delMenuById(Long menuId);

    /**
     * Get all Menu to together Tree
     * @return MenuTree
     */
    Optional<List<Menu>>  getMenuTree(Menu menu);

}
