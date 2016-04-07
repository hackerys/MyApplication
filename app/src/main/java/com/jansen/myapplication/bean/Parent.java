package com.jansen.myapplication.bean;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.Date;
import java.util.List;

/**
 * Created Jansen on 2016/3/30.
 */
//name为数据表的名字，oncreated保证了字段在表中不会有重复的值，
@Table(name = "parent", onCreated = "CREATE UNIQUE INDEX index_name ON parent(name,email)")
public class Parent {
    //name为字段名,isid为true表明为此表的自增长id
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "date")
    private Date mDate;

    //在内部封装此方法可以直接找到所有父类下的所有子类
    public List<Child> getChilds(DbManager db) throws DbException {
        return db.selector(Child.class).where("parentId", "=", id).findAll();
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }
}
