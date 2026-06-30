package com.example.pr13fedotovgame;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView car1, car2;
    Button btnCar1, btnStart, btnReboot;
    TextView tvWinner;

    boolean gameActive = false;
    float car1X, car2X;
    float finishX;
    float startX;

    Handler handler = new Handler();
    Runnable autoMoveRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        car1 = findViewById(R.id.car1);
        car2 = findViewById(R.id.car2);
        btnCar1 = findViewById(R.id.btnCar1);
        btnStart = findViewById(R.id.btnStart);
        btnReboot = findViewById(R.id.reboot);
        tvWinner = findViewById(R.id.tvWinner);

        btnCar1.setText("ГАЗ!");
        btnStart.setText("СТАРТ");
        btnReboot.setText("ЗАНОВО");

        car1.post(new Runnable() {
            @Override
            public void run() {
                startX = car1.getX();
                View finishLine = findViewById(R.id.finishLine);
                finishX = finishLine.getX() - car1.getWidth();
                car1X = startX;
                car2X = startX;
            }
        });

        btnCar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameActive) {
                    moveCar1();
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        btnReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allReboot();
            }
        });

        autoMoveRunnable = new Runnable() {
            @Override
            public void run() {
                if (gameActive) {
                    moveCar2Auto();
                    handler.postDelayed(this, 100);
                }
            }
        };
    }

    private void startGame() {
        gameActive = true;
        tvWinner.setText("");
        handler.post(autoMoveRunnable);
    }

    private void allReboot() {
        gameActive = false;
        handler.removeCallbacks(autoMoveRunnable);

        car1.setX(startX);
        car2.setX(startX);

        car1X = startX;
        car2X = startX;

        tvWinner.setText("");
    }

    private void moveCar1() {
        if (!gameActive) return;

        car1.setX(car1.getX() + 70);
        car1X = car1.getX();

        checkWin();
    }

    private void moveCar2Auto() {
        if (!gameActive) return;

        car2.setX(car2.getX() + 25);
        car2X = car2.getX();

        checkWin();
    }

    private void checkWin() {
        if (car1X >= finishX && car2X >= finishX) {
            tvWinner.setText("НИЧЬЯ!");
            gameActive = false;
            handler.removeCallbacks(autoMoveRunnable);
        } else if (car1X >= finishX) {
            tvWinner.setText("ТЫ ПОБЕДИЛ!");
            gameActive = false;
            handler.removeCallbacks(autoMoveRunnable);
        } else if (car2X >= finishX) {
            tvWinner.setText("ЗЕЛЕНАЯ МАШИНА ПОБЕДИЛА!");
            gameActive = false;
            handler.removeCallbacks(autoMoveRunnable);
        }
    }
}
