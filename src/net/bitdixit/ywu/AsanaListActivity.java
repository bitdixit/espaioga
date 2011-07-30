package net.bitdixit.ywu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AsanaListActivity extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  Library library = new Library(this);
	  
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.asana_list_item, library.asanaList()));
	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String asana=((TextView) view).getText().toString();

		Intent intent = new Intent( AsanaListActivity.this, AsanaInfoActivity.class);
		intent.putExtra("asana", asana);
		startActivity(intent);
		}
	  });
	  
	  
	}
}
