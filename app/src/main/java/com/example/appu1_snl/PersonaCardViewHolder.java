package com.example.appu1_snl;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

public class PersonaCardViewHolder extends RecyclerView.ViewHolder {
    public NetworkImageView personaImage;
    public TextView personaName;
    public TextView personaDni;

    public PersonaCardViewHolder(@NonNull View itemView) {
        super(itemView);
        personaImage = itemView.findViewById(R.id.persona_image);
        personaName = itemView.findViewById(R.id.persona_nombre);
        personaDni = itemView.findViewById(R.id.persona_dni);
    }
}
