package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    Person[] list = new Person[0];
    ProgressBar progressBar;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        DataAsyncTask asyntask = new DataAsyncTask();
        asyntask.execute("https://reqres.in/api/users?page=2");

    }

    public class DataAsyncTask extends AsyncTask<String, String, Person[]> {
        Person[] list;

        @Override
        protected Person[] doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputstream = connection.getInputStream();

                BufferedReader rd = new BufferedReader(new InputStreamReader(inputstream));
                String line;
                String text = "";
                while ((line = rd.readLine()) != null) {
                    text += line;
                }

                JSONObject obj = new JSONObject(text);
                JSONArray posts = obj.getJSONArray("data");
                Gson gson = new Gson(); // khởi tạo Gson
                list = gson.fromJson(posts.toString(), Person[].class);

            } catch ( Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Person[] people) {
            super.onPostExecute(people);
            progressBar.setVisibility(View.INVISIBLE);

            Log.i("Data", list.length + "");

            PersonAdapter adapter = new PersonAdapter(list);
            RecyclerView rec = findViewById(R.id.rc_persons);
            RecyclerView.LayoutManager layout = new LinearLayoutManager(MainActivity.this);
            rec.setLayoutManager(layout);
            rec.setAdapter(adapter);
        }

    }

    public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

        Person[] persons;

        public PersonAdapter(Person[] persons) {
            this.persons = persons;
        }

        @NonNull
        @Override
        public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_person, parent, false);
            return new PersonHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
            Glide.with(MainActivity.this).load(persons[position].getAvatar()).into(holder.avatar);
            holder.tv_email.setText("Email:   " + persons[position].getEmail());
            holder.tv_id.setText(   "Id:         " + persons[position].getId());
            holder.tv_name.setText( "Name:  "+
                    persons[position].getFirst_name() + " "
                            + persons[position].getLast_name()
            );
            holder.progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        public int getItemCount() {
            return persons.length;
        }

        public class PersonHolder extends RecyclerView.ViewHolder {
            ImageView avatar;
            TextView tv_id;
            TextView tv_name;
            TextView tv_email;
            ProgressBar progressBar;

            public PersonHolder(@NonNull View itemView) {
                super(itemView);
                avatar = itemView.findViewById(R.id.avatar);
                tv_id = itemView.findViewById(R.id.tv_id);
                tv_name = itemView.findViewById(R.id.tv_name);
                tv_email = itemView.findViewById(R.id.tv_email);
                progressBar = itemView.findViewById(R.id.progressItem);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getAdapterPosition();
                    }
                });
            }

        }

    }

}