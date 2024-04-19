package com.example.tsgbackend.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.tsgbackend.common.constant.CommonConstants;
import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.common.utils.SecurityUtil;
import com.example.tsgbackend.common.utils.StringUtil;
import com.example.tsgbackend.system.bean.SysMenu;
import com.example.tsgbackend.system.bean.SysRoleMenu;
import com.example.tsgbackend.system.mapper.SysMenuMapper;
import com.example.tsgbackend.system.mapper.SysRoleMenuMapper;
import com.example.tsgbackend.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper menuMapper;

    private final SysRoleMenuMapper roleMenuMapper;

    @Override
    public JSONArray getMenuTree(List<String> roles) {
        List<SysMenu> menuList;
        if(roles.contains(CommonConstants.ROLE_ADMIN)) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.ne(SysMenu::getType, "3");
            wrapper.orderByAsc(SysMenu::getSort);
            menuList = menuMapper.selectList(wrapper);
        } else {
            menuList = menuMapper.getMenuTree(roles);
            if(!CollectionUtils.isEmpty(menuList)) {
                menuList = menuList.stream().filter(item -> !item.getType().equals("3")).collect(Collectors.toList());
                Set<SysMenu> menuSet = new HashSet<>();
                List<SysMenu> list = new ArrayList<>();

                for(SysMenu sysMenu : menuList) {
                    list.add(sysMenu);
                    getAllMenusByChildId(sysMenu.getParentId(), list);
                    for(SysMenu menu : list) {
                        if(menuSet.stream().noneMatch(item -> item.getId().equals(menu.getId()))) {
                            menuSet.add(menu);
                        }
                    }
                }
                menuList = menuSet.stream().sorted(Comparator.comparing(SysMenu::getId)).collect(Collectors.toList());
            }
        }
        JSONArray jsonArray = new JSONArray();
        List<SysMenu> topList = menuList.stream().filter(item -> item.getParentId() == 0L).collect(Collectors.toList());
        return getObjects(menuList, jsonArray, topList);
    }

    @Override
    public void editMenu(SysMenu sysMenu) {
        if(sysMenu.getParentId() == null) {
            throw new BadRequestException("cannot find upper class menu, edit failed");
        }
        if(sysMenu.getId() != null) {
            menuMapper.updateById(sysMenu);
        } else {
            menuMapper.insert(sysMenu);
        }
    }

    @Override
    public void delMenu(Long id) {
        checkMenuRole(id);
        menuMapper.deleteById(id);
    }

    @Override
    public List<SysMenu> queryAllMenus(List<String> roles) {
        if(roles.contains(CommonConstants.ROLE_ADMIN)) {
            return menuMapper.selectList(null);
        }
        return menuMapper.getMenuTree(roles);
    }

    @Override
    public JSONArray getMenuTable(String blurry) {
        JSONArray jsonArray = new JSONArray();
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        if(StringUtil.isNotBlank(blurry)) {
            wrapper.like(SysMenu::getName, blurry);
            wrapper.or();
            wrapper.like(SysMenu::getPath, blurry);
        }
        wrapper.orderByAsc(SysMenu::getSort);
        List<SysMenu> list = menuMapper.selectList(wrapper);
        List<SysMenu> topList = list.stream().filter(item -> item.getParentId() == 0L).collect(Collectors.toList());
        return getObjects(list, jsonArray, topList);
    }

    @Override
    public List<String> getUrlsByRoles(List<String> roles) {
        return menuMapper.getMenuUrlByRole(roles);
    }

    @Override
    public List<String> getPermission() {
        List<String> roles = SecurityUtil.getCurrentRoles();
        List<String> permissions;
        if(roles.contains(CommonConstants.ROLE_ADMIN)) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(SysMenu::getPermission);
            wrapper.eq(SysMenu::getType, "3");
            permissions = menuMapper.selectObjs(wrapper).stream().map(o -> (String)o).collect(Collectors.toList());
        } else {
            permissions = menuMapper.getPermission(roles);
        }
        return permissions;
    }

    @Override
    public JSONArray getMenuTreeSelect() {
        JSONArray menuArray = getMenuTree(SecurityUtil.getCurrentRoles());
        JSONArray children = new JSONArray();
        if(!CollectionUtils.isEmpty(menuArray)) {
            for(int i = 0; i < menuArray.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", menuArray.getJSONObject(i).getLongValue("id"));
                jsonObject.put("label", menuArray.getJSONObject(i).getString("name"));
                if(menuArray.getJSONObject(i).getJSONArray("children") != null) {
                    jsonObject.put("children", getTreeChildren(menuArray.getJSONObject(i).getJSONArray("children")));
                }
                children.add(jsonObject);
            }
        }
        return children;
    }

    /**
     * @description get all upper class menu by menu id
     * @param menuId current menu id
     * @param list menu list
     */
    private void getAllMenusByChildId(Long menuId, List<SysMenu> list) {
        SysMenu sysMenu = menuMapper.selectById(menuId);
        if(sysMenu != null) {
            list.add(sysMenu);
            if(!sysMenu.getParentId().equals(0L)) {
                getAllMenusByChildId(sysMenu.getParentId(), list);
            }
        }
    }

    private JSONArray getObjects(List<SysMenu> menuList, JSONArray jsonArray, List<SysMenu> topList) {
        if(!CollectionUtils.isEmpty(topList)) {
            for(SysMenu sysMenu : topList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", sysMenu.getId());
                jsonObject.put("parentId", sysMenu.getParentId());
                jsonObject.put("name", sysMenu.getName());
                jsonObject.put("path", sysMenu.getPath());
                jsonObject.put("icon", sysMenu.getIcon());
                jsonObject.put("sort", sysMenu.getSort());
                jsonObject.put("component", sysMenu.getComponent());
                jsonObject.put("permission", sysMenu.getPermission());
                jsonObject.put("type", sysMenu.getType());
                if(!CollectionUtils.isEmpty(getChildById(menuList, sysMenu.getId()))) {
                    jsonObject.put("children", getChildById(menuList, sysMenu.getId()));
                }

                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    private JSONArray getChildById(List<SysMenu> menuList, long parentId) {
        JSONArray jsonArray = new JSONArray();
        List<SysMenu> children = menuList.stream().filter(item -> item.getParentId().equals(parentId)).collect(Collectors.toList());
        return getObjects(menuList, jsonArray, children);
    }

    private void checkMenuRole(Long menuId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getMenuId, menuId);
        long count = roleMenuMapper.selectCount(wrapper);
        if(count > 0) {
            throw new BadRequestException("this menu has been related to role, cannot delete");
        }
    }

    private JSONArray getTreeChildren(JSONArray jsonArray) {
        JSONArray children = new JSONArray();
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", jsonArray.getJSONObject(i).getLongValue("id"));
            jsonObject.put("label", jsonArray.getJSONObject(i).getString("name"));
            if(jsonArray.getJSONObject(i).getJSONArray("children") != null) {
                jsonObject.put("children", jsonArray.getJSONObject(i).getJSONArray("children"));
            }
            children.add(jsonObject);
        }
        return children;
    }
}
