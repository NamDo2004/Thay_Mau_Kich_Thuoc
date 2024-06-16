package com.example.doi_mau_kich_thuoc;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private View View1, View2, View3;
    private Button btnSave, btnLoad;
    private View First_Selected = null;
    private void findviews(){
        View1 = findViewById(R.id.View1);
        View2 = findViewById(R.id.View2);
        View3 = findViewById(R.id.View3);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findviews();
        View1.setOnClickListener(View_Click);
        View2.setOnClickListener(View_Click);
        View3.setOnClickListener(View_Click);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("my_save", MODE_PRIVATE);

                SharedPreferences.Editor editor =sharedPreferences.edit();

                //Luu mau sac View
                editor.putInt("view1_color", ((ColorDrawable) View1.getBackground()).getColor());
                editor.putInt("view2_color", ((ColorDrawable) View2.getBackground()).getColor());
                editor.putInt("view3_color", ((ColorDrawable) View3.getBackground()).getColor());

                //Luu kich thuoc
                ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) View1.getLayoutParams();
                ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) View2.getLayoutParams();
                ConstraintLayout.LayoutParams params3 = (ConstraintLayout.LayoutParams) View3.getLayoutParams();

                editor.putInt("view1_width", params1.width);
                editor.putInt("view1_height", params1.height);

                editor.putInt("view2_width", params2.width);
                editor.putInt("view2_height", params2.height);

                editor.putInt("view3_width", params3.width);
                editor.putInt("view3_height", params3.height);

                editor.apply();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("my_save", MODE_PRIVATE);

                //Tai mau sac
                int color_view1 = sharedPreferences.getInt("view1_color", getResources().getColor(R.color.yellow));
                int color_view2 = sharedPreferences.getInt("view2_color", getResources().getColor(R.color.yellow));
                int color_view3 = sharedPreferences.getInt("view3_color", getResources().getColor(R.color.yellow));

                View1.setBackgroundColor(color_view1);
                View2.setBackgroundColor(color_view2);
                View3.setBackgroundColor(color_view3);

                //Tai kich thuoc
                ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) View1.getLayoutParams();
                ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) View2.getLayoutParams();
                ConstraintLayout.LayoutParams params3 = (ConstraintLayout.LayoutParams) View3.getLayoutParams();

                params1.width = sharedPreferences.getInt("view1_width", params1.width);
                params1.height = sharedPreferences.getInt("view1_height", params1.height);

                params2.width = sharedPreferences.getInt("view2_width", params2.width);
                params2.height = sharedPreferences.getInt("view2_height", params2.height);

                params3.width = sharedPreferences.getInt("view3_width", params3.width);
                params3.height = sharedPreferences.getInt("view3_height", params3.height);

                View1.setLayoutParams(params1);
                View2.setLayoutParams(params2);
                View3.setLayoutParams(params3);
            }
        });
    }
    private final View.OnClickListener View_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(First_Selected == null){
                //Luu View dau tien duoc click
                First_Selected = v;
            }
            else{
                //Hoan doi thuoc tinh View dau tien va view hien tai
                SWAP(First_Selected, v);
                //Reset bien luu View
                First_Selected = null;
            }
        }
    };

    //Ham hoan doi mau, kich thuoc
    private void SWAP(View A, View B){
        //Lấy màu sắc hiện tại
        ColorDrawable colorDrawable_A = (ColorDrawable) A.getBackground();
        ColorDrawable colorDrawable_B = (ColorDrawable) B.getBackground();

        int colorA = colorDrawable_A.getColor();
        int colorB = colorDrawable_B.getColor();

        A.setBackgroundColor(colorA);
        B.setBackgroundColor(colorB);

        //Hoan doi kich thuoc
        ConstraintLayout.LayoutParams params_A = (ConstraintLayout.LayoutParams) A.getLayoutParams();
        ConstraintLayout.LayoutParams params_B = (ConstraintLayout.LayoutParams) B.getLayoutParams();

        int Width_A = params_A.width;
        int Height_A = params_A.height;

        params_A.width = params_B.width;
        params_A.height = params_B.height;

        params_B.width = Width_A;
        params_B.height = Height_A;

        A.setLayoutParams(params_A);
        B.setLayoutParams(params_B);
    }
}