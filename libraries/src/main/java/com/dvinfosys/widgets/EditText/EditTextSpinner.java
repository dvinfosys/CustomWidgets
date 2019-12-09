package com.dvinfosys.widgets.EditText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.dvinfosys.widgets.Exception.CustomException;
import com.dvinfosys.widgets.R;

import java.util.ArrayList;

public class EditTextSpinner extends AppCompatEditText implements View.OnClickListener {
    private Context context;
    private ArrayList<String> options = new ArrayList<>();

    public EditTextSpinner(Context context) {
        super(context);
        this.context = context;
        try {
            init(null);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public EditTextSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        try {
            init(attrs);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public EditTextSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        try {
            init(attrs);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    private void init(AttributeSet attrs) throws CustomException {
        this.setOnClickListener(this);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
            String getFontName = array.getString(R.styleable.CustomEditText_font_name);
            if (getFontName != null) {
                try {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + getFontName);
                    setTypeface(typeface);
                } catch (Exception e) {
                    throw new CustomException("Font Not Found Exception");
                }
            }
            array.recycle();
        }
    }

    private PopupWindow popupWindowsort(Context context) {

        // initialize a pop up window type
        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setWidth(this.getWidth());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line,
                options);
        // the drop down list is a list view
        ListView listViewSort = new ListView(context);

        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter);

        // set on item selected
        listViewSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditTextSpinner.this.setText(options.get(position));
                popupWindow.dismiss();
            }
        });

        // some other visual settings for popup window
        popupWindow.setFocusable(true);
        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the listview as popup content
        popupWindow.setContentView(listViewSort);

        return popupWindow;
    }

    @Override
    public void onClick(View v) {
        if (v == this) {
            PopupWindow window = popupWindowsort(v.getContext());
            window.showAsDropDown(v, 0, 0);
        }
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
