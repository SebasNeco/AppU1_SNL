package com.example.appu1_snl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appu1_snl.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnToast.setOnClickListener(v -> {
            Toast myToast = Toast.makeText(getActivity(), "Hola Toast!", Toast.LENGTH_SHORT);
            myToast.show();
        });

        binding.btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countString = binding.textviewFirst.getText().toString();
                int count = Integer.parseInt(countString);
                count++;
                binding.textviewFirst.setText(String.valueOf(count));
            }
        });

//        binding.btnNext.setOnClickListener(v ->
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
//        );
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentCount=Integer.parseInt(binding.textviewFirst.getText().toString());
                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action=
                        FirstFragmentDirections.actionFirstFragmentToSecondFragment(currentCount);
                NavHostFragment.findNavController(FirstFragment.this).navigate(action);

            }
        });

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("SP_USAT", getActivity().MODE_PRIVATE);
        String username=sharedPreferences.getString("username", "");
        // String countText= getString(R.string.random_heading, count);
        String saludo=getString(R.string.Bienvenida, username);
        binding.txtBienvenida.setText(saludo);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}