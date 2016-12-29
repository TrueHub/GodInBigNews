package com.others.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.news.activity.R;

/**
 * Created by Administrator on 2016/11/18.
 */
public class MyLoginFragment extends Fragment {

    private ImageView mFaceImg;
    private EditText mLoginEditAccount;
    private TextView mTextView01;
    private ImageButton mImageButton02;
    private EditText mLoginEditPwd;
    private TextView mTextView02;
    private CheckBox mLoginCbSavepwd;
    private Button mLoginBtnLogin;
    private RelativeLayout mRelativeLayout02;
    private CheckBox mLoginCbVisible;
    private CheckBox mLoginCbOpenvibra;
    private CheckBox mLoginCbReceivegroupmsg;
    private CheckBox mLoginCbQuite;
    private TableLayout mTableLayout01;
    private LinearLayout mLinearLayout01;
    private ImageButton mLoginOption;
    private RelativeLayout mRelativeLayout01;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater.inflate(R.layout.fragment_login, container, false));
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);


    }

    private void initView(View view) {

        mFaceImg = (ImageView) view.findViewById(R.id.faceImg);
        mLoginEditAccount = (EditText) view.findViewById(R.id.login_edit_account);
        mTextView01 = (TextView) view.findViewById(R.id.TextView01);
        mImageButton02 = (ImageButton) view.findViewById(R.id.ImageButton02);
        mLoginEditPwd = (EditText) view.findViewById(R.id.login_edit_pwd);
        mTextView02 = (TextView) view.findViewById(R.id.TextView02);
        mLoginCbSavepwd = (CheckBox) view.findViewById(R.id.login_cb_savepwd);
        mLoginBtnLogin = (Button) view.findViewById(R.id.login_btn_login);
        mRelativeLayout02 = (RelativeLayout) view.findViewById(R.id.RelativeLayout02);
        mLoginCbVisible = (CheckBox) view.findViewById(R.id.login_cb_visible);
        mLoginCbOpenvibra = (CheckBox) view.findViewById(R.id.login_cb_openvibra);
        mLoginCbReceivegroupmsg = (CheckBox) view.findViewById(R.id.login_cb_receivegroupmsg);
        mLoginCbQuite = (CheckBox) view.findViewById(R.id.login_cb_quite);
        mTableLayout01 = (TableLayout) view.findViewById(R.id.TableLayout01);
        mLinearLayout01 = (LinearLayout) view.findViewById(R.id.LinearLayout01);
        mLoginOption = (ImageButton) view.findViewById(R.id.login_option);
        mRelativeLayout01 = (RelativeLayout) view.findViewById(R.id.RelativeLayout01);
    }


}
