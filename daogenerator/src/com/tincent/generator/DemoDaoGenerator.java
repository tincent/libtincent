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

		addCarePlan(schema);

		new DaoGenerator().generateAll(schema, "../appdemo/src-gen");
	}

	private static void addCarePlan(Schema schema) {

		Entity motionType = schema.addEntity("MotionType");
		motionType.addIdProperty();
		motionType.addStringProperty("PlanId").notNull();
		motionType.addStringProperty("PatientId").notNull();
		motionType.addStringProperty("PlanTypeId").notNull();
		motionType.addStringProperty("PlanTypeName").notNull();
		motionType.addStringProperty("MotionTypeId").notNull();
		motionType.addStringProperty("MotionTypeName").notNull();
		motionType.addIntProperty("MotionDuration").notNull();
		motionType.addIntProperty("MotionInterval").notNull();
		motionType.addIntProperty("MotionFrequency").notNull();
		motionType.addStringProperty("ReservedOne").notNull();
		motionType.addStringProperty("ReservedTwo").notNull();

		Entity motionRecord = schema.addEntity("MotionRecord");
		motionRecord.addIdProperty();
		motionRecord.addStringProperty("PlanId").notNull();
		motionRecord.addStringProperty("PatientId").notNull();
		motionRecord.addStringProperty("PlanTypeId").notNull();
		motionRecord.addStringProperty("PlanTypeName").notNull();
		motionRecord.addStringProperty("MotionTypeId").notNull();
		motionRecord.addStringProperty("MotionTypeName").notNull();
		motionRecord.addDateProperty("MotionDay").notNull();
		motionRecord.addIntProperty("MotionDuration").notNull();
		motionRecord.addStringProperty("MotionWeek").notNull();

	}

}
