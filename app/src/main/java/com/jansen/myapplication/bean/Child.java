package com.jansen.myapplication.bean;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

/**
 * Created Jansen on 2016/3/30.
 */
@Table(name = "child")
public class Child {
    //此id会自增长，不需要认为设置
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "parentId" /*, property = "UNIQUE"//如果是一对一加上唯一约束*/)
    private long parentId; // 外键表id

    //此字段不会存入数据库
    private String willignore;

    //在内部封装此方法可以直接找到所属的父级类
    public Parent getParent(DbManager db) throws DbException {
        return db.findById(Parent.class, parentId);
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        name = mName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mEmail) {
        email = mEmail;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long mParentId) {
        parentId = mParentId;
    }

    public String getWillignore() {
        return willignore;
    }

    public void setWillignore(String mWillignore) {
        willignore = mWillignore;
    }
}
