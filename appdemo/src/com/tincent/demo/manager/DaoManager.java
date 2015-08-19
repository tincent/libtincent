/**
 * 
 */
package com.tincent.demo.manager;

import com.tincent.dao.DaoMaster;
import com.tincent.dao.DaoMaster.DevOpenHelper;
import com.tincent.dao.DaoSession;
import com.tincent.demo.util.Debug;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库访问对象管理器
 * 
 * @author hui.wang
 * @date 2015.4.13
 * 
 */
public class DaoManager {
	private static DaoManager daoManager;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;

	/**
	 * 私有构造
	 */
	private DaoManager() {

	}

	/**
	 * 获取单子实例
	 * 
	 * @return
	 */
	public static DaoManager getInstance() {
		if (daoManager == null) {
			daoManager = new DaoManager();
		}
		return daoManager;
	}

	/**
	 * 初始化数据库访问对象
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "demo.db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
	}

	/**
	 * 获取数据库对象
	 * 
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		return db;
	}

	/**
	 * 获取数据库访问对象
	 * 
	 * @return
	 */
	public DaoSession getDaoSession() {
		return daoSession;
	}

	/**
	 * 清空表中数据
	 */
	public void cleanTable() {
		Debug.o(new Exception(), "clean db table");
		daoSession.getNoteRecordDao().deleteAll();
	}
}
