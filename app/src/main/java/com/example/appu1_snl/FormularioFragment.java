package com.example.appu1_snl;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appu1_snl.Interface.DAMusatAPI;
import com.example.appu1_snl.databinding.FragmentFormularioBinding;
import com.example.appu1_snl.model.GuardarPersonaRequest;
import com.example.appu1_snl.model.RptaGeneral;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormularioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormularioFragment extends Fragment {

    private FragmentFormularioBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormularioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormularioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormularioFragment newInstance(String param1, String param2) {
        FormularioFragment fragment = new FormularioFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormularioBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
          /*binding.btnGuardar.setOnClickListener(v ->
                  Toast.makeText(getActivity(), "Guardado exitosamente",
                          Toast.LENGTH_SHORT).show());*/
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SP_USAT",
                Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("tokenJWT", "");
        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesarGuardar("JWT " + token);
            }
        });

    }

    private void procesarGuardar(String valorHeader) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sebasneco.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DAMusatAPI dambUsatApi = retrofit.create(DAMusatAPI.class);
        GuardarPersonaRequest guardarPersonaRequest = new GuardarPersonaRequest();
        guardarPersonaRequest.setDni(binding.txtDni.getText().toString());
        guardarPersonaRequest.setNombre(binding.txtNombreCliente.getText().toString());

        Call<RptaGeneral> call = dambUsatApi.guardarPersona(valorHeader, guardarPersonaRequest);
        call.enqueue(new Callback<RptaGeneral>() {
            @Override
            public void onResponse(Call<RptaGeneral> call, Response<RptaGeneral> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity().getBaseContext(), "Código: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    RptaGeneral rptaGeneral = response.body();
                    Toast.makeText(getActivity().getBaseContext(), rptaGeneral.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RptaGeneral> call, Throwable t) {

            }
        });
    }
}