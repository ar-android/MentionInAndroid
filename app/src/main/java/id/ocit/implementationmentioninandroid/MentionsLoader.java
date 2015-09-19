package id.ocit.implementationmentioninandroid;

import com.linkedin.android.spyglass.mentions.Mentionable;
import com.linkedin.android.spyglass.tokenization.QueryToken;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ocit on 9/19/15.
 */

public abstract class MentionsLoader<T extends Mentionable> {

    protected T[] mData;
    private static final String TAG = MentionsLoader.class.getSimpleName();

    public MentionsLoader(String[] arr) {
        mData = loadData(getDataArr(arr));
    }

    private JSONArray getDataArr(String[] arr) {
        JSONArray jArr = new JSONArray();
        for (int i = 0; i < arr.length; i++){
            jArr.put("@"+arr[i]);
        }
        return jArr;
    }

    public abstract T[] loadData(JSONArray arr);

    // Returns a subset
    public List<T> getSuggestions(QueryToken queryToken) {
        String prefix = queryToken.getKeywords().toLowerCase();
        List<T> suggestions = new ArrayList<>();
        if (mData != null) {
            for (T suggestion : mData) {
                String name = suggestion.getSuggestiblePrimaryText().toLowerCase();
                if (name.startsWith(prefix)) {
                    suggestions.add(suggestion);
                }
            }
        }
        return suggestions;
    }


}