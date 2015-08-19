/**
 * 
 */
package com.tincent.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * @author hui.wang
 * 
 */
public class DemoDaoGenerator {

	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(1, "com.tincent.dao");

		addNote(schema);

		new DaoGenerator().generateAll(schema, "../appdemo/src-gen");
	}

	private static void addNote(Schema schema) {

		Entity note = schema.addEntity("NoteRecord");
		note.addIdProperty();
		note.addStringProperty("NoteTitle").notNull();
		note.addStringProperty("NoteAuthor").notNull();
		note.addStringProperty("NoteContent").notNull();
		note.addLongProperty("TimeStamp").notNull();
		note.addDateProperty("CreateTime").notNull();
		note.addDateProperty("UpdateTime").notNull();

	}

}
