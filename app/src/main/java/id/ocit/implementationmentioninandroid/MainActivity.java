package id.ocit.implementationmentioninandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linkedin.android.spyglass.suggestions.SuggestionsResult;
import com.linkedin.android.spyglass.tokenization.QueryToken;
import com.linkedin.android.spyglass.tokenization.interfaces.QueryTokenReceiver;
import com.linkedin.android.spyglass.ui.RichEditorView;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements QueryTokenReceiver {

    private static final String BUCKET = "Mention-people";
    private RichEditorView editor;
    private Data.DataLoader data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] arr = {"rosid", "jaka", "ardi", "hasan", "bahri", "rezo", "andi", "rahma"};

        editor = (RichEditorView) findViewById(R.id.editor);
        editor.setQueryTokenReceiver(this);
        editor.setHint("Mention A Friend");
        data = new Data.DataLoader(arr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public List<String> onQueryReceived(final @NonNull QueryToken queryToken) {
        List<String> buckets = Collections.singletonList(BUCKET);
        List<Data> suggestions = data.getSuggestions(queryToken);
        SuggestionsResult result = new SuggestionsResult(queryToken, suggestions);
        editor.onReceiveSuggestionsResult(result, BUCKET);
        return buckets;
    }
}
