package com.example.studentapp.ui.post;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studentapp.databinding.FragmentPostBinding;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostFragment extends Fragment {

    private FragmentPostBinding binding;
    private PostDAO postDAO;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        postDAO = new PostDAO(getContext());
        postDAO.open();

        binding.uploadImageButton.setOnClickListener(v -> openFileChooser());
        binding.postButton.setOnClickListener(v -> addPost());

        postList = postDAO.getAllPosts();
        postAdapter = new PostAdapter(postList, postDAO);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(postAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        postDAO.close();
    }

    private void addPost() {
        String text = binding.postText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(getContext(), "Post text cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUriString = (imageUri != null) ? imageUri.toString() : null;
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        Post post = new Post(text, imageUriString, timestamp, 0);
        postDAO.addPost(post);
        postList.add(0, post);
        postAdapter.notifyItemInserted(0);
        binding.recyclerView.scrollToPosition(0);
        binding.postText.setText("");
        binding.uploadImageButton.setText("Upload Image");
        imageUri = null;  // Reset the image URI
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                binding.uploadImageButton.setText("Image Selected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
