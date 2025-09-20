package com.example.appu1_snl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appu1_snl.databinding.FragmentPacienteBinding;

public class PacienteFragment extends Fragment {

    private FragmentPacienteBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PacienteFragment() {
        // Required empty public constructor
    }

    public static PacienteFragment newInstance(String param1, String param2) {
        PacienteFragment fragment = new PacienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPacienteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnGuardar.setOnClickListener(v -> {

            boolean allFieldsValid = isCampoValido(binding.txtNombrePaciente.getText()) &&
                    isCampoValido(binding.txtEspecialista.getText()) &&
                    isCampoValido(binding.txtObservaciones.getText()) &&
                    isPrecioValido(binding.txtPrecioConsulta.getText()) &&
                    isCampoValido(binding.txtNumHistoria.getText()) &&
                    isCampoValido(binding.txtNumCama.getText()) &&
                    isCampoValido(binding.txtEnlace1.getText()) &&
                    isCampoValido(binding.txtEnlace2.getText());

            if (allFieldsValid) {
                Toast.makeText(getActivity(), "Registro correcto", Toast.LENGTH_SHORT).show();
                binding.txtNombrePaciente.setText("");
                binding.txtEspecialista.setText("");
                binding.txtObservaciones.setText("");
                binding.txtPrecioConsulta.setText("");
                binding.txtNumHistoria.setText("");
                binding.txtNumCama.setText("");
                binding.txtEnlace1.setText("");
                binding.txtEnlace2.setText("");
                binding.txtNombrePaciente.requestFocus();
            } else {
                Toast.makeText(getActivity(), "Registro incorrecto. Verifique los campos.", Toast.LENGTH_LONG).show();

            }
        });
    }

    private boolean isCampoValido(@Nullable Editable text){
        return text != null && !TextUtils.isEmpty(text);
    }

    private boolean isPrecioValido(@Nullable Editable text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        try {
            double precio = Double.parseDouble(text.toString());
            return precio >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
