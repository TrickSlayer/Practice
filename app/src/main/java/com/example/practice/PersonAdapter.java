//package com.example.practice;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import javax.net.ssl.HttpsURLConnection;
//
//public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder>{
//
//    Person[] persons;
//
//    public PersonAdapter(Person[] persons) {
//        this.persons = persons;
//    }
//
//    @NonNull
//    @Override
//    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_person, parent, false);
//        return new PersonHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
//        Glide.with(MainActivity).load(persons[position].getAvatar()).into(holder.avatar);
//        holder.url = persons[position].getAvatar();
//        holder.tv_email.setText(persons[position].getEmail());
//        holder.tv_id.setText(persons[position].getId());
//        holder.tv_name.setText(
//                persons[position].getFirst_name() + " "
//                      + persons[position].getLast_name()
//                );
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return persons.length;
//    }
//
//    public class PersonHolder extends RecyclerView.ViewHolder {
//        ImageView avatar;
//        TextView tv_id;
//        TextView tv_name;
//        TextView tv_email;
//        String url;
//        ProgressBar progressBar;
//
//        public PersonHolder(@NonNull View itemView) {
//            super(itemView);
//            avatar = itemView.findViewById(R.id.avatar);
//            tv_id = itemView.findViewById(R.id.tv_id);
//            tv_name = itemView.findViewById(R.id.tv_name);
//            tv_email = itemView.findViewById(R.id.tv_email);
//            progressBar = itemView.findViewById(R.id.progressItem);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int pos = getAdapterPosition();
//                }
//            });
//        }
//
//        public void LoadImg(){
//            DownloadAsyntask asyntask = new DownloadAsyntask();
//            asyntask.execute(url);
//        }
//
//        public class  DownloadAsyntask extends AsyncTask<String, String, Bitmap> {
//            Bitmap bitmap;
//
//            @Override
//            protected Bitmap doInBackground(String... strings) {
//
//                try{
//                    URL url = new URL(strings[0]);
//                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection() ;
//                    InputStream inputStream = connection.getInputStream();
//                    bitmap = BitmapFactory.decodeStream(inputStream);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return bitmap;
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            protected void onPostExecute(Bitmap bitmap) {
//                super.onPostExecute(bitmap);
//                progressBar.setVisibility(View.INVISIBLE);
//                avatar.setVisibility(View.VISIBLE);
//                avatar.setImageBitmap(bitmap);
//            }
//        }
//    }
//
//}
