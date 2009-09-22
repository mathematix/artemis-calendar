package com.ics.tcg.web.database;

import java.util.List;

import com.ics.tcg.web.database.bean.Records;

public interface RecordsDAO {
	/**
	 * get all records of specific event
	 * 
	 * @param calendarid
	 */
	public List<Records> getRecords(Integer calendarid);

	/**
	 * update one record
	 * 
	 * @param record
	 */
	public void updateRecord(Records record);
}
