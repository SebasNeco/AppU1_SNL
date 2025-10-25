package com.example.appu1_snl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class PersonaCardRecycleViewAdapter extends RecyclerView.Adapter<PersonaCardViewHolder>{
    private List<PersonaEntry> personaList;
    private ImageRequester imageRequester;
    public PersonaCardRecycleViewAdapter(List<PersonaEntry> personaList){
        this.personaList = personaList != null ? personaList : Collections.emptyList();
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
        PersonaEntry persona=personaList.get(position);
        holder.personaDni.setText(persona.dni);
        holder.personaName.setText(persona.nombre);
        imageRequester.setImageFromUrl(holder.personaImage,persona.url);
    }

    @Override
    public int getItemCount() {
        return personaList.size();
    }
}
