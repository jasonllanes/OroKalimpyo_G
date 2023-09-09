package sldevs.cdo.orokalimpyo.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class home_fragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView tvName;
    firebase_crud fc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        fc = new firebase_crud();

        tvName = v.findViewById(R.id.tvName);

        fc.retrieveName(getActivity(),getContext(),mAuth.getUid(),tvName);



        return v;

    }
}