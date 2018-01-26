package com.axp.axpseller.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.axp.axpseller.dao.AcquireScoreDao;
import com.axp.axpseller.dao.AdvertImgDao;
import com.axp.axpseller.dao.IpInfoDao;
import com.axp.axpseller.dao.LocationDao;
import com.axp.axpseller.dao.ZoneDao;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/**
 * GreenDao DaoMaster
 * 用于创建DaoSession
 * xu  2016/4/25.
 */
public class DaoMaster extends AbstractDaoMaster {

    public static final int SCHEMA_VERSION = 1005;

    /**
     * Creates underlying database table using DAOs.
     */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        IpInfoDao.createTable(db, ifNotExists);
        LocationDao.createTable(db, ifNotExists);
        AcquireScoreDao.createTable(db, ifNotExists);
        AdvertImgDao.createTable(db,ifNotExists);
        ZoneDao.createTable(db,ifNotExists);
    }

    /**
     * Drops underlying database table using DAOs.
     */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        LocationDao.dropTable(db, ifExists);
        IpInfoDao.dropTable(db, ifExists);
        AcquireScoreDao.dropTable(db, ifExists);
        AdvertImgDao.dropTable(db, ifExists);
        ZoneDao.dropTable(db, ifExists);
    }

    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {

            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");

            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);

        registerDaoClass(LocationDao.class);
        registerDaoClass(IpInfoDao.class);
        registerDaoClass(AcquireScoreDao.class);
        registerDaoClass(AdvertImgDao.class);
        registerDaoClass(ZoneDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
}
