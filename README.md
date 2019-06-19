# Android Widgets

## Add it in your root build.gradle at the end of repositories:

          allprojects {
              repositories {
                ...
                maven { url 'https://jitpack.io' }
              }
            }

##  Add the dependency
```groovy
dependencies {
      implementation 'com.github.dvinfosys:Android-Widgets:1.3'
}
```
Check out [Android-Widgets releases](https://github.com/dvinfosys/Android-Widgets/releases) to see more unstable versions.
	
you could customize following UI controls in your Android application

* NormalTextView, HeadingTextView, BlackTextView, ExtraBoldTextView, ItalicTextView, LightTextView, SemiBoldTextView, ThinTextView
* NormalEditText
* NormalCheckBox
* NormalButton
* NormalRadioButton
* NormalSwitch
* NormalToggleButton
* CircleImageView

Usage
-----

TextView

TextView (NormalTextView)
```xml
<com.dvinfosys.widgets.TextView.NormalTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"/>
```

Heading TextView (HeadingTextView)
```xml
<com.dvinfosys.widgets.TextView.HeadingTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"/>
```

Button (NormalButton)
```xml
<com.dvinfosys.widgets.TextView.NormalButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"/>
```

EditText (NormalEditText)
```xml
<com.dvinfosys.widgets.TextView.NormalEditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"/>
```

RadioButton (NormalRadioButton)
```xml
<com.dvinfosys.widgets.TextView.NormalRadioButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"/>
```

CheckBox (NormalCheckBox)
```xml
<com.dvinfosys.widgets.TextView.NormalCheckBox
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"/>
```

ToggleButton (NormalToggleButton)
```xml
<com.dvinfosys.widgets.TextView.NormalToggleButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"/>
```

Switch (NormalSwitch)
```xml
<com.dvinfosys.widgets.TextView.NormalSwitch
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"/>
```

CircleImageView
```xml
<com.dvinfosys.widgets.ImageView.CircleImageView
                android:id="@+id/thumbnail"
                android:layout_width="100dp"
                android:layout_height="100dp"
                android:background="?attr/selectableItemBackgroundBorderless"
                android:clickable="true"
                android:scaleType="centerCrop"
                android:src="@mipmap/ic_launcher"
                app:dv_border_color="#949494"
                app:dv_border_width="2dp" />
```

NOTE
-----

Widgets also contains some added fonts with the library,
```java
Poppins.ttf
Poppins_Black.ttf
Poppins_Bold.ttf
Poppins_ExtraBold.ttf
Poppins_Italic.ttf
Poppins_Light.ttf
Poppins_SemiBold.ttf
Poppins_Thin.ttf
```

## Author

DV Infosys, dvinfosys0@gmail.com

Checkout my other contributions, https://github.com/dvinfosys?tab=repositories
