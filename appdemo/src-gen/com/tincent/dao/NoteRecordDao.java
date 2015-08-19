package com.tincent.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.tincent.dao.NoteRecord;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOTE_RECORD".
*/
public class NoteRecordDao extends AbstractDao<NoteRecord, Long> {

    public static final String TABLENAME = "NOTE_RECORD";

    /**
     * Properties of entity NoteRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property NoteTitle = new Property(1, String.class, "NoteTitle", false, "NOTE_TITLE");
        public final static Property NoteAuthor = new Property(2, String.class, "NoteAuthor", false, "NOTE_AUTHOR");
        public final static Property NoteContent = new Property(3, String.class, "NoteContent", false, "NOTE_CONTENT");
        public final static Property TimeStamp = new Property(4, long.class, "TimeStamp", false, "TIME_STAMP");
        public final static Property CreateTime = new Property(5, java.util.Date.class, "CreateTime", false, "CREATE_TIME");
        public final static Property UpdateTime = new Property(6, java.util.Date.class, "UpdateTime", false, "UPDATE_TIME");
    };


    public NoteRecordDao(DaoConfig config) {
        super(config);
    }
    
    public NoteRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOTE_RECORD\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NOTE_TITLE\" TEXT NOT NULL ," + // 1: NoteTitle
                "\"NOTE_AUTHOR\" TEXT NOT NULL ," + // 2: NoteAuthor
                "\"NOTE_CONTENT\" TEXT NOT NULL ," + // 3: NoteContent
                "\"TIME_STAMP\" INTEGER NOT NULL ," + // 4: TimeStamp
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 5: CreateTime
                "\"UPDATE_TIME\" INTEGER NOT NULL );"); // 6: UpdateTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOTE_RECORD\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, NoteRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getNoteTitle());
        stmt.bindString(3, entity.getNoteAuthor());
        stmt.bindString(4, entity.getNoteContent());
        stmt.bindLong(5, entity.getTimeStamp());
        stmt.bindLong(6, entity.getCreateTime().getTime());
        stmt.bindLong(7, entity.getUpdateTime().getTime());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public NoteRecord readEntity(Cursor cursor, int offset) {
        NoteRecord entity = new NoteRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // NoteTitle
            cursor.getString(offset + 2), // NoteAuthor
            cursor.getString(offset + 3), // NoteContent
            cursor.getLong(offset + 4), // TimeStamp
            new java.util.Date(cursor.getLong(offset + 5)), // CreateTime
            new java.util.Date(cursor.getLong(offset + 6)) // UpdateTime
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, NoteRecord entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNoteTitle(cursor.getString(offset + 1));
        entity.setNoteAuthor(cursor.getString(offset + 2));
        entity.setNoteContent(cursor.getString(offset + 3));
        entity.setTimeStamp(cursor.getLong(offset + 4));
        entity.setCreateTime(new java.util.Date(cursor.getLong(offset + 5)));
        entity.setUpdateTime(new java.util.Date(cursor.getLong(offset + 6)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(NoteRecord entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(NoteRecord entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
