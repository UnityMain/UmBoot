package com.unitymain.student.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.unitymain.student.bean.Result;
import com.unitymain.student.entity.SysMenu;
import com.unitymain.student.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前端菜单(SysMenu)表控制层
 *
 * @author UnityMain
 * @since 2022-01-25 15:14:18
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("all")
    public Result<List<Tree<Integer>>> selectAll() {
        List<TreeNode<Integer>> nodeList = CollUtil.newArrayList();
        List<SysMenu> sysMenus = this.sysMenuService.queryAllMenu();
        Map<Integer,SysMenu> sysMenuMap = new HashMap<>();
        for(SysMenu sysMenu : sysMenus){
            nodeList.add(new TreeNode<>(
                    sysMenu.getId(),
                    sysMenu.getParent(),
                    sysMenu.getName(), 0));

            sysMenuMap.put(sysMenu.getId(),sysMenu);
        }
        List<Tree<Integer>> treeList = TreeUtil.build(nodeList,-1,(treeNode, tree)->{
            SysMenu sysMenu = sysMenuMap.get(treeNode.getId());
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
            tree.putExtra("component",sysMenu.getComponent());
            tree.putExtra("path",sysMenu.getPath());
            tree.putExtra("routeName",sysMenu.getName());
            tree.putExtra("redirect",null);
        });
        return Result.ok().body(treeList);
    }

}