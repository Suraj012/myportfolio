package com.example.user.myapps1st;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.user.myapps1st.Database.DatabaseHelper;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity{
	 DatabaseHelper mydb;
	 EditText name, email, username, password, contact;
	 Button register, cancel;
	 RadioGroup gender;
	 Spinner spinner;
	 ArrayAdapter<String> dataAdapter;
	 Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_form);
		mydb = new DatabaseHelper(Register.this);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Register Account");
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		register = (Button) findViewById(R.id.register);
		cancel = (Button) findViewById(R.id.cancel);
		name = (EditText) findViewById(R.id.name);
		email = (EditText) findViewById(R.id.email);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		contact = (EditText) findViewById(R.id.number);
		gender = (RadioGroup) findViewById(R.id.gender);
		spinner = (com.rey.material.widget.Spinner) findViewById(R.id.spinner);

		List<String> lists = new ArrayList<String>();
		lists.add("Male");
		lists.add("Female");
		dataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lists);
		dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

        cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Register.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		AddData();
	}
	
	
	public void AddData(){	
 
	register.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String namevalue = name.getText().toString();
			String emailvalue = email.getText().toString();
			String usernamevalue = username.getText().toString();
			String passwordvalue = password.getText().toString();
			String contactvalue = contact.getText().toString();
			String gendervalue = spinner.getSelectedItem().toString();
			Log.e("Gender", gendervalue);
			if (isValidMail(emailvalue) && isValidNumber(contactvalue)) {
				boolean insert = mydb.insertUserInfo(namevalue, emailvalue, gendervalue, usernamevalue, passwordvalue, contactvalue);
				if (insert == true) {
					Toast.makeText(Register.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
					finish();
				} else
					Toast.makeText(Register.this, "Unsuccessfull", Toast.LENGTH_LONG).show();

			}else{
				Toast.makeText(Register.this, "Enter valid information", Toast.LENGTH_LONG).show();
			}
		}
	});
	}

	private boolean isValidMail(String email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	private boolean isValidNumber(String contact){
		return Patterns.PHONE.matcher(contact).matches();
	}
}
