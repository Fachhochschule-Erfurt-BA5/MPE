package com.pme.mpe.ui.block;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pme.mpe.R;

import java.util.List;

public class BlockFragment extends Fragment {

    private BlockViewModel blockViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        blockViewModel = new ViewModelProvider(this).get(BlockViewModel.class);
        View root = inflater.inflate(R.layout.fragment_block, container, false);




        return root;
    }
}