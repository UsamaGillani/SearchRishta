package com.techroof.searchrishta.ChatBot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.techroof.searchrishta.Model.Conv;
import com.techroof.searchrishta.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConvesationActivity extends AppCompatActivity {

    private RecyclerView mConvList;
    private DatabaseReference mConvDatabase;
    private DatabaseReference mMessageDatabase;
    private DatabaseReference mUsersDatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convesation);

        mConvList = findViewById(R.id.chats_list);
        mAuth = FirebaseAuth.getInstance();

        try {
            mCurrent_user_id = mAuth.getCurrentUser().getUid();
            mConvDatabase = FirebaseDatabase.getInstance().getReference().child("chat").child(mCurrent_user_id);

            mConvDatabase.keepSynced(true);
            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users");
            mMessageDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(mCurrent_user_id);
            mUsersDatabase.keepSynced(true);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConvesationActivity.this);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);

            mConvList.setHasFixedSize(true);
            mConvList.setLayoutManager(linearLayoutManager);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();


        try {

            Query conversationQuery = mConvDatabase.orderByChild("timestamp");

            final FirebaseRecyclerAdapter<Conv, ConvViewHolder> firebaseConvAdapter =
                    new FirebaseRecyclerAdapter<Conv, ConvViewHolder>(
                            Conv.class,
                            R.layout.users_single_layout,
                            ConvViewHolder.class,
                            conversationQuery
                    ) {
                        @Override
                        protected void populateViewHolder(final ConvViewHolder convViewHolder,
                                                          final Conv conv, final int i) {

                            final String list_user_id = getRef(i).getKey();

                            Query lastMessageQuery = mMessageDatabase.child(list_user_id).limitToLast(1);

                            lastMessageQuery.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                    String data = dataSnapshot.child("message").getValue().toString();
                                    convViewHolder.setMessage(data, conv.isSeen());

                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                            mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    try {
                                        final String userName = dataSnapshot.child("name").getValue().toString();
                                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();

                                        if (dataSnapshot.hasChild("online")) {

                                            String userOnline = dataSnapshot.child("online").getValue().toString();
                                            convViewHolder.setUserOnline(userOnline);

                                        }

                                        convViewHolder.setName(userName);
                                        convViewHolder.setUserImage(userThumb, getApplicationContext());

                                        convViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                                                chatIntent.putExtra("userid", list_user_id);
                                                chatIntent.putExtra("user_name", userName);
                                                startActivity(chatIntent);

                                            }
                                        });
                                        convViewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                                            @Override
                                            public boolean onLongClick(View view) {
                                                notifyDataSetChanged();
                                                convViewHolder.mView.setVisibility(View.GONE);
                                                Toast.makeText(getApplicationContext(), "long clicked", Toast.LENGTH_SHORT).show();
                                                return false;

                                            }
                                        });


                                    } catch (Exception e) {
                                        // Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    };

            mConvList.setAdapter(firebaseConvAdapter);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    public static class ConvViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        Context context;
        View mView;

        public ConvViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setMessage(String message, boolean isSeen) {

            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(message);

            if (!isSeen) {
                userStatusView.setTypeface(userStatusView.getTypeface(), Typeface.BOLD);
            } else {
                userStatusView.setTypeface(userStatusView.getTypeface(), Typeface.NORMAL);
            }

        }

        public void setName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setUserImage(String thumb_image, Context ctx) {

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);
            Picasso.get().load(thumb_image).placeholder
                    (R.drawable.male_avatar).into(userImageView);

        }

        public void setUserOnline(String online_status) {

            ImageView userOnlineView = (ImageView) mView.findViewById(R.id.user_single_online_icon);

            if (online_status.equals("true")) {

                userOnlineView.setVisibility(View.VISIBLE);

            } else {

                userOnlineView.setVisibility(View.INVISIBLE);

            }

        }

    }

    }

