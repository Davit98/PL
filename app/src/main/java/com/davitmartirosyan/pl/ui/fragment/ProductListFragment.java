package com.davitmartirosyan.pl.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.davitmartirosyan.pl.R;
import com.davitmartirosyan.pl.db.entity.Product;
import com.davitmartirosyan.pl.io.bus.BusProvider;
import com.davitmartirosyan.pl.io.rest.HttpRequestManager;
import com.davitmartirosyan.pl.io.service.PLIntentService;
import com.davitmartirosyan.pl.ui.adapters.ProductAdapter;
import com.davitmartirosyan.pl.util.Constant;
import com.davitmartirosyan.pl.util.NetworkUtil;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;

public class ProductListFragment extends BaseFragment implements View.OnClickListener {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = ProductListFragment.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private static ArrayList<Product> products;

    private Bundle mArgumentData;

    // ===========================================================
    // Constructors
    // ===========================================================

    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    public static ProductListFragment newInstance(Bundle args) {
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // I don't know what this is for. Let's keep it yet.
        //setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        BusProvider.register(this);
        findViews(view);
        setListeners();
        getData();
        customizeActionBar();

        if(NetworkUtil.getInstance().isConnected(view.getContext())) {
            PLIntentService.start(
                    getActivity(),
                    Constant.API.PRODUCT_LIST,
                    HttpRequestManager.RequestType.PRODUCT_LIST
            );

//            PLIntentService.start(
//                    getActivity(),
//                    Constant.API.PRODUCT_ITEM,
//                    HttpRequestManager.RequestType.PRODUCT_ITEM
//            );
        }
        else {
            Toast.makeText(view.getContext(), R.string.msg_connection_error,Toast.LENGTH_LONG).show();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BusProvider.unregister(this);
    }

    // ===========================================================
    // Click Listeners
    // ===========================================================

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    // ===========================================================
    // Other Listeners, methods for/from Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    @Subscribe
    public void onEventReceived(ArrayList<Product> productArrayList) {
        products = productArrayList;
        doRecyclerView();
    }

    private void setListeners() {

    }

    private void findViews(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
    }

    private void doRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        productAdapter = new ProductAdapter(products,getContext());
        recyclerView.setAdapter(productAdapter);
    }

    public void getData() {
        if (getArguments() != null) {
            mArgumentData = getArguments().getBundle(Constant.Argument.ARGUMENT_DATA);
        }
    }

    private void customizeActionBar() {

    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}