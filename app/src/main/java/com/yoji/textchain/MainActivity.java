package com.yoji.textchain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Group topRowGroup;
    private Group bottomRowLtrGroup;
    private Group startColumnLtrGroup;
    private Group endColumnLtrGroup;

    private TextView calcMainTxtView;
    private TextView calcSmallTxtView;

    private StringBuilder calcTxtStringBuilder;
    private String calcTxt;
    private String action;
    private boolean sign = false;
    private boolean secondNumFlag = false;

    private View.OnClickListener signBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcTxt = calcMainTxtView.getText().toString();
            if (!calcTxt.equals("") && action == null) {
                sign = !sign;
                calcTxtStringBuilder.setLength(0);
                if (sign) {
                    calcTxtStringBuilder.append("-").append(calcTxt);
                } else {
                    calcTxt = calcTxt.replace("-", "");
                    calcTxtStringBuilder.append(calcTxt);
                }
                calcMainTxtView.setText(calcTxtStringBuilder);
            }
        }
    };

    private View.OnClickListener percentOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcTxt = calcMainTxtView.getText().toString();
            if (!calcTxt.equals("") && action == null) {
                float calcNum = Float.parseFloat(calcTxt);
                float percentNum = calcNum / 100;
                String percentTxt = String.valueOf(percentNum);
                calcTxtStringBuilder.setLength(0);
                calcTxtStringBuilder.append(percentTxt);
                calcMainTxtView.setText(calcTxtStringBuilder);
            }
        }
    };

    private View.OnClickListener dotBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcTxt = calcMainTxtView.getText().toString();
            if (!calcTxt.equals("") && !calcTxt.matches(".*\\..*")) {
                calcTxtStringBuilder.append(getString(R.string.dot));
                calcMainTxtView.setText(calcTxtStringBuilder);
            }
        }
    };

    private View.OnClickListener clearBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clear();
        }
    };

    private View.OnClickListener numBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcTxt = calcMainTxtView.getText().toString();
            int id = v.getId();
            if (calcTxt.equals("0")) {
                calcTxtStringBuilder.deleteCharAt(0);
            }
            if (action != null) {
                calcTxtStringBuilder.setLength(0);
                calcMainTxtView.setText("");
                secondNumFlag = true;
                sign = false;
            }
            switch (id) {
                case R.id.zeroBtnId:
                    calcTxtStringBuilder.append(getString(R.string.zero));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
                case R.id.oneBtnId:
                    calcTxtStringBuilder.append(getString(R.string.one));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
                case R.id.twoBtnId:
                    calcTxtStringBuilder.append(getString(R.string.two));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
                case R.id.threeBtnId:
                    calcTxtStringBuilder.append(getString(R.string.three));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
                case R.id.fourBtnId:
                    calcTxtStringBuilder.append(getString(R.string.four));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
                case R.id.fiveBtnId:
                    calcTxtStringBuilder.append(getString(R.string.five));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
                case R.id.sixBtnId:
                    calcTxtStringBuilder.append(getString(R.string.six));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
                case R.id.sevenBtnId:
                    calcTxtStringBuilder.append(getString(R.string.seven));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
                case R.id.eightBtnId:
                    calcTxtStringBuilder.append(getString(R.string.eight));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
                case R.id.nineBtnId:
                    calcTxtStringBuilder.append(getString(R.string.nine));
                    calcMainTxtView.setText(calcTxtStringBuilder);
                    break;
            }
            action = null;
        }
    };

    private View.OnClickListener actionBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            calcTxt = calcMainTxtView.getText().toString();
            if (!calcTxt.equals("")) {
                if (secondNumFlag) {
                    calcTxt = String.valueOf(equal(calcTxt));
                    calcTxtStringBuilder.setLength(0);
                }else{
                    calcTxt = calcMainTxtView.getText().toString();
                }
                calcTxtStringBuilder.setLength(0);
                switch (id) {
                    case R.id.divBtnId:
                        action = getString(R.string.div);
                        break;
                    case R.id.mulBtnId:
                        action = getString(R.string.mul);
                        break;
                    case R.id.subBtnId:
                        action = getString(R.string.sub);
                        break;
                    case R.id.sumBtnId:
                        action = getString(R.string.sum);
                        break;
                }
                calcTxtStringBuilder.append(calcTxt).append(" ").append(action);
                calcSmallTxtView.setText(calcTxtStringBuilder);
            }
        }
    };

    private View.OnClickListener equalOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcTxt = calcMainTxtView.getText().toString();
            if (secondNumFlag && !calcTxt.equals("")) {
                float result = equal(calcTxt);
                clear();
                calcMainTxtView.setText(String.valueOf(result));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setTextToLtrTxtViews();
    }

    public void init() {
        //TextViews
        calcMainTxtView = findViewById(R.id.calcMainTxtView);
        calcSmallTxtView = findViewById(R.id.calcSmallTxtViewId);

        //Letter groups
        topRowGroup = findViewById(R.id.topRowGroupLtrId);
        bottomRowLtrGroup = findViewById(R.id.bottomRowGroupLtrId);
        startColumnLtrGroup = findViewById(R.id.startColumnGroupLtrId);
        endColumnLtrGroup = findViewById(R.id.endColumnGroupLtrId);

        //Num buttons
        Button zeroBtn = findViewById(R.id.zeroBtnId);
        Button oneBtn = findViewById(R.id.oneBtnId);
        Button twoBtn = findViewById(R.id.twoBtnId);
        Button threeBtn = findViewById(R.id.threeBtnId);
        Button fourBtn = findViewById(R.id.fourBtnId);
        Button fiveBtn = findViewById(R.id.fiveBtnId);
        Button sixBtn = findViewById(R.id.sixBtnId);
        Button sevenBtn = findViewById(R.id.sevenBtnId);
        Button eightBtn = findViewById(R.id.eightBtnId);
        Button nineBtn = findViewById(R.id.nineBtnId);
        Button dotBtn = findViewById(R.id.dotBtnId);
        //Action buttons
        Button clearBtn = findViewById(R.id.clearBtnId);
        Button signBtn = findViewById(R.id.signBtnId);
        Button percentBtn = findViewById(R.id.percentBtnId);
        Button divBtn = findViewById(R.id.divBtnId);
        Button mulBtn = findViewById(R.id.mulBtnId);
        Button subBtn = findViewById(R.id.subBtnId);
        Button sumBtn = findViewById(R.id.sumBtnId);
        Button equalBtn = findViewById(R.id.equalBtnId);

        //Num buttons On Click Listeners
        zeroBtn.setOnClickListener(numBtnOnClickListener);
        oneBtn.setOnClickListener(numBtnOnClickListener);
        twoBtn.setOnClickListener(numBtnOnClickListener);
        threeBtn.setOnClickListener(numBtnOnClickListener);
        fourBtn.setOnClickListener(numBtnOnClickListener);
        fiveBtn.setOnClickListener(numBtnOnClickListener);
        sixBtn.setOnClickListener(numBtnOnClickListener);
        sevenBtn.setOnClickListener(numBtnOnClickListener);
        eightBtn.setOnClickListener(numBtnOnClickListener);
        nineBtn.setOnClickListener(numBtnOnClickListener);
        dotBtn.setOnClickListener(dotBtnOnClickListener);
        //Action buttons On Click Listeners
        clearBtn.setOnClickListener(clearBtnOnClickListener);
        signBtn.setOnClickListener(signBtnOnClickListener);
        percentBtn.setOnClickListener(percentOnClickListener);
        divBtn.setOnClickListener(actionBtnOnClickListener);
        mulBtn.setOnClickListener(actionBtnOnClickListener);
        subBtn.setOnClickListener(actionBtnOnClickListener);
        sumBtn.setOnClickListener(actionBtnOnClickListener);
        equalBtn.setOnClickListener(equalOnClickListener);

        calcTxtStringBuilder = new StringBuilder();
    }

    public float equal(String calcTxt){
        float result = 0;

        String calcSmallTxt = calcSmallTxtView.getText().toString();
        String[] calcSmallTxtSplit = calcSmallTxt.split(" ");
        float firstNum = Float.parseFloat(calcSmallTxtSplit[0]);
        action = calcSmallTxtSplit[1];
        float secondNum = Float.parseFloat(calcTxt);

        switch (action){
            case "÷":
                if (secondNum !=0) {
                    result = firstNum / secondNum;
                } else {
                    clear();
                    Toast.makeText(this, R.string.division_by_zero_message, Toast.LENGTH_SHORT).show();
                }
                break;
            case "×":
                result = firstNum * secondNum;
                break;
            case "+":
                result = firstNum + secondNum;
                break;
            case "-":
                result = firstNum - secondNum;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
        return result;
    }

    public void clear(){
        calcTxtStringBuilder.setLength(0);
        calcSmallTxtView.setText("");
        calcMainTxtView.setText("");
        action = null;
        sign = false;
        secondNumFlag = false;
    }

    public void setTextToLtrTxtViews(){
        setTextToGroup(topRowGroup);
        setTextToGroup(bottomRowLtrGroup);
        setTextToGroup(startColumnLtrGroup);
        setTextToGroup(endColumnLtrGroup);
    }

    public void setTextToGroup(Group group){
        int[] groupLtrId = group.getReferencedIds();

        String surname = getString(R.string.my_surname);
        String[] surnameLtr = surname.split("");
        int length = Math.min((surnameLtr.length - 1), groupLtrId.length);

        TextView[] groupLtr = new TextView[length];
        //Init TextViews of group
        for (int i=0; i < length; i++){
            groupLtr[i] = findViewById(groupLtrId[i]);
            groupLtr[i].setText(surnameLtr[i+1]);
        }

        int[] id = new int[length];
        System.arraycopy(groupLtrId, 0, id, 0, length);

        group.setReferencedIds(id);
    }
}
