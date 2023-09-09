package com.example.lista_tarefas;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lista_tarefas.dao.Tarefadao;
import com.example.lista_tarefas.helper.SQLiteDataBaseHelper;
import com.example.lista_tarefas.model.Tarefa;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class InserirTarefa extends BottomSheetDialogFragment {

    public static final String TAG = "InserirTarefa";

    private EditText mEditText;
    private Button mSaveButton;
    private Tarefadao tarefadao;

    public static InserirTarefa newInstance(){
        return new InserirTarefa();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_task,container,false);
        tarefadao = new Tarefadao(getActivity());
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = view.findViewById(R.id.newTaskText);
        mSaveButton = view.findViewById(R.id.newTaskButton);



        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate=true;
            String tarefa = bundle.getString("TAREFAS");
            mEditText.setText(tarefa);

            if (tarefa.length()>0){
                mSaveButton.setEnabled(false);
            }
        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().equals("")){
                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);
                }else {
                    mSaveButton.setEnabled(true);
                    mSaveButton.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_default_color_primary));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEditText.getText().toString();

                if (finalIsUpdate){
                   tarefadao.atualizar(bundle.getInt("ID"),text );

                }else {
                    Tarefa item = new Tarefa();
                    item.setTarefa(text);
                    item.setStatus(0);
                    tarefadao.inserir(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}


