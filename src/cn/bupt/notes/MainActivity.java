package cn.bupt.notes;

import android.R.integer;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cn.bupt.notes.db.NotesDB;


public class MainActivity extends ListActivity {
	private OnClickListener btnAddNote_clickHandler = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivityForResult(new Intent(MainActivity.this, AtyEditNote.class), REQUEST_CODE_ADD_NOTE);
			
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new NotesDB(this);
        dbRead = db.getReadableDatabase();
        
//        startActivityForResult(intent, requestCode)
        adapter = new SimpleCursorAdapter(this, R.layout.notes_list_cell, null, new String[] {NotesDB.COLUMN_NAME_NOTE_NAME}, new int[]{R.id.tvName,R.id.tvDate});
        setListAdapter(adapter);
        refreshNotesListView();
        
        findViewById(R.id.btnAddNote).setOnClickListener(btnAddNote_clickHandler);
        
        
    }
    
    
    public void refreshNotesListView() {
    	adapter.changeCursor(dbRead.query(NotesDB.TABLE_NAME_NOTES, null, null, null, null, null, null));
//		刷新列表
	}
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	Cursor c = adapter.getCursor();
    	
    	c.moveToPosition(position);
    	
    	Intent i = new Intent(MainActivity.this,AtyEditNote.class);
    	i.putExtra(AtyEditNote.EXTRA_NOTE_ID, c.getColumnIndex(NotesDB.COLUMN_NAME_ID));
    	i.putExtra(AtyEditNote.EXTRA_NOTE_NAME,c.getString(c.getColumnIndex(NotesDB.COLUMN_NAME_NOTE_NAME))) ;
    	i.putExtra(AtyEditNote.EXTRA_NOTE_CONTENT,c.getString(c.getColumnIndex(NotesDB.COLUMN_NAME_NOTE_CONTENT))) ;
    	startActivityForResult(i, REQUEST_CODE_EDIT_NOTE);
    	super.onListItemClick(l, v, position, id);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	switch (requestCode) {
    	
		case REQUEST_CODE_ADD_NOTE:
		case REQUEST_CODE_EDIT_NOTE:	
			if (requestCode == Activity.RESULT_OK) {
				refreshNotesListView();
				
				
			}
			
			break;

		default:
			break;
		}

    	super.onActivityResult(requestCode, resultCode, data);
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
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_EDIT_NOTE = 1;
    
}
