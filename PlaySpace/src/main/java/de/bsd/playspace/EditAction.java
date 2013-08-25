package de.bsd.playspace;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * // TODO: Document this
 *
 * @author Heiko W. Rupp
 */
public class EditAction extends ListActivity  implements AdapterView.OnItemSelectedListener {

   ListView listView ;
   List<String> items = new ArrayList<String>();
   private Spinner spinner;
   private EditText input;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      Intent intent = getIntent();
      if (intent!=null && intent.getStringArrayListExtra("commands")!=null) {
         List<String> commands = intent.getStringArrayListExtra("commands");
         for (String command : commands) {
            items.add(command.toLowerCase());
         }
      }

      setContentView(R.layout.edit_activity);

      spinner = (Spinner) findViewById(R.id.spinner);
      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
              R.array.itemSpinner, android.R.layout.simple_spinner_item);
      // Specify the layout to use when the list of choices appears
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      // Apply the adapter to the spinner
      spinner.setAdapter(adapter);
      spinner.setOnItemSelectedListener(this);

      listView = getListView();

      setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));

      input = (EditText) findViewById(R.id.editText);
   }



   public void add(View view) {

      String command = (String) spinner.getSelectedItem();
      command = command.toLowerCase();

      String tmp = input.getText().toString();
      if (tmp==null || tmp.isEmpty() && (command.startsWith("line") || command.startsWith("move"))) {
         tmp = "10";
      }

      command = command + ";" + tmp;
      items.add(command); // TODO add tmp too

      ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();
   }

   public void done(View v) {

      Intent resultData = new Intent();
      resultData.putStringArrayListExtra("commands", (ArrayList<String>) items);
      resultData.putExtra("bla",true);

      setResult(RESULT_OK, resultData);
      finish();
   }

   public void clear(View view) {
      items.clear();
      ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();
   }

   @Override
   public void onItemSelected(AdapterView<?> adapterView, View view, int position, long item) {
      // TODO: Customise this generated block

      if (item == 0 || item ==2) { // see Arrays.xml
         input.setText("90");
      }
      else {
         input.setText("10");
      }
   }

   @Override
   public void onNothingSelected(AdapterView<?> adapterView) {
      // TODO: Customise this generated block
   }
}
