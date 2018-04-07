package com.example.franklin.conference.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONObject;

import com.example.franklin.conference.App.MyApplication;
import com.example.franklin.conference.Data.Interaction;
import com.example.franklin.conference.R;
import com.example.franklin.conference.util.HttpPost;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EstablishFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EstablishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstablishFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView mconference_name;
    private TextView mconference_container;
    private  TextView mconference_type;
    private TextView mconference_place;
    private  TextView mconference_time;

    private EditText mconference_edit_name;
    private EditText mconference_edit_container;
    private Spinner mconference_edit_type;
    private Spinner mconference_edit_place;
    private TextView mconference_edit_time;
    private CheckBox checkBox;
    boolean is_open;

    private Button mCreate;
    private String result ="";
    private String typeID="1";
    private String placeID="1";




    public EstablishFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static EstablishFragment newInstance() {
        EstablishFragment fragment = new EstablishFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_establish, container, false);
        mconference_name = (TextView) view.findViewById(R.id.establish_name);
        mconference_container = (TextView) view.findViewById(R.id.establish_container);
        mconference_type = (TextView) view.findViewById(R.id.establish_type);
        mconference_place = (TextView) view.findViewById(R.id.establish_place);
        mconference_time = (TextView) view.findViewById(R.id.establish_time);

        mconference_edit_name = (EditText) view.findViewById(R.id.establish_edit_name);
        mconference_edit_container = (EditText) view.findViewById(R.id.establish_edit_container);
        mconference_edit_type = (Spinner) view.findViewById(R.id.establish_edit_type);
        mconference_edit_place = (Spinner) view.findViewById(R.id.establish_edit_place);
        mconference_edit_time = (TextView) view.findViewById(R.id.establish_edit_time);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        mCreate = (Button) view.findViewById(R.id.establish_btn_create);


        final TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                mconference_edit_time.setText(time);
                Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();
            }
        }, "2015-01-01 00:00", "2018-12-31 23:59:59");

       // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mconference_edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        timeSelector.show();
            }
        });

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create();
            }
        });

        return view;
    }


    public void Create(){

        mconference_edit_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int a = position + 1;
                typeID = String.valueOf(a);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });
        mconference_edit_place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               int b = position + 1;
               placeID = String.valueOf(b);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    is_open = true;
                }else is_open = false;
            }
        });
        final String name = mconference_edit_name.getText().toString();
        final String content = mconference_edit_container.getText().toString();
        final String time = mconference_edit_time.getText().toString();


        //生成Json格式的数据
        Interaction interaction = new Interaction();
        final JSONObject conferenceData = interaction.ConData(name,content,placeID,typeID,is_open,time);
        new Thread(new Runnable() {
            @Override
            public void run() {
                result = HttpPost.ConferencePost("http://39.108.186.78//conferenceOP/",conferenceData);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(), result, Toast.LENGTH_LONG).show();
                    }
                });

            }
        }).start();
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
