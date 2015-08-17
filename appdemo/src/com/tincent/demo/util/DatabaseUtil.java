/**
 * 
 */
package com.tincent.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.tincent.demo.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author hui.wang
 * 
 */
public class DatabaseUtil {
	private static final String DATABASE_PATH = "/data/data/com.tincent.demo/databases";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "diettips.db";

	private static String OUTPUT_FILENAME = DATABASE_PATH + "/" + DATABASE_NAME;

	private SQLiteDatabase database;

	private static DatabaseUtil instance;

	public static DatabaseUtil getInstatnce() {
		if (instance == null) {
			instance = new DatabaseUtil();
		}
		return instance;
	}

	private DatabaseUtil() {
		
	}

	public void init(Context context) {

		File file = new File(OUTPUT_FILENAME);
		if (file.exists()) {
			database = SQLiteDatabase.openOrCreateDatabase(OUTPUT_FILENAME, null);
			if (database.getVersion() != DATABASE_VERSION) {
				database.close();
				file.delete();
			}
		}
		try {
			buildDatabase(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildDatabase(Context context) throws Exception {
		InputStream input = context.getResources().openRawResource(R.raw.diettips);
		File file = new File(OUTPUT_FILENAME);

		File dir = new File(DATABASE_PATH);
		if (!dir.exists()) {
			if (!dir.mkdir()) {
				throw new Exception("创建失败");
			}
		}

		if (!file.exists()) {
			try {
				OutputStream output = new FileOutputStream(OUTPUT_FILENAME);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = input.read(buffer)) > 0) {
					output.write(buffer, 0, length);
				}
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 查询数据库
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public Cursor query(String selection, String[] selectionArgs) {
		database = SQLiteDatabase.openOrCreateDatabase(OUTPUT_FILENAME, null);
		String[] columns = { "_id", "TIPS_NO", "TIPS_CONTENT", "CANCER_FLAG", "CORONARY_FLAG", "BLOODPRESSURE_FLAG", "DIABETES_FLAG", "FAT_FLAG", "LIVER_FLAG", "RENAL_FLAG", "OLDWOMAN_FLAG",
				"VALVE_FLAG" };
		return database.query(true, "TIPS", columns, selection, selectionArgs, null, null, null, null);
	}
}
