package com.sms.repository;

import java.sql.ResultSet;

public interface IDBModel {
	/// this is used by Database context for Fill fetched data
	/// from Database
	public abstract void fillObject(ResultSet rs);
}
