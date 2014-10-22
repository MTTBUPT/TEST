package cn.bupt.notes;

import android.R.integer;
import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import cn.bupt.notes.db.NotesDB;


public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new NotesDB(this);
        dbRead = db.getReadableDatabase();
        
//        startActivityForResult(intent, requestCode)
        adapter = new SimpleCursorAdapter(this, R.layout.notes_list_cell, null, new String[] {NotesDB.COLUMN_NAME_NOTE_NAME}, new int[]{R.id.tvDate});
        setListAdapter(adapter);
        refreshNotesListView();
    }
    public void refreshNotesListView() {
    	adapter.changeCursor(dbRead.query(NotesDB.TABLE_NAME_NOTES, null, null, null, null, null, null));
		
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private SimpleCursorAdapter adapter = null;
    private NotesDB db;
    private SQLiteDatabase dbRead;
    private integer test;
    
}
