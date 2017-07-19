package com.davitmartirosyan.pl.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class ProductListFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

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
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private TextView noNetwork;
    private ImageView noNetworkIcon;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        Log.d("testt","onCreateView");
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        BusProvider.register(this);
        findViews(view);
        swipeRefreshLayout.setEnabled(false);
        setListeners();
        getData();
        customizeActionBar();
        makeRequest();


        // To be moved elsewhere
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    public void makeRequest() {
        Log.d("testt","makeRequest");
        if(NetworkUtil.getInstance().isConnected(getContext())) {
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
            if(!swipeRefreshLayout.isEnabled()) {
                swipeRefreshLayout.setEnabled(true);
            }
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            noNetwork.setVisibility(View.VISIBLE);
            noNetworkIcon.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Log.d("testt","onDestroyView");
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
        Log.d("testt","onEventReceived");
        noNetwork.setVisibility(View.GONE);
        noNetworkIcon.setVisibility(View.GONE);
        products = productArrayList;
        drawRecyclerView();
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void findViews(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar)view.findViewById(R.id.product_list_pb);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.product_list_root_rl);
        noNetwork = (TextView)view.findViewById(R.id.product_list_no_network_tv);
        noNetworkIcon = (ImageView)view.findViewById(R.id.product_list_no_network_icon_iv);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.product_list_swipe_srl);
    }

    private void drawRecyclerView() {
        Log.d("testt","drawRecyclerView");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        productAdapter = new ProductAdapter(products,getContext());
        recyclerView.setAdapter(productAdapter);

        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if(progressBar.isShown()) {
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setEnabled(true);
                }
                if(swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    public void getData() {
        if (getArguments() != null) {
            mArgumentData = getArguments().getBundle(Constant.Argument.ARGUMENT_DATA);
        }
    }

    private void customizeActionBar() {
    }

    @Override
    public void onRefresh() {
        if (productAdapter != null) {
            products.clear();
            productAdapter.notifyDataSetChanged();
        }
        makeRequest();
        Log.d("testt", "onRefresh");
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}