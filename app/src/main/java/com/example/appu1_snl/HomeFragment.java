package com.example.appu1_snl;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appu1_snl.Interface.DAMusatAPI;
import com.example.appu1_snl.databinding.FragmentFirstBinding;
import com.example.appu1_snl.databinding.FragmentHomeBinding;
import com.example.appu1_snl.model.AuthRequest;
import com.example.appu1_snl.model.AuthResponse;
import com.example.appu1_snl.model.GuardarPersonaRequest;
import com.example.appu1_snl.model.RptaGeneral;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        cargaInicialHome();
        return binding.getRoot();
        // Inflate the layout for this fragment
    }

    private void cargaInicialHome(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sebasneco.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DAMusatAPI dambUsatApi = retrofit.create(DAMusatAPI.class);
        Call<RptaGeneral> call = dambUsatApi.obtenerPersonas();
        call.enqueue(new Callback<RptaGeneral>() {
            @Override
            public void onResponse(Call<RptaGeneral> call, Response<RptaGeneral> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity().getBaseContext(), "Código: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                } else{
                    RptaGeneral rptaGeneral = response.body();
                    Object objeto=rptaGeneral.getData();
                    List<PersonaEntry> listaPersona= (List<PersonaEntry>) objeto;
                    for (int indice=0;indice<listaPersona.size();indice++){
                        Log.d("XYZ",listaPersona.get(indice).toString());
                    }
                    RecyclerView recyclerView=binding.recyclerView;
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,
                            GridLayoutManager.VERTICAL,false));
                    PersonaCardRecycleViewAdapter adapter=new PersonaCardRecycleViewAdapter(null);
                    recyclerView.setAdapter(adapter);
                    int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
                    int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
                    recyclerView.addItemDecoration(new PersonaGridItemDecoration(largePadding, smallPadding));
                }
            }
            @Override
            public void onFailure(Call<RptaGeneral> call, Throwable t) {

            }
        });

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataJson();
    }

    public void getDataJson(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sebasneco.pythonanywhere.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DAMusatAPI damusatAPI = retrofit.create(DAMusatAPI.class);

    }
}