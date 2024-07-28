package com.example.studentapp.ui.post;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentapp.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class PostDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public PostDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addPost(Post post) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEXT, post.getText());
        values.put(DatabaseHelper.COLUMN_IMAGE_URI, post.getImageUri());
        values.put(DatabaseHelper.COLUMN_TIMESTAMP, post.getTimestamp());
        values.put(DatabaseHelper.COLUMN_LIKE_COUNT, post.getLikeCount());
        database.insert(DatabaseHelper.TABLE_POSTS, null, values);
    }

    public void updateLikeCount(int postId, int likeCount) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LIKE_COUNT, likeCount);
        database.update(DatabaseHelper.TABLE_POSTS, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(postId)});
    }

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_POSTS, null, null, null, null, null, DatabaseHelper.COLUMN_TIMESTAMP + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Post post = cursorToPost(cursor);
            posts.add(post);
            cursor.moveToNext();
        }
        cursor.close();
        return posts;
    }

    private Post cursorToPost(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
        String text = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
        String imageUri = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URI));
        String timestamp = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIMESTAMP));
        int likeCount = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_LIKE_COUNT));

        Post post = new Post(text, imageUri, timestamp, likeCount);
        return post;
    }
}

