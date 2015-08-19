package com.tincent.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.tincent.dao.NoteRecord;

import com.tincent.dao.NoteRecordDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig noteRecordDaoConfig;

    private final NoteRecordDao noteRecordDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        noteRecordDaoConfig = daoConfigMap.get(NoteRecordDao.class).clone();
        noteRecordDaoConfig.initIdentityScope(type);

        noteRecordDao = new NoteRecordDao(noteRecordDaoConfig, this);

        registerDao(NoteRecord.class, noteRecordDao);
    }
    
    public void clear() {
        noteRecordDaoConfig.getIdentityScope().clear();
    }

    public NoteRecordDao getNoteRecordDao() {
        return noteRecordDao;
    }

}