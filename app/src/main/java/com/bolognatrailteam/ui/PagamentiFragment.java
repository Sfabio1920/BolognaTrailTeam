package com.bolognatrailteam.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bolognatrailteam.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.transition.MaterialFadeThrough;
import com.satispay.satispayintent.SatispayIntent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PagamentiFragment extends Fragment {
    private MaterialFadeThrough materialFadeThrough=new MaterialFadeThrough();
    private TextInputLayout nomePagamento;
    private TextInputLayout cognomePagamento;
    private TextInputLayout causalePagamento;
    private TextInputLayout importoPagamento;
    private TextInputEditText nome;
    private TextInputEditText cognome;
    private TextInputEditText causale;
    private TextInputEditText importo;
    private MaterialButton materialButton;
    public PagamentiFragment(){
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialFadeThrough.setDuration(500);
        setEnterTransition(materialFadeThrough);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_pagamenti, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nomePagamento=getActivity().findViewById(R.id.nomePagamento);
        cognomePagamento=getActivity().findViewById(R.id.cognomePagamento);
        causalePagamento=getActivity().findViewById(R.id.causalePagamento);
        importoPagamento=getActivity().findViewById(R.id.importoPagamento);
        materialButton=getActivity().findViewById(R.id.button_payment);
        nome=getActivity().findViewById(R.id.nome);
        cognome=getActivity().findViewById(R.id.cognome);
        causale=getActivity().findViewById(R.id.causale);
        importo=getActivity().findViewById(R.id.importo);
        button();
        checkInput();
    }
    public void checkSatispay(){
        boolean isSatispayAvailable = SatispayIntent.isSatispayAvailable(getActivity(), SatispayIntent.PRODUCTION_SCHEME);
        Log.d("PagamentiFragment",""+isSatispayAvailable);
        if (isSatispayAvailable){
            Uri satispayPayment=Uri.parse("https://tag.satispay.com/BOLOGNATRAILTEAM");
            Intent openSatispay=new Intent(Intent.ACTION_VIEW,satispayPayment);
            startActivity(openSatispay);
        }
        else{
            Intent openPlayStoreIntent = SatispayIntent.openPlayStore(getActivity(), SatispayIntent.PRODUCTION_APP_PACKAGE);
            startActivity(openPlayStoreIntent);
        }

    }
    public void button(){
        materialButton.setOnClickListener(i->{
            StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycby2iUV8BcIIn2t7oC2FtKwwz9XZgKiObh9o42qRmFMJL2JYFF5fI_UaiKSLdbAKw0-QaQ/exec",
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){

                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){

                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parmas = new HashMap<>();
                    parmas.put("name", Objects.requireNonNull(nome.getText()).toString());
                    parmas.put("surname", Objects.requireNonNull(cognome.getText()).toString());
                    parmas.put("amount", Objects.requireNonNull(causale.getText()).toString());
                    parmas.put("product", Objects.requireNonNull(importo.getText()).toString());
                    return parmas;
                }
            };
            RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);
            RequestQueue queue = Volley.newRequestQueue(requireContext());
            queue.add(stringRequest);
            nomePagamento.clearFocus();
            cognomePagamento.clearFocus();
            causalePagamento.clearFocus();
            importoPagamento.clearFocus();
            nome.setText("");
            cognome.setText("");
            causale.setText("");
            importo.setText("");
            checkSatispay();
        });
    }
    private void checkInput(){
        TextWatcher tw=new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                String name=nome.getText().toString().trim();
                String surname=cognome.getText().toString().trim();
                String cause=causale.getText().toString().trim();
                String quantity=importo.getText().toString().trim();
                boolean enabled=(!name.isEmpty()&&!surname.isEmpty()&&!cause.isEmpty()&&!quantity.isEmpty());
                materialButton.setEnabled(enabled);
                if(materialButton.isEnabled()){
                    materialButton.setBackground(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.en_roundbutton, null)));
                }
                else{
                    materialButton.setBackground(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.dis_roundbutton, null)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable){

            }
        };
        nome.addTextChangedListener(tw);
        cognome.addTextChangedListener(tw);
        causale.addTextChangedListener(tw);
        importo.addTextChangedListener(tw);
    }
    public boolean onKeyDown() {
        boolean prova = false;
        if (nomePagamento.hasFocus()) {
            nomePagamento.clearFocus();
            prova = true;
        } else {
            if (cognomePagamento.hasFocus()) {
                cognomePagamento.clearFocus();
                prova = true;
            } else {
                if (causalePagamento.hasFocus()) {
                    causalePagamento.clearFocus();
                    prova = true;
                } else {
                    if (importoPagamento.hasFocus()) {
                        importoPagamento.clearFocus();
                        prova = true;
                    }
                }
            }
        }
        return prova;
    }
}