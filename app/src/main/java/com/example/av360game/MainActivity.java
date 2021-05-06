package com.example.av360game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button button,restart;
    RecyclerView counters;
    int randomNo;
    Random rand = new Random();
    List<Boolean> list = new ArrayList<>();
    AVAdapter avAdapter;
    int control =1;
    private  LinearLayoutManager linearLayoutManager;
    int mainCounter =0;
    int cc=0,winner;
    int previous;
    int flag=1;
    TextView tag, value;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button=findViewById(R.id.button);
        counters=findViewById(R.id.counter);
        restart=findViewById(R.id.restart);
        tag=findViewById(R.id.tag);
        value=findViewById(R.id.value);

        linearLayoutManager  =new LinearLayoutManager(this);
        counters.setLayoutManager(linearLayoutManager);
        counters.setAdapter(avAdapter);


        createRandom();
        setUp();
        //Log.i("Random -------------", String.valueOf(randomNo));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("----------Control----", String.valueOf(control));
                if(mainCounter==1){
                    if(control==1){
                        control=2;
                        mainCounter=0;
                        tag.setText("Chance : ");
                        value.setText("Player "+control);
                        Toast.makeText(MainActivity.this, "Chance of player 2", Toast.LENGTH_SHORT).show();
                        //avAdapter = new AVAdapter(control);
                    }
                    else if(control==2){
                        control=1;
                        mainCounter=0;
                        tag.setText("Chance : ");                    value.setText("Player "+control);
                        Toast.makeText(MainActivity.this, "Chance of player 1", Toast.LENGTH_SHORT).show();
                        //avAdapter = new AVAdapter(control);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "You can't pass your chance..", Toast.LENGTH_SHORT).show();
                }

            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUp();
            }
        });



    }

    private void setUp() {
        list.clear();
        mainCounter=0;
        previous=10;
        control=1;
        cc=0;
        createRandom();
        value.setText("Player "+1);
        Toast.makeText(MainActivity.this, "Chaliye shuru karte hai", Toast.LENGTH_LONG).show();



        for(int i=0; i<randomNo;i++){
            list.add(false);
        }
        avAdapter = new AVAdapter(list, getApplicationContext());
        counters.setAdapter(avAdapter);
    }

    private void createRandom() {
        randomNo = rand.nextInt(15);
        if(randomNo<9){
            createRandom();
        }
    }

    public void setControl(int i) {
        if(i==1){
            control=2;
            Toast.makeText(MainActivity.this, "Chance of player 2", Toast.LENGTH_SHORT).show();

            //avAdapter = new AVAdapter(control);
        }
        else if(i==2){
            control=1;
            Toast.makeText(MainActivity.this, "Chance of player 1", Toast.LENGTH_SHORT).show();

            //avAdapter = new AVAdapter(control);
        }
    }

    public class AVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Boolean> list;
        Context applicationContext;
        boolean m;
        //int control=1;
        //MainActivity obj = new MainActivity();
        public AVAdapter(List<Boolean> list, Context applicationContext) {
            this.list=list;
            this.applicationContext=applicationContext;
        }

    /*public AVAdapter(int control) {
        this.control=control;
        mainCounter=0;
    }*/

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            RecyclerView.ViewHolder viewHolder = null;

            View view =layoutInflater.inflate(R.layout.counter_row,parent,false);
            viewHolder = new HolderClass(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            m=list.get(position);
            configure((HolderClass) holder, position, m);
        }

        private void configure(final HolderClass h, final int position, boolean m) {
            h.number.setText(String.valueOf(position+1));
            h.myImage.setImageResource(R.drawable.white);

            h.myImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if(mainCounter==1){
                        if (position==previous+1 || position==previous-1){
                            list.set(position, true);
                            previous=position;
                            //Log.i("MainCounter --- ", String.valueOf(mainCounter));
                            mainCounter=mainCounter+1;
                        }
                        else{
                            flag=0;
                            //list.set(position, true);
                            Toast.makeText(applicationContext, "Not allowed here, try another block", Toast.LENGTH_LONG).show();
                        }
                    }*/
                    if(!list.get(position) && mainCounter==0){
                        if(control==1){

                            h.myImage.setImageResource(R.drawable.red);
                            cc=cc+1;
                            if(cc==list.size()){
                                //Log.i()
                                tag.setText("Winner : ");
                                value.setText("Player "+control);
                                Toast.makeText(applicationContext, "Player "+control+" wins", Toast.LENGTH_LONG).show();
                            }
                        }
                        if(control==2) {
                            h.myImage.setImageResource(R.drawable.green_cross);
                            cc=cc+1;
                            if(cc==list.size()){
                                tag.setText("Winner : ");
                                value.setText("Player "+control);
                                Toast.makeText(applicationContext, "Player "+control+" wins", Toast.LENGTH_LONG).show();
                            }
                        }
                            list.set(position, true);
                            previous=position;
                            //Log.i("MainCounter --- ", String.valueOf(mainCounter));
                            mainCounter=mainCounter+1;

                        if(position==list.size()-1){
                            if(mainCounter!=2){
                                if(list.get(position-1)){
                                    if(control==1){
                                        control=2;
                                        Log.i("Counter ----", String.valueOf(control));
                                        mainCounter=0;
                                        tag.setText("Chance : ");                    value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Chance of Player 2", Toast.LENGTH_SHORT).show();

                                        //applicationContext.setControl(1);

                                    }
                                    else if(control==2){
                                        control=1;
                                        Log.i("Counter ----", String.valueOf(control));
                                        mainCounter=0;
                                        tag.setText("Chance : ");                    value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Chance of Player 1", Toast.LENGTH_SHORT).show();

                                        //obj.setControl(2);
                                    }
                                }
                            }

                        }
                        if(position==0){
                            if(list.get(position+1)){
                                if(mainCounter!=2){
                                    if(control==1){
                                        control=2;
                                        Log.i("Counter ----", String.valueOf(control));
                                        mainCounter=0;
                                        tag.setText("Chance : ");                    value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Chance of Player 2", Toast.LENGTH_SHORT).show();

                                        //applicationContext.setControl(1);

                                    }
                                    else if(control==2){
                                        control=1;
                                        Log.i("Counter ----", String.valueOf(control));
                                        mainCounter=0;
                                        tag.setText("Chance : ");                    value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Chance of Player 1", Toast.LENGTH_SHORT).show();

                                        //obj.setControl(2);
                                    }
                                }

                            }
                        }
                        if(position>0 && position<list.size()-1){
                            if(mainCounter!=2){
                                if(list.get(position-1) && list.get(position+1)){
                                    if(control==1){
                                        control=2;
                                        Log.i("Counter ----", String.valueOf(control));
                                        mainCounter=0;
                                        tag.setText("Chance : ");                    value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Chance of Player 2", Toast.LENGTH_SHORT).show();

                                        //applicationContext.setControl(1);

                                    }
                                    else if(control==2){
                                        control=1;
                                        Log.i("Counter ----", String.valueOf(control));
                                        mainCounter=0;
                                        tag.setText("Chance : ");                    value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Chance of Player 1", Toast.LENGTH_SHORT).show();

                                        //obj.setControl(2);
                                    }
                                }
                            }

                        }

                        //Log.i("MainCounter after--- ", String.valueOf(mainCounter));
                        if(mainCounter==2){
                            if(control==1){
                                control=2;
                                Log.i("Counter ----", String.valueOf(control));
                                mainCounter=0;
                                tag.setText("Chance : ");                    value.setText("Player "+control);
                                Toast.makeText(applicationContext, "Chance of Player 2", Toast.LENGTH_SHORT).show();
                                //obj.setControl(1);
                            }
                            else if(control==2){
                                control=1;
                                Log.i("Counter ----", String.valueOf(control));
                                mainCounter=0;
                                tag.setText("Chance : ");                    value.setText("Player "+control);
                                Toast.makeText(applicationContext, "Chance of Player 1", Toast.LENGTH_SHORT).show();
                                //obj.setControl(2);

                            }
                        }
                    }
                    else if(mainCounter==1){
                        if(position==0){
                            if(list.get(position+1)){
                                if(control==1){
                                    h.myImage.setImageResource(R.drawable.red);
                                    cc=cc+1;
                                    if(cc==list.size()){
                                        tag.setText("Winner : ");
                                        value.setText("Player "+control);Toast.makeText(applicationContext, "Player "+control+" wins", Toast.LENGTH_LONG).show();
                                    }
                                }
                                if(control==2){
                                    h.myImage.setImageResource(R.drawable.green_cross);
                                    cc=cc+1;
                                    if(cc==list.size()){                                tag.setText("Winner : ");
                                        value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Player "+control+" wins", Toast.LENGTH_LONG).show();
                                    }
                                }
                                list.set(position, true);
                                previous=position;
                                //Log.i("MainCounter --- ", String.valueOf(mainCounter));
                                mainCounter=mainCounter+1;
                            }
                            else {
                                Toast.makeText(applicationContext, "You can't click on this block", Toast.LENGTH_SHORT).show();
                            }
                            if(mainCounter==2){
                                if(control==1){
                                    control=2;
                                    Log.i("Counter ----", String.valueOf(control));
                                    mainCounter=0;
                                    tag.setText("Chance : ");                    value.setText("Player "+control);Toast.makeText(applicationContext, "Chance of Player 2", Toast.LENGTH_SHORT).show();
                                    //obj.setControl(1);
                                }
                                else if(control==2){
                                    control=1;
                                    Log.i("Counter ----", String.valueOf(control));
                                    mainCounter=0;
                                    tag.setText("Chance : ");                    value.setText("Player "+control);Toast.makeText(applicationContext, "Chance of Player 1", Toast.LENGTH_SHORT).show();
                                    //obj.setControl(2);

                                }
                            }
                        }
                        else if(position==list.size()-1){
                            if(list.get(position-1)){
                                if(control==1){
                                    h.myImage.setImageResource(R.drawable.red);
                                    cc=cc+1;
                                    if(cc==list.size()){
                                        tag.setText("Winner : ");
                                        value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Player "+control+" wins", Toast.LENGTH_LONG).show();
                                    }
                                }
                                if(control==2){
                                    h.myImage.setImageResource(R.drawable.green_cross);
                                    cc=cc+1;
                                    if(cc==list.size()){
                                        tag.setText("Winner : ");
                                        value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Player "+control+" wins", Toast.LENGTH_LONG).show();
                                    }
                                }
                                list.set(position, true);
                                previous=position;
                                //Log.i("MainCounter --- ", String.valueOf(mainCounter));
                                mainCounter=mainCounter+1;
                            }
                            else {
                                Toast.makeText(applicationContext, "You can't click on this block", Toast.LENGTH_LONG).show();
                            }
                            if(mainCounter==2){
                                if(control==1){
                                    control=2;
                                    Log.i("Counter ----", String.valueOf(control));
                                    mainCounter=0;
                                    tag.setText("Chance : ");                    value.setText("Player "+control);Toast.makeText(applicationContext, "Chance of Player 2", Toast.LENGTH_SHORT).show();
                                    //obj.setControl(1);
                                }
                                else if(control==2){
                                    control=1;
                                    Log.i("Counter ----", String.valueOf(control));
                                    mainCounter=0;
                                    tag.setText("Chance : ");                    value.setText("Player "+control);Toast.makeText(applicationContext, "Chance of Player 1", Toast.LENGTH_SHORT).show();
                                    //obj.setControl(2);

                                }
                            }
                        }
                        else {
                            if(list.get(position-1)||list.get(position+1)){
                                if(control==1){
                                    h.myImage.setImageResource(R.drawable.red);
                                    cc=cc+1;
                                    if(cc==list.size()){
                                        tag.setText("Winner : ");
                                        value.setText("Player "+control);
                                        Toast.makeText(applicationContext, "Player "+control+" wins", Toast.LENGTH_LONG).show();
                                    }
                                }
                                if(control==2){
                                    h.myImage.setImageResource(R.drawable.green_cross);
                                    cc=cc+1;
                                    if(cc==list.size()){
                                        tag.setText("Winner : ");
                                        value.setText("Player "+control);
                                       Toast.makeText(applicationContext, "Player "+control+" wins", Toast.LENGTH_LONG).show();
                                    }
                                }
                                list.set(position, true);
                                previous=position;
                                //Log.i("MainCounter --- ", String.valueOf(mainCounter));
                                mainCounter=mainCounter+1;
                            }
                            else {
                                Toast.makeText(applicationContext, "You can't click on this block", Toast.LENGTH_LONG).show();
                            }
                            if(mainCounter==2){
                                if(control==1){
                                    control=2;
                                    Log.i("Counter ----", String.valueOf(control));
                                    mainCounter=0;
                                    tag.setText("Chance : ");                    value.setText("Player "+control);
                                    Toast.makeText(applicationContext, "Chance of Player 2", Toast.LENGTH_SHORT).show();
                                    //obj.setControl(1);
                                }
                                else if(control==2){
                                    control=1;
                                    Log.i("Counter ----", String.valueOf(control));
                                    mainCounter=0;
                                    tag.setText("Chance : ");                    value.setText("Player "+control);Toast.makeText(applicationContext, "Chance of Player 1", Toast.LENGTH_SHORT).show();
                                    //obj.setControl(2);

                                }
                            }
                        }

                    }

                    else {

                            Toast.makeText(applicationContext, "This is already clicked try another block", Toast.LENGTH_LONG).show();


                    }
                    Log.i("Position ----", String.valueOf(position));

                    /*for(boolean val:list){
                        if(val){
                            cc=cc+1;
                            Log.i()
                        }

                    }
                    if(cc==list.size()){
                        if(control==1){
                            winner=2;
                        }
                        if(control==2){
                            winner=1;
                        }
                        Toast.makeText(applicationContext, "Player : "+String.valueOf(winner)+" wins",Toast.LENGTH_LONG).show();
                    }*/

                    //list.set(position, false);

                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public class HolderClass extends RecyclerView.ViewHolder{
            LinearLayout mainLayout;
            ImageView myImage;
            TextView number;

            public HolderClass(@NonNull View itemView) {
                super(itemView);
                mainLayout = itemView.findViewById(R.id.main_layout);
                myImage = itemView.findViewById(R.id.image);
                number = itemView.findViewById(R.id.numbering);
            }
        }

    }

}