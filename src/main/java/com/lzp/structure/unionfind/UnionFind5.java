package com.lzp.structure.unionfind;

/**
 * 使用树结构实现并查集--基于rank的优化
 * 优化：find添加路径压缩
 *
 * @author lzp
 * @version v1.0 at 2019/3/24
 */
public class UnionFind5 implements UF {

    /**
     * 底层数据结构--数组，指向父节点
     */
    private int[] parent;

    /**
     * 集合中元素个数
     */
    private int[] rank;

    public UnionFind5(int size) {
        parent = new int[size];
        rank = new int[size];

        // 初始化每个元素的parent[i]指向自己
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    /**
     * 元素个数
     *
     * @return int
     */
    @Override
    public int getSize() {
        return parent.length;
    }

    /**
     * 判断两元素是否连接
     * O(h)复杂度, h为树的高度
     *
     * @param p 元素索引
     * @param q 元素索引
     * @return 布尔
     */
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * 找到元素p对应的集合号
     * O(h)复杂度, h为树的高度
     *
     * @param p
     * @return
     */
    private int find(int p) {
        if (p < 0 || p >= parent.length) {
            throw new IllegalArgumentException("p不合法");
        }

        // 不断查询自己的父亲节点，直到根节点
        // 根节点的特点是 parent[p] == p
        while (parent[p] != p) {
            // 当前节点的父亲节点为，其父亲节点的父亲节点
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    /**
     * 合并元素，将属于一个集合的元素归为一个集合
     * O(h)复杂度, h为树的高度
     *
     * @param p 元素索引
     * @param q 元素索引
     */
    @Override
    public void unionElements(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        // 如果两个元素本来就属于一个集合
        if (pRoot == qRoot) {
            return;
        }
        // 根据集合（树）的元素rank判断合并方向
        // rank低的集合合并到rank高的集合上
        if (rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
        } else if (rank[qRoot] < rank[pRoot]) {
            parent[qRoot] = pRoot;
        } else {
            // 如果两棵树高度一致时，需要维护rank
            parent[qRoot] = pRoot;
            rank[pRoot] += 1;
        }
    }
}
