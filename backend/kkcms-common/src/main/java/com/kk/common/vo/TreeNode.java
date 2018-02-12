package com.kk.common.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangfei on 2017/10/16.
 */
public class TreeNode {
    protected Integer id;
    protected Integer parentId;
    List<TreeNode> children = new ArrayList<>();

    public void add(TreeNode node) {
        children.add(node);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
