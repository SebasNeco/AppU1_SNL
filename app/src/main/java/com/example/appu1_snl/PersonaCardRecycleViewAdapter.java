package com.example.appu1_snl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonaCardRecycleViewAdapter extends RecyclerView.Adapter<PersonaCardViewHolder>{
    private List<PersonaEntry> personaList;
    private ImageRequester imageRequester;
    public PersonaCardRecycleViewAdapter(List<PersonaEntry> personaList){
        this.personaList=personaList;
        imageRequester=ImageRequester.getInstance();
    }


    @NonNull
    @Override
    public PersonaCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.persona_card,parent,false);
        return new PersonaCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonaCardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return personaList.size();
    }
}
