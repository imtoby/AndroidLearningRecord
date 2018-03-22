package com.syberos.learnandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.MessageFormat;

public class ReceivePersonActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_person);

        TextView textView = findViewById(R.id.showPerson);

        SendPersonUseSerializable personUseSerializable = (SendPersonUseSerializable)
                getIntent().getSerializableExtra("person_serializable_data");

        if (null != personUseSerializable) {
            textView.setText(MessageFormat.format("Person name: {0}\nPerson age: {1}",
                    personUseSerializable.getName(), personUseSerializable.getAge()));
        }

        SendPersonUseParcelable personUseParcelable =
                getIntent().getParcelableExtra("person_parcelable_data");

        if (null != personUseParcelable) {
            textView.setText(MessageFormat.format("Person name: {0}\nPerson age: {1}",
                    personUseParcelable.getName(), personUseParcelable.getAge()));
        }
    }
}
