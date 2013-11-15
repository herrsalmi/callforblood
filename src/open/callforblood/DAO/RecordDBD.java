package open.callforblood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RecordDBD {

	private SQLiteDatabase bdd;

	private SQLiteDatabaseHandler SQLiteDb;
	private static final int NUM_COL_ID = 0;
	private static final int NUM_COL_EMAIL = 1;
	private static final int NUM_COL_PASSWORD = 2;

	public RecordDBD(Context context) {
		SQLiteDb = new SQLiteDatabaseHandler(context);
	}

	public void open() {
		bdd = SQLiteDb.getWritableDatabase();
	}

	public void close() {
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	// Adding new recored
	public long insertContactMail(String email, String password) {
		ContentValues values = new ContentValues();
		values.put(SQLiteDatabaseHandler.USER_ID, "0");
		values.put(SQLiteDatabaseHandler.MAIL, email);
		values.put(SQLiteDatabaseHandler.PASSWORD, password);

		return bdd.insert(SQLiteDatabaseHandler.TABLE_USER, null, values);

	}

	public int removeUserWithID(String id) {

		return bdd.delete(SQLiteDatabaseHandler.TABLE_USER,
				SQLiteDatabaseHandler.USER_ID + " = '" + id + "'", null);
	}

	public int updateContactIdByMail(String email, String id) {
		ContentValues values = new ContentValues();
		values.put(SQLiteDatabaseHandler.USER_ID, id);
		values.put(SQLiteDatabaseHandler.MAIL, email);

		return bdd.update(SQLiteDatabaseHandler.TABLE_USER, values,
				SQLiteDatabaseHandler.MAIL + " = " + email, null);
	}

	
	public String getEmailWithDefaultId(){
		Cursor c = bdd.query(SQLiteDatabaseHandler.TABLE_USER, new String[] {
				SQLiteDatabaseHandler.USER_ID, SQLiteDatabaseHandler.MAIL,
				SQLiteDatabaseHandler.PASSWORD }, SQLiteDatabaseHandler.USER_ID + " = '0' ", null, null, null, null);
		return cursorToIMPEntity(c).getEmail();
		
	}
	
	public IMPEntity getLoginData(String email) {
		Cursor c = bdd.query(SQLiteDatabaseHandler.TABLE_USER, new String[] {
				SQLiteDatabaseHandler.USER_ID, SQLiteDatabaseHandler.MAIL,
				SQLiteDatabaseHandler.PASSWORD }, SQLiteDatabaseHandler.MAIL + " LIKE \""
				+ email + "\"", null, null, null, null);
		return cursorToIMPEntity(c);
	}

	private IMPEntity cursorToIMPEntity(Cursor c) {
		if (c.getCount() == 0)
			return null;

		c.moveToFirst();
		IMPEntity entity = new IMPEntity();
		entity.setId(c.getString(NUM_COL_ID));
		entity.setPassword(c.getString(NUM_COL_PASSWORD));
		entity.setEmail(c.getString(NUM_COL_EMAIL));
		c.close();

		return entity;
	}

}
