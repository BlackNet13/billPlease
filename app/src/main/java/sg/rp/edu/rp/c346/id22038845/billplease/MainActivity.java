package sg.rp.edu.rp.c346.id22038845.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    //declaration
    TextView outputV1;
    TextView outputV2;
    TextView astri1;
    TextView astri2;
    TextView astri3;
    TextView astri4;

    EditText amtIp;
    EditText numIp;
    EditText discIp;
    EditText phoneNum;

    ToggleButton svsT;
    ToggleButton gstT;

    Button splitBt;
    Button resetBt;

    RadioGroup payRG;
    Switch discSw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link var to id
        amtIp=findViewById(R.id.amtInpt);
        numIp=findViewById(R.id.numInpt);
        discIp=findViewById(R.id.discInpt);
        outputV1=findViewById(R.id.output1);
        outputV2=findViewById(R.id.output2);
        svsT=findViewById(R.id.svsTg);
        gstT=findViewById(R.id.gstTg);
        payRG=findViewById(R.id.payRdG);
        splitBt=findViewById(R.id.splitBtn);
        resetBt=findViewById(R.id.resetBtn);
        phoneNum=findViewById(R.id.payNContact);
        discSw=findViewById(R.id.swDisc);
        astri1=findViewById(R.id.ast1);
        astri2=findViewById(R.id.ast2);
        astri3=findViewById(R.id.ast3);
        astri4=findViewById(R.id.ast4);

        //special
        phoneNum.setVisibility(View.GONE);
        discIp.setVisibility(View.GONE);
        astri1.setVisibility(View.GONE);
        astri2.setVisibility(View.GONE);
        astri3.setVisibility(View.GONE);
        astri4.setVisibility(View.GONE);
        amtIp.setInputType(InputType.TYPE_CLASS_NUMBER );
        numIp.setInputType(InputType.TYPE_CLASS_NUMBER );
        discIp.setInputType(InputType.TYPE_CLASS_NUMBER );
        phoneNum.setInputType(InputType.TYPE_CLASS_NUMBER );


        //check if radio selection has change
        payRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.pnRd){
                    phoneNum.setVisibility(View.VISIBLE);
                }else if(checkedId==R.id.cashRd){
                    phoneNum.setVisibility(View.GONE);
                }
            }
        });


        //function for discount switch
        discSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    discIp.setVisibility(View.VISIBLE);
                }else if(isChecked==false){
                    astri3.setVisibility(View.GONE);
                    discIp.setVisibility(View.GONE);
                }
            }
        });

        //function calculate the split
        splitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amtV = amtIp.getText().toString(); //every input is treated as a string first then converted to other formats
                String numV = numIp.getText().toString();
                String pNum = phoneNum.getText().toString();

                if((amtV.isEmpty())||(numV.isEmpty())){
                    astri1.setVisibility(View.VISIBLE);
                    astri2.setVisibility(View.VISIBLE);
                    Toast redToast = Toast.makeText(getBaseContext() ,Html.fromHtml("<font color='#ff0303' ><b>" + "All compulsory fields must be filled." + "</b></font>"), Toast.LENGTH_LONG);
                    redToast.show();
                    return;
                }else{
                    astri1.setVisibility(View.GONE);
                    astri2.setVisibility(View.GONE);
                }


                int amtI = Integer.parseInt(amtV);
                int numI = Integer.parseInt(numV);

                if((amtI<=0)||(numI<=1)){
                    astri1.setVisibility(View.VISIBLE);
                    astri2.setVisibility(View.VISIBLE);
                    Toast redToast = Toast.makeText(getBaseContext() ,Html.fromHtml("<font color='#ff0303' ><b>" + "Amount must be more than 0. Pax must be more than 1" + "</b></font>"), Toast.LENGTH_LONG);
                    redToast.show();
                    return;
                }else{
                    astri1.setVisibility(View.GONE);
                    astri2.setVisibility(View.GONE);
                }


                float total=amtI;
                String dInpt = discIp.getText().toString();

                if(dInpt.isEmpty()){
                    dInpt="0";
                }
                int discI = Integer.parseInt(dInpt);

                if((discI>=1)&&(discI<=100)){
                    astri3.setVisibility(View.GONE);
                    total=(float)((total*(100-discI))/100);
                }else if(discI==0){

                }else{
                    astri3.setVisibility(View.VISIBLE);
                    Toast redToast = Toast.makeText(getBaseContext() ,Html.fromHtml("<font color='#ff0303' ><b>" + "Discount can only be within the range of 0 - 100." + "</b></font>"), Toast.LENGTH_LONG);
                    redToast.show();
                    return;
                }

                if(svsT.isChecked()==true){
                    total=(float)(total+(amtI*0.1));
                }
                if(gstT.isChecked()==true){
                    total=(float)(total+(amtI*0.07));
                }


                String payMent ="";

                int checkRadio = payRG.getCheckedRadioButtonId();
                if(checkRadio==R.id.pnRd){
                    phoneNum.setVisibility(View.VISIBLE);
                    if(pNum.isEmpty()){
                        astri4.setVisibility(View.VISIBLE);
                        Toast redToast = Toast.makeText(getBaseContext() ,Html.fromHtml("<font color='#ff0303' ><b>" + "All compulsory fields must be filled." + "</b></font>"), Toast.LENGTH_LONG);
                        redToast.show();
                        return;
                    }else{
                        astri4.setVisibility(View.GONE);
                        payMent= " via PayNow to "+ pNum;
                    }
                }

                float splitting=total/numI;
                /*String formt= String.format("%.2f",);*/

                outputV1.setText("Total Bill: $"+total);
                outputV2.setText("Each Pays: $"+ String.format("%.2f",splitting)+payMent);


            }
        });

        //function clear
        resetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amtIp.getText().clear();
                numIp.getText().clear();
                discIp.setText("0");
                payRG.clearCheck();
                outputV1.setText("Total Bill: $0.00");
                outputV2.setText("Each Pays: $0.00");
            }
        });

    }
}