package com.cm.davidmatos.androidfinalapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;
import com.cm.davidmatos.androidfinalapp.WS.Carro;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.dbCarAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dbCar extends AppCompatActivity {

    dbCarAdapter adapter;
    Context context;
    List<Carro> listaCarros = new ArrayList<>();
    Dialog myDialog;
    ListView listCarros;
    FloatingActionButton btnAdd;
    String[] lMarca;
    String[] lModel;
    Spinner spMarca, spModelo, spCombustivel;
    Double consumos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_car);

        hideNavigationBar();

        myDialog = new Dialog(this);
        context = this;

        listCarros = (ListView) findViewById(R.id.listviewCar);

        carregaLista();

        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpAdd();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar();
    }

    private void hideNavigationBar() {
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }

    private void carregaLista() {

        hideNavigationBar();

        // Loads User
        Response.Listener<JSONObject> objUser = new Response.Listener<JSONObject>() {
            Boolean get;
            JSONArray Carros;
            JSONObject Carro;

            @Override
            public void onResponse(JSONObject response) {
                listaCarros.removeAll(listaCarros);
                try {
                    get = response.getBoolean("Get");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (get){
                    try {
                        Carros = response.getJSONArray("Result");
                        for (int i = 0; i < Carros.length(); i++){
                            Carro = Carros.getJSONObject(i);

                            com.cm.davidmatos.androidfinalapp.WS.Carro c = new Carro(
                                    Carro.getInt("idCar"),
                                    Carro.getString("marca"),
                                    Carro.getString("modelo"),
                                    Carro.getInt("combustivel"),
                                    Carro.getDouble("consumo"),
                                    Carro.getInt("fkUser"),
                                    Carro.getInt("estado")
                            );

                            listaCarros.add(c);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new dbCarAdapter(getApplicationContext(), listaCarros);
                    listCarros.setAdapter(adapter);
                } else {
                    showErrorPopUp("Nenhum historico encontrado");
                }
            }
        };

        Response.ErrorListener errorListenerUser = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        WS.getInstance(context).GetCarroByIdUser(objUser, errorListenerUser);

        listCarros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopUpEdit(listaCarros.get(position));
            }
        });
    }

    private void showErrorPopUp(String errorMessage){
        TextView btnClose;
        TextView txtDescricaoErrorPopUp;
        Button btnErrorPopUp;

        myDialog.setContentView(R.layout.error_popup);

        btnClose = (TextView) myDialog.findViewById(R.id.btnClose);
        btnErrorPopUp = (Button) myDialog.findViewById(R.id.btnErrorPopUp);
        txtDescricaoErrorPopUp = (TextView) myDialog.findViewById(R.id.txtDescricaoErrorPopUp);

        txtDescricaoErrorPopUp.setText(errorMessage);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btnErrorPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void showPopUpAdd() {

        myDialog.setContentView(R.layout.custum_popup_db_car_add);

        TextView btnClose;
        Button btnCancelar, btnRegistar;

        btnClose = myDialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                carregaLista();
            }
        });

        btnCancelar = myDialog.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                carregaLista();
            }
        });

        btnRegistar = myDialog.findViewById(R.id.btnRegistar);
        btnRegistar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getDataCar(myDialog);
            }
        });

        loadDataCarAdd(myDialog);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

    private void showPopUpEdit(Carro c) {

        myDialog.setContentView(R.layout.custum_popup_db_car_add);

        TextView btnClose;
        Button btnCancelar, btnRegistar;
        Spinner spMarca, spModelo, spCombustivel;
        EditText txtConsumos;

        spMarca = myDialog.findViewById(R.id.spMarca);
        spModelo = myDialog.findViewById(R.id.spModelo);
        spCombustivel = myDialog.findViewById(R.id.spCombustivel);

        spMarca.setEnabled(false);
        spCombustivel.setEnabled(false);
        spModelo.setEnabled(false);

        String[] auxMarca = new String[1];
        auxMarca[0] = c.getMarca();
        ArrayAdapter<String> adapterMarca = new ArrayAdapter<String>(this, R.layout.spinner_item, auxMarca);
        spMarca.setAdapter(adapterMarca);

        String[] auxModelo = new String[1];
        auxModelo[0] = c.getModelo();
        ArrayAdapter<String> adapterModelo = new ArrayAdapter<String>(this, R.layout.spinner_item, auxModelo);
        spModelo.setAdapter(adapterModelo);

        String[] auxCombustivel = new String[1];
        if (c.getCombustivel() == 0) {
            auxCombustivel[0] = "Gasolina";
        } else if (c.getCombustivel() == 1) {
            auxCombustivel[0] = "Gasoleo";
        } else {
            auxCombustivel[0] = "Eletrico";
        }

        ArrayAdapter<String> adapterCombustivel = new ArrayAdapter<String>(this, R.layout.spinner_item, auxCombustivel);
        spCombustivel.setAdapter(adapterCombustivel);

        txtConsumos = myDialog.findViewById(R.id.txtConsumos);
        txtConsumos.setText(c.getConsumo() + "");

        btnClose = myDialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                carregaLista();
            }
        });
        final int idCarro = c.getIdCarro();
        btnCancelar = myDialog.findViewById(R.id.btnCancelar);
        btnCancelar.setText("Apagar");
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<JSONObject> objMarca = new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Boolean delete = response.getBoolean("delete");
                            if (delete) {
                                showSuccessPopUp("O veiculo foi Removdo com Sucesso");
                            } else {
                                showErrorPopUp("Ocorreu um erro ao remover o veiculo, tente novamente mais tarde");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Response.ErrorListener errorListenerMarca = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                };
                WS.getInstance(context).DeleteCarro(idCarro, objMarca, errorListenerMarca);
            }
        });

        btnRegistar = myDialog.findViewById(R.id.btnRegistar);
        btnRegistar.setText("Editar");

        Double.valueOf(txtConsumos.getText().toString());
        btnRegistar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Response.Listener<JSONObject> objMarca = new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean put = response.getBoolean("edit");

                            if (put){
                                showSuccessPopUp("O veiculo foi editado com sucesso");
                            } else {
                                showErrorPopUp("Ocorreu um erro ao editar o veiculo");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Response.ErrorListener errorListenerMarca = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                };
                WS.getInstance(context).UpdateCarro(idCarro, getConsumos(), objMarca, errorListenerMarca);
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

    private Double getConsumos() {
        EditText txtConsumos;
        txtConsumos = myDialog.findViewById(R.id.txtConsumos);
        return Double.valueOf(txtConsumos.getText().toString());
    }

    private void getDataCar(Dialog dialog){
        EditText txtComsumos, txtQuantidadeLugar;
        txtComsumos = myDialog.findViewById(R.id.txtConsumos);

        if ((txtComsumos.getText().toString().isEmpty() || txtComsumos.getText().toString().equals("")) && Utils.modelo.equals("") && Utils.marca.equals("")){
            showErrorPopUp("Todos os campos tem que estar ");
        } else {
             Carro c = new Carro(
                     1,
                     Utils.marca,
                     Utils.modelo,
                     Utils.combustivel,
                     Double.parseDouble(txtComsumos.getText().toString()),
                     Utils.idUser,
                     1
             );

            Response.Listener<JSONObject> objMarca = new Response.Listener<JSONObject>() {

                JSONObject Post ;

                @Override
                public void onResponse(JSONObject response) {

                    try {
                        Boolean get = response.getBoolean("Post");

                        if (get){
                            showSuccessPopUp("O Veiculo foi adicionado com sucesso");
                        } else {
                            showErrorPopUp("Ocorreu um erro durante a incerção do carro");
                        }

                        fillDataCarMake();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener errorListenerMarca = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            };
            WS.getInstance(context).newCar(c, objMarca, errorListenerMarca);

        }
    }

    private void showSuccessPopUp(String successMessage){
        TextView btnClose;
        TextView txtDescricaoSuccessPopUp;
        Button btnErrorPopUp;

        myDialog.setContentView(R.layout.sucess_popup);

        btnClose = (TextView) myDialog.findViewById(R.id.btnClose);
        btnErrorPopUp = (Button) myDialog.findViewById(R.id.btnErrorPopUp);
        txtDescricaoSuccessPopUp = (TextView) myDialog.findViewById(R.id.txtDescricaoSuccessPopUp);

        txtDescricaoSuccessPopUp.setText(successMessage);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btnErrorPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                carregaLista();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void loadDataCarAdd(Dialog dialog) {

        spMarca = dialog.findViewById(R.id.spMarca);
        spModelo = dialog.findViewById(R.id.spModelo);
        spCombustivel = dialog.findViewById(R.id.spCombustivel);

        spModelo.setEnabled(false);

        String[] comb = {"Gasolina", "Gasoleo", "Eletrico"};
        ArrayAdapter<String> adapterCombustivel = new ArrayAdapter<String>(this, R.layout.spinner_item, comb);
        spCombustivel.setAdapter(adapterCombustivel);
        spCombustivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.combustivel = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Utils.combustivel = 0;
            }
        });

        loadDataCarMake();

    }

    private void loadDataCarMake() {

        Response.Listener<JSONObject> objMarca = new Response.Listener<JSONObject>() {

            JSONArray Result;
            JSONObject Marca;

            @Override
            public void onResponse(JSONObject response) {

                int count = 0;

                try {
                    Result = response.getJSONArray("Results");
                    lMarca = new String[Result.length()];
                    for (int i = 0; i < Result.length(); i++){
                        Marca = Result.getJSONObject(i);

                        lMarca[count] = Marca.getString("MakeName");
                        count++;
                    }
                    fillDataCarMake();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListenerMarca = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        WS.getInstance(context).GetMake(objMarca, errorListenerMarca);

    }

    private void fillDataCarMake(){
        ArrayAdapter<String> adapterCombustivel = new ArrayAdapter<String>(this, R.layout.spinner_item, lMarca);
        spMarca.setAdapter(adapterCombustivel);
        spMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String marca = lMarca[position];
                Utils.marca = marca;
                loadDataCarModel(marca);
                spModelo.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showErrorPopUp("Tem que selecionar uma marca");
            }
        });
    }

    private void loadDataCarModel(String make) {

        Response.Listener<JSONObject> objMarca = new Response.Listener<JSONObject>() {

            JSONArray Result;
            JSONObject Modelo;

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Result = response.getJSONArray("Results");
                    lModel = new String[Result.length()];
                    int count = 0;
                    for (int i = 0; i < Result.length(); i++){
                        Modelo = Result.getJSONObject(i);

                        lModel[count] = Modelo.getString("Model_Name");
                        count++;

                    }
                    fillDataCarModel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListenerMarca = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        WS.getInstance(context).GetModel(make, objMarca, errorListenerMarca);
    }

    private void fillDataCarModel(){
        ArrayAdapter<String> adapterCombustivel = new ArrayAdapter<String>(this, R.layout.spinner_item, lModel);
        spModelo.setAdapter(adapterCombustivel);
        spModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.modelo = lModel[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showErrorPopUp("Tem que selecionar um Modelo");
            }
        });
    }

    public class Make {

        private int MakeId;
        private String MakeName;
        private int VehicleTypeId;
        private String VehicleTypeName;

        public Make(int makeId, String makeName, int vehicleTypeId, String vehicleTypeName) {
            MakeId = makeId;
            MakeName = makeName;
            VehicleTypeId = vehicleTypeId;
            VehicleTypeName = vehicleTypeName;
        }

        public Make() {
        }

        public int getMakeId() {
            return MakeId;
        }

        public void setMakeId(int makeId) {
            MakeId = makeId;
        }

        public String getMakeName() {
            return MakeName;
        }

        public void setMakeName(String makeName) {
            MakeName = makeName;
        }

        public int getVehicleTypeId() {
            return VehicleTypeId;
        }

        public void setVehicleTypeId(int vehicleTypeId) {
            VehicleTypeId = vehicleTypeId;
        }

        public String getVehicleTypeName() {
            return VehicleTypeName;
        }

        public void setVehicleTypeName(String vehicleTypeName) {
            VehicleTypeName = vehicleTypeName;
        }
    }

    public class Model {
        private int Make_ID;
        private String Make_Name;
        private int Model_ID;
        private String Model_Name;

        public Model(int make_ID, String make_Name, int model_ID, String model_Name) {
            Make_ID = make_ID;
            Make_Name = make_Name;
            Model_ID = model_ID;
            Model_Name = model_Name;
        }

        public Model() {
        }

        public int getMake_ID() {
            return Make_ID;
        }

        public void setMake_ID(int make_ID) {
            Make_ID = make_ID;
        }

        public String getMake_Name() {
            return Make_Name;
        }

        public void setMake_Name(String make_Name) {
            Make_Name = make_Name;
        }

        public int getModel_ID() {
            return Model_ID;
        }

        public void setModel_ID(int model_ID) {
            Model_ID = model_ID;
        }

        public String getModel_Name() {
            return Model_Name;
        }

        public void setModel_Name(String model_Name) {
            Model_Name = model_Name;
        }
    }

}
