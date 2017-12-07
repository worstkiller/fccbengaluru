package com.android.buffer.fccbengaluru.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.model.EventEntryModel;
import com.android.buffer.fccbengaluru.util.Constants;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by incred on 7/12/17.
 */

public class EventEntryActivity extends BaseActivity {

    @BindView(R.id.etEventTitle)
    EditText mEtEventTitle;
    @BindView(R.id.etEventDate)
    EditText mEtEventDate;
    @BindView(R.id.etEventPinCode)
    EditText mEtEventPinCode;
    @BindView(R.id.etEventAddress)
    EditText mEtEventAddress;
    @BindView(R.id.etEventDetails)
    EditText mEtEventDetails;
    @BindView(R.id.etEventImage)
    EditText mEtEventImage;
    @BindView(R.id.btnContinue)
    Button mBtnContinue;
    DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_entry);
        ButterKnife.bind(this);
        initFirebase();
    }

    private void initFirebase() {
        //get a reference to the online database
        mFirebaseDatabase = FirebaseDatabase.getInstance()
                .getReference(Constants.DATABASE_REFERENCE_EVENT);

    }

    @OnClick(R.id.btnContinue)
    public void onViewClicked() {
        //make server upload call
        if (isValidate()) {
            showProgressDialog();
            mFirebaseDatabase
                    .push()
                    .setValue(getEventEntryModel(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(final DatabaseError databaseError, final DatabaseReference databaseReference) {
                            hideProgressDialog();
                            if (databaseError == null) {
                                showSnackBar("Data inserted successfully");
                            } else {
                                showSnackBar(getString(R.string.error_common));
                            }
                        }
                    });
        }
    }

    private EventEntryModel getEventEntryModel() {
        //here return a event model
        EventEntryModel eventEntryModel = new EventEntryModel();
        eventEntryModel.setAddress(mEtEventAddress.getText().toString());
        eventEntryModel.setTitle(mEtEventTitle.getText().toString());
        eventEntryModel.setDate(mEtEventDate.getText().toString());
        eventEntryModel.setImageUrl(mEtEventImage.getText().toString());
        eventEntryModel.setPinCode(mEtEventPinCode.getText().toString());
        eventEntryModel.setShortDetails(mEtEventDetails.getText().toString());
        return eventEntryModel;
    }

    private boolean isValidate() {
        //validate the user input here
        if (mEtEventTitle.length() <= 0) {
            showSnackBar(getString(R.string.error_input_common));
            return false;
        }
        if (mEtEventDate.length() <= 0) {
            showSnackBar(getString(R.string.error_input_common));
            return false;
        }
        if (mEtEventPinCode.length() < 6) {
            showSnackBar(getString(R.string.error_input_common));
            return false;
        }
        if (mEtEventAddress.length() <= 0) {
            showSnackBar(getString(R.string.error_input_common));
            return false;
        }
        if (mEtEventDetails.length() <= 0) {
            showSnackBar(getString(R.string.error_input_common));
            return false;
        }
        if (mEtEventImage.length() <= 0) {
            showSnackBar(getString(R.string.error_input_common));
            return false;
        }
        return true;
    }
}
